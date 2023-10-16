package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.SAInterstitialAd

class SAAdMobInterstitialAd(
    private val adConfiguration: MediationInterstitialAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
) : MediationInterstitialAd, SAInterface {

    private var adCallback: MediationInterstitialAdCallback? = null
    private var loadedPlacementId = 0

    init {
        SAInterstitialAd.setListener(EventListener.interstitialEvents)
        EventListener.interstitialEvents.subscribe(this)
    }

    fun load() {
        val context = adConfiguration.context
        val extras = SAAdMobExtras.readBundle(adConfiguration.mediationExtras)

        if (extras != null) {
            SAInterstitialAd.setTestMode(extras.testMode)
            SAInterstitialAd.setOrientation(extras.orientation)
            SAInterstitialAd.setParentalGate(extras.parentalGate)
            SAInterstitialAd.setBumperPage(extras.bumperPage)
            SAInterstitialAd.setBackButton(extras.backButton)
        }

        loadedPlacementId =
            adConfiguration.serverParameters.getString(SAAdMobExtras.PARAMETER)?.toIntOrNull() ?: 0

        if (loadedPlacementId == 0) {
            mediationAdLoadCallback.onFailure(
                SAAdMobError.adFailedToLoad("Failed to request ad, placementID is null or empty.")
            )
            return
        }

        SAInterstitialAd.load(loadedPlacementId, context)
    }

    override fun showAd(context: Context) {
        if (SAInterstitialAd.hasAdAvailable(loadedPlacementId)) {
            SAInterstitialAd.play(loadedPlacementId, context)
        } else {
            adFailedToShown()
        }
    }

    // SAInterstitialAd Listener Event
    override fun onEvent(placementId: Int, event: SAEvent) {
        when (event) {
            SAEvent.adLoaded, SAEvent.adAlreadyLoaded -> adLoaded()
            SAEvent.adEmpty, SAEvent.adFailedToLoad -> adFailedToLoad()
            SAEvent.adShown -> adCallback?.onAdOpened()
            SAEvent.adFailedToShow -> adFailedToShown()
            SAEvent.adClicked -> adCallback?.reportAdClicked()
            SAEvent.adClosed -> adClosed()
            else -> Unit // Unused
        }
    }

    private fun adLoaded() {
        adCallback = mediationAdLoadCallback.onSuccess(this)
    }

    private fun adFailedToLoad() {
        mediationAdLoadCallback.onFailure(SAAdMobError.adFailedToLoad(loadedPlacementId))
    }

    private fun adFailedToShown() {
        adCallback?.onAdFailedToShow(SAAdMobError.adFailedToShow(loadedPlacementId))
        adFailedToLoad()
    }

    private fun adClosed() {
        adCallback?.onAdClosed()
        EventListener.interstitialEvents.unsubscribe(this)
    }
}
