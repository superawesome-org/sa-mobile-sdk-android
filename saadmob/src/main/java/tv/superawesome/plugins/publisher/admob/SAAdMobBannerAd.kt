package tv.superawesome.plugins.publisher.admob

import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration
import tv.superawesome.sdk.publisher.common.components.NumberGenerator
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView

class SAAdMobBannerAd(
    private val adConfiguration: MediationBannerAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
) : MediationBannerAd, SAInterface {
    private var bannerAd: BannerView? = null
    private var adCallback: MediationBannerAdCallback? = null
    private var loadedPlacementId = 0
    private var adLoaded = false
    private var setupCompleted = false
    private var layoutChanged = false

    fun load() {
        val context = adConfiguration.context
        loadedPlacementId =
            adConfiguration.serverParameters.getString(SAAdMobExtras.PARAMETER)?.toIntOrNull() ?: 0

        bannerAd = BannerView(context)
        bannerAd?.id = NumberGenerator().nextIntForCache()

        val extras = adConfiguration.mediationExtras

        if (extras.size() > 0) {
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
        bannerAd?.addOnLayoutChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int ->
            layoutChanged = true
            if (adLoaded && !setupCompleted) {
                bannerAd?.play()
            }
        }
        bannerAd?.load(loadedPlacementId)
    }

    override fun getView(): View {
        return bannerAd ?: View(adConfiguration.context)
    }

    override fun onEvent(placementId: Int, event: SAEvent) {
        when (event) {
            SAEvent.AdLoaded -> adLoaded()
            SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> adFailedToLoad()
            SAEvent.AdAlreadyLoaded -> {
                // do nothing
            }
            SAEvent.AdShown -> adCallback?.onAdOpened()
            SAEvent.AdFailedToShow -> adFailedToLoad()
            SAEvent.AdClicked -> adCallback?.reportAdClicked()
            SAEvent.AdClosed -> adCallback?.onAdClosed()
            else -> {}
        }
    }

    private fun adLoaded() {
        adLoaded = true

        if (layoutChanged && bannerAd != null && !setupCompleted) {
            bannerAd?.play()
            setupCompleted = true
        }
        adCallback = mediationAdLoadCallback.onSuccess(this)
    }

    private fun adFailedToLoad() {
        mediationAdLoadCallback.onFailure(
            AdError(
                AdRequest.ERROR_CODE_NO_FILL,
                "Ad failed to load for $loadedPlacementId",
                ""
            )
        )
    }
}