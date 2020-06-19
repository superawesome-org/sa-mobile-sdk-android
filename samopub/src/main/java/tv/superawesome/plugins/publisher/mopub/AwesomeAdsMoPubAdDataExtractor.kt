package tv.superawesome.plugins.publisher.mopub

import com.mopub.mobileads.AdData
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.SADefaults
import tv.superawesome.sdk.publisher.SAOrientation

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
        get() = try {
            adData.extras[Keys.placementId]?.toInt() ?: SADefaults.defaultPlacementId()
        } catch (e: Exception) {
            SADefaults.defaultPlacementId()
        }

    val isTestEnabled: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.testEnabled])
        } catch (e: Exception) {
            SADefaults.defaultTestMode()
        }

    val isParentalGateEnabled: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.parentalGate])
        } catch (e: Exception) {
            SADefaults.defaultParentalGate()
        }

    val isBumperPageEnabled: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.bumperPage])
        } catch (e: Exception) {
            SADefaults.defaultBumperPage()
        }

    val configuration: SAConfiguration
        get() = try {
            val config = adData.extras[Keys.configuration] ?: ""
            if (config == "STAGING") SAConfiguration.STAGING else SAConfiguration.PRODUCTION
        } catch (e: Exception) {
            SADefaults.defaultConfiguration()
        }

    val orientation: SAOrientation
        get() = try {
            val orient: String = adData.extras[Keys.orientation] ?: ""
            var orientation = SADefaults.defaultOrientation()
            if (orient == "PORTRAIT") {
                orientation = SAOrientation.PORTRAIT
            } else if (orient == "LANDSCAPE") {
                orientation = SAOrientation.LANDSCAPE
            }
            orientation
        } catch (e: Exception) {
            SADefaults.defaultOrientation()
        }

    val adUnitId: String?
        get() = try {
            adData.extras[Keys.adUnit]
        } catch (e: java.lang.Exception) {
            null
        }

    val play: String?
        get() = try {
            adData.extras[Keys.playbackMode]
        } catch (e: java.lang.Exception) {
            null
        }

    val shouldShowCloseButton: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.shouldShowClose])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultCloseButton()
        }

    val shouldAutomaticallyCloseAtEnd: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.shouldAutoClose])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultCloseAtEnd()
        }

    val shouldShowSmallClickButton: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.videoButtonStyle])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultCloseAtEnd()
        }

    val enableBackButton: Boolean
        get() = try {
            java.lang.Boolean.valueOf(adData.extras[Keys.backButton])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultBackButton()
        }
}