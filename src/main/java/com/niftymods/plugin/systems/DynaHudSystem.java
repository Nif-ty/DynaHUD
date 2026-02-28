package com.niftymods.plugin.systems;

import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.Order;
import com.hypixel.hytale.component.dependency.SystemGroupDependency;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import com.niftymods.plugin.config.PlayerConfig;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.Set;

public class DynaHudSystem extends EntityTickingSystem<EntityStore> {

    @Nonnull
    private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
    private final ComponentType<EntityStore, DynaHudComponent> DYNAHUD_COMPONENT_TYPE = DynaHudPlugin.get().getDynaHudComponentType();

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
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        assert playerRef != null;
        DynaHudComponent dynaHudComponent = store.getComponent(ref, DYNAHUD_COMPONENT_TYPE);
        assert dynaHudComponent != null;
        HudManager hudManager = player.getHudManager();
        PlayerConfig config = dynaHudComponent.getPlayerConfig();

        handleStatusBar(config, dynaHudComponent, hudManager, playerRef);
        handleHotbar(config, dynaHudComponent, hudManager, playerRef);
        handleAmmoBar(config, dynaHudComponent, hudManager, playerRef);
        handleReticle(config, dynaHudComponent, hudManager, playerRef);
    }

    private void handleStatusBar(
            PlayerConfig config,
            DynaHudComponent dynaHudComponent,
            HudManager hudManager,
            PlayerRef playerRef
    ) {
        boolean disabled = config.getStatusBarTrigger().equalsIgnoreCase("Disable");
        boolean inCombat = dynaHudComponent.getStatusBarCombatTickTimer().isRunning();
        // Health
        if (disabled || inCombat || dynaHudComponent.getHealthBarTickTimer().isRunning()) {
            showHudComponent(hudManager, playerRef, HudComponent.Health);
        } else {
            hideHudComponent(hudManager, playerRef, HudComponent.Health);
        }
        // Stamina
        if (disabled || inCombat || dynaHudComponent.getStaminaBarTickTimer().isRunning()) {
            showHudComponent(hudManager, playerRef, HudComponent.Stamina);
        } else {
            hideHudComponent(hudManager, playerRef, HudComponent.Stamina);
        }
        // Mana
        if (disabled || inCombat || dynaHudComponent.getManaBarTickTimer().isRunning()) {
            showHudComponent(hudManager, playerRef, HudComponent.Mana);
        } else {
            hideHudComponent(hudManager, playerRef, HudComponent.Mana);
        }
    }

    private void handleHotbar(
            PlayerConfig config,
            DynaHudComponent dynaHudComponent,
            HudManager hudManager,
            PlayerRef playerRef
    ) {
        boolean disabled = config.getHotbarTrigger().equalsIgnoreCase("Disable");
        boolean inCombat = dynaHudComponent.getHotbarCombatTickTimer().isRunning();
        if (disabled || inCombat || dynaHudComponent.getHotbarTickTimer().isRunning()) {
            showHudComponent(hudManager, playerRef, HudComponent.Hotbar);
        } else {
            hideHudComponent(hudManager, playerRef, HudComponent.Hotbar);
        }
    }

    private void handleAmmoBar(
            PlayerConfig config,
            DynaHudComponent dynaHudComponent,
            HudManager hudManager,
            PlayerRef playerRef
    ) {
        boolean disabled = config.getAmmoTrigger().equalsIgnoreCase("Disable");
        if (disabled || dynaHudComponent.getAmmoBarTickTimer().isRunning()) {
            showHudComponent(hudManager, playerRef, HudComponent.AmmoIndicator);
        } else {
            hideHudComponent(hudManager, playerRef, HudComponent.AmmoIndicator);
        }
    }

    private void handleReticle(
            PlayerConfig config,
            DynaHudComponent dynaHudComponent,
            HudManager hudManager,
            PlayerRef playerRef
    ) {
        boolean disabled = config.getReticleTrigger().equalsIgnoreCase("Disable");
        if (disabled || dynaHudComponent.getReticleCombatTickTimer().isRunning()) {
            showHudComponent(hudManager, playerRef, HudComponent.Reticle);
        } else {
            hideHudComponent(hudManager, playerRef, HudComponent.Reticle);
        }
    }

    private void showHudComponent(HudManager hudManager, PlayerRef playerRef, HudComponent hudComponent) {
        if (!hudManager.getVisibleHudComponents().contains(hudComponent)) {
            hudManager.showHudComponents(playerRef, hudComponent);
        }
    }

    private void hideHudComponent(HudManager hudManager, PlayerRef playerRef,HudComponent hudComponent) {
        if (hudManager.getVisibleHudComponents().contains(hudComponent)) {
            hudManager.hideHudComponents(playerRef, hudComponent);
        }
    }

    @Nonnull
    public Set<Dependency<EntityStore>> getDependencies() {
        return Set.of(
                new SystemGroupDependency<>(Order.AFTER, DynaHudPlugin.get().getHudDurationGroup())
        );
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return DynaHudComponent.getComponentType();
    }

}
