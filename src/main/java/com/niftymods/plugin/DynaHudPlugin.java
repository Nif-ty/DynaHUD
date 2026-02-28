package com.niftymods.plugin;

import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.SystemGroup;
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
import com.niftymods.plugin.systems.*;

import javax.annotation.Nonnull;

public class DynaHudPlugin extends JavaPlugin {

    private static DynaHudPlugin instance;
    private final Config<ServerConfig> serverConfig = this.withConfig("server", ServerConfig.CODEC);
    private ComponentType<EntityStore, DynaHudComponent> dynaHudComponentType;
    private SystemGroup<EntityStore> hudConditionGroup;
    private SystemGroup<EntityStore> hudEventGroup;
    private SystemGroup<EntityStore> hudDurationGroup;


    public DynaHudPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        super.setup();
        serverConfig.save();
        ComponentRegistryProxy<EntityStore> entityStoreRegistry = this.getEntityStoreRegistry();

        // Register Groups
        this.hudConditionGroup = entityStoreRegistry.registerSystemGroup();
        this.hudEventGroup = entityStoreRegistry.registerSystemGroup();
        this.hudDurationGroup = entityStoreRegistry.registerSystemGroup();

        // Register Components
        this.dynaHudComponentType = entityStoreRegistry.registerComponent(
                DynaHudComponent.class, DynaHudComponent::new);

        // Register Events
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, DynaHudSetupEvent::onPlayerReady);

        // Register Systems
        entityStoreRegistry.registerSystem(new HudConditionSystem());
        entityStoreRegistry.registerSystem(new CombatDamageEventSystem());
        entityStoreRegistry.registerSystem(new HudVisibilityDurationSystem());
        entityStoreRegistry.registerSystem(new DynaHudSystem());

        // Register Commands
        this.getCommandRegistry().registerCommand(new DynaHudCommand());

    }

    public static DynaHudPlugin get() {
        return instance;
    }

    public SystemGroup<EntityStore> getHudConditionGroup() {
        return hudConditionGroup;
    }

    public SystemGroup<EntityStore> getHudEventGroup() {
        return hudEventGroup;
    }

    public SystemGroup<EntityStore> getHudDurationGroup() {
        return hudDurationGroup;
    }

    public Config<ServerConfig> getServerConfig() {
        return serverConfig;
    }

    public ComponentType<EntityStore, DynaHudComponent> getDynaHudComponentType() {
        return dynaHudComponentType;
    }

}
