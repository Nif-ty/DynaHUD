package com.niftymods.plugin.components;

import com.hypixel.hytale.component.ComponentType;
import com.niftymods.plugin.DynaHudPlugin;
import com.niftymods.plugin.config.PlayerConfig;
import com.niftymods.plugin.utils.TickTimer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class DynaHudComponent implements Component<EntityStore> {

    private PlayerConfig playerConfig;

    // HudCondition
    private final TickTimer healthBarTickTimer;
    private final TickTimer staminaBarTickTimer;
    private final TickTimer manaBarTickTimer;
    private final TickTimer ammoBarTickTimer;
    private final TickTimer hotbarTickTimer;
    private byte activeSlot;

    // CombatEvent
    private final TickTimer statusBarCombatTickTimer;
    private final TickTimer hotbarCombatTickTimer;
    private final TickTimer reticleCombatTickTimer;


    public DynaHudComponent() {
        this.healthBarTickTimer = new TickTimer(0);
        this.staminaBarTickTimer = new TickTimer(0);
        this.manaBarTickTimer = new TickTimer(0);
        this.ammoBarTickTimer = new TickTimer(0);
        this.hotbarTickTimer = new TickTimer(0);
        this.statusBarCombatTickTimer = new TickTimer(0);
        this.hotbarCombatTickTimer = new TickTimer(0);
        this.reticleCombatTickTimer = new TickTimer(0);
        activeSlot = 0;
    }

    public DynaHudComponent(DynaHudComponent other) {
        this.playerConfig = other.playerConfig;
        this.healthBarTickTimer = other.healthBarTickTimer;
        this.staminaBarTickTimer = other.staminaBarTickTimer;
        this.manaBarTickTimer = other.manaBarTickTimer;
        this.ammoBarTickTimer = other.ammoBarTickTimer;
        this.hotbarTickTimer = other.hotbarTickTimer;
        this.statusBarCombatTickTimer = other.statusBarCombatTickTimer;
        this.hotbarCombatTickTimer = other.hotbarCombatTickTimer;
        this.reticleCombatTickTimer = other.reticleCombatTickTimer;
        activeSlot = 0;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new DynaHudComponent(this);
    }

    public static ComponentType<EntityStore, DynaHudComponent> getComponentType() {
        return DynaHudPlugin.get().getDynaHudComponentType();
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public void setPlayerConfig(PlayerConfig playerConfig) {
        this.playerConfig = playerConfig;
        healthBarTickTimer.setDefaultTime(playerConfig.getStatusBarDelayThreshold());
        staminaBarTickTimer.setDefaultTime(playerConfig.getStatusBarDelayThreshold());
        manaBarTickTimer.setDefaultTime(playerConfig.getStatusBarDelayThreshold());
        ammoBarTickTimer.setDefaultTime(playerConfig.getAmmoDelayReload());
        hotbarTickTimer.setDefaultTime(playerConfig.getHotbarDelayChange());
        statusBarCombatTickTimer.setDefaultTime(playerConfig.getStatusBarDelayCombat());
        hotbarCombatTickTimer.setDefaultTime(playerConfig.getHotbarDelayCombat());
        reticleCombatTickTimer.setDefaultTime(playerConfig.getReticleDelayCombat());
    }

    public TickTimer getHealthBarTickTimer() {
        return healthBarTickTimer;
    }

    public TickTimer getStaminaBarTickTimer() {
        return staminaBarTickTimer;
    }

    public TickTimer getManaBarTickTimer() {
        return manaBarTickTimer;
    }

    public TickTimer getAmmoBarTickTimer() {
        return ammoBarTickTimer;
    }

    public TickTimer getHotbarTickTimer() {
        return hotbarTickTimer;
    }

    public TickTimer getStatusBarCombatTickTimer() {
        return statusBarCombatTickTimer;
    }

    public TickTimer getHotbarCombatTickTimer() {
        return hotbarCombatTickTimer;
    }

    public TickTimer getReticleCombatTickTimer() {
        return reticleCombatTickTimer;
    }

    public void updateTickTimers() {
        healthBarTickTimer.setDefaultTime(playerConfig.getStatusBarDelayThreshold());
        staminaBarTickTimer.setDefaultTime(playerConfig.getStatusBarDelayThreshold());
        manaBarTickTimer.setDefaultTime(playerConfig.getStatusBarDelayThreshold());
        ammoBarTickTimer.setDefaultTime(playerConfig.getAmmoDelayReload());
        hotbarTickTimer.setDefaultTime(playerConfig.getHotbarDelayChange());
        statusBarCombatTickTimer.setDefaultTime(playerConfig.getStatusBarDelayCombat());
        hotbarCombatTickTimer.setDefaultTime(playerConfig.getHotbarDelayCombat());
        reticleCombatTickTimer.setDefaultTime(playerConfig.getReticleDelayCombat());
    }

    public byte getActiveSlot() {
        return activeSlot;
    }

    public void setActiveSlot(byte activeSlot) {
        this.activeSlot = activeSlot;
    }

}
