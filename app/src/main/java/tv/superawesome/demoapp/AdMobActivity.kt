package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd
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
    private lateinit var publisherInterstitialAd: PublisherInterstitialAd

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
        publisherInterstitialAd = PublisherInterstitialAd(this)
        publisherInterstitialAd.adUnitId = getString(R.string.admob_interstitial_ad_id)
    }

    private fun addAddViewListener() {
        adView.adListener = object : AdListener() {
            override fun onAdClosed() {
                Log.d(tag, "Banner ad closed")
            }

            override fun onAdFailedToLoad(i: Int) {
                Log.d(tag, "Banner ad failed to load")
            }

            override fun onAdLeftApplication() {
                Log.d(tag, "Banner ad left application")
            }

            override fun onAdOpened() {
                Log.d(tag, "Banner ad opened")
            }

            override fun onAdLoaded() {
                Log.d(tag, "Banner ad loaded")
            }
        }
    }

    private fun requestBannerAd() {
        val bundle = SAAdMobExtras.extras()
                .setTestMode(false)
                .setParentalGate(false)
                .setTransparent(true)
                .build()
        adView.loadAd(AdRequest.Builder().addCustomEventExtrasBundle(SAAdMobBannerCustomEvent::class.java, bundle).build())
    }

    private fun requestInterstitialAd() {
        val bundle = SAAdMobExtras.extras()
            .setTestMode(false)
            .setOrientation(Orientation.Portrait)
            .setParentalGate(true)
            .build()

        publisherInterstitialAd.loadAd(
            PublisherAdRequest.Builder().addCustomEventExtrasBundle(
                SAAdMobInterstitialCustomEvent::class.java, bundle
            ).build()
        )
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