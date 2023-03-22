package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import tv.superawesome.plugins.publisher.admob.SAAdMobAdapter
import tv.superawesome.plugins.publisher.admob.SAAdMobExtras
import tv.superawesome.sdk.publisher.SAOrientation

class AdMobActivity : Activity() {
    private val tag = "SADefaults/AdMob"
    private lateinit var adView: AdView
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob)

        MobileAds.initialize(this) {
            Log.d(tag, "onInitializationComplete")
        }
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("A86A81848A15ED8C5E27A1A72B263929")).build()
        )

        adView = findViewById(R.id.adView)
        requestBannerAd()
        findViewById<Button>(R.id.playInterstitialBtn)?.setOnClickListener {
            requestInterstitialAd()
        }
        findViewById<Button>(R.id.playVideoBtn)?.setOnClickListener {
            requestVideoAd()
        }
    }

    private fun requestBannerAd() {
        val bundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setParentalGate(false)
            .setTransparent(true)
            .build()

        adView.loadAd(
            AdRequest.Builder()
                .addNetworkExtrasBundle(SAAdMobAdapter::class.java, bundle)
                .build()
        )
    }

    private fun requestInterstitialAd() {
        val bundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setOrientation(SAOrientation.PORTRAIT)
            .setParentalGate(false)
            .build()

        AdManagerInterstitialAd.load(
            this,
            getString(R.string.admob_interstitial_ad_id),
            AdRequest.Builder()
                .addNetworkExtrasBundle(SAAdMobAdapter::class.java, bundle)
                .build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    interstitialAd = ad
                    interstitialAd?.show(this@AdMobActivity)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                }
            })
    }

    private fun requestVideoAd() {
        val bundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setParentalGate(false)
            .setOrientation(SAOrientation.PORTRAIT)
            .setSmallClick(true)
            .setCloseAtEnd(true)
            .setCloseButton(true)
            .build()

        RewardedAd.load(this,
            getString(R.string.admob_video_ad_id),
            AdRequest.Builder()
                .addNetworkExtrasBundle(SAAdMobAdapter::class.java, bundle)
                .build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.e(tag, error.message)
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    super.onAdLoaded(ad)
                    rewardedAd = ad
                    rewardedAd?.show(this@AdMobActivity) {
                        Log.e(tag, it.amount.toString())
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        adView.destroy()
    }
}