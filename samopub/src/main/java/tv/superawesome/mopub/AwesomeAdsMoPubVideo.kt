package tv.superawesome.mopub

import android.app.Activity
import android.content.Context
import com.mopub.common.LifecycleListener
import com.mopub.common.MoPubReward
import com.mopub.common.logging.MoPubLog
import com.mopub.mobileads.AdData
import com.mopub.mobileads.BaseAd
import com.mopub.mobileads.MoPubErrorCode
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sasession.defines.SARTBStartDelay
import tv.superawesome.sdk.publisher.SADefaults
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAOrientation
import tv.superawesome.sdk.publisher.SAVideoAd

class AwesomeAdsMoPubVideo : BaseAd() {
    private val adapterName: String = AwesomeAdsMoPubVideo::class.java.simpleName

    private var adUnitId: String? = null
    private var placementId: Int = 0
    private var context: Context? = null
    private var isTestEnabled = false
    private var isParentalGateEnabled = false
    private var isBumperPageEnabled = false
    private var shouldShowCloseButton = false
    private var shouldAutomaticallyCloseAtEnd = false
    private var shouldShowSmallClickButton = false
    private var enableBackButton = false
    private var orientation: SAOrientation? = null
    private var configuration: SAConfiguration? = null
    private var playback: SARTBStartDelay? = null

    override fun onInvalidate() {
    }

    override fun getLifecycleListener(): LifecycleListener? = null

    override fun getAdNetworkId(): String = adUnitId.toString()

    override fun checkAndInitializeSdk(activity: Activity, adData: AdData): Boolean {
        try {
            adUnitId = adData.extras[AwesomeAdsMoPub.adUnit].toString()
        } catch (e: java.lang.Exception) {
            // do nothing
        }
        placementId = try {
            adData.extras[AwesomeAdsMoPub.placementId]?.toInt()
                    ?: SADefaults.defaultPlacementId()
        } catch (e: java.lang.Exception) {
            SADefaults.defaultPlacementId()
        }
        isTestEnabled = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.testEnabled])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultTestMode()
        }
        isParentalGateEnabled = try {
            java.lang.Boolean.valueOf(adData.extras.get(AwesomeAdsMoPub.parentalGate))
        } catch (e: java.lang.Exception) {
            SADefaults.defaultParentalGate()
        }
        isBumperPageEnabled = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.bumperPage])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultBumperPage()
        }
        configuration = SADefaults.defaultConfiguration()
        try {
            val config: String? = adData.extras[AwesomeAdsMoPub.configuration]
            if (config != null && (config == "STAGING")) {
                configuration = SAConfiguration.STAGING
            }
        } catch (e: java.lang.Exception) {
            // do nothing
        }
        orientation = SADefaults.defaultOrientation()
        try {
            val orient: String? = adData.extras[AwesomeAdsMoPub.orientation]
            if (orient != null && (orient == "PORTRAIT")) {
                orientation = SAOrientation.PORTRAIT
            }
            if (orient != null && (orient == "LANDSCAPE")) {
                orientation = SAOrientation.LANDSCAPE
            }
        } catch (e: java.lang.Exception) {
            // do nothing
        }
        playback = SADefaults.defaultPlaybackMode()
        try {
            val play: String? = adData.extras[AwesomeAdsMoPub.playbackMode]
            if (play != null) {
                when (play) {
                    "POST_ROLL" -> {
                        run { playback = SARTBStartDelay.POST_ROLL }
                        run { playback = SARTBStartDelay.MID_ROLL }
                        run { playback = SARTBStartDelay.PRE_ROLL }
                        run { playback = SARTBStartDelay.GENERIC_MID_ROLL }
                    }
                    "MID_ROLL" -> {
                        run { playback = SARTBStartDelay.MID_ROLL }
                        run { playback = SARTBStartDelay.PRE_ROLL }
                        run { playback = SARTBStartDelay.GENERIC_MID_ROLL }
                    }
                    "PRE_ROLL" -> {
                        run { playback = SARTBStartDelay.PRE_ROLL }
                        run { playback = SARTBStartDelay.GENERIC_MID_ROLL }
                    }
                    "GENERIC_MID_ROLL" -> {
                        playback = SARTBStartDelay.GENERIC_MID_ROLL
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            // do nothing
        }
        shouldShowCloseButton = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.shouldShowClose])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultCloseButton()
        }
        shouldAutomaticallyCloseAtEnd = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.shouldAutoClose])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultCloseAtEnd()
        }
        shouldShowSmallClickButton = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.videoButtonStyle])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultCloseAtEnd()
        }
        enableBackButton = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.backButton])
        } catch (e: java.lang.Exception) {
            SADefaults.defaultBackButton()
        }
        return true
    }

    override fun load(context: Context, adData: AdData) {
        this.context = context

        // configure the interstitial
        SAVideoAd.setConfiguration(configuration)
        SAVideoAd.setTestMode(isTestEnabled)
        SAVideoAd.setParentalGate(isParentalGateEnabled)
        SAVideoAd.setBumperPage(isBumperPageEnabled)
        SAVideoAd.setOrientation(orientation)
        SAVideoAd.setListener { _, event ->
            when (event) {
                SAEvent.adLoaded -> {
                    if (mLoadListener != null) {
                        val ad = SAVideoAd.getAd(placementId)
                        var html: String? = null
                        if (ad != null) {
                            html = ad.creative.details.media.html
                        }
                        val isEmpty = html != null && html.contains("mopub://failLoad")

                        if (isEmpty) {
                            mLoadListener.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                        } else {
                            mLoadListener.onAdLoaded()
                        }
                    }
                }
                SAEvent.adEmpty, SAEvent.adFailedToLoad -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
                SAEvent.adShown -> mInteractionListener?.onAdShown()
                SAEvent.adFailedToShow -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_INVALID_STATE)
                SAEvent.adClicked -> mInteractionListener?.onAdClicked()
                SAEvent.adClosed -> mInteractionListener?.onAdDismissed()
                SAEvent.adEnded -> mInteractionListener?.onAdComplete(MoPubReward.success(MoPubReward.NO_REWARD_LABEL, 0))
                else -> {
                }
            }
        }

        // load the interstitial ad
        SAVideoAd.load(placementId, context)

        MoPubLog.log(adNetworkId, MoPubLog.AdapterLogEvent.LOAD_ATTEMPTED, adapterName)
    }

    override fun show() {
        MoPubLog.log(adNetworkId, MoPubLog.AdapterLogEvent.SHOW_ATTEMPTED, adapterName)
        if (SAVideoAd.hasAdAvailable(placementId)) {
            SAVideoAd.play(placementId, this.context)
        } else {
            MoPubLog.log(adNetworkId, MoPubLog.AdapterLogEvent.SHOW_FAILED, adapterName,
                    MoPubErrorCode.NETWORK_NO_FILL.intCode,
                    MoPubErrorCode.NETWORK_NO_FILL);

            if (mInteractionListener != null) {
                mInteractionListener.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
            }
        }
    }
}