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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob)

        MobileAds.initialize(this) { Log.d("SADefaults/Admob", "onInitializationComplete") }

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
                Log.d("SADefaults/AdMob", "Video Ad Loaded")
            }

            override fun onRewardedVideoAdOpened() {
                Log.d("SADefaults/AdMob", "Video Ad opened")
            }

            override fun onRewardedVideoStarted() {
                Log.d("SADefaults/AdMob", "Video Ad Started")
            }

            override fun onRewardedVideoAdClosed() {
                Log.d("SADefaults/AdMob", "Video AD Closed")
                requestVideoAd()
            }

            override fun onRewarded(rewardItem: RewardItem) {
                Log.d("SADefaults/AdMob", "Video Ad Rewarded")
            }

            override fun onRewardedVideoAdLeftApplication() {
                Log.d("SADefaults/AdMob", "Video Ad Left app")
            }

            override fun onRewardedVideoAdFailedToLoad(i: Int) {
                Log.d("SADefaults/AdMob", "Video Ad Failed to load")
            }

            override fun onRewardedVideoCompleted() {
                Log.d("SADefaults/AdMob", "Video Ad Completed")
            }
        }
    }

    private fun configureInterstitialAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.admob_interstitial_ad_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                Log.d("SADefaults/AdMob", "Interstitial ad closed")
                requestInterstitialAd()
            }

            override fun onAdFailedToLoad(i: Int) {
                Log.d("SADefaults/AdMob", "Interstitial ad failed to load")
            }

            override fun onAdLeftApplication() {
                Log.d("SADefaults/AdMob", "Interstitial ad left application")
            }

            override fun onAdOpened() {
                Log.d("SADefaults/AdMob", "Interstitial ad opened")
            }

            override fun onAdLoaded() {
                Log.d("SADefaults/AdMob", "Interstitial ad loaded")
            }
        }
    }

    private fun configureBannerAd() {
        adView = findViewById(R.id.adView)
        adView.adListener = object : AdListener() {
            override fun onAdClosed() {
                Log.d("SADefaults/AdMob", "Banner ad closed")
            }

            override fun onAdFailedToLoad(i: Int) {
                Log.d("SADefaults/AdMob", "Banner ad failed to load")
            }

            override fun onAdLeftApplication() {
                Log.d("SADefaults/AdMob", "Banner ad left application")
            }

            override fun onAdOpened() {
                Log.d("SADefaults/AdMob", "Banner ad opened")
            }

            override fun onAdLoaded() {
                Log.d("SADefaults/AdMob", "Banner ad loaded")
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
            Log.d("SADefaults/Admob", "Interstitial not loaded yet")
        }
    }

    fun playVideo(view: View?) {
        if (rewardedVideoAd.isLoaded) {
            rewardedVideoAd.show()
        } else {
            Log.d("SADefaults/Admob", "Video Ad not loaded yet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adView.destroy()
    }
}