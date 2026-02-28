package com.niftymods.plugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.Order;
import com.hypixel.hytale.component.dependency.SystemGroupDependency;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class HudVisibilityDurationSystem extends EntityTickingSystem<EntityStore>  {

    @Nonnull
    private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
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
        DynaHudComponent dynaHudComponent = store.getComponent(ref, DYNAHUD_COMPONENT_TYPE);
        assert dynaHudComponent != null;

        // PlayerStatCondition
        dynaHudComponent.getHealthBarTickTimer().process(dt);
        dynaHudComponent.getStaminaBarTickTimer().process(dt);
        dynaHudComponent.getManaBarTickTimer().process(dt);
        dynaHudComponent.getAmmoBarTickTimer().process(dt);

        // HotbarSlotEvent
        dynaHudComponent.getHotbarTickTimer().process(dt);

        // CombatEvent
        dynaHudComponent.getStatusBarCombatTickTimer().process(dt);
        dynaHudComponent.getHotbarCombatTickTimer().process(dt);
        dynaHudComponent.getReticleCombatTickTimer().process(dt);
    }

    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return DynaHudPlugin.get().getHudDurationGroup();
    }

    @Nonnull
    public Set<Dependency<EntityStore>> getDependencies() {
        return Set.of(
                new SystemGroupDependency<>(Order.AFTER, DynaHudPlugin.get().getHudConditionGroup()),
                new SystemGroupDependency<>(Order.AFTER, DynaHudPlugin.get().getHudEventGroup())
        );
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return DynaHudComponent.getComponentType();
    }

}
