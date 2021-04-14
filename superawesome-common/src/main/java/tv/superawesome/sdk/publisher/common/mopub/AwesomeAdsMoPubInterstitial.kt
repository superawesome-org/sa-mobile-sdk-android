package tv.superawesome.sdk.publisher.common.mopub

import android.app.Activity
import android.content.Context
import com.mopub.common.LifecycleListener
import com.mopub.common.logging.MoPubLog
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_ATTEMPTED
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_FAILED
import com.mopub.mobileads.AdData
import com.mopub.mobileads.BaseAd
import com.mopub.mobileads.MoPubErrorCode
import tv.superawesome.sdk.publisher.common.components.NumberGenerator
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd

class AwesomeAdsMoPubInterstitial : BaseAd() {
    private val adapterName: String = AwesomeAdsMoPubInterstitial::class.java.simpleName

    private val adUnitId = NumberGenerator().nextIntForCache()
    private var placementId: Int = 0
    private var context: Context? = null

    override fun onInvalidate() {
    }

    override fun getLifecycleListener(): LifecycleListener? = null

    override fun getAdNetworkId(): String = adUnitId.toString()

    override fun checkAndInitializeSdk(launcherActivity: Activity, adData: AdData): Boolean = false

    override fun load(context: Context, adData: AdData) {
        val extractor = AwesomeAdsMoPubAdDataExtractor(adData)
        this.context = context
        this.placementId = extractor.placementId

        SAInterstitialAd.setTestMode(extractor.isTestEnabled)
        SAInterstitialAd.setParentalGate(extractor.isParentalGateEnabled)
        SAInterstitialAd.setBumperPage(extractor.isBumperPageEnabled)
        SAInterstitialAd.setOrientation(extractor.orientation)

        SAInterstitialAd.setListener { placementId, event ->
            when (event) {
                SAEvent.AdLoaded -> {
                    if (mLoadListener != null) {
                        val hasAd = SAInterstitialAd.hasAdAvailable(placementId)

                        if (hasAd) {
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
                SAEvent.AdAlreadyLoaded, SAEvent.AdEnded -> {
                }
                else -> {
                }
            }
        }

        // load the interstitial ad
        SAInterstitialAd.load(placementId, context)

        MoPubLog.log(adNetworkId, MoPubLog.AdapterLogEvent.LOAD_ATTEMPTED, adapterName)
    }

    override fun show() {
        MoPubLog.log(adNetworkId, SHOW_ATTEMPTED, adapterName)
        val context = this.context
        if (context != null && SAInterstitialAd.hasAdAvailable(placementId)) {
            SAInterstitialAd.play(placementId, context)
        } else {
            MoPubLog.log(
                adNetworkId, SHOW_FAILED, adapterName,
                MoPubErrorCode.NETWORK_NO_FILL.intCode,
                MoPubErrorCode.NETWORK_NO_FILL
            )

            if (mInteractionListener != null) {
                mInteractionListener.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
            }
        }
    }
}
