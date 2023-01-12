package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.rewarded.RewardItem
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.ui.video.SAVideoAd

class SAAdMobRewardedAd(
    private val adConfiguration: MediationRewardedAdConfiguration,
    private var mediationAdLoadCallback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
) : MediationRewardedAd, SAInterface {

    private var rewardedAdCallback: MediationRewardedAdCallback? = null
    private var loadedPlacementId = 0

    fun load() {
        val context = adConfiguration.context

        val extras = adConfiguration.mediationExtras

        if (extras.size() > 0) {
            SAVideoAd.setTestMode(extras.getBoolean(SAAdMobExtras.kKEY_TEST))
            SAVideoAd.setOrientation(
                Orientation.fromValue(extras.getInt(SAAdMobExtras.kKEY_ORIENTATION))
                    ?: Constants.defaultOrientation
            )
            SAVideoAd.setParentalGate(extras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            SAVideoAd.setBumperPage(extras.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            SAVideoAd.setSmallClick(extras.getBoolean(SAAdMobExtras.kKEY_SMALL_CLICK))
            SAVideoAd.setCloseButton(extras.getBoolean(SAAdMobExtras.kKEY_CLOSE_BUTTON))
            SAVideoAd.setCloseAtEnd(extras.getBoolean(SAAdMobExtras.kKEY_CLOSE_AT_END))
            SAVideoAd.setBackButton(extras.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
            SAVideoAd.setPlaybackMode(
                AdRequest.StartDelay.fromValue(extras.getInt(SAAdMobExtras.kKEY_PLAYBACK_MODE))
                    ?: Constants.defaultStartDelay
            )
        }

        loadedPlacementId =
            adConfiguration.serverParameters.getString(SAAdMobExtras.PARAMETER)?.toIntOrNull() ?: 0

        if (loadedPlacementId == 0) {
            mediationAdLoadCallback.onFailure("Failed to request ad, placementID is null or empty.")
            return
        }

        SAVideoAd.setListener(this)
        SAVideoAd.load(loadedPlacementId, context)
    }

    override fun showAd(context: Context) {
        if (SAVideoAd.hasAdAvailable(loadedPlacementId)) {
            SAVideoAd.play(loadedPlacementId, context)

            rewardedAdCallback?.onVideoStart()
        } else {
            mediationAdLoadCallback.onFailure("Ad is not ready")
            rewardedAdCallback?.onAdFailedToShow("Ad is not ready")
        }
    }

    // SAVideoAd Listener Event
    override fun onEvent(placementId: Int, event: SAEvent) {
        when (event) {
            SAEvent.AdLoaded -> adLoaded()
            SAEvent.AdEmpty, SAEvent.AdFailedToLoad -> adFailedToLoad()
            SAEvent.AdAlreadyLoaded -> {
                // do nothing
            }
            SAEvent.AdShown -> rewardedAdCallback?.onAdOpened()
            SAEvent.AdFailedToShow -> rewardedAdCallback?.onAdFailedToShow("Ad failed to show for $loadedPlacementId")
            SAEvent.AdClicked -> rewardedAdCallback?.reportAdClicked()
            SAEvent.AdEnded -> rewardedAdCallback?.onUserEarnedReward(RewardItem.DEFAULT_REWARD)
            SAEvent.AdClosed -> rewardedAdCallback?.onAdClosed()
            else -> {}
        }
    }

    private fun adLoaded() {
        rewardedAdCallback = mediationAdLoadCallback.onSuccess(this)
    }

    private fun adFailedToLoad() {
        mediationAdLoadCallback.onFailure("Ad failed to load for $loadedPlacementId")
    }
}
