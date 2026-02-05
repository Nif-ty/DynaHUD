package co.headpats.plugin.components;

import co.headpats.plugin.classes.VisibilityDelayTime;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class DelayTimeComponent implements Component<EntityStore> {


    private final VisibilityDelayTime healthVisibilityDelayTime;
    private final VisibilityDelayTime staminaVisibilityDelayTime;
    private final VisibilityDelayTime manaVisibilityDelayTime;
    private final VisibilityDelayTime hotbarVisibilityDelayTime;
    private final VisibilityDelayTime compassVisibilityDelayTime;
    private final VisibilityDelayTime ammoVisibilityDelayTime;
    private byte activeHotbarSlot;

    public static final BuilderCodec<DelayTimeComponent> CODEC = BuilderCodec.builder(
            DelayTimeComponent.class, DelayTimeComponent::new).build();

    public DelayTimeComponent() {
        this.activeHotbarSlot = 0;
        this.healthVisibilityDelayTime = new VisibilityDelayTime();
        this.staminaVisibilityDelayTime = new VisibilityDelayTime();
        this.manaVisibilityDelayTime = new VisibilityDelayTime();
        this.hotbarVisibilityDelayTime = new VisibilityDelayTime();
        this.compassVisibilityDelayTime = new VisibilityDelayTime();
        this.ammoVisibilityDelayTime = new VisibilityDelayTime();
    }

    public DelayTimeComponent(DelayTimeComponent other) {
        this.activeHotbarSlot = other.activeHotbarSlot;
        this.healthVisibilityDelayTime = other.healthVisibilityDelayTime;
        this.staminaVisibilityDelayTime = other.staminaVisibilityDelayTime;
        this.manaVisibilityDelayTime = other.manaVisibilityDelayTime;
        this.hotbarVisibilityDelayTime = other.hotbarVisibilityDelayTime;
        this.compassVisibilityDelayTime = other.compassVisibilityDelayTime;
        this.ammoVisibilityDelayTime = other.ammoVisibilityDelayTime;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new DelayTimeComponent(this);
    }

    public void setActiveHotbarSlot(byte hotbarIndex) {
        this.activeHotbarSlot = hotbarIndex;
    }

    public byte getActiveHotbarSlot() {
        return activeHotbarSlot;
    }

    public VisibilityDelayTime getHealthVisibilityDelayTime() {
        return healthVisibilityDelayTime;
    }

    public VisibilityDelayTime getStaminaVisibilityDelayTime() {
        return staminaVisibilityDelayTime;
    }

    public VisibilityDelayTime getManaVisibilityDelayTime() {
        return manaVisibilityDelayTime;
    }

    public VisibilityDelayTime getHotbarVisibilityDelayTime() {
        return hotbarVisibilityDelayTime;
    }

    public VisibilityDelayTime getCompassVisibilityDelayTime() {
        return compassVisibilityDelayTime;
    }

    public VisibilityDelayTime getAmmoVisibilityDelayTime() { return this.ammoVisibilityDelayTime; }

}
