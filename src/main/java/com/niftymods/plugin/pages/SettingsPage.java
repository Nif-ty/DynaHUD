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
        private float thresholdHealth;
        private float thresholdStamina;
        private float thresholdMana;

        private String triggerHotbar;
        private String triggerReticle;

        private boolean flagCompass;
        private boolean flagAmmo;
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
                        .append(new KeyedCodec<>("@TriggerReticle", Codec.STRING),
                                (data, value) -> data.triggerReticle = value,
                                (data) -> data.triggerReticle).add()
                        .append(new KeyedCodec<>("@FlagCompass", Codec.BOOLEAN),
                                (data, value) -> data.flagCompass = value,
                                (data) -> data.flagCompass).add()
                        .append(new KeyedCodec<>("@FlagAmmo", Codec.BOOLEAN),
                                (data, value) -> data.flagAmmo = value,
                                (data) -> data.flagAmmo).add()
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

        // Event Binding
        bindPresetEvents(eventBuilder);
        bindStatusBarEvents(eventBuilder);
        bindHotbarEvents(eventBuilder);
        bindReticleEvents(eventBuilder);
        bindMiscEvents(eventBuilder);

        update(commandBuilder);
    }

    private void update(UICommandBuilder commandBuilder) {
        // Preset
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());

        // Status Bar
        commandBuilder.set("#SelectStatusBar #Dropdown.Value", config.getStatusBarTrigger());
        commandBuilder.set("#SliderHealthThreshold #Slider.Value", (int) config.getHealthThreshold());
        commandBuilder.set("#SliderHealthThreshold #Field.Value", config.getHealthThreshold());
        commandBuilder.set("#SliderStaminaThreshold #Slider.Value", (int) config.getStaminaThreshold());
        commandBuilder.set("#SliderStaminaThreshold #Field.Value", config.getStaminaThreshold());
        commandBuilder.set("#SliderManaThreshold #Slider.Value", (int) config.getManaThreshold());
        commandBuilder.set("#SliderManaThreshold #Field.Value", config.getManaThreshold());

        // Hotbar
        commandBuilder.set("#SelectHotbar #Dropdown.Value", config.getHotbarTrigger());

        // Reticle
        commandBuilder.set("#SelectReticle #Dropdown.Value", config.getReticleTrigger());

        // Misc
        commandBuilder.set("#CheckBoxCompass #CheckBox.Value", config.isHideCompass());
        commandBuilder.set("#CheckBoxAmmo #CheckBox.Value", config.isHideAmmo());
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
                "#CheckBoxAmmo #CheckBox",
                new EventData()
                        .append("Category", "Misc")
                        .append("Action", "CheckBoxAmmo")
                        .append("@FlagAmmo", "#CheckBoxAmmo #CheckBox.Value")
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
                    break;
                case "Disable":
                    config.setPreset("Disable");
                    config.setStatusBarTrigger("Disable");
                    config.setHotbarTrigger("Disable");
                    config.setReticleTrigger("Disable");
                    config.setHideCompass(false);
                    config.setHideAmmo(false);
                    config.setHideInputBindings(false);
            }
            update(commandBuilder);
        }
    }

    private void handleStatusBarEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("StatusBar")) return;
        switch (data.action) {
            case "SelectStatusBar":
                config.setStatusBarTrigger(data.triggerStatusBar);
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
                    commandBuilder.set("#SliderHealthThreshold #Slider.Value", (int) thresholdValue);
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
                    commandBuilder.set("#SliderStaminaThreshold #Slider.Value", (int) thresholdValue);
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
                    commandBuilder.set("#SliderManaThreshold #Slider.Value", (int) thresholdValue);
                    config.setManaThreshold(data.thresholdMana);
                }
                break;
        }
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

    private void handleHotbarEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("Hotbar")) return;
        if (!data.action.equalsIgnoreCase("SelectHotbar")) return;
        config.setHotbarTrigger(data.triggerHotbar);
        config.setPreset("Custom");
        commandBuilder.set("#SelectPreset #Dropdown.Value", config.getPreset());
    }

    private void handleReticleEvent(SettingsEventData data, UICommandBuilder commandBuilder) {
        if (!data.category.equalsIgnoreCase("Reticle")) return;
        if (!data.action.equalsIgnoreCase("SelectReticle")) return;
        config.setReticleTrigger(data.triggerReticle);
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
            case "CheckBoxAmmo":
                config.setHideAmmo(data.flagAmmo);
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
