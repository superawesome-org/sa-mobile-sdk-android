package tv.superawesome.demoapp.model

import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.state.CloseButtonState

data class SettingsData(
    val environment: SAConfiguration = SAConfiguration.PRODUCTION,
    val closeButtonState: CloseButtonState = CloseButtonState.VisibleWithDelay,
    val bumperEnabled: Boolean = false,
    val parentalEnabled: Boolean = false,
    val playEnabled: Boolean = true,
    val muteOnStart: Boolean = false,
    val videoWarnOnClose: Boolean = false,
    val closeAtEnd: Boolean = true,
)
