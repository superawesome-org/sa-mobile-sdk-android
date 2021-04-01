package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import tv.superawesome.plugins.publisher.admob.SAAdMobBannerCustomEvent
import tv.superawesome.plugins.publisher.admob.SAAdMobExtras
import tv.superawesome.plugins.publisher.admob.SAAdMobInterstitialCustomEvent
import tv.superawesome.plugins.publisher.admob.SAAdMobVideoMediationAdapter
import tv.superawesome.sdk.publisher.common.models.Orientation
import tv.superawesome.sdk.publisher.common.network.Environment

class AdMobActivity : Activity() {
    private lateinit var adView: AdView
    private lateinit var interstitialAd: InterstitialAd
    private lateinit var rewardedVideoAd: RewardedVideoAd
    private val logTag = "SADefaults/AdMob"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob)

        MobileAds.initialize(this) { Log.d(logTag, "onInitializationComplete") }

        configureBannerAd()
        configureInterstitialAd()
        configureRewardedVideoAd()

        requestBannerAd()
        requestInterstitialAd()
        requestVideoAd()
    }

    private fun configureRewardedVideoAd() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        rewardedVideoAd.rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onRewardedVideoAdLoaded() {
                Log.d(logTag, "Video Ad Loaded")
            }

            override fun onRewardedVideoAdOpened() {
                Log.d(logTag, "Video Ad opened")
            }

            override fun onRewardedVideoStarted() {
                Log.d(logTag, "Video Ad Started")
            }

            override fun onRewardedVideoAdClosed() {
                Log.d(logTag, "Video AD Closed")
                requestVideoAd()
            }

            override fun onRewarded(rewardItem: RewardItem) {
                Log.d(logTag, "Video Ad Rewarded")
            }

            override fun onRewardedVideoAdLeftApplication() {
                Log.d(logTag, "Video Ad Left app")
            }

            override fun onRewardedVideoAdFailedToLoad(i: Int) {
                Log.d(logTag, "Video Ad Failed to load")
            }

            override fun onRewardedVideoCompleted() {
                Log.d(logTag, "Video Ad Completed")
            }
        }
    }

    private fun configureInterstitialAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.admob_interstitial_ad_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                Log.d(logTag, "Interstitial ad closed")
                requestInterstitialAd()
            }

            override fun onAdFailedToLoad(i: Int) {
                Log.d(logTag, "Interstitial ad failed to load")
            }

            override fun onAdLeftApplication() {
                Log.d(logTag, "Interstitial ad left application")
            }

            override fun onAdOpened() {
                Log.d(logTag, "Interstitial ad opened")
            }

            override fun onAdLoaded() {
                Log.d(logTag, "Interstitial ad loaded")
            }
        }
    }

    private fun configureBannerAd() {
        adView = findViewById(R.id.adView)
        adView.adListener = object : AdListener() {
            override fun onAdClosed() {
                Log.d(logTag, "Banner ad closed")
            }

            override fun onAdFailedToLoad(i: Int) {
                Log.d(logTag, "Banner ad failed to load")
            }

            override fun onAdLeftApplication() {
                Log.d(logTag, "Banner ad left application")
            }

            override fun onAdOpened() {
                Log.d(logTag, "Banner ad opened")
            }

            override fun onAdLoaded() {
                Log.d(logTag, "Banner ad loaded")
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
        interstitialAd.loadAd(AdRequest.Builder().addCustomEventExtrasBundle(SAAdMobInterstitialCustomEvent::class.java, bundle).build())
    }

    private fun requestVideoAd() {
        val bundle = SAAdMobExtras.extras()
                .setTestMode(false)
                .setParentalGate(false)
                .setOrientation(Orientation.Landscape)
                .setSmallClick(true)
                .setCloseAtEnd(true)
                .setCloseButton(true)
                .build()
        rewardedVideoAd.loadAd(getString(R.string.admob_rewarded_ad_id),
                AdRequest.Builder().addNetworkExtrasBundle(SAAdMobVideoMediationAdapter::class.java, bundle).build())
    }

    fun playInterstitial(view: View?) {
        if (interstitialAd.isLoaded) {
            interstitialAd.show()
        } else {
            Log.d(logTag, "Interstitial not loaded yet")
        }
    }

    fun playVideo(view: View?) {
        if (rewardedVideoAd.isLoaded) {
            rewardedVideoAd.show()
        } else {
            Log.d(logTag, "Video Ad not loaded yet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adView.destroy()
    }
}