package co.headpats.plugin.commands;

import co.headpats.plugin.pages.SettingsPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.permissions.HytalePermissions;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class DynaHudCommand extends AbstractPlayerCommand {

    public DynaHudCommand() {
        super("dynahud", "Opens DynaHUD settings UI", false);
        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected void execute(
            CommandContext commandContext,
            Store<EntityStore> store,
            Ref<EntityStore> ref,
            PlayerRef playerRef,
            World world
    ) {
        Player player = store.getComponent(ref, Player.getComponentType());
        SettingsPage page = new SettingsPage(playerRef);
        assert player != null;
        player.getPageManager().openCustomPage(ref, store, page);
    }
}
