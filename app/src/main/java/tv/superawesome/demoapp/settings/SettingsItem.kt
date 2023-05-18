package tv.superawesome.demoapp.settings

import tv.superawesome.sdk.publisher.common.models.CloseButtonState

enum class Settings(val label: String) {
    CloseButton("Close button"),
    BumperPage("Bumper page"),
    ParentalGate("Parental gate"),
    Playback("Play ad Immediately"),
    MuteOnStart("Mute on start"),
    LeaveVideoWarning("Leave video warning"),
    CloseAtEnd("Close at the end");

    fun options(): List<SettingsItemOption<Any>> = when (this) {
        CloseButton -> listOf(
            SettingsItemOption(
                "No delay",
                "SettingsItem.Buttons.CloseImmediately",
                CloseButtonState.VisibleImmediately
            ),
            SettingsItemOption(
                "Delay",
                "SettingsItem.Buttons.CloseDelayed",
                CloseButtonState.VisibleWithDelay
            ),
            SettingsItemOption(
                "Hidden",
                "SettingsItem.Buttons.CloseHidden",
                CloseButtonState.Hidden
            )
        )

        BumperPage -> listOf(
            SettingsItemOption("Enable", "SettingsItem.Buttons.BumperEnable", true),
            SettingsItemOption("Disable", "SettingsItem.Buttons.BumperDisable", false),
        )

        ParentalGate -> listOf(
            SettingsItemOption("Enable", "SettingsItem.Buttons.ParentalGateEnable", true),
            SettingsItemOption("Disable", "SettingsItem.Buttons.ParentalGateDisable", false),
        )

        Playback -> listOf(
            SettingsItemOption("Enable", "SettingsItem.Buttons.PlaybackEnable", true),
            SettingsItemOption("Disable", "SettingsItem.Buttons.PlaybackDisable", false),
        )

        MuteOnStart -> listOf(
            SettingsItemOption("Enable", "SettingsItem.Buttons.MuteEnable", true),
            SettingsItemOption("Disable", "SettingsItem.Buttons.MuteDisable", false),
        )

        LeaveVideoWarning -> listOf(
            SettingsItemOption("Enable", "SettingsItem.Buttons.VideoCloseDialogEnable", true),
            SettingsItemOption(
                "Disable",
                "SettingsItem.Buttons.VideoCloseDialogDisable",
                false
            ),
        )

        CloseAtEnd -> listOf(
            SettingsItemOption("Enable", "SettingsItem.Buttons.VideoCloseAtEndEnable", true),
            SettingsItemOption("Disable", "SettingsItem.Buttons.VideoCloseAtEndDisable", false),
        )

    }
}

data class SettingsData(
    val closeButtonState: CloseButtonState = CloseButtonState.VisibleWithDelay,
    val bumperEnabled: Boolean = false,
    val parentalEnabled: Boolean = false,
    val playEnabled: Boolean = true,
    val muteOnStart: Boolean = false,
    val videoWarnOnClose: Boolean = false,
    val closeAtEnd: Boolean = true,
)

object DataStore {
    var data = SettingsData()
        private set

    fun updateSettings(item: SettingsItem<Any>, value: Any) {
        data = when (item.item) {
            Settings.CloseButton -> data.copy(closeButtonState = value as CloseButtonState)
            Settings.BumperPage -> data.copy(bumperEnabled = value as Boolean)
            Settings.ParentalGate -> data.copy(parentalEnabled = value as Boolean)
            Settings.Playback -> data.copy(playEnabled = value as Boolean)
            Settings.MuteOnStart -> data.copy(muteOnStart = value as Boolean)
            Settings.LeaveVideoWarning -> data.copy(videoWarnOnClose = value as Boolean)
            Settings.CloseAtEnd -> data.copy(closeAtEnd = value as Boolean)
        }
    }

    fun reset() {
        data = SettingsData()
    }

    fun toList(): List<SettingsItem<Any>> = listOf(
        SettingsItem(
            Settings.CloseButton,
            data.closeButtonState,
        ),
        SettingsItem(
            Settings.BumperPage,
            data.bumperEnabled,
        ),
        SettingsItem(
            Settings.ParentalGate,
            data.parentalEnabled,
        ),
        SettingsItem(
            Settings.Playback,
            data.playEnabled,
        ),
        SettingsItem(
            Settings.MuteOnStart,
            data.muteOnStart,
        ),
        SettingsItem(
            Settings.LeaveVideoWarning,
            data.videoWarnOnClose,
        ),
        SettingsItem(
            Settings.CloseAtEnd,
            data.closeAtEnd,
        ),
    )
}

data class SettingsItemOption<T>(
    val label: String,
    val contentDescription: String,
    val value: T
)

data class SettingsItem<T>(
    val item: Settings,
    val selected: T,
)