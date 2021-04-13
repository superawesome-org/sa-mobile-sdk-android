package tv.superawesome.sdk.publisher.common.mopub

import android.app.Activity
import android.content.Context
import com.mopub.common.LifecycleListener
import com.mopub.common.MoPubReward
import com.mopub.common.logging.MoPubLog
import com.mopub.mobileads.AdData
import com.mopub.mobileads.BaseAd
import com.mopub.mobileads.MoPubErrorCode
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd

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
    private var orientation: Orientation? = null
    private var playback: AdRequest.StartDelay? = null

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
        orientation = extractor.orientation
        playback = Constants.defaultStartDelay

        val play: String? = extractor.play
        if (play != null) {
            when (play) {
                "POST_ROLL" -> playback = AdRequest.StartDelay.PostRoll
                "MID_ROLL" -> playback = AdRequest.StartDelay.MidRoll
                "PRE_ROLL" -> playback = AdRequest.StartDelay.PreRoll
                "GENERIC_MID_ROLL" -> playback = AdRequest.StartDelay.GenericMidRoll
            }
        }

        shouldShowCloseButton = extractor.shouldShowCloseButton
        shouldAutomaticallyCloseAtEnd = extractor.shouldAutomaticallyCloseAtEnd
        shouldShowSmallClickButton = extractor.shouldShowSmallClickButton
        enableBackButton = extractor.enableBackButton

        return true
    }

    override fun load(context: Context, adData: AdData) {
        this.context = context

        SAVideoAd.setTestMode(isTestEnabled)
        SAVideoAd.setParentalGate(isParentalGateEnabled)
        SAVideoAd.setBumperPage(isBumperPageEnabled)
        orientation?.let { SAVideoAd.setOrientation(it) }

        SAInterstitialAd.setListener(object : SAInterface {
            override fun onEvent(placementId: Int, event: SAEvent) {
                when (event) {
                    SAEvent.AdLoaded -> {
                        if (mLoadListener != null) {
                            val hasAdAvailable = SAVideoAd.hasAdAvailable(placementId)

                            if (hasAdAvailable) {
                                mLoadListener.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                            } else {
                                mLoadListener.onAdLoaded()
                            }
                        }
                    }
                    SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> mInteractionListener?.onAdFailed(
                        MoPubErrorCode.NETWORK_NO_FILL
                    )
                    SAEvent.AdShown -> mInteractionListener?.onAdShown()
                    SAEvent.AdFailedToShow -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_INVALID_STATE)
                    SAEvent.AdClicked -> mInteractionListener?.onAdClicked()
                    SAEvent.AdClosed -> mInteractionListener?.onAdDismissed()
                    SAEvent.AdEnded -> mInteractionListener?.onAdComplete(
                        MoPubReward.success(
                            MoPubReward.NO_REWARD_LABEL,
                            0
                        )
                    )
                    else -> {
                    }
                }
            }
        })

        // load the interstitial ad
        SAVideoAd.load(placementId, context)

        MoPubLog.log(adNetworkId, MoPubLog.AdapterLogEvent.LOAD_ATTEMPTED, adapterName)
    }

    override fun show() {
        MoPubLog.log(adNetworkId, MoPubLog.AdapterLogEvent.SHOW_ATTEMPTED, adapterName)
        val context = context
        if (context != null && SAVideoAd.hasAdAvailable(placementId)) {
            SAVideoAd.play(placementId, context)
        } else {
            MoPubLog.log(
                adNetworkId, MoPubLog.AdapterLogEvent.SHOW_FAILED, adapterName,
                MoPubErrorCode.NETWORK_NO_FILL.intCode,
                MoPubErrorCode.NETWORK_NO_FILL
            )

            if (mInteractionListener != null) {
                mInteractionListener.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
            }
        }
    }
}
