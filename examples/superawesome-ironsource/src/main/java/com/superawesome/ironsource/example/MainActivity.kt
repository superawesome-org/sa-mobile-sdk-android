package com.superawesome.ironsource.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.integration.IntegrationHelper
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.model.Placement
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener
import com.superawesome.ironsource.example.ui.theme.MyComposeTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity(), LevelPlayRewardedVideoListener, LevelPlayInterstitialListener {

    private val rewardedVideoLoadedState = MutableStateFlow(false)
    private val interstitialAdLoadedState = MutableStateFlow(false)
    private val rewardedVideoPlacement = MutableStateFlow<Placement?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        rewardedVideoLoadedState,
                        interstitialAdLoadedState,
                        rewardedVideoPlacement,
                        onRewardedVideoButtonClick = {
                            Log.d(TAG, "Is rewarded video available: ${IronSource.isRewardedVideoAvailable()}")
                            if (IronSource.isRewardedVideoAvailable()) {
                                IronSource.showRewardedVideo()
                            }
                        },
                        onInterstitialButtonClick = {
                            Log.d(TAG, "Is interstitial ready: ${IronSource.isInterstitialReady()}")
                            if (IronSource.isInterstitialReady()) {
                                IronSource.showInterstitial()
                            }
                        },
                        onDismissDialog = {
                            rewardedVideoPlacement.value = null
                        }
                    )
                }
            }
        }

        // The integrationHelper is used to validate the integration.
        // Remove the integrationHelper before going live!
        IntegrationHelper.validateIntegration(this)
        setupIronSource()
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
        IronSource.loadInterstitial()
        IronSource.loadRewardedVideo()
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private fun setupIronSource() {
        val advertisingId = IronSource.getAdvertiserId(this)
        // we're using an advertisingId as the 'userId'
        initIronSource(advertisingId)

        //Network Connectivity Status
        IronSource.shouldTrackNetworkState(this, true)
    }

    private fun initIronSource(userId: String?) {
        IronSource.setLevelPlayRewardedVideoListener(this)
        IronSource.setLevelPlayInterstitialListener(this)
        IronSource.setUserId(userId)
        IronSource.init(
            this,
            BuildConfig.APP_KEY,
            IronSource.AD_UNIT.REWARDED_VIDEO,
            IronSource.AD_UNIT.INTERSTITIAL,
        )
    }

    override fun onAdOpened(p0: AdInfo?) {
        Log.d(TAG, "Rewarded video opened")
    }

    override fun onAdShowFailed(p0: IronSourceError?, p1: AdInfo?) {
        Log.e(TAG, "Rewarded video failed to show. ${p0?.errorMessage}")
    }

    override fun onAdClicked(p0: Placement?, p1: AdInfo?) {
        Log.d(TAG, "Rewarded video clicked")
    }

    override fun onAdRewarded(p0: Placement?, p1: AdInfo?) {
        Log.d(TAG, "Rewarded video placement reward: $p0")
        rewardedVideoPlacement.value = p0
    }

    override fun onAdClosed(p0: AdInfo?) {
        Log.d(TAG, "Rewarded video closed")
    }

    override fun onAdAvailable(p0: AdInfo?) {
        Log.d(TAG, "Rewarded video available")
        rewardedVideoLoadedState.value = true
    }

    override fun onAdUnavailable() {
        Log.w(TAG, "Rewarded video ad unavailable")
    }

    override fun onAdClicked(p0: AdInfo?) {
        Log.d(TAG, "Interstitial ad clicked")
    }

    override fun onAdLoadFailed(p0: IronSourceError?) {
        Log.e(TAG, "Interstitial ad load failed: $p0")
    }

    override fun onAdReady(p0: AdInfo?) {
        Log.d(TAG, "Interstitial ad ready")
        interstitialAdLoadedState.value = true
    }

    override fun onAdShowSucceeded(p0: AdInfo?) {
        Log.d(TAG, "Interstitial ad show succeeded")
    }

    companion object {
        private const val TAG = "SA-IronSource"
    }
}
