package tv.superawesome.mopub

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
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.sdk.publisher.SABannerAd
import tv.superawesome.sdk.publisher.SADefaults
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
        val placementId: Int = try {
            adData.extras[AwesomeAdsMoPub.placementId]?.toInt() ?: SADefaults.defaultPlacementId()
        } catch (e: Exception) {
            SADefaults.defaultPlacementId()
        }
        val isTestEnabled: Boolean = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.testEnabled])
        } catch (e: Exception) {
            SADefaults.defaultTestMode()
        }
        val isParentalGateEnabled: Boolean = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.parentalGate])
        } catch (e: Exception) {
            SADefaults.defaultParentalGate()
        }
        val isBumperPageEnabled: Boolean = try {
            java.lang.Boolean.valueOf(adData.extras[AwesomeAdsMoPub.bumperPage])
        } catch (e: Exception) {
            SADefaults.defaultBumperPage()
        }
        var configuration = SADefaults.defaultConfiguration()
        try {
            val config = adData.extras[AwesomeAdsMoPub.configuration]
            if (config != null && config == "STAGING") {
                configuration = SAConfiguration.STAGING
            }
        } catch (e: Exception) {
            // do nothing
        }

        // create & customise the banner ad
        bannerAd = SABannerAd(context).apply {
            id = adUnitId
            setTestMode(isTestEnabled)
            setParentalGate(isParentalGateEnabled)
            setBumperPage(isBumperPageEnabled)
            setConfiguration(configuration)
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
                                mLoadListener.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                            } else {
                                mLoadListener.onAdLoaded()
                                play(context)
                            }
                        }
                    }
                    SAEvent.adEmpty, SAEvent.adFailedToLoad -> {
                        if (mInteractionListener != null) {
                            mInteractionListener.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
                        }
                    }
                    SAEvent.adShown -> {
                        if (mInteractionListener != null) {
                            mInteractionListener.onAdShown()
                        }
                    }
                    SAEvent.adFailedToShow -> {
                        if (mInteractionListener != null) {
                            mInteractionListener.onAdFailed(MoPubErrorCode.NETWORK_INVALID_STATE)
                        }
                    }
                    SAEvent.adClicked -> {
                        if (mInteractionListener != null) {
                            mInteractionListener.onAdClicked()
                        }
                    }
                    SAEvent.adClosed -> {
                        if (mInteractionListener != null) {
                            mInteractionListener.onAdDismissed()
                        }
                    }
                    else -> {
                    }
                }
            }
            load(placementId)
        }

        MoPubLog.log(adNetworkId, LOAD_ATTEMPTED, adapterName)
    }

    override fun getAdView(): View? = bannerAd
}