package com.niftymods.plugin.events;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import com.niftymods.plugin.config.PlayerConfig;
import com.niftymods.plugin.config.ServerConfig;

public class DynaHudSetupEvent {

    public static void onPlayerReady(PlayerReadyEvent event) {
        ComponentType<EntityStore, DynaHudComponent> dynaHudComponentType = DynaHudPlugin.getInstance().getDynaHudComponentType();
        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());


        // Create player config file
        if (playerRef != null && !PlayerConfig.hasConfigFile(playerRef.getUuid().toString())) {
            PlayerConfig playerConfig = PlayerConfig.getFromPlayerId(playerRef.getUuid().toString());
            Config<ServerConfig> config = DynaHudPlugin.getInstance().getServerConfig();
            ServerConfig serverConfig = config.get();

            if (serverConfig.isDisabledByDefault()) {
                playerConfig.setPreset("Disable");
                playerConfig.setStatusBarTrigger("Disable");
                playerConfig.setHotbarTrigger("Disable");
                playerConfig.setReticleTrigger("Disable");
                playerConfig.setHideCompass(false);
                playerConfig.setHideAmmo(false);
                playerConfig.setHideInputBindings(false);
                playerConfig.save();
            }
        }

        boolean hasDynaHudComponent = store.getComponent(ref, dynaHudComponentType) != null;
        if (!hasDynaHudComponent) {
            DynaHudComponent dynaHudComponent = new DynaHudComponent();
            if (playerRef != null) {
                Player player = event.getPlayer();
                PlayerConfig playerConfig = PlayerConfig.getFromPlayerId(playerRef.getUuid().toString());
                HudManager hudManager = player.getHudManager();

                dynaHudComponent.setPlayerConfig(playerConfig);
                if (playerConfig.isHideCompass())
                    hudManager.hideHudComponents(playerRef, HudComponent.Compass);
                if (playerConfig.isHideInputBindings())
                    hudManager.hideHudComponents(playerRef, HudComponent.InputBindings);
            }

            store.addComponent(ref, dynaHudComponentType, dynaHudComponent);
        }
    }

}
