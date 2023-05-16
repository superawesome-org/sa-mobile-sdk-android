package tv.superawesome.demoapp.model

import tv.superawesome.sdk.publisher.common.models.CloseButtonState

data class SettingsData(
    val closeButtonState: CloseButtonState = CloseButtonState.VisibleWithDelay,
    val bumperEnabled: Boolean = false,
    val parentalEnabled: Boolean = false,
    val playEnabled: Boolean = true,
    val muteOnStart: Boolean = false,
    val videoWarnOnClose: Boolean = false,
    val closeAtEnd: Boolean = true,
)
