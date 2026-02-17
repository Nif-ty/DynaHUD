package com.niftymods.plugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.entity.damage.DamageDataComponent;
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.niftymods.plugin.components.DynaHudComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class CombatDamageEventSystem extends EntityEventSystem<EntityStore, Damage> {

    public CombatDamageEventSystem() {
        super(Damage.class);
    }

    @Override
    public void handle(
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
            @NonNullDecl Damage damage
    ) {
        // Filter only damage caused by entity
        if (!(damage.getSource() instanceof Damage.EntitySource source)) return;

        // If player damages an entity that is not invulnerable:
        Ref<EntityStore> attackerRef = source.getRef();
        boolean isSourceInvulnerable = archetypeChunk.getComponent(index, Invulnerable.getComponentType()) != null;
        if(attackerRef.isValid() && !isSourceInvulnerable) {
            DynaHudComponent attackerDynaHudComponent = store.getComponent(attackerRef, DynaHudComponent.getComponentType());
            if(attackerDynaHudComponent != null) {
                attackerDynaHudComponent.getOnDamageDelayTime().resetHideTime();
            }
        }

        // If player gets damaged by entity:
        DynaHudComponent recieverDynaHudComponent = archetypeChunk.getComponent(index, DynaHudComponent.getComponentType());
        if (recieverDynaHudComponent != null) {
            recieverDynaHudComponent.getOnDamageDelayTime().resetHideTime();
        }

    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return DamageDataComponent.getComponentType();
    }
}
