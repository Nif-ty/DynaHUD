package com.niftymods.plugin.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hypixel.hytale.logger.HytaleLogger;
import com.niftymods.plugin.DynaHudPlugin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class PlayerConfig {

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private String version = "1.0.0";
    private String playerId;
    private String preset = "Default";
    private String statusBarTrigger = "Both";
    private float healthThreshold = 30.0f;
    private float staminaThreshold = 20.0f;
    private float manaThreshold = 20.0f;
    private String hotbarTrigger = "Both";
    private String reticleTrigger = "Disable";
    private boolean hideCompass = true;
    private boolean hideAmmo = true;
    private boolean hideInputBindings = true;

    public PlayerConfig() {}

    public PlayerConfig(String playerId) {
        this.playerId = playerId;
    }

    public static PlayerConfig getFromPlayerId(String playerId) {
        DynaHudPlugin dynaHudPlugin = DynaHudPlugin.getInstance();
        Path configPath = dynaHudPlugin.getDataDirectory().resolve("players/" + playerId + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PlayerConfig playerConfig;

        // Create players directory if missing
        if(configPath.getParent().toFile().mkdirs()) {
            LOGGER.atInfo().log("Failed to find players folder. Creating directory... ");
        }

        // Check if file exists
        try (FileReader reader = new FileReader(configPath.toFile())) {
            playerConfig = gson.fromJson(reader, PlayerConfig.class);
            playerConfig.setPlayerId(playerId);
        } catch (IOException e) { // Create if it doesn't
            LOGGER.atInfo().withCause(e).log("Failed to find player config for " + playerId + ". Attempting to create new...");
            playerConfig = new PlayerConfig();
            playerConfig.setPlayerId(playerId);

            // Write to file
            try (FileWriter writer = new FileWriter(configPath.toFile())) {
                gson.toJson(playerConfig, writer);
            } catch (IOException ex) {
                LOGGER.atSevere().withCause(ex).log("Failed to save player config for " + playerId);
            }
        }

        return playerConfig;
    }

    public static boolean hasConfigFile(String playerId) {
        DynaHudPlugin dynaHudPlugin = DynaHudPlugin.getInstance();
        Path configPath = dynaHudPlugin.getDataDirectory().resolve("players/" + playerId + ".json");
        return configPath.toFile().exists();
    }

    public void save() {
        DynaHudPlugin dynaHudPlugin = DynaHudPlugin.getInstance();
        Path configPath = dynaHudPlugin.getDataDirectory().resolve("players/" + playerId + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Create players directory if missing
        if(configPath.getParent().toFile().mkdirs()) {
            LOGGER.atInfo().log("Failed to find players folder. Creating directory... ");
        }

        // Write to file
        try (FileWriter writer = new FileWriter(configPath.toFile())) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            LOGGER.atSevere().withCause(e).log("Failed to save player config for " + playerId);
        }
    }

    public void reset() {
        PlayerConfig other = new PlayerConfig(this.playerId);
        this.preset = other.preset;
        this.statusBarTrigger = other.statusBarTrigger;
        this.healthThreshold = other.healthThreshold;
        this.staminaThreshold = other.staminaThreshold;
        this.manaThreshold = other.manaThreshold;
        this.hotbarTrigger = other.hotbarTrigger;
        this.reticleTrigger = other.reticleTrigger;
        this.hideCompass = other.hideCompass;
        this.hideAmmo = other.hideAmmo;
        this.hideInputBindings = other.hideInputBindings;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public String getPreset() { return preset; }

    public void setPreset(String preset) { this.preset = preset; }

    public String getStatusBarTrigger() {
        return statusBarTrigger;
    }

    public void setStatusBarTrigger(String statusBarTrigger) {
        this.statusBarTrigger = statusBarTrigger;
    }

    public float getHealthThreshold() {
        return healthThreshold;
    }

    public void setHealthThreshold(float healthThreshold) {
        this.healthThreshold = healthThreshold;
    }

    public float getStaminaThreshold() {
        return staminaThreshold;
    }

    public void setStaminaThreshold(float staminaThreshold) {
        this.staminaThreshold = staminaThreshold;
    }

    public float getManaThreshold() {
        return manaThreshold;
    }

    public void setManaThreshold(float manaThreshold) {
        this.manaThreshold = manaThreshold;
    }

    public String getHotbarTrigger() {
        return hotbarTrigger;
    }

    public void setHotbarTrigger(String hotbarTrigger) {
        this.hotbarTrigger = hotbarTrigger;
    }

    public String getReticleTrigger() {
        return reticleTrigger;
    }

    public void setReticleTrigger(String reticleTrigger) {
        this.reticleTrigger = reticleTrigger;
    }

    public boolean isHideCompass() {
        return hideCompass;
    }

    public void setHideCompass(boolean hideCompass) {
        this.hideCompass = hideCompass;
    }

    public boolean isHideAmmo() {
        return hideAmmo;
    }

    public void setHideAmmo(boolean hideAmmo) {
        this.hideAmmo = hideAmmo;
    }

    public boolean isHideInputBindings() {
        return hideInputBindings;
    }

    public void setHideInputBindings(boolean hideInputBindings) {
        this.hideInputBindings = hideInputBindings;
    }

}
