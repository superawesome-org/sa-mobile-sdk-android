package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.network.Environment

object Constants {
    const val defaultClickThresholdInMs: Long = 5
    const val defaultTestMode = false
    const val defaultParentalGate = false
    const val defaultBumperPage = false
    const val defaultCloseAtEnd = true
    const val defaultCloseButton = false
    const val defaultSmallClick = false

    //    const val defaultOrientation = GradientDrawable.Orientation.any
    val defaultEnvironment = Environment.production
    const val defaultMoatLimitingState = true
    val defaultStartDelay = AdRequest.StartDelay.PreRoll
}