package com.niftymods.plugin.components;

import com.niftymods.plugin.config.PlayerConfig;
import com.niftymods.plugin.utils.VisibilityDelayTime;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class DynaHudComponent implements Component<EntityStore> {

    private PlayerConfig playerConfig;
    private final VisibilityDelayTime healthDelayTime;
    private final VisibilityDelayTime staminaDelayTime;
    private final VisibilityDelayTime manaDelayTime;
    private final VisibilityDelayTime hotbarDelayTime;
    private final VisibilityDelayTime ammoDelayTime;
    private byte activeHotbarSlot;

    public static final BuilderCodec<DynaHudComponent> CODEC = BuilderCodec.builder(
            DynaHudComponent.class, DynaHudComponent::new).build();

    public DynaHudComponent() {
        this.activeHotbarSlot = 0;
        this.healthDelayTime = new VisibilityDelayTime();
        this.staminaDelayTime = new VisibilityDelayTime();
        this.manaDelayTime = new VisibilityDelayTime();
        this.hotbarDelayTime = new VisibilityDelayTime();
        this.ammoDelayTime = new VisibilityDelayTime();
    }

    public DynaHudComponent(DynaHudComponent other) {
        this.playerConfig = other.playerConfig;
        this.activeHotbarSlot = other.activeHotbarSlot;
        this.healthDelayTime = other.healthDelayTime;
        this.staminaDelayTime = other.staminaDelayTime;
        this.manaDelayTime = other.manaDelayTime;
        this.hotbarDelayTime = other.hotbarDelayTime;
        this.ammoDelayTime = other.ammoDelayTime;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new DynaHudComponent(this);
    }

    public PlayerConfig getPlayerConfig() { return playerConfig; }

    public void setPlayerConfig(PlayerConfig playerConfig) { this.playerConfig = playerConfig; }

    public void setActiveHotbarSlot(byte hotbarIndex) {
        this.activeHotbarSlot = hotbarIndex;
    }

    public byte getActiveHotbarSlot() {
        return activeHotbarSlot;
    }

    public VisibilityDelayTime getHealthDelayTime() { return healthDelayTime; }

    public VisibilityDelayTime getStaminaDelayTime() {
        return staminaDelayTime;
    }

    public VisibilityDelayTime getManaDelayTime() {
        return manaDelayTime;
    }

    public VisibilityDelayTime getHotbarDelayTime() {
        return hotbarDelayTime;
    }

    public VisibilityDelayTime getAmmoDelayTime() { return this.ammoDelayTime; }

}
