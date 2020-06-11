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
        private const val BannerId = "06bd0a18718e45cead8216427916b11e"
        private const val InterstitialId = "5a1ed1a3355d40f6ba549d83efc8c0f4"
        private const val VideoId = "10f827049a714613bf605bd58fa90624"

        // MoPub Test Ids
//        private const val BannerId = "b195f8dd8ded45fe847ad89ed1d016da"
//        private const val InterstitialId = "24534e1901884e398f1253216226017e"
//        private const val VideoId = "920b6145fb1546cf8b5cf2ac34638bb7"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mopub)

        MoPub.initializeSdk(this, SdkConfiguration.Builder(BannerId).build()) {
            Log.d("SADefaults/MoPub", "MoPub.initializeSdk completed")

            configureBannerAd()
            configureInterstitialAd()
            configureRewardedVideoAd()
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
        banner?.adUnitId = BannerId
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

    fun playInterstitial(view: View?) {
        if (interstitial?.isReady == true) {
            interstitial?.show()
        } else {
            Log.d("SADefaults/MoPub", "Interstitial not ready yet")
        }
    }

    fun playVideo(view: View?) {
        if (MoPubRewardedVideos.hasRewardedVideo(VideoId)) {
            MoPubRewardedVideos.showRewardedVideo(VideoId)
        } else {
            Log.d("SADefaults/MoPub", "Video not ready yet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        banner?.destroy()
        interstitial?.destroy()
    }
}