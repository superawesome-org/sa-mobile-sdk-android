package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter
import com.google.android.gms.ads.rewarded.RewardItem
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.lib.sasession.defines.SARTBStartDelay
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAOrientation
import tv.superawesome.sdk.publisher.SAVideoAd

class SAAdMobRewardedAd(private val adConfiguration: MediationRewardedAdConfiguration,
                        private var mediationAdLoadCallback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>) : MediationRewardedAd {

    private var rewardedAdCallback: MediationRewardedAdCallback? = null
    private var loadedPlacementId = 0

    fun load() {
        val context = adConfiguration.context

        if (context == null) {
            mediationAdLoadCallback.onFailure("Context is null")
            return
        }

        val mediationExtras = adConfiguration.mediationExtras
        if (mediationExtras != null) {
            SAVideoAd.setConfiguration(SAConfiguration.fromValue(mediationExtras.getInt(SAAdMobExtras.kKEY_CONFIGURATION)))
            SAVideoAd.setTestMode(mediationExtras.getBoolean(SAAdMobExtras.kKEY_TEST))
            SAVideoAd.setOrientation(SAOrientation.fromValue(mediationExtras.getInt(SAAdMobExtras.kKEY_ORIENTATION)))
            SAVideoAd.setParentalGate(mediationExtras.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE))
            SAVideoAd.setBumperPage(mediationExtras.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE))
            SAVideoAd.setSmallClick(mediationExtras.getBoolean(SAAdMobExtras.kKEY_SMALL_CLICK))
            SAVideoAd.setCloseButton(mediationExtras.getBoolean(SAAdMobExtras.kKEY_CLOSE_BUTTON))
            SAVideoAd.setCloseAtEnd(mediationExtras.getBoolean(SAAdMobExtras.kKEY_CLOSE_AT_END))
            SAVideoAd.setBackButton(mediationExtras.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON))
            SAVideoAd.setPlaybackMode(SARTBStartDelay.fromValue(mediationExtras.getInt(SAAdMobExtras.kKEY_PLAYBACK_MODE)))
        }

        loadedPlacementId = adConfiguration.serverParameters
                .getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD)
                ?.toIntOrNull() ?: 0

        if (loadedPlacementId == 0) {
            mediationAdLoadCallback.onFailure("Failed to request ad, placementID is null or empty.")
            return
        }


        SAVideoAd.setListener { _: Int, event: SAEvent? ->
            when (event) {
                SAEvent.adLoaded -> adLoaded()
                SAEvent.adEmpty, SAEvent.adFailedToLoad -> adFailedToLoad()
                SAEvent.adAlreadyLoaded -> {
                    // do nothing
                }
                SAEvent.adShown -> rewardedAdCallback?.onAdOpened()
                SAEvent.adFailedToShow -> rewardedAdCallback?.onAdFailedToShow("Ad failed to show for $loadedPlacementId")
                SAEvent.adClicked -> rewardedAdCallback?.reportAdClicked()
                SAEvent.adEnded -> rewardedAdCallback?.onUserEarnedReward(RewardItem.DEFAULT_REWARD)
                SAEvent.adClosed -> rewardedAdCallback?.onAdClosed()
            }
        }
        SAVideoAd.load(loadedPlacementId, context)
    }

    override fun showAd(context: Context?) {
        if (SAVideoAd.hasAdAvailable(loadedPlacementId)) {
            SAVideoAd.play(loadedPlacementId, context)

            rewardedAdCallback?.onVideoStart()
        } else {
            mediationAdLoadCallback.onFailure("Ad is not ready")
            rewardedAdCallback?.onAdFailedToShow("Ad is not ready")
        }
    }

    private fun adLoaded() {
        rewardedAdCallback = mediationAdLoadCallback.onSuccess(this)
    }

    private fun adFailedToLoad() {
        mediationAdLoadCallback.onFailure("Ad failed to load for $loadedPlacementId")
    }
}