package com.niftymods.plugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import com.niftymods.plugin.config.PlayerConfig;
import com.niftymods.plugin.utils.TickTimer;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HudConditionSystem extends EntityTickingSystem<EntityStore> {

    @Nonnull
    private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
    @Nonnull
    private static final ComponentType<EntityStore, EntityStatMap> ENTITY_STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();
    private final ComponentType<EntityStore, DynaHudComponent> DYNAHUD_COMPONENT_TYPE = DynaHudComponent.getComponentType();

    @Override
    public void tick(
            float dt,
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        // Variable setup
        Player player = archetypeChunk.getComponent(index, PLAYER_COMPONENT_TYPE);
        assert player != null;
        Ref<EntityStore> ref = player.getReference();
        assert ref != null;
        EntityStatMap statMap = archetypeChunk.getComponent(index, ENTITY_STAT_MAP_COMPONENT_TYPE);
        DynaHudComponent dynaHudComponent = store.getComponent(ref, DYNAHUD_COMPONENT_TYPE);
        assert dynaHudComponent != null;
        PlayerConfig config = dynaHudComponent.getPlayerConfig();

        handleStatusBar(statMap, config, dynaHudComponent);
        handleAmmoBar(statMap, config, dynaHudComponent);
        handleHotbar(player, config, dynaHudComponent);
    }

    private void handleStatusBar(EntityStatMap statMap, PlayerConfig config, DynaHudComponent dynaHudComponent) {
        boolean isEnabled = config.getStatusBarTrigger().equalsIgnoreCase("Both") ||
                config.getStatusBarTrigger().equalsIgnoreCase("Threshold");
        if (isEnabled) {
            // Health
            handleStatType(
                    statMap,
                    DefaultEntityStatTypes.getHealth(),
                    dynaHudComponent.getHealthBarTickTimer(),
                    config.getHealthThreshold()
            );

            // Stamina
            handleStatType(
                    statMap,
                    DefaultEntityStatTypes.getStamina(),
                    dynaHudComponent.getStaminaBarTickTimer(),
                    config.getStaminaThreshold()
            );

            // Mana
            handleStatType(
                    statMap,
                    DefaultEntityStatTypes.getMana(),
                    dynaHudComponent.getManaBarTickTimer(),
                    config.getManaThreshold()
            );
        }
    }

    private void handleAmmoBar(EntityStatMap statMap, PlayerConfig config, DynaHudComponent dynaHudComponent) {
        boolean isEnabled = config.getStatusBarTrigger().equalsIgnoreCase("Reload");
        if (isEnabled) {
            handleStatType(
                    statMap,
                    DefaultEntityStatTypes.getAmmo(),
                    dynaHudComponent.getAmmoBarTickTimer(),
                    -1.0f
            );
        }
    }

    private void handleStatType(
            EntityStatMap statMap,
            int statMapIndex,
            TickTimer hudTickTimer,
            float configThreshold
    ) {
        // Setup stat and condition
        EntityStatValue stat = statMap.get(statMapIndex);
        assert stat != null;
        float threshold = configThreshold == -1.0f ? stat.getMax() : (configThreshold * 0.01f) * stat.getMax();
        boolean statBelowLimit = stat.get() < threshold;

        if (!statBelowLimit) return;
        hudTickTimer.start();
    }

    private void handleHotbar(
            Player player,
            PlayerConfig config,
            DynaHudComponent dynaHudComponent
    ) {
        boolean isEnabled = config.getHotbarTrigger().equalsIgnoreCase("Both") ||
                config.getHotbarTrigger().equalsIgnoreCase("Change");
        if (isEnabled) {
            byte activeSlot = player.getInventory().getActiveHotbarSlot();
            if (dynaHudComponent.getActiveSlot() != activeSlot) {
                dynaHudComponent.setActiveSlot(activeSlot);
                dynaHudComponent.getHotbarTickTimer().start();
            }
        }
    }

    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return DynaHudPlugin.get().getHudConditionGroup();
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return DynaHudComponent.getComponentType();
    }

}
