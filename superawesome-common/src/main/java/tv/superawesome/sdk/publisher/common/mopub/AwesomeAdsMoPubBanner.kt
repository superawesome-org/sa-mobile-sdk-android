package tv.superawesome.sdk.publisher.common.mopub

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
import tv.superawesome.sdk.publisher.common.components.NumberGenerator
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView

class AwesomeAdsMoPubBanner : BaseAd() {
    private val adapterName: String = AwesomeAdsMoPubBanner::class.java.simpleName
    private val adUnitId = NumberGenerator().nextIntForCache()
    private var bannerAd: BannerView? = null

    override fun onInvalidate() {
        if (bannerAd != null) {
            bannerAd = null
            MoPubLog.log(adNetworkId, CUSTOM, adapterName, "Banner destroyed")
        }
    }

    override fun getLifecycleListener(): LifecycleListener? = null

    override fun getAdNetworkId(): String = adUnitId.toString()

    override fun checkAndInitializeSdk(launcherActivity: Activity, adData: AdData): Boolean = false

    override fun load(context: Context, adData: AdData) {
        val extractor = AwesomeAdsMoPubAdDataExtractor(adData)

        bannerAd = BannerView(context).apply {
            id = adUnitId
            setTestMode(extractor.isTestEnabled)
            setParentalGate(extractor.isParentalGateEnabled)
            setBumperPage(extractor.isBumperPageEnabled)

            setListener { placementId, event ->
                when (event) {
                    SAEvent.AdLoaded -> {
                        if (mLoadListener != null) {
                            val ad = controller.currentAdResponse?.ad
                            var html: String? = null
                            if (ad != null) {
                                html = controller.currentAdResponse?.html
                            }
                            val isEmpty = html != null && html.contains("mopub://failLoad")

                            if (isEmpty) {
                                mLoadListener.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                            } else {
                                mLoadListener.onAdLoaded()
                                play()
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
