package tv.superawesome.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mopub)

        MoPub.initializeSdk(this, SdkConfiguration.Builder(BannerId)
                .withAdditionalNetwork("tv.superawesome.mopub.AwesomeAdsMoPubAdapterConfiguration")
                .build()) {
            Log.d("SADefaults/MoPub", "MoPub.initializeSdk completed")

            configureBannerAd()
            configureInterstitialAd()
            configureRewardedVideoAd()
        }
         findViewById<Button>(R.id.playInterstitialBtn)?.setOnClickListener {
             if (interstitial?.isReady == true) {
                 interstitial?.show()
             } else {
                 Log.d("SADefaults/MoPub", "Interstitial not ready yet")
             }
         }

        findViewById<Button>(R.id.playVideoBtn)?.setOnClickListener {
            if (MoPubRewardedVideos.hasRewardedVideo(VideoId)) {
                MoPubRewardedVideos.showRewardedVideo(VideoId)
            } else {
                Log.d("SADefaults/MoPub", "Video not ready yet")
            }
        }
    }

    private fun configureRewardedVideoAd() {
        MoPubRewardedVideos.setRewardedVideoListener(object : MoPubRewardedVideoListener {
            override fun onRewardedVideoLoadSuccess(adUnitId: String) {
                Log.d("SADefaults/MoPub", "Video loaded")
            }

            override fun onRewardedVideoLoadFailure(adUnitId: String, errorCode: MoPubErrorCode) {
                Log.d("SADefaults/MoPub", "Video failure: $errorCode")
            }

            override fun onRewardedVideoStarted(adUnitId: String) {
                Log.d("SADefaults/MoPub", "Video started")
            }

            override fun onRewardedVideoPlaybackError(adUnitId: String, errorCode: MoPubErrorCode) {
                Log.d("SADefaults/MoPub", "Video error: $errorCode")
            }

            override fun onRewardedVideoClicked(adUnitId: String) {
                Log.d("SADefaults/MoPub", "Video clicked")
            }

            override fun onRewardedVideoClosed(adUnitId: String) {
                Log.d("SADefaults/MoPub", "Video closed")
            }

            override fun onRewardedVideoCompleted(adUnitIds: Set<String>, reward: MoPubReward) {
                Log.d("SADefaults/MoPub", "Video completed")
            }
        })
        MoPubRewardedVideos.loadRewardedVideo(VideoId)
    }

    private fun configureInterstitialAd() {
        interstitial = MoPubInterstitial(this, InterstitialId)
        interstitial?.interstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialLoaded(interstitial: MoPubInterstitial) {
                Log.d("SADefaults/MoPub", "Interstitial loaded")
            }

            override fun onInterstitialFailed(interstitial: MoPubInterstitial, errorCode: MoPubErrorCode) {
                Log.d("SADefaults/MoPub", "Interstitial failed: $errorCode")
            }

            override fun onInterstitialShown(interstitial: MoPubInterstitial) {
                Log.d("SADefaults/MoPub", "Interstitial shown")
            }

            override fun onInterstitialClicked(interstitial: MoPubInterstitial) {
                Log.d("SADefaults/MoPub", "Interstitial clicked")
            }

            override fun onInterstitialDismissed(interstitial: MoPubInterstitial) {
                Log.d("SADefaults/MoPub", "Interstitial dimissed")
            }
        }
        interstitial?.load()
    }

    private fun configureBannerAd() {
        banner = findViewById<View>(R.id.adview) as MoPubView
        banner?.setAdUnitId(BannerId)
        banner?.bannerAdListener = object : BannerAdListener {
            override fun onBannerLoaded(banner: MoPubView) {
                Log.d("SADefaults/MoPub", "Banner ad loaded")
            }

            override fun onBannerFailed(banner: MoPubView, errorCode: MoPubErrorCode) {
                Log.d("SADefaults/MoPub", "Banner ad failed: $errorCode")
            }

            override fun onBannerClicked(banner: MoPubView) {
                Log.d("SADefaults/MoPub", "Banner ad clicked")
            }

            override fun onBannerExpanded(banner: MoPubView) {
                Log.d("SADefaults/MoPub", "Banner ad expanded")
            }

            override fun onBannerCollapsed(banner: MoPubView) {
                Log.d("SADefaults/MoPub", "Banner ad collapsed")
            }
        }
        banner?.loadAd()
    }


    override fun onDestroy() {
        super.onDestroy()
        banner?.destroy()
        interstitial?.destroy()
    }
}