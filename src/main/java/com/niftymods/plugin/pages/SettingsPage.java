package com.niftymods.plugin.pages;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.DropdownEntryInfo;
import com.hypixel.hytale.server.core.ui.LocalizableString;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.niftymods.plugin.config.PlayerConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SettingsPage extends InteractiveCustomUIPage<SettingsPage.SettingsEventData> {

    private final PlayerConfig config;

    public static class SettingsEventData {

        private String category;
        private String action;
        private String subAction;

        private String preset;

        private String triggerStatusBar;
        private float statusBarThresholdDelay;
        private float statusBarCombatDelay;
        private float thresholdHealth;
        private float thresholdStamina;
        private float thresholdMana;

        private String triggerHotbar;
        private float hotbarChangeDelay;
        private float hotbarCombatDelay;

        private String triggerReticle;
        private float reticleCombatDelay;

        private String triggerAmmo;
        private float ammoReloadDelay;

        private boolean flagCompass;
        private boolean flagInputBindings;

        public static final BuilderCodec<SettingsPage.SettingsEventData> CODEC =
                BuilderCodec.builder(SettingsPage.SettingsEventData.class, SettingsPage.SettingsEventData::new)
                        .append(new KeyedCodec<>("Category", Codec.STRING),
                                (data, value) -> data.category = value,
                                (data) -> data.category).add()
                        .append(new KeyedCodec<>("Action", Codec.STRING),
                                (data, value) -> data.action = value,
                                (data) -> data.action).add()
                        .append(new KeyedCodec<>("SubAction", Codec.STRING),
                                (data, value) -> data.subAction = value,
                                (data) -> data.subAction).add()
                        .append(new KeyedCodec<>("@Preset", Codec.STRING),
                                (data, value) -> data.preset = value,
                                (data) -> data.preset).add()
                        .append(new KeyedCodec<>("@TriggerStatusBar", Codec.STRING),
                                (data, value) -> data.triggerStatusBar = value,
                                (data) -> data.triggerStatusBar).add()
                        .append(new KeyedCodec<>("@StatusBarThresholdDelay", Codec.FLOAT),
                                (data, value) -> data.statusBarThresholdDelay = value,
                                (data) -> data.statusBarThresholdDelay).add()
                        .append(new KeyedCodec<>("@StatusBarCombatDelay", Codec.FLOAT),
                                (data, value) -> data.statusBarCombatDelay = value,
                                (data) -> data.statusBarCombatDelay).add()
                        .append(new KeyedCodec<>("@ThresholdHealth", Codec.FLOAT),
                                (data, value) -> data.thresholdHealth = value,
                                (data) -> data.thresholdHealth).add()
                        .append(new KeyedCodec<>("@ThresholdStamina", Codec.FLOAT),
                                (data, value) -> data.thresholdStamina = value,
                                (data) -> data.thresholdStamina).add()
                        .append(new KeyedCodec<>("@ThresholdMana", Codec.FLOAT),
                                (data, value) -> data.thresholdMana = value,
                                (data) -> data.thresholdMana).add()
                        .append(new KeyedCodec<>("@TriggerHotbar", Codec.STRING),
                                (data, value) -> data.triggerHotbar = value,
                                (data) -> data.triggerHotbar).add()
                        .append(new KeyedCodec<>("@HotbarChangeDelay", Codec.FLOAT),
                                (data, value) -> data.hotbarChangeDelay = value,
                                (data) -> data.hotbarChangeDelay).add()
                        .append(new KeyedCodec<>("@HotbarCombatDelay", Codec.FLOAT),
                                (data, value) -> data.hotbarCombatDelay = value,
                                (data) -> data.hotbarCombatDelay).add()
                        .append(new KeyedCodec<>("@TriggerReticle", Codec.STRING),
                                (data, value) -> data.triggerReticle = value,
                                (data) -> data.triggerReticle).add()
                        .append(new KeyedCodec<>("@ReticleCombatDelay", Codec.FLOAT),
                                (data, value) -> data.reticleCombatDelay = value,
                                (data) -> data.reticleCombatDelay).add()
                        .append(new KeyedCodec<>("@TriggerAmmo", Codec.STRING),
                                (data, value) -> data.triggerAmmo = value,
                                (data) -> data.triggerAmmo).add()
                        .append(new KeyedCodec<>("@AmmoReloadDelay", Codec.FLOAT),
                                (data, value) -> data.ammoReloadDelay = value,
                                (data) -> data.ammoReloadDelay).add()
                        .append(new KeyedCodec<>("@FlagCompass", Codec.BOOLEAN),
                                (data, value) -> data.flagCompass = value,
                                (data) -> data.flagCompass).add()
                        .append(new KeyedCodec<>("@FlagInputBindings", Codec.BOOLEAN),
                                (data, value) -> data.flagInputBindings = value,
                                (data) -> data.flagInputBindings).add()
                        .build();
    }

    public SettingsPage(@NonNullDecl PlayerRef playerRef, PlayerConfig config) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, SettingsPage.SettingsEventData.CODEC);
        this.config = config;
    }

    @Override
    public void build(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl UICommandBuilder commandBuilder,
            @NonNullDecl UIEventBuilder eventBuilder,
            @NonNullDecl Store<EntityStore> store
    ) {
        commandBuilder.append("Pages/DynaHudSettings.ui");

        // Entries - Preset
        ObjectArrayList<DropdownEntryInfo> presetEntries = new ObjectArrayList<>();
        presetEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Default"), "Default"));
        presetEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Custom"), "Custom"));
        presetEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Disable"), "Disable"));
        commandBuilder.set("#SelectPreset #Dropdown.Entries", presetEntries);

        // Entries - Status Bar
        ObjectArrayList<DropdownEntryInfo> statusBarEntries = new ObjectArrayList<>();
        statusBarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Threshold"), "Threshold"));
        statusBarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Combat"), "Combat"));
        statusBarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Both"), "Both"));
        statusBarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Disable"), "Disable"));
        commandBuilder.set("#SelectStatusBar #Dropdown.Entries", statusBarEntries);

        // Entries - Hotbar
        ObjectArrayList<DropdownEntryInfo> hotbarEntries = new ObjectArrayList<>();
        hotbarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Change"), "Change"));
        hotbarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Combat"), "Combat"));
        hotbarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Both"), "Both"));
        hotbarEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Disable"), "Disable"));
        commandBuilder.set("#SelectHotbar #Dropdown.Entries", hotbarEntries);

        // Entries - Reticle
        ObjectArrayList<DropdownEntryInfo> reticleEntries = new ObjectArrayList<>();
        reticleEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Combat"), "Combat"));
        reticleEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Hide"), "Hide"));
        reticleEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Disable"), "Disable"));
        commandBuilder.set("#SelectReticle #Dropdown.Entries", reticleEntries);

        // Entries - Ammo
        ObjectArrayList<DropdownEntryInfo> ammoEntries = new ObjectArrayList<>();
        ammoEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Reload"), "Reload"));
        ammoEntries.add(new DropdownEntryInfo(LocalizableString.fromString("Disable"), "Disable"));
        commandBuilder.set("#SelectAmmo #Dropdown.Entries", ammoEntries);

        // Event Binding
        bindPresetEvents(eventBuilder);
        bindStatusBarEvents(eventBuilder);
        bindHotbarEvents(eventBuilder);
        bindReticleEvents(eventBuilder);
        bindAmmoEvents(eventBuilder);
        bindMiscEvents(eventBuilder);

        update(commandBuilder);
    }

    private void update(UICommandBuilder commandBuilder) {
        // Preset
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());

        // Status Bar
        commandBuilder.set("#SelectStatusBar #Dropdown.Value", config.getStatusBarTrigger());
        commandBuilder.set("#StatusBarThresholdDelay #Slider.Value", config.getStatusBarDelay()[0]);
        commandBuilder.set("#StatusBarThresholdDelay #Field.Value", config.getStatusBarDelay()[0]);
        commandBuilder.set("#StatusBarCombatDelay #Slider.Value", config.getStatusBarDelay()[1]);
        commandBuilder.set("#StatusBarCombatDelay #Field.Value", config.getStatusBarDelay()[1]);
        commandBuilder.set("#SliderHealthThreshold #Slider.Value", config.getHealthThreshold());
        commandBuilder.set("#SliderHealthThreshold #Field.Value", config.getHealthThreshold());
        commandBuilder.set("#SliderStaminaThreshold #Slider.Value", config.getStaminaThreshold());
        commandBuilder.set("#SliderStaminaThreshold #Field.Value", config.getStaminaThreshold());
        commandBuilder.set("#SliderManaThreshold #Slider.Value", config.getManaThreshold());
        commandBuilder.set("#SliderManaThreshold #Field.Value", config.getManaThreshold());

        // Hotbar
        commandBuilder.set("#SelectHotbar #Dropdown.Value", config.getHotbarTrigger());
        commandBuilder.set("#HotbarChangeDelay #Slider.Value", config.getHotbarDelay()[0]);
        commandBuilder.set("#HotbarChangeDelay #Field.Value", config.getHotbarDelay()[0]);
        commandBuilder.set("#HotbarCombatDelay #Slider.Value", config.getHotbarDelay()[1]);
        commandBuilder.set("#HotbarCombatDelay #Field.Value", config.getHotbarDelay()[1]);

        // Reticle
        commandBuilder.set("#SelectReticle #Dropdown.Value", config.getReticleTrigger());
        commandBuilder.set("#ReticleCombatDelay #Slider.Value", config.getReticleDelay());
        commandBuilder.set("#ReticleCombatDelay #Field.Value", config.getReticleDelay());

        // Ammo
        commandBuilder.set("#SelectAmmo #Dropdown.Value", config.getAmmoTrigger());
        commandBuilder.set("#AmmoReloadDelay #Slider.Value", config.getAmmoDelay());
        commandBuilder.set("#AmmoReloadDelay #Field.Value", config.getAmmoDelay());

        // Misc
        commandBuilder.set("#CheckBoxCompass #CheckBox.Value", config.isHideCompass());
        commandBuilder.set("#CheckBoxInputBindings #CheckBox.Value", config.isHideInputBindings());
    }

    private void bindPresetEvents(UIEventBuilder eventBuilder) {
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SelectPreset #Dropdown",
                new EventData()
                        .append("Category", "Preset")
                        .append("Action", "SelectPreset")
                        .append("@Preset", "#SelectPreset #Dropdown.Value")
        );
    }

    private void bindStatusBarEvents(UIEventBuilder eventBuilder) {
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SelectStatusBar #Dropdown",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SelectStatusBar")
                        .append("@TriggerStatusBar", "#SelectStatusBar #Dropdown.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StatusBarThresholdDelay #Slider",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "StatusBarThresholdDelay")
                        .append("SubAction", "Slider")
                        .append("@StatusBarThresholdDelay", "#StatusBarThresholdDelay #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StatusBarThresholdDelay #Field",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "StatusBarThresholdDelay")
                        .append("SubAction", "Field")
                        .append("@StatusBarThresholdDelay", "#StatusBarThresholdDelay #Field.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StatusBarCombatDelay #Slider",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "StatusBarCombatDelay")
                        .append("SubAction", "Slider")
                        .append("@StatusBarCombatDelay", "#StatusBarCombatDelay #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StatusBarCombatDelay #Field",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "StatusBarCombatDelay")
                        .append("SubAction", "Field")
                        .append("@StatusBarCombatDelay", "#StatusBarCombatDelay #Field.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SliderHealthThreshold #Slider",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SliderHealthThreshold")
                        .append("SubAction", "Slider")
                        .append("@ThresholdHealth", "#SliderHealthThreshold #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SliderHealthThreshold #Field",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SliderHealthThreshold")
                        .append("SubAction", "Field")
                        .append("@ThresholdHealth", "#SliderHealthThreshold #Field.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SliderStaminaThreshold #Slider",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SliderStaminaThreshold")
                        .append("SubAction", "Slider")
                        .append("@ThresholdStamina", "#SliderStaminaThreshold #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SliderStaminaThreshold #Field",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SliderStaminaThreshold")
                        .append("SubAction", "Field")
                        .append("@ThresholdStamina", "#SliderStaminaThreshold #Field.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SliderManaThreshold #Slider",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SliderManaThreshold")
                        .append("SubAction", "Slider")
                        .append("@ThresholdMana", "#SliderManaThreshold #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SliderManaThreshold #Field",
                new EventData()
                        .append("Category", "StatusBar")
                        .append("Action", "SliderManaThreshold")
                        .append("SubAction", "Field")
                        .append("@ThresholdMana", "#SliderManaThreshold #Field.Value")
        );
    }

    private void bindHotbarEvents(UIEventBuilder eventBuilder) {
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SelectHotbar #Dropdown",
                new EventData()
                        .append("Category", "Hotbar")
                        .append("Action", "SelectHotbar")
                        .append("@TriggerHotbar", "#SelectHotbar #Dropdown.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HotbarChangeDelay #Slider",
                new EventData()
                        .append("Category", "Hotbar")
                        .append("Action", "HotbarChangeDelay")
                        .append("SubAction", "Slider")
                        .append("@HotbarChangeDelay", "#HotbarChangeDelay #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HotbarChangeDelay #Field",
                new EventData()
                        .append("Category", "Hotbar")
                        .append("Action", "HotbarChangeDelay")
                        .append("SubAction", "Field")
                        .append("@HotbarChangeDelay", "#HotbarChangeDelay #Field.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HotbarCombatDelay #Slider",
                new EventData()
                        .append("Category", "Hotbar")
                        .append("Action", "HotbarCombatDelay")
                        .append("SubAction", "Slider")
                        .append("@HotbarCombatDelay", "#HotbarCombatDelay #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HotbarCombatDelay #Field",
                new EventData()
                        .append("Category", "Hotbar")
                        .append("Action", "HotbarCombatDelay")
                        .append("SubAction", "Field")
                        .append("@HotbarCombatDelay", "#HotbarCombatDelay #Field.Value")
        );
    }

    private void bindReticleEvents(UIEventBuilder eventBuilder) {
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SelectReticle #Dropdown",
                new EventData()
                        .append("Category", "Reticle")
                        .append("Action", "SelectReticle")
                        .append("@TriggerReticle", "#SelectReticle #Dropdown.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#ReticleCombatDelay #Slider",
                new EventData()
                        .append("Category", "Reticle")
                        .append("Action", "ReticleCombatDelay")
                        .append("SubAction", "Slider")
                        .append("@ReticleCombatDelay", "#ReticleCombatDelay #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#ReticleCombatDelay #Field",
                new EventData()
                        .append("Category", "Reticle")
                        .append("Action", "ReticleCombatDelay")
                        .append("SubAction", "Field")
                        .append("@ReticleCombatDelay", "#ReticleCombatDelay #Field.Value")
        );
    }

    private void bindAmmoEvents(UIEventBuilder eventBuilder) {
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#SelectAmmo #Dropdown",
                new EventData()
                        .append("Category", "Ammo")
                        .append("Action", "SelectAmmo")
                        .append("@TriggerAmmo", "#SelectAmmo #Dropdown.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#AmmoReloadDelay #Slider",
                new EventData()
                        .append("Category", "Ammo")
                        .append("Action", "AmmoReloadDelay")
                        .append("SubAction", "Slider")
                        .append("@AmmoReloadDelay", "#AmmoReloadDelay #Slider.Value"), false
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#AmmoReloadDelay #Field",
                new EventData()
                        .append("Category", "Ammo")
                        .append("Action", "AmmoReloadDelay")
                        .append("SubAction", "Field")
                        .append("@AmmoReloadDelay", "#AmmoReloadDelay #Field.Value")
        );
    }

    private void bindMiscEvents(UIEventBuilder eventBuilder) {
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#CheckBoxCompass #CheckBox",
                new EventData()
                        .append("Category", "Misc")
                        .append("Action", "CheckBoxCompass")
                        .append("@FlagCompass", "#CheckBoxCompass #CheckBox.Value")
        );
        eventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#CheckBoxInputBindings #CheckBox",
                new EventData()
                        .append("Category", "Misc")
                        .append("Action", "CheckBoxInputBindings")
                        .append("@FlagInputBindings", "#CheckBoxInputBindings #CheckBox.Value")
        );
    }

    @Override
    public void handleDataEvent(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl SettingsEventData data
    ) {
        UICommandBuilder commandBuilder = new UICommandBuilder();
        UIEventBuilder eventBuilder = new UIEventBuilder();

        handlePresetEvent(data, commandBuilder);
        handleStatusBarEvent(data, commandBuilder);
        handleHotbarEvent(data, commandBuilder);
        handleReticleEvent(data, commandBuilder);
        handleAmmoEvent(data, commandBuilder);
        handleMiscEvent(data, commandBuilder, ref, store);

        sendUpdate(commandBuilder, eventBuilder, false);
        config.save();
    }

    private void handlePresetEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("Preset")) return;
        if (data.action.equalsIgnoreCase("SelectPreset")) {
            switch (data.preset) {
                case "Default":
                    config.reset();
                    commandBuilder.set("#StatusBarHideDelay.Visible", true);
                    commandBuilder.set("#HotbarHideDelay.Visible", true);
                    commandBuilder.set("#ReticleHideDelay.Visible", false);
                    commandBuilder.set("#AmmoHideDelay.Visible", true);
                    commandBuilder.set("#StatusBarThresholdDelay.Visible", true);
                    commandBuilder.set("#StatusBarCombatDelay.Visible", true);
                    commandBuilder.set("#HotbarChangeDelay.Visible", true);
                    commandBuilder.set("#HotbarCombatDelay.Visible", true);
                    break;
                case "Disable":
                    config.setPreset("Disable");
                    config.setStatusBarTrigger("Disable");
                    config.setHotbarTrigger("Disable");
                    config.setReticleTrigger("Disable");
                    config.setAmmoTrigger("Disable");
                    config.setHideCompass(false);
                    config.setHideInputBindings(false);
                    commandBuilder.set("#StatusBarHideDelay.Visible", false);
                    commandBuilder.set("#HotbarHideDelay.Visible", false);
                    commandBuilder.set("#ReticleHideDelay.Visible", false);
                    commandBuilder.set("#AmmoHideDelay.Visible", false);
            }
            update(commandBuilder);
        }
    }

    private void handleStatusBarEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("StatusBar")) return;
        switch (data.action) {
            case "SelectStatusBar":
                config.setStatusBarTrigger(data.triggerStatusBar);
                boolean isDisabled = data.triggerStatusBar.equalsIgnoreCase("Disable");
                boolean isBothVisible = data.triggerStatusBar.equalsIgnoreCase("Both");
                boolean isThresholdDelayVisible = data.triggerStatusBar.equalsIgnoreCase("Threshold") || isBothVisible;
                boolean isCombatDelayVisible = data.triggerStatusBar.equalsIgnoreCase("Combat") || isBothVisible;
                commandBuilder.set("#StatusBarHideDelay.Visible", !isDisabled);
                commandBuilder.set("#StatusBarThresholdDelay.Visible", isThresholdDelayVisible);
                commandBuilder.set("#StatusBarCombatDelay.Visible", isCombatDelayVisible);
                break;
            case "StatusBarThresholdDelay":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#StatusBarThresholdDelay #Field.Value", data.statusBarThresholdDelay);
                    config.setStatusBarDelay(0, data.statusBarThresholdDelay);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.statusBarThresholdDelay;
                    if (thresholdValue < 0.1f) thresholdValue = 0.1f;
                    if (thresholdValue > 60.0f) thresholdValue = 60.0f;
                    commandBuilder.set("#StatusBarThresholdDelay #Field.Value", thresholdValue);
                    commandBuilder.set("#StatusBarThresholdDelay #Slider.Value", thresholdValue);
                    config.setStatusBarDelay(0, data.statusBarThresholdDelay);
                }
                break;
            case "StatusBarCombatDelay":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#StatusBarCombatDelay #Field.Value", data.statusBarCombatDelay);
                    config.setStatusBarDelay(1, data.statusBarCombatDelay);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.statusBarCombatDelay;
                    if (thresholdValue < 0.1f) thresholdValue = 0.1f;
                    if (thresholdValue > 60.0f) thresholdValue = 60.0f;
                    commandBuilder.set("#StatusBarCombatDelay #Field.Value", thresholdValue);
                    commandBuilder.set("#StatusBarCombatDelay #Slider.Value", thresholdValue);
                    config.setStatusBarDelay(1, data.statusBarCombatDelay);
                }
                break;
            case "SliderHealthThreshold":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#SliderHealthThreshold #Field.Value", data.thresholdHealth);
                    config.setHealthThreshold(data.thresholdHealth);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.thresholdHealth;
                    if (thresholdValue < 1.0f) thresholdValue = 1.0f;
                    if (thresholdValue > 100.0f) thresholdValue = 100.0f;
                    commandBuilder.set("#SliderHealthThreshold #Field.Value", thresholdValue);
                    commandBuilder.set("#SliderHealthThreshold #Slider.Value", thresholdValue);
                    config.setHealthThreshold(data.thresholdHealth);
                }
                break;
            case "SliderStaminaThreshold":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#SliderStaminaThreshold #Field.Value", data.thresholdStamina);
                    config.setStaminaThreshold(data.thresholdStamina);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.thresholdStamina;
                    if (thresholdValue < 1.0f) thresholdValue = 1.0f;
                    if (thresholdValue > 100.0f) thresholdValue = 100.0f;
                    commandBuilder.set("#SliderStaminaThreshold #Field.Value", thresholdValue);
                    commandBuilder.set("#SliderStaminaThreshold #Slider.Value", thresholdValue);
                    config.setStaminaThreshold(data.thresholdStamina);
                }
                break;
            case "SliderManaThreshold":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#SliderManaThreshold #Field.Value", data.thresholdMana);
                    config.setManaThreshold(data.thresholdMana);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.thresholdMana;
                    if (thresholdValue < 1.0f) thresholdValue = 1.0f;
                    if (thresholdValue > 100.0f) thresholdValue = 100.0f;
                    commandBuilder.set("#SliderManaThreshold #Field.Value", thresholdValue);
                    commandBuilder.set("#SliderManaThreshold #Slider.Value", thresholdValue);
                    config.setManaThreshold(data.thresholdMana);
                }
                break;
        }
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

    private void handleHotbarEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("Hotbar")) return;
        switch (data.action) {
            case "SelectHotbar":
                config.setHotbarTrigger(data.triggerHotbar);
                boolean isDisabled = data.triggerHotbar.equalsIgnoreCase("Disable");
                boolean isBothVisible = data.triggerHotbar.equalsIgnoreCase("Both");
                boolean isChangeDelayVisible = data.triggerHotbar.equalsIgnoreCase("Change") || isBothVisible;
                boolean isCombatDelayVisible = data.triggerHotbar.equalsIgnoreCase("Combat") || isBothVisible;
                commandBuilder.set("#HotbarHideDelay.Visible", !isDisabled);
                commandBuilder.set("#HotbarChangeDelay.Visible", isChangeDelayVisible);
                commandBuilder.set("#HotbarCombatDelay.Visible", isCombatDelayVisible);
                break;
            case "HotbarChangeDelay":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#HotbarChangeDelay #Field.Value", data.hotbarChangeDelay);
                    config.setHotbarDelay(0, data.hotbarChangeDelay);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.hotbarChangeDelay;
                    if (thresholdValue < 0.1f) thresholdValue = 0.1f;
                    if (thresholdValue > 60.0f) thresholdValue = 60.0f;
                    commandBuilder.set("#HotbarChangeDelay #Field.Value", thresholdValue);
                    commandBuilder.set("#HotbarChangeDelay #Slider.Value", thresholdValue);
                    config.setHotbarDelay(0, data.hotbarChangeDelay);
                }
                break;
            case "HotbarCombatDelay":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#HotbarCombatDelay #Field.Value", data.hotbarCombatDelay);
                    config.setHotbarDelay(1, data.hotbarCombatDelay);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.hotbarCombatDelay;
                    if (thresholdValue < 0.1f) thresholdValue = 0.1f;
                    if (thresholdValue > 60.0f) thresholdValue = 60.0f;
                    commandBuilder.set("#HotbarCombatDelay #Field.Value", thresholdValue);
                    commandBuilder.set("#HotbarCombatDelay #Slider.Value", thresholdValue);
                    config.setHotbarDelay(1, data.hotbarCombatDelay);
                }
                break;
        }
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

    private void handleReticleEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("Reticle")) return;
        switch (data.action) {
            case "SelectReticle":
                config.setReticleTrigger(data.triggerReticle);
                boolean isDisabled = data.triggerReticle.equalsIgnoreCase("Disable");
                boolean isHidden = data.triggerReticle.equalsIgnoreCase("Hide");
                commandBuilder.set("#ReticleHideDelay.Visible", !(isDisabled || isHidden));
                break;
            case "ReticleCombatDelay":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#ReticleCombatDelay #Field.Value", data.reticleCombatDelay);
                    config.setReticleDelay(data.reticleCombatDelay);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.reticleCombatDelay;
                    if (thresholdValue < 0.1f) thresholdValue = 0.1f;
                    if (thresholdValue > 60.0f) thresholdValue = 60.0f;
                    commandBuilder.set("#ReticleCombatDelay #Field.Value", thresholdValue);
                    commandBuilder.set("#ReticleCombatDelay #Slider.Value", thresholdValue);
                    config.setReticleDelay(data.reticleCombatDelay);
                }
                break;
        }
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

    private void handleAmmoEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("Ammo")) return;
        switch (data.action) {
            case "SelectAmmo":
                config.setAmmoTrigger(data.triggerAmmo);
                boolean isDisabled = data.triggerAmmo.equalsIgnoreCase("Disable");
                commandBuilder.set("#AmmoHideDelay.Visible", !isDisabled);
                break;
            case "AmmoReloadDelay":
                if (data.subAction.equalsIgnoreCase("Slider")) {
                    commandBuilder.set("#AmmoReloadDelay #Field.Value", data.ammoReloadDelay);
                    config.setAmmoDelay(data.ammoReloadDelay);
                } else if (data.subAction.equalsIgnoreCase("Field")) {
                    float thresholdValue = data.ammoReloadDelay;
                    if (thresholdValue < 1.0f) thresholdValue = 1.0f;
                    if (thresholdValue > 100.0f) thresholdValue = 100.0f;
                    commandBuilder.set("#AmmoReloadDelay #Field.Value", thresholdValue);
                    commandBuilder.set("#AmmoReloadDelay #Slider.Value", thresholdValue);
                    config.setAmmoDelay(data.ammoReloadDelay);
                }
                break;
        }
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

    private void handleMiscEvent(SettingsEventData data, UICommandBuilder commandBuilder, Ref<EntityStore> ref, Store<EntityStore> store) {
        if (!data.category.equalsIgnoreCase("Misc")) return;
        Player player = store.getComponent(ref, Player.getComponentType());
        assert player != null;
        switch (data.action) {
            case "CheckBoxCompass":
                config.setHideCompass(data.flagCompass);
                if (data.flagCompass) {
                    player.getHudManager().hideHudComponents(playerRef, HudComponent.Compass);
                } else {
                    player.getHudManager().showHudComponents(playerRef, HudComponent.Compass);
                }
                break;
            case "CheckBoxInputBindings":
                config.setHideInputBindings(data.flagInputBindings);
                if (data.flagInputBindings) {
                    player.getHudManager().hideHudComponents(playerRef, HudComponent.InputBindings);
                } else {
                    player.getHudManager().showHudComponents(playerRef, HudComponent.InputBindings);
                }
                break;
        }
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

}
