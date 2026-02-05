package co.headpats.plugin;

import co.headpats.plugin.commands.DynaHudCommand;
import co.headpats.plugin.components.DelayTimeComponent;
import co.headpats.plugin.components.SettingsData;
import co.headpats.plugin.systems.DynaHudStartSystem;
import co.headpats.plugin.systems.DynaHudSystem;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class DynaHudPlugin extends JavaPlugin {

    private static DynaHudPlugin instance;
    private ComponentType<EntityStore, DelayTimeComponent> delayTimeComponent;
    private ComponentType<EntityStore, SettingsData> settingsDataComponent;

    public DynaHudPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        super.setup();
        this.getEntityStoreRegistry().registerSystem(new DynaHudStartSystem());
        this.settingsDataComponent = this.getEntityStoreRegistry().registerComponent(
                SettingsData.class, "SettingsDataComponent", SettingsData.CODEC);
        this.delayTimeComponent = this.getEntityStoreRegistry().registerComponent(
                DelayTimeComponent.class,DelayTimeComponent::new);
        this.getEntityStoreRegistry().registerSystem(new DynaHudSystem());
        this.getCommandRegistry().registerCommand(new DynaHudCommand());
    }

    public ComponentType<EntityStore, DelayTimeComponent> getDelayTimeComponentType() {
        return delayTimeComponent;
    }

    public ComponentType<EntityStore, SettingsData> getSettingsDataComponentType() {
        return this.settingsDataComponent;
    }

    public static DynaHudPlugin get() {
        return instance;
    }
}
