package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.rewarded.RewardItem
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sasession.defines.SARTBStartDelay
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import tv.superawesome.sdk.publisher.SAOrientation
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

        val extras = adConfiguration.mediationExtras

        if (extras.size() > 0) {
            SAVideoAd.setConfiguration(SAConfiguration.fromOrdinal(extras.getInt(SAAdMobExtras.kKEY_CONFIGURATION)))
            SAVideoAd.setTestMode(extras.getBoolean(SAAdMobExtras.kKEY_TEST))
            SAVideoAd.setOrientation(SAOrientation.fromValue(extras.getInt(SAAdMobExtras.kKEY_ORIENTATION)))
            SAVideoAd.setParentalGate(extras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            SAVideoAd.setBumperPage(extras.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            SAVideoAd.setSmallClick(extras.getBoolean(SAAdMobExtras.kKEY_SMALL_CLICK))
            SAVideoAd.setCloseButton(extras.getBoolean(SAAdMobExtras.kKEY_CLOSE_BUTTON))
            SAVideoAd.setCloseAtEnd(extras.getBoolean(SAAdMobExtras.kKEY_CLOSE_AT_END))
            SAVideoAd.setBackButton(extras.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
            SAVideoAd.setPlaybackMode(SARTBStartDelay.fromValue(extras.getInt(SAAdMobExtras.kKEY_PLAYBACK_MODE)))
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
