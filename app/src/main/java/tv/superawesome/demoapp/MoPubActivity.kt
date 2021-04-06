package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mopub.common.MoPub
import com.mopub.common.MoPubReward
import com.mopub.common.SdkConfiguration
import com.mopub.mobileads.*
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener
import com.mopub.mobileads.MoPubView.BannerAdListener

class MoPubActivity : Activity() {
    private var banner: MoPubView? = null
    private var interstitial: MoPubInterstitial? = null

    companion object {
        private const val BannerId = "b195f8dd8ded45fe847ad89ed1d016da"
        private const val InterstitialId = "24534e1901884e398f1253216226017e"
        private const val VideoId = "920b6145fb1546cf8b5cf2ac34638bb7"
        private const val tag = "SADefaults/MoPub"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mopub)

        MoPub.initializeSdk(this, SdkConfiguration.Builder(BannerId)
                .withAdditionalNetwork("tv.superawesome.mopub.AwesomeAdsMoPubAdapterConfiguration")
                .build()) {
            Log.d(tag, "MoPub.initializeSdk completed")
            configureBannerAd()
            configureInterstitialAd()
            configureRewardedVideoAd()
        }
    }

    private fun configureRewardedVideoAd() {
        MoPubRewardedAds.setRewardedAdListener(object :MoPubRewardedAdListener{
            override fun onRewardedAdClicked(adUnitId: String) {
                Log.d(tag, "clicked")
            }

            override fun onRewardedAdClosed(adUnitId: String) {
                Log.d(tag, "closed")
            }

            override fun onRewardedAdCompleted(adUnitIds: Set<String?>, reward: MoPubReward) {
                Log.d(tag, "completed")
            }

            override fun onRewardedAdLoadFailure(adUnitId: String, errorCode: MoPubErrorCode) {
                Log.d(tag, "failed")
            }

            override fun onRewardedAdLoadSuccess(adUnitId: String) {
                Log.d(tag, "success")
            }

            override fun onRewardedAdShowError(adUnitId: String, errorCode: MoPubErrorCode) {
                Log.d(tag, "error")
            }

            override fun onRewardedAdStarted(adUnitId: String) {
                Log.d(tag, "started")
            }
        })
        MoPubRewardedAds.loadRewardedAd(VideoId)
    }

    private fun configureInterstitialAd() {
        interstitial = MoPubInterstitial(this, InterstitialId)
        interstitial?.interstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialLoaded(interstitial: MoPubInterstitial) {
                Log.d(tag, "Interstitial loaded")
            }

            override fun onInterstitialFailed(interstitial: MoPubInterstitial, errorCode: MoPubErrorCode) {
                Log.d(tag, "Interstitial failed: $errorCode")
            }

            override fun onInterstitialShown(interstitial: MoPubInterstitial) {
                Log.d(tag, "Interstitial shown")
            }

            override fun onInterstitialClicked(interstitial: MoPubInterstitial) {
                Log.d(tag, "Interstitial clicked")
            }

            override fun onInterstitialDismissed(interstitial: MoPubInterstitial) {
                Log.d(tag, "Interstitial dimissed")
            }
        }
        interstitial?.load()
    }

    private fun configureBannerAd() {
        banner = findViewById<View>(R.id.adview) as MoPubView
        banner?.setAdUnitId(BannerId)
        banner?.bannerAdListener = object : BannerAdListener {
            override fun onBannerLoaded(banner: MoPubView) {
                Log.d(tag, "Banner ad loaded")
            }

            override fun onBannerFailed(banner: MoPubView, errorCode: MoPubErrorCode) {
                Log.d(tag, "Banner ad failed: $errorCode")
            }

            override fun onBannerClicked(banner: MoPubView) {
                Log.d(tag, "Banner ad clicked")
            }

            override fun onBannerExpanded(banner: MoPubView) {
                Log.d(tag, "Banner ad expanded")
            }

            override fun onBannerCollapsed(banner: MoPubView) {
                Log.d(tag, "Banner ad collapsed")
            }
        }
        banner?.loadAd()
    }

    fun playInterstitial(view: View?) {
        if (interstitial?.isReady == true) {
            interstitial?.show()
        } else {
            Log.d(tag, "Interstitial not ready yet")
        }
    }

    fun playVideo(view: View?) {
        if (MoPubRewardedAds.hasRewardedAd(VideoId)) {
            MoPubRewardedAds.showRewardedAd(VideoId)
        } else {
            Log.d(tag, "Video not ready yet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        banner?.destroy()
        interstitial?.destroy()
    }
}