package com.superawesome.composeexample.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.superawesome.composeexample.enums.FeatureType
import com.superawesome.composeexample.models.FeatureItem
import com.superawesome.composeexample.models.PlacementItem
import com.superawesome.composeexample.ui.theme.SAMobileSDKAndroidTheme
import com.superawesome.composeexample.views.AdBannerView
import com.superawesome.composeexample.views.FeatureToggles
import com.superawesome.composeexample.views.PlacementsList
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.ui.video.SAVideoAd

class FeatureDetailActivity : ComponentActivity() {

    private val featureItem: FeatureItem by lazy {
        (intent.getParcelableExtra(featureExtraKey) as? FeatureItem)!!
    }

    private fun loadAd(placement: PlacementItem) {
        when (featureItem.type) {
            FeatureType.Interstitial -> {
                loadInterstitial(placement = placement)
            }

            FeatureType.Video -> {
                loadVideo(placement = placement)
            }

            else -> Unit
        }
    }

    private fun loadInterstitial(placement: PlacementItem) {
        if (placement.creativeId != null && placement.lineItemId != null) {
            SAInterstitialAd.load(
                placementId = placement.placementId,
                lineItemId = placement.lineItemId,
                creativeId = placement.creativeId,
                context = this,
            )
        } else {
            SAInterstitialAd.load(placementId = placement.placementId, context = this)
        }
    }

    private fun loadVideo(placement: PlacementItem) {
        if (placement.creativeId != null && placement.lineItemId != null) {
            SAVideoAd.load(
                placementId = placement.placementId,
                lineItemId = placement.lineItemId,
                creativeId = placement.creativeId,
                context = this,
            )
        } else {
            SAVideoAd.load(placementId = placement.placementId, context = this)
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpInterstitials()
        setUpVideos()

        setContent {

            val selectedPlacement: MutableState<PlacementItem?> = remember { mutableStateOf(null) }
            val isTestModeEnabled = remember { mutableStateOf(false) }
            val isBumperEnabled = remember { mutableStateOf(false) }
            val isParentGateEnabled = remember { mutableStateOf(false) }
            val isBackButtonEnabled = remember { mutableStateOf(false) }
            val isCloseButtonEnabled = remember { mutableStateOf(false) }
            val isCloseButtonDelayEnabled = remember { mutableStateOf(false) }
            val isVideoMuteStartEnabled = remember { mutableStateOf(false) }
            val isLeaveWarningEnabled = remember { mutableStateOf(false) }

            SAMobileSDKAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                backgroundColor = MaterialTheme.colors.primary,
                                title = { Text(stringResource(featureItem.type.titleRes)) },
                            )
                        },
                        content = { _ ->
                            Column {
                                PlacementsList(
                                    placements = featureItem.placements,
                                    onTapPlacement = { placement ->
                                        selectedPlacement.value = placement
                                        loadAd(placement = placement)
                                    },
                                    modifier = Modifier
                                        .padding(start = 6.dp, end = 6.dp, bottom = 20.dp)
                                        .weight(1.0f)
                                )
                                if (featureItem.type == FeatureType.Banner) {
                                    AdBannerView(
                                        placement = selectedPlacement.value,
                                        isTestModeEnabled = isTestModeEnabled.value,
                                        isBumperEnabled = isBumperEnabled.value,
                                        isParentGateEnabled = isParentGateEnabled.value,
                                        modifier = Modifier
                                            .fillMaxWidth(1.0f)
                                            .height(adHeight)
                                    )
                                }
                                FeatureToggles(
                                    feature = featureItem,
                                    isTestModeEnabled = isTestModeEnabled,
                                    isBumperEnabled = isBumperEnabled,
                                    isParentGateEnabled = isParentGateEnabled,
                                    isBackButtonEnabled = isBackButtonEnabled,
                                    isCloseButtonEnabled = isCloseButtonEnabled,
                                    isCloseButtonDelayEnabled = isCloseButtonDelayEnabled,
                                    isVideoMuteStartEnabled = isVideoMuteStartEnabled,
                                    isVideoLeaveWarningEnabled = isLeaveWarningEnabled,
                                    modifier = Modifier
                                        .padding(start = 16.dp, end = 10.dp, bottom = 10.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    private fun setUpInterstitials() {
        SAInterstitialAd.setListener { placementId, event ->
            when (event) {
                SAEvent.adLoaded -> SAInterstitialAd.play(placementId, this)
                else -> Unit
            }
        }
    }

    private fun setUpVideos() {
        SAVideoAd.setListener { placementId, event ->
            when (event) {
                SAEvent.adLoaded -> SAVideoAd.play(placementId, this)
                else -> Unit
            }
        }
    }

    companion object {
        const val featureExtraKey = "FEATURE"
        private val adHeight = 100.dp
    }
}
