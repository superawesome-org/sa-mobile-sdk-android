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
        val extractor = AwesomeAdsMoPubAdDataExtractor(adData)

        adUnitId = extractor.adUnitId
        placementId = extractor.placementId
        isTestEnabled = extractor.isTestEnabled
        isParentalGateEnabled = extractor.isParentalGateEnabled
        isBumperPageEnabled = extractor.isBumperPageEnabled
        configuration = extractor.configuration
        orientation = extractor.orientation
        playback = SADefaults.defaultPlaybackMode()
        try {
            val play: String? = extractor.play
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
        shouldShowCloseButton = extractor.shouldShowCloseButton
        shouldAutomaticallyCloseAtEnd = extractor.shouldAutomaticallyCloseAtEnd
        shouldShowSmallClickButton = extractor.shouldShowSmallClickButton
        enableBackButton = extractor.enableBackButton

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