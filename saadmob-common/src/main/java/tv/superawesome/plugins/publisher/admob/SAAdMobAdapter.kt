package tv.superawesome.plugins.publisher.admob

import android.content.Context
import com.google.android.gms.ads.mediation.Adapter
import com.google.android.gms.ads.mediation.InitializationCompleteCallback
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration
import com.google.android.gms.ads.mediation.MediationConfiguration
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.mediation.VersionInfo
import tv.superawesome.sdk.publisher.models.Configuration
import tv.superawesome.sdk.publisher.sdk.AwesomeAds


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
        AwesomeAds.init(context, Configuration(logging = false))
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
        val splits = AwesomeAds.info()?.versionNumber?.split(".") ?: emptyList()
        return if (splits.size >= 3) {
            VersionInfo(splits[0].toInt(), splits[1].toInt(), splits[2].toInt())
        } else {
            VersionInfo(0, 0, 0)
        }
    }

    override fun getVersionInfo(): VersionInfo {
        val splits = AwesomeAds.info()?.versionNumber?.split(".") ?: emptyList()
        return if (splits.size >= 3) {
            VersionInfo(splits[0].toInt(), splits[1].toInt(), splits[2].toInt() * 100)
        } else {
            VersionInfo(0, 0, 0)
        }
    }
}
