package tv.superawesome.demoapp.settings

import tv.superawesome.demoapp.HasEnvironment
import tv.superawesome.demoapp.SDKEnvironment
import tv.superawesome.sdk.publisher.models.CloseButtonState
import tv.superawesome.sdk.publisher.models.Orientation

enum class Settings(val label: String) {
    Environment("Environment"),
    CloseButton("Close button"),
    CloseButtonDelay("Close delay"),
    BumperPage("Bumper page"),
    ParentalGate("Parental gate"),
    Playback("Play ad Immediately"),
    MuteOnStart("Mute on start"),
    LeaveVideoWarning("Leave video warning"),
    CloseAtEnd("Close at the end"),
    AdOrientation("Orientation");

    fun options(): List<SettingsItemOption<Any>> = when (this) {
        Environment -> listOf(
            SettingsItemOption(
                "Production",
                "SettingsItem.Buttons.Environment.Production",
                SDKEnvironment.Production,
            ),
            SettingsItemOption(
                "Staging",
                "SettingsItem.Buttons.Environment.Staging",
                SDKEnvironment.Staging,
            ),
        )
        CloseButton -> listOf(
            SettingsItemOption(
                "No delay",
                "SettingsItem.Buttons.CloseImmediately",
                CloseButtonState.VisibleImmediately,
            ),
            SettingsItemOption(
                "Delay",
                "SettingsItem.Buttons.CloseDelayed",
                CloseButtonState.VisibleWithDelay,
            ),
            SettingsItemOption(
                "Hidden",
                "SettingsItem.Buttons.CloseHidden",
                CloseButtonState.Hidden,
            ),
            SettingsItemOption(
                "Custom",
                "SettingsItem.Buttons.CloseCustom",
                CloseButtonState.Custom(0.0),
            ),
        )

        CloseButtonDelay -> listOf(
            SettingsItemOption(
                "5s",
                "SettingsItem.Buttons.5s",
                5.0,
            ),
            SettingsItemOption(
                "10s",
                "SettingsItem.Buttons.10s",
                10.0,
            ),
            SettingsItemOption(
                "15s",
                "SettingsItem.Buttons.15s",
                15.0,
            ),
            SettingsItemOption(
                "30s",
                "SettingsItem.Buttons.30s",
                30.0,
            ),
        )

        BumperPage -> listOf(
            SettingsItemOption(
                "Enable",
                "SettingsItem.Buttons.BumperEnable",
                true,
            ),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.BumperDisable",
                false,
            ),
        )

        ParentalGate -> listOf(
            SettingsItemOption(
                "Enable",
                "SettingsItem.Buttons.ParentalGateEnable",
                true,
            ),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.ParentalGateDisable",
                false,
            ),
        )

        Playback -> listOf(
            SettingsItemOption(
                "Enable",
                "SettingsItem.Buttons.PlaybackEnable",
                true,
            ),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.PlaybackDisable",
                false,
            ),
        )

        MuteOnStart -> listOf(
            SettingsItemOption(
                "Enable",
                "SettingsItem.Buttons.MuteEnable",
                true,
            ),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.MuteDisable",
                false,
            ),
        )

        LeaveVideoWarning -> listOf(
            SettingsItemOption(
                "Enable",
                "SettingsItem.Buttons.VideoCloseDialogEnable",
                true,
            ),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.VideoCloseDialogDisable",
                false
            ),
        )

        CloseAtEnd -> listOf(
            SettingsItemOption(
                "Enable",
                "SettingsItem.Buttons.VideoCloseAtEndEnable",
                true,
            ),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.VideoCloseAtEndDisable",
                false,
            ),
        )

        AdOrientation -> listOf(
            SettingsItemOption(
                "Any",
                "SettingsItem.Buttons.OrientationAny",
                Orientation.Any,
            ),
            SettingsItemOption(
                "Landscape",
                "SettingsItem.Buttons.OrientationLandscape",
                Orientation.Landscape,
            ),
            SettingsItemOption(
                "Portrait",
                "SettingsItem.Buttons.OrientationPortrait",
                Orientation.Portrait,
            ),
        )
    }
}

data class SettingsData(
    val environment: SDKEnvironment = SDKEnvironment.Production,
    val useBaseModule: Boolean = true,
    val closeButtonState: CloseButtonState = CloseButtonState.VisibleWithDelay,
    val closeButtonDelay: Double = 5.0,
    val bumperEnabled: Boolean = false,
    val parentalEnabled: Boolean = false,
    val playEnabled: Boolean = true,
    val muteOnStart: Boolean = false,
    val videoWarnOnClose: Boolean = false,
    val closeAtEnd: Boolean = true,
    val orientation: Orientation = Orientation.Any,
)

object DataStore {
    var data = SettingsData(
        environment = HasEnvironment.environment,
    )
        private set

    fun updateSettings(item: Settings, value: Any) {
        data = when (item) {
            Settings.Environment -> data.copy(environment = value as SDKEnvironment)
            Settings.CloseButton -> data.copy(
                closeButtonState = if (value is CloseButtonState.Custom) {
                    CloseButtonState.Custom(data.closeButtonDelay)
                } else {
                    value as CloseButtonState
                }
            )
            Settings.CloseButtonDelay -> {
                var newData = data.copy(closeButtonDelay = value as Double)
                if (data.closeButtonState is CloseButtonState.Custom) {
                    newData = newData.copy(
                        closeButtonState = CloseButtonState.Custom(newData.closeButtonDelay)
                    )
                }
                newData
            }
            Settings.BumperPage -> data.copy(bumperEnabled = value as Boolean)
            Settings.ParentalGate -> data.copy(parentalEnabled = value as Boolean)
            Settings.Playback -> data.copy(playEnabled = value as Boolean)
            Settings.MuteOnStart -> data.copy(muteOnStart = value as Boolean)
            Settings.LeaveVideoWarning -> data.copy(videoWarnOnClose = value as Boolean)
            Settings.CloseAtEnd -> data.copy(closeAtEnd = value as Boolean)
            Settings.AdOrientation -> data.copy(orientation = value as Orientation)
        }
    }

    fun reset() {
        data = SettingsData(
            environment = HasEnvironment.environment,
        )
    }

    fun toList(): List<SettingsItem<Any>> = listOf(
        SettingsItem(Settings.Environment, data.environment),
        SettingsItem(Settings.CloseButton, data.closeButtonState),
        SettingsItem(Settings.CloseButtonDelay, data.closeButtonDelay),
        SettingsItem(Settings.BumperPage, data.bumperEnabled),
        SettingsItem(Settings.ParentalGate, data.parentalEnabled),
        SettingsItem(Settings.Playback, data.playEnabled),
        SettingsItem(Settings.MuteOnStart, data.muteOnStart),
        SettingsItem(Settings.LeaveVideoWarning, data.videoWarnOnClose),
        SettingsItem(Settings.CloseAtEnd, data.closeAtEnd),
        SettingsItem(Settings.AdOrientation, data.orientation),
    )
}

data class SettingsItemOption<T>(
    val label: String,
    val contentDescription: String,
    val value: T,
)

data class SettingsItem<T>(
    val item: Settings,
    val selected: T,
)
