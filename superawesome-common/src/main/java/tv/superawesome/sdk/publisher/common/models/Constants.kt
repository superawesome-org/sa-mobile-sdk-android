package tv.superawesome.sdk.publisher.common.models

import android.graphics.Color
import tv.superawesome.sdk.publisher.common.network.Environment
import tv.superawesome.sdk.publisher.common.state.CloseButtonState

object Constants {
    const val defaultSuperAwesomeUrl = "https://ads.superawesome.tv"
    const val defaultSafeAdUrl = "https://ads.superawesome.tv/v2/safead"
    const val defaultClickThresholdInMs: Long = 5000
    const val defaultBumperPageShowTimeInSec: Int = 5

    const val defaultBackgroundColorEnabled = false
    val backgroundColorGray = Color.rgb(224, 224, 224)

    const val defaultTestMode = false
    const val defaultParentalGate = false
    const val defaultBumperPage = false
    const val defaultCloseAtEnd = true
    const val defaultSmallClick = false
    const val defaultBackButtonEnabled = true
    const val defaultCloseWarning = false

    val defaultCloseButtonState = CloseButtonState.Hidden
    val defaultOrientation = Orientation.Any
    val defaultEnvironment = Environment.Production
    const val defaultMoatLimitingState = true
    val defaultStartDelay = AdRequest.StartDelay.PreRoll
    const val defaultMuteOnStart = false

    object Keys {
        const val placementId = "placementId"
        const val config = "config"
        const val html = "html"
    }
}