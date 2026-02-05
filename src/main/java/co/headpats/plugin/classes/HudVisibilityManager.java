package co.headpats.plugin.classes;

import co.headpats.plugin.DynaHudPlugin;
import co.headpats.plugin.components.DelayTimeComponent;
import co.headpats.plugin.components.SettingsData;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class HudVisibilityManager {
    @Nonnull
    private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
    @Nonnull
    private static final ComponentType<EntityStore, EntityStatMap> ENTITY_STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();
    private final ComponentType<EntityStore, DelayTimeComponent> DELAY_TIME_COMPONENT_TYPE = DynaHudPlugin.get().getDelayTimeComponentType();
    private final ComponentType<EntityStore, SettingsData> SETTINGS_COMPONENT_TYPE = DynaHudPlugin.get().getSettingsDataComponentType();

    private float deltaTime;
    private Store<EntityStore> store;
    private Player player;
    private Ref<EntityStore> ref;
    private PlayerRef playerRef;
    private EntityStatMap statMap;
    private HudManager hudManager;
    private DelayTimeComponent delayTimeComponent;
    private SettingsData settingsData;

    public void update(
            float deltaTime,
            int index,
            ArchetypeChunk<EntityStore> archetypeChunk,
            Store<EntityStore> store
    ) {
        this.deltaTime = deltaTime;
        this.store = store;
        this.player = archetypeChunk.getComponent(index, PLAYER_COMPONENT_TYPE);
        assert player != null;
        this.ref = player.getReference();
        assert ref != null;
        this.playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        this.statMap = archetypeChunk.getComponent(index, ENTITY_STAT_MAP_COMPONENT_TYPE);
        this.hudManager = player.getHudManager();
        this.delayTimeComponent = store.getComponent(ref, DELAY_TIME_COMPONENT_TYPE);
        this.settingsData = store.getComponent(ref, SETTINGS_COMPONENT_TYPE);
    }

    public void monitorHealthBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Health);
        if (!settingsData.isHealthBarEnabled()) {
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Health);
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getHealth());
        assert stat != null;

        VisibilityDelayTime healthVisibilityDelayTime = delayTimeComponent.getHealthVisibilityDelayTime();
        float threshold = (settingsData.getHealthThreshold() * 0.01f) * stat.getMax();

        boolean statBelowLimit = stat.get() < threshold; //Modify this to get config stat limit
        float hideDelayTime = 1.5f; // Modify this to get config hide delay time

        if (statBelowLimit) {
            healthVisibilityDelayTime.resetHideDelayTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Health);
        } else if (hasHud) {
            healthVisibilityDelayTime.incrementHideDelayTime(deltaTime);
            if (healthVisibilityDelayTime.getHideDelayTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Health);
                healthVisibilityDelayTime.resetHideDelayTime();
            }
        }
    }

    public void monitorStaminaBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Stamina);
        if (!settingsData.isStaminaBarEnabled()) {
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Stamina);
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getStamina());
        assert stat != null;

        VisibilityDelayTime staminaVisibilityDelayTime = delayTimeComponent.getStaminaVisibilityDelayTime();
        float threshold = (settingsData.getStaminaThreshold() * 0.01f) * stat.getMax();

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = 1.5f;

        if (statBelowLimit) {
            staminaVisibilityDelayTime.resetHideDelayTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Stamina);
        } else if (hasHud) {
            staminaVisibilityDelayTime.incrementHideDelayTime(deltaTime);
            if (staminaVisibilityDelayTime.getHideDelayTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Stamina);
                staminaVisibilityDelayTime.resetHideDelayTime();
            }
        }
    }

    public void monitorManaBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Mana);
        if (!settingsData.isManaBarEnabled()) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.Mana);
            }
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getMana());
        assert stat != null;

        VisibilityDelayTime manaVisibilityDelayTime = delayTimeComponent.getManaVisibilityDelayTime();
        float threshold = (settingsData.getManaThreshold() * 0.01f) * stat.getMax();

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = 1.5f;
        if (statBelowLimit) {
            manaVisibilityDelayTime.resetHideDelayTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Mana);
        } else if (hasHud) {
            manaVisibilityDelayTime.incrementHideDelayTime(deltaTime);
            if (manaVisibilityDelayTime.getHideDelayTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Mana);
                manaVisibilityDelayTime.resetHideDelayTime();
            }
        }
    }

    public void monitorHotBar() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Hotbar);
        if (!settingsData.isHotbarEnabled()) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.Hotbar);
            }
            return;
        }

        VisibilityDelayTime hotbarVisibilityDelayTime = delayTimeComponent.getHotbarVisibilityDelayTime();
        Inventory inventory = player.getInventory();

        boolean hotbarIndexChanged = inventory.getActiveHotbarSlot() != delayTimeComponent.getActiveHotbarSlot();

        float hideDelayTime = 1.5f;

        if (hotbarIndexChanged) {
            hotbarVisibilityDelayTime.resetHideDelayTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.Hotbar);
        } else if (hasHud) {
            hotbarVisibilityDelayTime.incrementHideDelayTime(deltaTime);
            if (hotbarVisibilityDelayTime.getHideDelayTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Hotbar);
                hotbarVisibilityDelayTime.resetHideDelayTime();
            }
        }
        delayTimeComponent.setActiveHotbarSlot(inventory.getActiveHotbarSlot());
    }

    public void monitorReticle() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Reticle);
        if (settingsData.isReticleEnabled()) {
            if (hasHud)
                hudManager.hideHudComponents(playerRef, HudComponent.Reticle);
        } else if (!hasHud) {
            hudManager.showHudComponents(playerRef, HudComponent.Reticle);
        }
    }

    public void monitorCompass() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.Compass);
        if (!settingsData.isCompassEnabled()) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.Compass);
            }
            return;
        }

        MovementStatesComponent movementStatesComponent = store.getComponent(ref, MovementStatesComponent.getComponentType());
        assert movementStatesComponent != null;
        VisibilityDelayTime compassVisibilityDelayTime = delayTimeComponent.getCompassVisibilityDelayTime();

        float showDelayTime = 1.0f;
        float hideDelayTime = 3.0f;

        if (movementStatesComponent.getMovementStates().crouching) {
            if (!hasHud) {
                compassVisibilityDelayTime.incrementShowDelayTime(deltaTime);
                if (compassVisibilityDelayTime.getShowDelayTime() >= showDelayTime) {
                    hudManager.showHudComponents(playerRef, HudComponent.Compass);
                    compassVisibilityDelayTime.resetShowDelayTime();
                }
            }
            compassVisibilityDelayTime.resetHideDelayTime();
            return;
        }
        compassVisibilityDelayTime.resetShowDelayTime();
        if (hasHud) {
            compassVisibilityDelayTime.incrementHideDelayTime(deltaTime);
            if (compassVisibilityDelayTime.getHideDelayTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.Compass);
                compassVisibilityDelayTime.resetHideDelayTime();
            }
        }
    }

    public void monitorAmmo() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.AmmoIndicator);
        if (!settingsData.isAmmoIndicatorEnabled()) {
            if (!hasHud) {
                hudManager.showHudComponents(playerRef, HudComponent.AmmoIndicator);
            }
            return;
        }

        EntityStatValue stat = statMap.get(DefaultEntityStatTypes.getAmmo());
        assert stat != null;

        VisibilityDelayTime ammoVisibilityDelayTime = delayTimeComponent.getAmmoVisibilityDelayTime();
        float threshold = stat.getMax(); // Can be added to settings

        boolean statBelowLimit = stat.get() < threshold;
        float hideDelayTime = 1.5f;
        if (statBelowLimit) {
            ammoVisibilityDelayTime.resetHideDelayTime();
            if (!hasHud)
                hudManager.showHudComponents(playerRef, HudComponent.AmmoIndicator);
        } else if (hasHud) {
            ammoVisibilityDelayTime.incrementHideDelayTime(deltaTime);
            if (ammoVisibilityDelayTime.getHideDelayTime() >= hideDelayTime) {
                hudManager.hideHudComponents(playerRef, HudComponent.AmmoIndicator);
                ammoVisibilityDelayTime.resetHideDelayTime();
            }

        }
    }

    public void monitorInputBindings() {
        boolean hasHud = hudManager.getVisibleHudComponents().contains(HudComponent.InputBindings);
        if (settingsData.isInputBindingsEnabled()) {
            if (hasHud)
                hudManager.hideHudComponents(playerRef, HudComponent.InputBindings);
        } else if (!hasHud) {
            hudManager.showHudComponents(playerRef, HudComponent.InputBindings);
        }
    }

}
