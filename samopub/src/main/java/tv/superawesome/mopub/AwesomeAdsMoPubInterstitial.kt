package tv.superawesome.mopub

import android.app.Activity
import android.content.Context
import com.mopub.common.LifecycleListener
import com.mopub.common.logging.MoPubLog
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_ATTEMPTED
import com.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_FAILED
import com.mopub.mobileads.AdData
import com.mopub.mobileads.BaseAd
import com.mopub.mobileads.MoPubErrorCode
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.sdk.publisher.SADefaults
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAOrientation

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
        this.context = context

        placementId = try {
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

        var orientation = SADefaults.defaultOrientation()
        try {
            val orient: String = adData.extras[AwesomeAdsMoPub.orientation] ?: ""
            if (orient == "PORTRAIT") {
                orientation = SAOrientation.PORTRAIT
            } else if (orient == "LANDSCAPE") {
                orientation = SAOrientation.LANDSCAPE
            }
        } catch (e: Exception) {
            // do nothing
        }

        // configure the interstitial
        SAInterstitialAd.setConfiguration(configuration)
        SAInterstitialAd.setTestMode(isTestEnabled)
        SAInterstitialAd.setParentalGate(isParentalGateEnabled)
        SAInterstitialAd.setBumperPage(isBumperPageEnabled)
        SAInterstitialAd.setOrientation(orientation)
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
                            mLoadListener.onAdLoadFailed(MoPubErrorCode.NETWORK_NO_FILL)
                        } else {
                            mLoadListener.onAdLoaded()
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
                    MoPubErrorCode.NETWORK_NO_FILL);

            if (mInteractionListener != null) {
                mInteractionListener.onAdFailed(MoPubErrorCode.NETWORK_NO_FILL)
            }
        }
    }
}