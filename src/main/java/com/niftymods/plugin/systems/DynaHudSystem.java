package com.niftymods.plugin.systems;

import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import com.niftymods.plugin.utils.DynaHudManager;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class DynaHudSystem extends EntityTickingSystem<EntityStore> {

    private final DynaHudManager dynaHudManager;

    public DynaHudSystem() {
        dynaHudManager = new DynaHudManager();
    }

    @Override
    public void tick(
            float dt,
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        dynaHudManager.update(dt, index, archetypeChunk, store);
        dynaHudManager.monitorHealthBar();
        dynaHudManager.monitorStaminaBar();
        dynaHudManager.monitorManaBar();
        dynaHudManager.monitorAmmo();
        dynaHudManager.monitorHotbar();
        //dynaHudManager.monitorReticle();

    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return DynaHudComponent.getComponentType();
    }

}
