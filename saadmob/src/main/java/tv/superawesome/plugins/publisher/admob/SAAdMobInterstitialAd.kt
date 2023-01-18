package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.models.SAInterface
import tv.superawesome.sdk.publisher.common.ui.interstitial.SAInterstitialAd

class SAAdMobInterstitialAd(
    private val adConfiguration: MediationInterstitialAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
) : MediationInterstitialAd, SAInterface {

    private var adCallback: MediationInterstitialAdCallback? = null
    private var loadedPlacementId = 0

    fun load() {
        val context = adConfiguration.context

        val extras = adConfiguration.mediationExtras

        if (extras.size() > 0) {
            SAInterstitialAd.setTestMode(extras.getBoolean(SAAdMobExtras.kKEY_TEST))
            SAInterstitialAd.setOrientation(
                Orientation.fromValue(extras.getInt(SAAdMobExtras.kKEY_ORIENTATION))
                    ?: Constants.defaultOrientation
            )
            SAInterstitialAd.setParentalGate(extras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            SAInterstitialAd.setBumperPage(extras.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            SAInterstitialAd.setBackButton(extras.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
        }

        loadedPlacementId =
            adConfiguration.serverParameters.getString(SAAdMobExtras.PARAMETER)?.toIntOrNull() ?: 0

        if (loadedPlacementId == 0) {
            mediationAdLoadCallback.onFailure("Failed to request ad, placementID is null or empty.")
            return
        }

        SAInterstitialAd.setListener(this)
        SAInterstitialAd.load(loadedPlacementId, context)
    }

    override fun showAd(context: Context) {
        if (SAInterstitialAd.hasAdAvailable(loadedPlacementId)) {
            SAInterstitialAd.play(loadedPlacementId, context)
        } else {
            mediationAdLoadCallback.onFailure("Ad is not ready")
            adCallback?.onAdFailedToShow("Ad is not ready")
        }
    }

    // SAInterstitialAd Listener Event
    override fun onEvent(placementId: Int, event: SAEvent) {
        when (event) {
            SAEvent.AdLoaded -> adLoaded()
            SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> adFailedToLoad()
            SAEvent.AdAlreadyLoaded -> {
                // do nothing
            }
            SAEvent.AdShown -> adCallback?.onAdOpened()
            SAEvent.AdFailedToShow -> adCallback?.onAdFailedToShow("Ad failed to show for $loadedPlacementId")
            SAEvent.AdClicked -> adCallback?.reportAdClicked()
            SAEvent.AdClosed -> adCallback?.onAdClosed()
            else -> {}
        }
    }

    private fun adLoaded() {
        adCallback = mediationAdLoadCallback.onSuccess(this)
    }

    private fun adFailedToLoad() {
        mediationAdLoadCallback.onFailure("Ad failed to load for $loadedPlacementId")
    }
}
