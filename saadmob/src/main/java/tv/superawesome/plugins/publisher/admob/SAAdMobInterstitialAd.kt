package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import tv.superawesome.sdk.publisher.SAInterstitialAd
import tv.superawesome.sdk.publisher.SAOrientation

class SAAdMobInterstitialAd(
    private val adConfiguration: MediationInterstitialAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
) : MediationInterstitialAd, SAInterface {

    private var adCallback: MediationInterstitialAdCallback? = null
    private var loadedPlacementId = 0
    private val paramKey = "parameter"

    fun load() {
        val context = adConfiguration.context

        val extras = adConfiguration.mediationExtras

        if (extras.size() > 0) {
            SAInterstitialAd.setConfiguration(
                SAConfiguration.fromOrdinal(extras.getInt(SAAdMobExtras.kKEY_CONFIGURATION))
            )
            SAInterstitialAd.setTestMode(extras.getBoolean(SAAdMobExtras.kKEY_TEST))
            SAInterstitialAd.setOrientation(SAOrientation.fromValue(extras.getInt(SAAdMobExtras.kKEY_ORIENTATION)))
            SAInterstitialAd.setParentalGate(extras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            SAInterstitialAd.setBumperPage(extras.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            SAInterstitialAd.setBackButton(extras.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
        }

        loadedPlacementId = adConfiguration.serverParameters.getString(paramKey)?.toIntOrNull() ?: 0

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
    override fun onEvent(placementId: Int, event: SAEvent?) {
        when (event) {
            SAEvent.adLoaded -> adLoaded()
            SAEvent.adEmpty, SAEvent.adFailedToLoad -> adFailedToLoad()
            SAEvent.adAlreadyLoaded -> {
                // do nothing
            }
            SAEvent.adShown -> adCallback?.onAdOpened()
            SAEvent.adFailedToShow -> adCallback?.onAdFailedToShow("Ad failed to show for $loadedPlacementId")
            SAEvent.adClicked -> adCallback?.reportAdClicked()
            SAEvent.adClosed -> adCallback?.onAdClosed()
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
