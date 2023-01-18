package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.*
import tv.superawesome.sdk.publisher.common.models.Configuration
import tv.superawesome.sdk.publisher.common.sdk.AwesomeAdsSdk

/**
 * AdMob mediation adapter for AwesomeAds
 */
open class SAAdMobAdapter : Adapter() {

    private var bannerAd: SAAdMobBannerAd? = null
    private var interstitialAd: SAAdMobInterstitialAd? = null
    private var rewardedAd: SAAdMobRewardedAd? = null

    override fun initialize(
        context: Context,
        initializationCompleteCallback: InitializationCompleteCallback,
        mediationConfigurations: MutableList<MediationConfiguration>
    ) {
        AwesomeAdsSdk.init(context, Configuration())
        initializationCompleteCallback.onInitializationSucceeded()
    }

    override fun loadRewardedAd(
        adConfiguration: MediationRewardedAdConfiguration,
        callback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
    ) {
        rewardedAd = SAAdMobRewardedAd(adConfiguration, callback)
        rewardedAd?.load()
    }

    override fun loadBannerAd(
        adConfiguration: MediationBannerAdConfiguration,
        callback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
    ) {
        bannerAd = SAAdMobBannerAd(adConfiguration, callback)
        bannerAd?.load()
    }

    override fun loadInterstitialAd(
        adConfiguration: MediationInterstitialAdConfiguration,
        callback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
    ) {
        interstitialAd = SAAdMobInterstitialAd(adConfiguration, callback)
        interstitialAd?.load()
    }

    override fun getSDKVersionInfo(): VersionInfo {
        val splits = AwesomeAdsSdk.info()?.versionNumber?.split(".") ?: emptyList()
        return if (splits.size >= 3) {
            VersionInfo(splits[0].toInt(), splits[1].toInt(), splits[2].toInt())
        } else {
            VersionInfo(0, 0, 0)
        }
    }

    override fun getVersionInfo(): VersionInfo {
        val splits = AwesomeAdsSdk.info()?.versionNumber?.split(".") ?: emptyList()
        return if (splits.size >= 3) {
            VersionInfo(splits[0].toInt(), splits[1].toInt(), splits[2].toInt() * 100)
        } else {
            VersionInfo(0, 0, 0)
        }
    }
}
