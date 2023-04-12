package tv.superawesome.plugins.publisher.admob

import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.sdk.publisher.SABannerAd
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface

class SAAdMobBannerAd(
    private val adConfiguration: MediationBannerAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
) : MediationBannerAd, SAInterface {
    private var bannerAd: SABannerAd? = null
    private val ID = SAUtils.randomNumberBetween(1000000, 1500000)
    private var adCallback: MediationBannerAdCallback? = null
    private var loadedPlacementId = 0
    private var adLoaded = false
    private var setupCompleted = false
    private var layoutChanged = false

    fun load() {
        val context = adConfiguration.context
        loadedPlacementId =
            adConfiguration.serverParameters.getString(SAAdMobExtras.PARAMETER)?.toIntOrNull() ?: 0

        bannerAd = SABannerAd(context)
        bannerAd?.id = ID

        val extras = adConfiguration.mediationExtras

        if (extras.size() > 0) {
            bannerAd?.setConfiguration(SAConfiguration.fromOrdinal(extras.getInt(SAAdMobExtras.kKEY_CONFIGURATION)))
            bannerAd?.setTestMode(extras.getBoolean(SAAdMobExtras.kKEY_TEST))
            bannerAd?.setParentalGate(extras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            bannerAd?.setBumperPage(extras.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            bannerAd?.setColor(extras.getBoolean(SAAdMobExtras.kKEY_TRANSPARENT))
        }

        val adSize = adConfiguration.adSize
        val widthInPixels: Int = adSize.getWidthInPixels(context)
        val heightInPixels: Int = adSize.getHeightInPixels(context)

        bannerAd?.layoutParams = ViewGroup.LayoutParams(widthInPixels, heightInPixels)

        bannerAd?.setListener(this)
        bannerAd?.addOnLayoutChangeListener { v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int ->
            layoutChanged = true
            if (adLoaded && !setupCompleted) {
                bannerAd?.play(context)
            }
        }
        bannerAd?.load(loadedPlacementId)
    }

    override fun getView(): View {
        return bannerAd ?: View(adConfiguration.context)
    }

    override fun onEvent(placementId: Int, event: SAEvent) {
        when (event) {
            SAEvent.adLoaded, SAEvent.adAlreadyLoaded -> adLoaded()
            SAEvent.adEmpty, SAEvent.adFailedToLoad, SAEvent.adFailedToShow -> adFailedToLoad()
            SAEvent.adShown -> adCallback?.onAdOpened()
            SAEvent.adClicked -> adCallback?.reportAdClicked()
            SAEvent.adClosed -> adCallback?.onAdClosed()
            SAEvent.adEnded, SAEvent.adPlaying, SAEvent.adPaused -> Unit // Unused
        }
    }

    private fun adLoaded() {
        adLoaded = true

        if (layoutChanged && bannerAd != null && !setupCompleted) {
            bannerAd?.play(bannerAd?.context)
            setupCompleted = true
        }
        adCallback = mediationAdLoadCallback.onSuccess(this)
    }

    private fun adFailedToLoad() {
        mediationAdLoadCallback.onFailure(SAAdMobError.adFailedToLoad(loadedPlacementId))
    }
}