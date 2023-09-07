package com.superawesome.composeexample.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.superawesome.composeexample.R
import com.superawesome.composeexample.enums.FeatureType
import com.superawesome.composeexample.models.FeatureItem
import tv.superawesome.sdk.publisher.ui.interstitial.SAInterstitialAd
import tv.superawesome.sdk.publisher.ui.video.SAVideoAd

@Composable
fun FeatureToggles(
    feature: FeatureItem,
    isTestModeEnabled: MutableState<Boolean>,
    isBumperEnabled: MutableState<Boolean>,
    isParentGateEnabled: MutableState<Boolean>,
    isBackButtonEnabled: MutableState<Boolean>,
    isCloseButtonEnabled: MutableState<Boolean>,
    isCloseButtonDelayEnabled: MutableState<Boolean>,
    isVideoMuteStartEnabled: MutableState<Boolean>,
    isVideoLeaveWarningEnabled: MutableState<Boolean>,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        FeatureSwitch(
            name = stringResource(id = R.string.options_test_mode_title),
            value = isTestModeEnabled,
            onChange = { value ->
                when (feature.type) {
                    FeatureType.Banner -> {}
                    FeatureType.Interstitial -> {
                        if (value) SAInterstitialAd.enableTestMode() else SAInterstitialAd.disableTestMode()
                    }
                    FeatureType.Video -> {
                        if (value) SAVideoAd.enableTestMode() else SAVideoAd.disableTestMode()
                    }
                }
            }
        )
        FeatureSwitch(
            name = stringResource(id = R.string.options_bumper_title),
            value = isBumperEnabled,
            onChange = { value ->
                when (feature.type) {
                    FeatureType.Banner -> {}
                    FeatureType.Interstitial -> {
                        if (value) SAInterstitialAd.enableBumperPage() else SAInterstitialAd.disableBumperPage()
                    }
                    FeatureType.Video -> {
                        if (value) SAVideoAd.enableBumperPage() else SAVideoAd.disableBumperPage()
                    }
                }
            }
        )
        FeatureSwitch(
            name = stringResource(id = R.string.options_parent_gate_title),
            value = isParentGateEnabled,
            onChange = { value ->
                when (feature.type) {
                    FeatureType.Banner -> {}
                    FeatureType.Interstitial -> {
                        if (value) SAInterstitialAd.enableParentalGate() else SAInterstitialAd.disableParentalGate()
                    }
                    FeatureType.Video -> {
                        if (value) SAVideoAd.enableParentalGate() else SAVideoAd.disableParentalGate()
                    }
                }
            }
        )
        if (feature.type != FeatureType.Banner) {
            FeatureSwitch(
                name = stringResource(id = R.string.options_back_button_title),
                value = isBackButtonEnabled,
                onChange = { value ->
                    when (feature.type) {
                        FeatureType.Interstitial -> {
                            if (value) SAInterstitialAd.enableBackButton() else SAInterstitialAd.disableBackButton()
                        }
                        FeatureType.Video -> {
                            if (value) SAVideoAd.enableBackButton() else SAVideoAd.disableBackButton()
                        }
                        else -> Unit
                    }
                }
            )
        }
        when (feature.type) {
            FeatureType.Interstitial -> {
                FeatureSwitch(
                    name = stringResource(id = R.string.options_close_button_delay_title),
                    value = isCloseButtonDelayEnabled,
                    onChange = { value ->
                        if (value) SAInterstitialAd.enableCloseButtonNoDelay() else SAInterstitialAd.enableCloseButton()
                    }
                )
            }

            FeatureType.Video -> {
                FeatureSwitch(
                    name = stringResource(id = R.string.options_close_button_title),
                    value = isCloseButtonEnabled,
                    onChange = { value ->
                        if (value) SAVideoAd.enableCloseButton() else SAVideoAd.disableCloseButton()
                    }
                )
                FeatureSwitch(
                    name = stringResource(id = R.string.options_mute_video_start_title),
                    value = isVideoMuteStartEnabled,
                    onChange = { value ->
                        if (value) SAVideoAd.enableMuteOnStart() else SAVideoAd.disableMuteOnStart()
                    }
                )
                FeatureSwitch(
                    name = stringResource(id = R.string.options_video_close_warning_title),
                    value = isVideoLeaveWarningEnabled,
                    onChange = { value ->
                        SAVideoAd.setCloseButtonWarning(value)
                    }
                )
            }
            else -> Unit
        }
    }
}
