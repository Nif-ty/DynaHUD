package com.niftymods.plugin.commands;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import com.niftymods.plugin.config.PlayerConfig;
import com.niftymods.plugin.config.ServerConfig;
import com.niftymods.plugin.pages.SettingsPage;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;


public class DynaHudCommand extends AbstractPlayerCommand {

    private final ComponentType<EntityStore, DynaHudComponent> dynaHudComponentType = DynaHudPlugin.getInstance().getDynaHudComponentType();
    private final OptionalArg<Boolean> disabledByDefaultArg;

    public DynaHudCommand() {
        super("dynahud", "Opens DynaHUD settings UI.", false);
        this.setPermissionGroup(GameMode.Adventure);
        this.disabledByDefaultArg = withOptionalArg("disabledByDefault", "Disable/enable server default by adding 'true' or 'false' as argument.", ArgTypes.BOOLEAN);
    }

    @Override
    protected void execute(
            @NonNullDecl CommandContext context,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world
    ) {
        DynaHudPlugin dynaHudPlugin = DynaHudPlugin.getInstance();
        DynaHudComponent dynaHudComponent = store.getComponent(ref, dynaHudComponentType);
        assert dynaHudComponent != null;
        Player player = store.getComponent(ref, Player.getComponentType());
        assert player != null;
        Boolean defaultFlag = disabledByDefaultArg.get(context);

        // Handle server default
        if (defaultFlag != null) {
            Config<ServerConfig> config = dynaHudPlugin.getServerConfig();
            config.get().setDisabledByDefault(defaultFlag);
            config.save();
            if (defaultFlag) {
                player.sendMessage(Message.raw("Disabled DynaHUD Default: new players will have the mod disabled by default and must use DynaHUD settings to re-enable individually."));
            } else {
                player.sendMessage(Message.raw("Enabled DynaHUD Default: new players will have the mod enabled by default."));
            }
            return;
        }

        // Handle settings UI
        PlayerConfig playerConfig = dynaHudComponent.getPlayerConfig();

        SettingsPage page = new SettingsPage(playerRef, playerConfig);
        player.getPageManager().openCustomPage(ref, store, page);
    }
}
