package com.niftymods.plugin.utils;

import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.components.DynaHudComponent;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.niftymods.plugin.config.PlayerConfig;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class DynaHudManager {
    @Nonnull
    private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
    @Nonnull
    private static final ComponentType<EntityStore, EntityStatMap> ENTITY_STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();
    private final ComponentType<EntityStore, DynaHudComponent> COMPONENT_TYPE = DynaHudComponent.getComponentType();

    private float deltaTime;
    private Player player;
    private PlayerRef playerRef;
    private EntityStatMap statMap;
    private HudManager hudManager;
    private DynaHudComponent dynaHudComponent;
    private PlayerConfig config;

    public void update(
            float deltaTime,
            int index,
            @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
            @NonNullDecl Store<EntityStore> store
    ) {
        this.deltaTime = deltaTime;
        this.player = archetypeChunk.getComponent(index, PLAYER_COMPONENT_TYPE);
        assert player != null;
        Ref<EntityStore> ref = player.getReference();
        assert ref != null;
        this.playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        this.statMap = archetypeChunk.getComponent(index, ENTITY_STAT_MAP_COMPONENT_TYPE);
        this.hudManager = player.getHudManager();
        this.dynaHudComponent = store.getComponent(ref, COMPONENT_TYPE);
        assert dynaHudComponent != null;
        this.config = dynaHudComponent.getPlayerConfig();
    }

    public void monitorHealthBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Health);
        if (config.getStatusBarTrigger().equalsIgnoreCase("Disable")) {
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Health);
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getHealth());
        assert stat != null;

        VisibilityDelayTime visibilityDelay = dynaHudComponent.getHealthDelayTime();
        float threshold = (config.getHealthThreshold() * 0.01f) * stat.getMax();

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = config.getStatusBarDelay()[0]; // Threshold hide delay

        if (statBelowLimit) {
            visibilityDelay.resetHideTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Health);
        } else if (hasHud) {
            visibilityDelay.incrementHideTime(deltaTime);
            if (visibilityDelay.getHideTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Health);
                visibilityDelay.resetHideTime();
            }
        }
    }

    public void monitorStaminaBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Stamina);
        if (config.getStatusBarTrigger().equalsIgnoreCase("Disable")) {
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Stamina);
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getStamina());
        assert stat != null;

        VisibilityDelayTime visibilityDelay = dynaHudComponent.getStaminaDelayTime();
        float threshold = (config.getStaminaThreshold() * 0.01f) * stat.getMax();

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = config.getStatusBarDelay()[0]; // Threshold hide delay

        if (statBelowLimit) {
            visibilityDelay.resetHideTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Stamina);
        } else if (hasHud) {
            visibilityDelay.incrementHideTime(deltaTime);
            if (visibilityDelay.getHideTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Stamina);
                visibilityDelay.resetHideTime();
            }
        }
    }

    public void monitorManaBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Mana);
        if (config.getStatusBarTrigger().equalsIgnoreCase("Disable")) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.Mana);
            }
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getMana());
        assert stat != null;

        VisibilityDelayTime visibilityDelay = dynaHudComponent.getManaDelayTime();
        float threshold = (config.getManaThreshold() * 0.01f) * stat.getMax();

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = config.getStatusBarDelay()[0]; // Threshold hide delay

        if (statBelowLimit) {
            visibilityDelay.resetHideTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Mana);
        } else if (hasHud) {
            visibilityDelay.incrementHideTime(deltaTime);
            if (visibilityDelay.getHideTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Mana);
                visibilityDelay.resetHideTime();
            }
        }
    }

    public void monitorAmmo() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.AmmoIndicator);
        if (config.getAmmoTrigger().equalsIgnoreCase("Disable")) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.AmmoIndicator);
            }
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getAmmo());
        assert stat != null;

        VisibilityDelayTime visibilityDelay = dynaHudComponent.getAmmoDelayTime();
        float threshold = stat.getMax(); // Can be added to settings

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = config.getAmmoDelay(); // Reload hide delay

        if (statBelowLimit) {
            visibilityDelay.resetHideTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.AmmoIndicator);
        } else if (hasHud) {
            visibilityDelay.incrementHideTime(deltaTime);
            if (visibilityDelay.getHideTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.AmmoIndicator);
                visibilityDelay.resetHideTime();
            }
        }
    }

    public void monitorHotbar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Hotbar);
        if (config.getHotbarTrigger().equalsIgnoreCase("Disable")) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.Hotbar);
            }
            return;
        }

        VisibilityDelayTime visibilityDelay = dynaHudComponent.getHotbarDelayTime();
        Inventory inventory = player.getInventory();

        boolean hotbarIndexChanged = inventory.getActiveHotbarSlot() != dynaHudComponent.getActiveHotbarSlot();
        float hideDelayTime = config.getHotbarDelay()[0]; // Change hide delay

        if (hotbarIndexChanged) {
            visibilityDelay.resetHideTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Hotbar);
        } else if (hasHud) {
            visibilityDelay.incrementHideTime(deltaTime);
            if (visibilityDelay.getHideTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Hotbar);
                visibilityDelay.resetHideTime();
            }
        }
        dynaHudComponent.setActiveHotbarSlot(inventory.getActiveHotbarSlot());
    }

//    public void monitorReticle() {
//        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Reticle);
//        if (settingsData.isReticleEnabled()) {
//            if (hasHud)
//                hudManager.hideHudComponents(playerRef, HudComponent.Reticle);
//        } else if (!hasHud) {
//            hudManager.showHudComponents(playerRef, HudComponent.Reticle);
//        }
//    }

}
