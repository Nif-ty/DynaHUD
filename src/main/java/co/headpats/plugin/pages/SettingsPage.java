package co.headpats.plugin.pages;

import co.headpats.plugin.DynaHudPlugin;
import co.headpats.plugin.components.SettingsData;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Arrays;

public class SettingsPage extends InteractiveCustomUIPage<SettingsPage.SettingsEventData> {

    public static class SettingsEventData {
        // Routing
        public String route;

        // Actions
        private String toggleHudAction;
        private String thresholdFieldAction;

        // Condition Settings
        private float healthThreshold;
        private float staminaThreshold;
        private float manaThreshold;

        public static final BuilderCodec<SettingsEventData> CODEC =
                BuilderCodec.builder(SettingsEventData.class, SettingsEventData::new)
                        .append(new KeyedCodec<>("Route", Codec.STRING),
                                (data, value) -> data.route = value, // Setter
                                (data) -> data.route).add() // Getter
                        .append(new KeyedCodec<>("ToggleHudAction", Codec.STRING),
                                (data, value) -> data.toggleHudAction = value,
                                data -> data.toggleHudAction).add()
                        .append(new KeyedCodec<>("ThresholdFieldAction", Codec.STRING),
                                (data, value) -> data.thresholdFieldAction = value,
                                data -> data.thresholdFieldAction).add()
                        .append(new KeyedCodec<>("@HealthThreshold", Codec.FLOAT),
                                (data, value) -> data.healthThreshold = value,
                                data -> data.healthThreshold).add()
                        .append(new KeyedCodec<>("@StaminaThreshold", Codec.FLOAT),
                                (data, value) -> data.staminaThreshold = value,
                                data -> data.staminaThreshold).add()
                        .append(new KeyedCodec<>("@ManaThreshold", Codec.FLOAT),
                                (data, value) -> data.manaThreshold = value,
                                data -> data.manaThreshold).add()
                        .build();
    }

    public SettingsPage(PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, SettingsEventData.CODEC);
    }

    @Override
    public void build(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl UICommandBuilder uiCommandBuilder,
            @NonNullDecl UIEventBuilder uiEventBuilder,
            @NonNullDecl Store<EntityStore> store
    ) {
        uiCommandBuilder.append("Pages/DynaHudSettings.ui");
        bindRouteEvents(uiEventBuilder);
        bindGeneralSettingsEvents(uiEventBuilder);
        bindConditionSettingsEvents(uiEventBuilder);
        routeContent("General", uiCommandBuilder);

        SettingsData settingsData = store.ensureAndGetComponent(ref, DynaHudPlugin.get().getSettingsDataComponentType());
        refreshGeneral(uiCommandBuilder, settingsData);
    }

    private void bindRouteEvents(UIEventBuilder uiEventBuilder) {
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#GeneralBtn",
                new EventData().append("Route", "RouteToGeneral")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#ConditionsBtn",
                new EventData().append("Route", "RouteToConditions")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#TimersBtn",
                new EventData().append("Route", "RouteToTimers")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#CloseBtn",
                new EventData().append("Route", "Close")
        );
    }

    private void bindGeneralSettingsEvents(UIEventBuilder uiEventBuilder) {
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HealthBarState #CheckBox",
                new EventData().append("ToggleHudAction", "HealthBar")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StaminaBarState #CheckBox",
                new EventData().append("ToggleHudAction", "StaminaBar")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#ManaBarState #CheckBox",
                new EventData().append("ToggleHudAction", "ManaBar")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StatusIconsState #CheckBox",
                new EventData().append("ToggleHudAction", "StatusIcons")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HotbarState #CheckBox",
                new EventData().append("ToggleHudAction", "Hotbar")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#ReticleState #CheckBox",
                new EventData().append("ToggleHudAction", "Reticle")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#CompassState #CheckBox",
                new EventData().append("ToggleHudAction", "Compass")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#AmmoIndicatorState #CheckBox",
                new EventData().append("ToggleHudAction", "AmmoIndicator")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#InputBindingsState #CheckBox",
                new EventData().append("ToggleHudAction", "InputBindings")
        );
    }

    private void bindConditionSettingsEvents(UIEventBuilder uiEventBuilder) {
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#HealthThresholdField",
                new EventData()
                        .append("@HealthThreshold", "#HealthThresholdField.Value")
                        .append("ThresholdFieldAction", "Health")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#StaminaThresholdField",
                new EventData()
                        .append("@StaminaThreshold", "#StaminaThresholdField.Value")
                        .append("ThresholdFieldAction", "Stamina")
        );
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.ValueChanged,
                "#ManaThresholdField",
                new EventData()
                        .append("@ManaThreshold", "#ManaThresholdField.Value")
                        .append("ThresholdFieldAction", "Mana")
        );
    }

    @Override
    public void handleDataEvent(
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl SettingsEventData eventData
    ) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        UIEventBuilder uiEventBuilder = new UIEventBuilder();
        SettingsData settingsData = store.ensureAndGetComponent(ref, DynaHudPlugin.get().getSettingsDataComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        assert player != null;

        // Routing State
        if (eventData.route != null) {
            switch (eventData.route) {
                case "RouteToGeneral":
                    routeContent("General", uiCommandBuilder);
                    refreshGeneral(uiCommandBuilder, settingsData);
                    sendUpdate(uiCommandBuilder, uiEventBuilder, false);
                    break;
                case "RouteToConditions":
                    routeContent("Conditions", uiCommandBuilder);
                    refreshConditions(uiCommandBuilder, settingsData);
                    sendUpdate(uiCommandBuilder, uiEventBuilder, false);
                    break;
                case "RouteToTimers":
                    routeContent("Timers", uiCommandBuilder);
                    refreshTimers(uiCommandBuilder, settingsData);
                    sendUpdate(uiCommandBuilder, uiEventBuilder, false);
                    break;
                case "Close":
                    player.getPageManager().setPage(ref, store, Page.None);
            }
        }

        // Settings Event Handlers
        generalSettingsEventHandler(player, uiCommandBuilder, eventData, settingsData);
        conditionSettingsEventHandler(uiCommandBuilder, eventData, settingsData);
        sendUpdate(uiCommandBuilder, uiEventBuilder, false);

    }

    private void routeContent(
            String route,
            UICommandBuilder uiCommandBuilder
    ) {
        boolean isGeneral = route.equals("General");
        boolean isConditions = route.equals("Conditions");
        boolean isTimers = route.equals("Timers");

        uiCommandBuilder.set("#GeneralBtn.Style.Default.LabelStyle.TextColor", isGeneral ? "#e8ce79" : "#9E9E9E");
        uiCommandBuilder.set("#GeneralContent.Visible", isGeneral);

        uiCommandBuilder.set("#ConditionsBtn.Style.Default.LabelStyle.TextColor", isConditions ? "#e8ce79" : "#9E9E9E");
        uiCommandBuilder.set("#ConditionsContent.Visible", isConditions);

        uiCommandBuilder.set("#TimersBtn.Style.Default.LabelStyle.TextColor", isTimers ? "#e8ce79" : "#9E9E9E");
        uiCommandBuilder.set("#TimersContent.Visible", isTimers);

        switch (route) {
            case "General":
                uiCommandBuilder.set("#Title.Text", "General Settings");
                uiCommandBuilder.set("#SubContext.Text", "Enable/Disable mod functionalities:");
                break;
            case "Conditions":
                uiCommandBuilder.set("#Title.Text", "Visibility Conditions");
                uiCommandBuilder.set("#SubContext.Text", "Trigger conditions for hiding HUD components:");
                break;
            case "Timers":
                uiCommandBuilder.set("#Title.Text", "Delay Timers");
                uiCommandBuilder.set("#SubContext.Text", "Time delays for showing/hiding HUD components:");
                break;
        }
    }

    private void generalSettingsEventHandler(
            Player player,
            UICommandBuilder uiCommandBuilder,
            SettingsEventData eventData,
            SettingsData settingsData
    ) {
        if (eventData.toggleHudAction == null) return;
        HudManager hudManager = player.getHudManager();

        switch (eventData.toggleHudAction) {
            case "HealthBar":
                settingsData.switchHealthBarState();
                uiCommandBuilder.set("#HealthBarState #CheckBox.Value", settingsData.isHealthBarEnabled());
                break;
            case "StaminaBar":
                settingsData.switchStaminaBarState();
                uiCommandBuilder.set("#StaminaBarState #CheckBox.Value", settingsData.isStaminaBarEnabled());
                break;
            case "ManaBar":
                settingsData.switchManaBarState();
                uiCommandBuilder.set("#ManaBarState #CheckBox.Value", settingsData.isManaBarEnabled());
                break;
            case "StatusIcons":
                settingsData.switchStatusIconsState();
                uiCommandBuilder.set("#StatusIconsState #CheckBox.Value", settingsData.isStatusIconsEnabled());
                if (settingsData.isStatusIconsEnabled()) {
                    hudManager.hideHudComponents(playerRef, HudComponent.StatusIcons);
                } else {
                    hudManager.showHudComponents(playerRef, HudComponent.StatusIcons);
                }
                break;
            case "Hotbar":
                settingsData.switchHotbarState();
                uiCommandBuilder.set("#HotbarState #CheckBox.Value", settingsData.isHotbarEnabled());
                break;
            case "Reticle":
                settingsData.switchReticleState();
                uiCommandBuilder.set("#ReticleState #CheckBox.Value", settingsData.isReticleEnabled());
                break;
            case "Compass":
                settingsData.switchCompassState();
                uiCommandBuilder.set("#CompassState #CheckBox.Value", settingsData.isCompassEnabled());
                break;
            case "AmmoIndicator":
                settingsData.switchAmmoIndicatorState();
                uiCommandBuilder.set("#AmmoIndicatorState #CheckBox.Value", settingsData.isAmmoIndicatorEnabled());
                break;
            case "InputBindings":
                settingsData.switchInputBindingsState();
                uiCommandBuilder.set("#InputBindingsState #CheckBox.Value", settingsData.isInputBindingsEnabled());
                if (settingsData.isInputBindingsEnabled()) {
                    hudManager.hideHudComponents(playerRef, HudComponent.InputBindings);
                } else {
                    hudManager.showHudComponents(playerRef, HudComponent.InputBindings);
                }
                break;
        }
    }

    private void refreshGeneral(
            UICommandBuilder uiCommandBuilder,
            SettingsData settingsData
    ) {
        uiCommandBuilder.set("#HealthBarState #CheckBox.Value", settingsData.isHealthBarEnabled());
        uiCommandBuilder.set("#StaminaBarState #CheckBox.Value", settingsData.isStaminaBarEnabled());
        uiCommandBuilder.set("#ManaBarState #CheckBox.Value", settingsData.isManaBarEnabled());
        uiCommandBuilder.set("#StatusIconsState #CheckBox.Value", settingsData.isStatusIconsEnabled());
        uiCommandBuilder.set("#HotbarState #CheckBox.Value", settingsData.isHotbarEnabled());
        uiCommandBuilder.set("#ReticleState #CheckBox.Value", settingsData.isReticleEnabled());
        uiCommandBuilder.set("#CompassState #CheckBox.Value", settingsData.isCompassEnabled());
        uiCommandBuilder.set("#AmmoIndicatorState #CheckBox.Value", settingsData.isAmmoIndicatorEnabled());
        uiCommandBuilder.set("#InputBindingsState #CheckBox.Value", settingsData.isInputBindingsEnabled());
    }

    private void conditionSettingsEventHandler(
            UICommandBuilder uiCommandBuilder,
            SettingsEventData eventData,
            SettingsData settingsData
    ) {
        if (eventData.thresholdFieldAction == null) return;
        switch (eventData.thresholdFieldAction) {
            case "Health":
                float healthThreshold = eventData.healthThreshold;
                if (healthThreshold < 0.0f) healthThreshold = 0.0f;
                if (healthThreshold > 100.0f) healthThreshold = 100.0f;
                settingsData.setHealthThreshold(healthThreshold);
                uiCommandBuilder.set("#HealthThresholdField.Value",  healthThreshold);
                break;
            case "Stamina":
                float staminaThreshold = eventData.staminaThreshold;
                if (staminaThreshold < 0.0f) staminaThreshold = 0.0f;
                if (staminaThreshold > 100.0f) staminaThreshold = 100.0f;
                settingsData.setStaminaThreshold(staminaThreshold);
                uiCommandBuilder.set("#StaminaThresholdField.Value",  staminaThreshold);
                break;
            case "Mana":
                float manaThreshold = eventData.manaThreshold;
                if (manaThreshold < 0.0f) manaThreshold = 0.0f;
                if (manaThreshold > 100.0f) manaThreshold = 100.0f;
                settingsData.setManaThreshold(manaThreshold);
                uiCommandBuilder.set("#ManaThresholdField.Value",  manaThreshold);
        }
    }

    private void refreshConditions(
            UICommandBuilder uiCommandBuilder,
            SettingsData settingsData
    ) {
        uiCommandBuilder.set("#HealthThresholdField.Value", settingsData.getHealthThreshold());
        uiCommandBuilder.set("#StaminaThresholdField.Value", settingsData.getStaminaThreshold());
        uiCommandBuilder.set("#ManaThresholdField.Value", settingsData.getManaThreshold());
    }

    private void refreshTimers(
            UICommandBuilder uiCommandBuilder,
            SettingsData settingsData
    ) {

    }

}
