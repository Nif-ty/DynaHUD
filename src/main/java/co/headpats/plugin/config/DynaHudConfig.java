package co.headpats.plugin.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class DynaHudConfig {

    public static final BuilderCodec<DynaHudConfig> CODEC = BuilderCodec.builder(
            DynaHudConfig.class,DynaHudConfig::new)
            .append(new KeyedCodec<>("HealthBarState", Codec.BOOLEAN),
                    ((dynaHudConfig, healthBarState) -> dynaHudConfig.healthBarState = healthBarState),
                    (DynaHudConfig::isHealthBarEnabled)).add()
            .append(new KeyedCodec<>("StaminaBarState", Codec.BOOLEAN),
                    ((dynaHudConfig, staminaBarState) -> dynaHudConfig.staminaBarState = staminaBarState),
                    (DynaHudConfig::isStaminaBarEnabled)).add()
            .append(new KeyedCodec<>("ManaBarState", Codec.BOOLEAN),
                    ((dynaHudConfig, manaBarState) -> dynaHudConfig.manaBarState = manaBarState),
                    (DynaHudConfig::isManaBarEnabled)).add()
            .append(new KeyedCodec<>("HotbarState", Codec.BOOLEAN),
                    ((dynaHudConfig, hotbarState) -> dynaHudConfig.hotbarState = hotbarState),
                    (DynaHudConfig::isHotbarEnabled)).add()
            .append(new KeyedCodec<>("CompassState", Codec.BOOLEAN),
                    ((dynaHudConfig, compassState) -> dynaHudConfig.compassState = compassState),
                    (DynaHudConfig::isCompassEnabled)).add()
            .build();

    private boolean healthBarState = true;
    private boolean staminaBarState = true;
    private boolean manaBarState = true;
    private boolean hotbarState = true;
    private boolean compassState = true;

    public boolean isHealthBarEnabled() {
        return healthBarState;
    }

    public void setHealthBarState(boolean healthBarState) {
        this.healthBarState = healthBarState;
    }

    public boolean isStaminaBarEnabled() {
        return staminaBarState;
    }

    public void setStaminaBarState(boolean staminaBarState) {
        this.staminaBarState = staminaBarState;
    }

    public boolean isManaBarEnabled() {
        return manaBarState;
    }

    public void setManaBarState(boolean manaBarState) {
        this.manaBarState = manaBarState;
    }

    public boolean isHotbarEnabled() {
        return hotbarState;
    }

    public void setHotbarState(boolean hotbarState) {
        this.hotbarState = hotbarState;
    }

    public boolean isCompassEnabled() {
        return compassState;
    }

    public void setCompassState(boolean compassState) {
        this.compassState = compassState;
    }
}
