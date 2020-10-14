package tv.superawesome.sdk.publisher.common.models

import android.graphics.Color
import tv.superawesome.sdk.publisher.common.network.Environment

object Constants {
    const val defaultSuperAwesomeUrl = "https://ads.superawesome.tv"
    const val defaultClickThresholdInMs: Long = 5000
    const val defaultBumperPageShowTimeInSec: Int = 5

    const val defaultBackgroundColorEnabled = false
    val backgroundColorGray = Color.rgb(224, 224, 224)

    const val defaultTestMode = false
    const val defaultParentalGate = false
    const val defaultBumperPage = false
    const val defaultCloseAtEnd = true
    const val defaultCloseButton = false
    const val defaultSmallClick = false
    const val defaultBackButtonEnabled = true

    val defaultOrientation = Orientation.any
    val defaultEnvironment = Environment.production
    const val defaultMoatLimitingState = true
    val defaultStartDelay = AdRequest.StartDelay.PreRoll
}