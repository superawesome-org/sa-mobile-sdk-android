package tv.superawesome.sdk.publisher.common.mopub

import com.mopub.mobileads.AdData
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.network.Environment

class AwesomeAdsMoPubAdDataExtractor(private val adData: AdData) {
    object Keys {
        const val adUnit = "com_mopub_ad_unit_id"
        const val placementId = "placementId"
        const val testEnabled = "isTestEnabled"
        const val parentalGate = "isParentalGateEnabled"
        const val bumperPage = "isBumperPageEnabled"
        const val orientation = "orientation"
        const val configuration = "configuration"
        const val shouldShowClose = "shouldShowCloseButton"
        const val shouldAutoClose = "shouldAutomaticallyCloseAtEnd"
        const val videoButtonStyle = "shouldShowSmallClickButton"
        const val backButton = "shouldEnableBackButton"
        const val playbackMode = "playBackMode"
    }

    val placementId: Int
        get() = adData.extras[Keys.placementId]?.toInt() ?: Constants.defaultPlacementId

    val isTestEnabled: Boolean
        get() = adData.extras[Keys.testEnabled]?.toBoolean() ?: Constants.defaultTestMode

    val isParentalGateEnabled: Boolean
        get() = adData.extras[Keys.parentalGate]?.toBoolean() ?: Constants.defaultParentalGate

    val isBumperPageEnabled: Boolean
        get() = adData.extras[Keys.bumperPage]?.toBoolean() ?: Constants.defaultBumperPage

    val configuration: Environment
        get() {
            val config = adData.extras[Keys.configuration] ?: ""
            return if (config == "STAGING") Environment.Staging else Environment.Production
        }

    val orientation: Orientation
        get() {
            val orient: String = adData.extras[Keys.orientation] ?: ""
            var orientation = Constants.defaultOrientation
            if (orient == "PORTRAIT") {
                orientation = Orientation.Portrait
            } else if (orient == "LANDSCAPE") {
                orientation = Orientation.Landscape
            }
            return orientation
        }

    val adUnitId: String?
        get() = adData.extras[Keys.adUnit]

    val play: String?
        get() = adData.extras[Keys.playbackMode]

    val shouldShowCloseButton: Boolean
        get() = adData.extras[Keys.shouldShowClose]?.toBoolean() ?: Constants.defaultCloseButton

    val shouldAutomaticallyCloseAtEnd: Boolean
        get() = adData.extras[Keys.shouldAutoClose]?.toBoolean() ?: Constants.defaultCloseAtEnd

    val shouldShowSmallClickButton: Boolean
        get() = adData.extras[Keys.videoButtonStyle]?.toBoolean() ?: Constants.defaultCloseAtEnd

    val enableBackButton: Boolean
        get() = adData.extras[Keys.backButton]?.toBoolean() ?: Constants.defaultBackButtonEnabled
}