package tv.superawesome.plugins.publisher.mopub

import android.app.Activity
import android.content.Context
import com.mopub.common.LifecycleListener
import com.mopub.common.logging.MoPubLog
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_ATTEMPTED
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_FAILED
import com.mopub.mobileads.AdData
import com.mopub.mobileads.BaseAd
import com.mopub.mobileads.MoPubErrorCode
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd

class AwesomeAdsMoPubInterstitial : BaseAd() {
    private val adapterName: String = AwesomeAdsMoPubInterstitial::class.java.simpleName

    private val adUnitId = SAUtils.randomNumberBetween(1000000, 1500000)
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

        SAInterstitialAd.setConfiguration(extractor.configuration)
        SAInterstitialAd.setTestMode(extractor.isTestEnabled)
        SAInterstitialAd.setParentalGate(extractor.isParentalGateEnabled)
        SAInterstitialAd.setBumperPage(extractor.isBumperPageEnabled)
        SAInterstitialAd.setOrientation(extractor.orientation)
        SAInterstitialAd.setListener { _, event ->
            when (event) {
                SAEvent.adLoaded -> {
                    if (mLoadListener != null) {
                        val ad = SAInterstitialAd.getAd(placementId)
                        var html: String? = null
                        if (ad != null) {
                            html = ad.creative.details.media.html
                        }
                        val isEmpty = html != null && html.contains("mopub://failLoad")

                        if (isEmpty) {
                            mLoadListener?.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                        } else {
                            mLoadListener?.onAdLoaded()
                        }
                    }
                }
                SAEvent.adEmpty, SAEvent.adFailedToLoad -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
                SAEvent.adShown -> mInteractionListener?.onAdShown()
                SAEvent.adFailedToShow -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_INVALID_STATE)
                SAEvent.adClicked -> mInteractionListener?.onAdClicked()
                SAEvent.adClosed -> mInteractionListener?.onAdDismissed()
                SAEvent.adAlreadyLoaded, SAEvent.adEnded -> {
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
        if (SAInterstitialAd.hasAdAvailable(placementId)) {
            SAInterstitialAd.play(placementId, this.context)
        } else {
            MoPubLog.log(adNetworkId, SHOW_FAILED, adapterName,
                    MoPubErrorCode.NETWORK_NO_FILL.intCode,
                    MoPubErrorCode.NETWORK_NO_FILL)
            mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
        }
    }
}