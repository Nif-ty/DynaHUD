package co.headpats.plugin.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class SettingsData implements Component<EntityStore> {

    // States (enabled/disabled)
    private boolean healthBarState;
    private boolean staminaBarState;
    private boolean manaBarState;
    private boolean statusIconsState;
    private boolean hotbarState;
    private boolean reticleState;
    private boolean compassState;
    private boolean ammoIndicatorState;
    private boolean inputBindingsState;

    // Conditions - limits(float)
    private float healthThreshold;
    private float staminaThreshold;
    private float manaThreshold;
    // NOTE:
    //   below thresholds need further looking into if necessary.
    //   needs re-implementing if added
//    private float signatureThreshold;
//    private float ammoThreshold;

    public static final BuilderCodec<SettingsData> CODEC = BuilderCodec.builder(
            SettingsData.class, SettingsData::new)
            .append(new KeyedCodec<>("HealthBarState", Codec.BOOLEAN),
                    (data, value) -> data.healthBarState = value, // Setter
                    data -> data.healthBarState).add() // Getter
            .append(new KeyedCodec<>("StaminaBarState", Codec.BOOLEAN),
                    (data, value) -> data.staminaBarState = value,
                    data -> data.staminaBarState).add()
            .append(new KeyedCodec<>("ManaBarState", Codec.BOOLEAN),
                    (data, value) -> data.manaBarState = value,
                    data -> data.manaBarState).add()
            .append(new KeyedCodec<>("StatusIconsState", Codec.BOOLEAN),
                    (data, value) -> data.statusIconsState = value,
                    data -> data.statusIconsState).add()
            .append(new KeyedCodec<>("HotbarState", Codec.BOOLEAN),
                    (data, value) -> data.hotbarState = value,
                    data -> data.hotbarState).add()
            .append(new KeyedCodec<>("ReticleState", Codec.BOOLEAN),
                    (data, value) -> data.reticleState = value,
                    data -> data.reticleState).add()
            .append(new KeyedCodec<>("CompassState", Codec.BOOLEAN),
                    (data, value) -> data.compassState = value,
                    data -> data.compassState).add()
            .append(new KeyedCodec<>("AmmoIndicatorState", Codec.BOOLEAN),
                    (data, value) -> data.ammoIndicatorState = value,
                    data -> data.ammoIndicatorState).add()
            .append(new KeyedCodec<>("InputBindingsState", Codec.BOOLEAN),
                    (data, value) -> data.inputBindingsState = value,
                    data -> data.inputBindingsState).add()
            .append(new KeyedCodec<>("HealthThreshold", Codec.FLOAT),
                    (data, value) -> data.healthThreshold = value,
                    data -> data.healthThreshold).add()
            .append(new KeyedCodec<>("StaminaThreshold", Codec.FLOAT),
                    (data, value) -> data.staminaThreshold = value,
                    data -> data.staminaThreshold).add()
            .append(new KeyedCodec<>("ManaThreshold", Codec.FLOAT),
                    (data, value) -> data.manaThreshold = value,
                    data -> data.manaThreshold).add()
            .build();

    public SettingsData() {
        healthBarState = true;
        staminaBarState = true;
        manaBarState = true;
        statusIconsState = false;
        hotbarState = true;
        reticleState = false;
        compassState = true;
        ammoIndicatorState = true;
        inputBindingsState = true;
        healthThreshold = 30.0f;
        staminaThreshold = 20.0f;
        manaThreshold = 20.0f;
    }

    public SettingsData(SettingsData other) {
        this.healthBarState = other.healthBarState;
        this.staminaBarState = other.staminaBarState;
        this.manaBarState = other.manaBarState;
        this.statusIconsState = other.statusIconsState;
        this.hotbarState = other.hotbarState;
        this.reticleState = other.reticleState;
        this.compassState = other.compassState;
        this.ammoIndicatorState = other.ammoIndicatorState;
        this.inputBindingsState = other.inputBindingsState;
        this.healthThreshold = other.healthThreshold;
        this.staminaThreshold = other.staminaThreshold;
        this.manaThreshold = other.manaThreshold;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new SettingsData(this);
    }

    public boolean isHealthBarEnabled() { return healthBarState; }
    public void switchHealthBarState() { this.healthBarState = !this.healthBarState; }

    public boolean isStaminaBarEnabled() {
        return staminaBarState;
    }
    public void switchStaminaBarState() {
        this.staminaBarState = !this.staminaBarState;
    }

    public boolean isManaBarEnabled() {
        return manaBarState;
    }
    public void switchManaBarState() {
        this.manaBarState = !this.manaBarState;
    }

    public boolean isStatusIconsEnabled() { return statusIconsState; }
    public void switchStatusIconsState() { this.statusIconsState = !this.statusIconsState; }

    public boolean isHotbarEnabled() {
        return hotbarState;
    }
    public void switchHotbarState() {
        this.hotbarState = !this.hotbarState;
    }

    public boolean isReticleEnabled() { return reticleState; }
    public void switchReticleState() { this.reticleState = !this.reticleState; }

    public boolean isCompassEnabled() {
        return compassState;
    }
    public void switchCompassState() {
        this.compassState = !this.compassState;
    }

    public boolean isAmmoIndicatorEnabled() { return ammoIndicatorState; }
    public void switchAmmoIndicatorState() { this.ammoIndicatorState = !this.ammoIndicatorState; }

    public boolean isInputBindingsEnabled() { return inputBindingsState; }
    public void switchInputBindingsState() { this.inputBindingsState = !this.inputBindingsState; }

    // Thresholds get/set
    public float getHealthThreshold() { return healthThreshold; }
    public void setHealthThreshold(float healthThreshold) { this.healthThreshold = healthThreshold; }

    public float getStaminaThreshold() { return staminaThreshold; }
    public void setStaminaThreshold(float staminaThreshold) { this.staminaThreshold = staminaThreshold; }

    public float getManaThreshold() { return manaThreshold; }
    public void setManaThreshold(float manaThreshold) { this.manaThreshold = manaThreshold; }

}
