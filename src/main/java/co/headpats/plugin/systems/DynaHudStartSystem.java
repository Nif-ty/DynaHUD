package co.headpats.plugin.systems;

import co.headpats.plugin.DynaHudPlugin;
import co.headpats.plugin.components.DelayTimeComponent;
import co.headpats.plugin.components.SettingsData;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class DynaHudStartSystem extends RefSystem<EntityStore> {

    @Override
    public void onEntityAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl AddReason addReason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        DelayTimeComponent newDelayTimeComponent = new DelayTimeComponent();
        DelayTimeComponent delayTimeComponent = commandBuffer.getComponent(ref, DynaHudPlugin.get().getDelayTimeComponentType());
        SettingsData newSettingsData = new SettingsData();
        SettingsData settingsData = commandBuffer.getComponent(ref, DynaHudPlugin.get().getSettingsDataComponentType());

        boolean hasPlayerComponent = commandBuffer.getComponent(ref, Player.getComponentType()) != null;
        boolean hasDelayTimeComponent = delayTimeComponent != null;
        boolean hasSettingsData = settingsData != null;

        if (hasPlayerComponent && !hasDelayTimeComponent) {
            commandBuffer.addComponent(ref, DynaHudPlugin.get().getDelayTimeComponentType(), newDelayTimeComponent);
        }
        if (hasPlayerComponent && !hasSettingsData) {
            commandBuffer.addComponent(ref, DynaHudPlugin.get().getSettingsDataComponentType(), newSettingsData);
        }
    }

    @Override
    public void onEntityRemove(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl RemoveReason removeReason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        boolean hasDelayTimeComponent = commandBuffer.getComponent(ref, DynaHudPlugin.get().getDelayTimeComponentType()) != null;
        boolean hasSettingsData = commandBuffer.getComponent(ref, DynaHudPlugin.get().getSettingsDataComponentType()) != null;
        if (!hasDelayTimeComponent) {
            commandBuffer.removeComponent(ref, DynaHudPlugin.get().getDelayTimeComponentType());
        }
        if (!hasSettingsData) {
            commandBuffer.removeComponent(ref, DynaHudPlugin.get().getSettingsDataComponentType());
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
