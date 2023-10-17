package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.rewarded.RewardItem
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.SAVideoAd

class SAAdMobRewardedAd(
    private val adConfiguration: MediationRewardedAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
) : MediationRewardedAd, SAInterface {

    private var adCallback: MediationRewardedAdCallback? = null
    private var loadedPlacementId = 0

    init {
        SAVideoAd.setListener(EventListener.videoEvents)
        EventListener.videoEvents.subscribe(this)
    }

    fun load() {
        val context = adConfiguration.context
        val extras = SAAdMobExtras.readBundle(adConfiguration.mediationExtras)

        if (extras != null) {
            SAVideoAd.setTestMode(extras.testMode)
            SAVideoAd.setOrientation(extras.orientation)
            SAVideoAd.setParentalGate(extras.parentalGate)
            SAVideoAd.setBumperPage(extras.bumperPage)
            SAVideoAd.setSmallClick(extras.smallClick)
            SAVideoAd.setCloseButton(extras.closeButton)
            SAVideoAd.setCloseAtEnd(extras.closeAtEnd)
            SAVideoAd.setBackButton(extras.backButton)
            SAVideoAd.setPlaybackMode(extras.playback)
        }

        loadedPlacementId =
            adConfiguration.serverParameters.getString(SAAdMobExtras.PARAMETER)?.toIntOrNull() ?: 0

        if (loadedPlacementId == 0) {
            mediationAdLoadCallback.onFailure(
                SAAdMobError.adFailedToLoad("Failed to request ad, placementID is null or empty.")
            )
            return
        }
        SAVideoAd.load(loadedPlacementId, context)
    }

    override fun showAd(context: Context) {
        if (SAVideoAd.hasAdAvailable(loadedPlacementId)) {
            SAVideoAd.play(loadedPlacementId, context)

            adCallback?.onVideoStart()
        } else {
            adFailedToShow()
        }
    }

    // SAVideoAd Listener Event
    override fun onEvent(placementId: Int, event: SAEvent) {
        if (loadedPlacementId != placementId) return
        when (event) {
            SAEvent.adLoaded, SAEvent.adAlreadyLoaded -> adLoaded()
            SAEvent.adEmpty, SAEvent.adFailedToLoad -> adFailedToLoad()
            SAEvent.adShown -> adCallback?.onAdOpened()
            SAEvent.adFailedToShow -> adFailedToShow()
            SAEvent.adClicked -> adCallback?.reportAdClicked()
            SAEvent.adEnded -> adCallback?.onUserEarnedReward(RewardItem.DEFAULT_REWARD)
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

    private fun adFailedToShow() {
        adCallback?.onAdFailedToShow(SAAdMobError.adFailedToShow(loadedPlacementId))
        adFailedToLoad()
    }

    private fun adClosed() {
        adCallback?.onAdClosed()
        EventListener.videoEvents.unsubscribe(this)
    }
}
