package tv.superawesome.plugins.publisher.mopub

import android.app.Activity
import android.content.Context
import android.view.View
import com.mopub.common.LifecycleListener
import com.mopub.common.logging.MoPubLog
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.CUSTOM
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.LOAD_ATTEMPTED
import com.mopub.mobileads.AdData
import com.mopub.mobileads.BaseAd
import com.mopub.mobileads.MoPubErrorCode
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.sdk.publisher.SABannerAd
import tv.superawesome.sdk.publisher.SAEvent

class AwesomeAdsMoPubBanner : BaseAd() {
    private val adapterName: String = AwesomeAdsMoPubBanner::class.java.simpleName
    private val adUnitId = SAUtils.randomNumberBetween(1000000, 1500000)
    private var bannerAd: SABannerAd? = null

    override fun onInvalidate() {
        if (bannerAd != null) {
            bannerAd = null
            MoPubLog.log(adNetworkId, CUSTOM, adapterName, "Banner destroyed");
        }
    }

    override fun getLifecycleListener(): LifecycleListener? = null

    override fun getAdNetworkId(): String = adUnitId.toString()

    override fun checkAndInitializeSdk(launcherActivity: Activity, adData: AdData): Boolean = false

    override fun load(context: Context, adData: AdData) {
        val extractor = AwesomeAdsMoPubAdDataExtractor(adData)

        bannerAd = SABannerAd(context).apply {
            id = adUnitId
            setTestMode(extractor.isTestEnabled)
            setParentalGate(extractor.isParentalGateEnabled)
            setBumperPage(extractor.isBumperPageEnabled)
            setConfiguration(extractor.configuration)
            setListener { _, event ->
                when (event) {
                    SAEvent.adLoaded -> {
                        if (mLoadListener != null) {
                            val ad = ad
                            var html: String? = null
                            if (ad != null) {
                                html = ad.creative.details.media.html
                            }
                            val isEmpty = html != null && html.contains("mopub://failLoad")

                            if (isEmpty) {
                                mLoadListener?.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                            } else {
                                mLoadListener?.onAdLoaded()
                                play(context)
                            }
                        }
                    }
                    SAEvent.adEmpty, SAEvent.adFailedToLoad -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
                    SAEvent.adShown -> mInteractionListener?.onAdShown()
                    SAEvent.adFailedToShow -> mInteractionListener?.onAdFailed(MoPubErrorCode.NETWORK_INVALID_STATE)
                    SAEvent.adClicked -> mInteractionListener?.onAdClicked()
                    SAEvent.adClosed -> mInteractionListener?.onAdDismissed()
                    else -> {
                    }
                }
            }
            load(extractor.placementId)
        }

        MoPubLog.log(adNetworkId, LOAD_ATTEMPTED, adapterName)
    }

    override fun getAdView(): View? = bannerAd
}