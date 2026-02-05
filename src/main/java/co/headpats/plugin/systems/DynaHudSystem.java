package co.headpats.plugin.systems;

import co.headpats.plugin.classes.HudVisibilityManager;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class DynaHudSystem extends EntityTickingSystem<EntityStore> {
    private final HudVisibilityManager hudVisibilityManager;

    public DynaHudSystem() {
        hudVisibilityManager = new HudVisibilityManager();
    }

    @Override
    public void tick(
            float dt,
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer
    ) {
        hudVisibilityManager.update(dt, index, archetypeChunk, store);
        hudVisibilityManager.monitorHealthBar();
        hudVisibilityManager.monitorStaminaBar();
        hudVisibilityManager.monitorManaBar();
        hudVisibilityManager.monitorHotBar();
        hudVisibilityManager.monitorReticle();
        hudVisibilityManager.monitorCompass();
        hudVisibilityManager.monitorAmmo();
        hudVisibilityManager.monitorInputBindings();
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }

}
