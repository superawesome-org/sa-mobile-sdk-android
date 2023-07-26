package tv.superawesome.sdk.publisher.common.models

import android.graphics.Color
import tv.superawesome.sdk.publisher.common.network.Environment

object Constants {
    const val defaultSuperAwesomeUrl = "https://ads.superawesome.tv"
    const val defaultSafeAdUrl = "https://ads.superawesome.tv/v2/safead"
    const val defaultClickThresholdInMs: Long = 5000
    const val defaultBumperPageShowTimeInSec: Int = 3
    const val defaultBackgroundColorEnabled = false
    const val defaultTestMode = false
    const val defaultParentalGate = false
    const val defaultBumperPage = false
    const val defaultCloseAtEnd = true
    const val defaultSmallClick = false
    const val defaultBackButtonEnabled = true
    const val defaultCloseWarning = false
    const val defaultMuteOnStart = false

    @JvmField
    val defaultCloseButtonState = CloseButtonState.Hidden

    @JvmField
    val defaultOrientation = Orientation.Any

    @JvmField
    val defaultEnvironment = Environment.Production

    @JvmField
    val defaultStartDelay = tv.superawesome.sdk.publisher.common.models.AdRequest.StartDelay.PreRoll

    @JvmField
    val backgroundColorGray = Color.rgb(224, 224, 224)

    object Keys {
        const val placementId = "placementId"
        const val config = "config"
        const val html = "html"
        const val baseUrl = "baseUrl"
    }
}
