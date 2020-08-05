package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.mediation.*
import tv.superawesome.plugins.publisher.admob.SAAdMobVideoMediationAdapter.SARewardItem
import tv.superawesome.sdk.publisher.AwesomeAds
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import tv.superawesome.sdk.publisher.SAVideoAd

class RewardedAdapter : Adapter(), MediationRewardedAd {

    private var loadedPlacementId = 0

    override fun initialize(context: Context?, initializationCompleteCallback: InitializationCompleteCallback?, mediationConfigurations: MutableList<MediationConfiguration>?) {
        if (context == null) {
            initializationCompleteCallback?.onInitializationFailed("Initialization Failed: Context is null.")
            return
        }

        AwesomeAds.init(context, false)
        initializationCompleteCallback?.onInitializationSucceeded()
    }

    override fun getSDKVersionInfo(): VersionInfo {
        val splits = BuildConfig.VERSION_NAME.split("\\.")
        return if (splits.size >= 3) {
            VersionInfo(splits[0].toInt(), splits[1].toInt(), splits[2].toInt())
        } else {
            VersionInfo(0, 0, 0)
        }
    }

    override fun getVersionInfo(): VersionInfo {
        val splits = BuildConfig.VERSION_NAME.split("\\.")
        return if (splits.size >= 3) {
            VersionInfo(splits[0].toInt(), splits[1].toInt(), splits[2].toInt() * 100)
        } else {
            VersionInfo(0, 0, 0)
        }
    }

    override fun loadRewardedAd(adConfiguration: MediationRewardedAdConfiguration?, callback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>?) {

//        SAVideoAd.setListener(SAInterface { placementId: Int, event: SAEvent? ->
//            when (event) {
//                SAEvent.adLoaded -> {
//                    callback.onAdLoaded(this@SAAdMobVideoMediationAdapter)
//                }
//                SAEvent.adEmpty, SAEvent.adFailedToLoad -> {
//                    listener.onAdFailedToLoad(this@SAAdMobVideoMediationAdapter, AdRequest.ERROR_CODE_NO_FILL)
//                }
//                SAEvent.adAlreadyLoaded -> {
//                }
//                SAEvent.adShown -> {
//                    listener.onAdOpened(this@SAAdMobVideoMediationAdapter)
//                }
//                SAEvent.adFailedToShow -> {
//                    listener.onAdFailedToLoad(this@SAAdMobVideoMediationAdapter, AdRequest.ERROR_CODE_INVALID_REQUEST)
//                }
//                SAEvent.adClicked -> {
//                    listener.onAdClicked(this@SAAdMobVideoMediationAdapter)
//                    listener.onAdLeftApplication(this@SAAdMobVideoMediationAdapter)
//                }
//                SAEvent.adEnded -> {
//                    val item = SARewardItem("", 1)
//                    listener.onRewarded(this@SAAdMobVideoMediationAdapter, item)
//                }
//                SAEvent.adClosed -> {
//                    listener.onAdClosed(this@SAAdMobVideoMediationAdapter)
//                }
//            }
//        } as SAInterface)
//        SAVideoAd.load(loadedPlacementId, context)
    }

    override fun showAd(context: Context?) {
        SAVideoAd.play(loadedPlacementId, context)
    }

}