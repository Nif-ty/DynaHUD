package com.niftymods.plugin;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.niftymods.plugin.commands.DynaHudCommand;
import com.niftymods.plugin.components.DynaHudComponent;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.niftymods.plugin.config.ServerConfig;
import com.niftymods.plugin.events.DynaHudSetupEvent;
import com.niftymods.plugin.systems.DynaHudSystem;

import javax.annotation.Nonnull;

public class DynaHudPlugin extends JavaPlugin {

    private static DynaHudPlugin instance;
    private ComponentType<EntityStore, DynaHudComponent> dynaHudComponentType;
    private final Config<ServerConfig> serverConfig = this.withConfig("server", ServerConfig.CODEC);

    public DynaHudPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        super.setup();
        serverConfig.save();

        // Register Components
        this.dynaHudComponentType = this.getEntityStoreRegistry().registerComponent(
                DynaHudComponent.class, DynaHudComponent::new);

        // Register Events
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, DynaHudSetupEvent::onPlayerReady);

        // Register Systems
        this.getEntityStoreRegistry().registerSystem(new DynaHudSystem());


        // Register Commands
        this.getCommandRegistry().registerCommand(new DynaHudCommand());

        /*
        this.getEntityStoreRegistry().registerSystem(new DynaHudStartSystem());
        this.settingsDataComponent = this.getEntityStoreRegistry().registerComponent(
                SettingsData.class, "SettingsDataComponent", SettingsData.CODEC);
        this.delayTimeComponent = this.getEntityStoreRegistry().registerComponent(
                DelayTimeComponent.class, DelayTimeComponent::new);
        this.getEntityStoreRegistry().registerSystem(new HudVisibilitySystem());
        this.getCommandRegistry().registerCommand(new DynaHudCommand());
         */
    }

    public static DynaHudPlugin getInstance() {
        return instance;
    }

    public Config<ServerConfig> getServerConfig() {
        return serverConfig;
    }

    public ComponentType<EntityStore, DynaHudComponent> getDynaHudComponentType() {
        return dynaHudComponentType;
    }

}
