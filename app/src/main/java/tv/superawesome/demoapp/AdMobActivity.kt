package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import tv.superawesome.sdk.publisher.common.admob.SAAdMobBannerCustomEvent
import tv.superawesome.sdk.publisher.common.admob.SAAdMobExtras
import tv.superawesome.sdk.publisher.common.admob.SAAdMobInterstitialCustomEvent
import tv.superawesome.sdk.publisher.common.admob.SAAdMobVideoMediationAdapter
import tv.superawesome.sdk.publisher.common.models.Orientation

class AdMobActivity : Activity() {
    private val tag = "SADefaults/AdMob"
    private lateinit var adView: AdView
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd:RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob)

        MobileAds.initialize(this) { Log.d(tag, "onInitializationComplete") }
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("A86A81848A15ED8C5E27A1A72B263929")).build()
        )

        adView = findViewById(R.id.adView)
        requestBannerAd()
    }

    private fun requestBannerAd() {
        val bundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setParentalGate(false)
            .setTransparent(true)
            .build()
        adView.loadAd(
            AdRequest.Builder()
                .addCustomEventExtrasBundle(SAAdMobBannerCustomEvent::class.java, bundle).build()
        )
    }

    private fun requestInterstitialAd() {
        val bundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setOrientation(Orientation.Portrait)
            .setParentalGate(true)
            .build()

        AdManagerInterstitialAd.load(
            this,
            getString(R.string.admob_interstitial_ad_id),
            AdManagerAdRequest.Builder().addCustomEventExtrasBundle(
                SAAdMobInterstitialCustomEvent::class.java, bundle
            ).build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    interstitialAd = p0
                    interstitialAd?.show(this@AdMobActivity)
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                }
            })
    }

    private fun requestVideoAd() {
        val videoBundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setParentalGate(false)
            .setOrientation(Orientation.Landscape)
            .setSmallClick(true)
            .setCloseAtEnd(true)
            .setCloseButton(true)
            .build()

        RewardedAd.load(this,
            getString(R.string.play_admob_video),
            AdManagerAdRequest.Builder()
                .addNetworkExtrasBundle(SAAdMobVideoMediationAdapter::class.java, videoBundle)
                .build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.e(tag, error.message)
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    super.onAdLoaded(p0)
                    rewardedAd = p0
                    rewardedAd?.show(this@AdMobActivity){
                        Log.e(tag, it.amount.toString())
                    }
                }
            })
    }

    fun playInterstitial(view: View?) {
        requestInterstitialAd()
    }

    fun playVideo(view: View?) {
        requestVideoAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        adView.destroy()
    }
}