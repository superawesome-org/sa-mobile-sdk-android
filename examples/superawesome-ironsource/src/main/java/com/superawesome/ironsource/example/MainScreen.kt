package com.superawesome.ironsource.example

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.model.Placement
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    rewardedVideoLoadedState: StateFlow<Boolean>,
    interstitialLoadedState: StateFlow<Boolean>,
    placementReward: StateFlow<Placement?>,
    onRewardedVideoButtonClick: () -> Unit,
    onInterstitialButtonClick: () -> Unit,
    onDismissDialog: () -> Unit,
) {
    val isRewardedVideoLoaded by rewardedVideoLoadedState.collectAsState()
    val isInterstitialAdLoaded by interstitialLoadedState.collectAsState()
    val rewardDialog by placementReward.collectAsState()

    if (rewardDialog != null) {
        RewardDialog(onDismissDialog)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // SA + IS logo
        Image(
            painter = painterResource(id = R.drawable.logo_sa),
            contentDescription = null,
            modifier = Modifier
                .padding(24.dp)
                .size(64.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logo_is),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
        // Menu
        MainMenu(
            isRewardedVideoLoaded = isRewardedVideoLoaded,
            rewardedVideoButtonClick = onRewardedVideoButtonClick,
            isInterstitialAdLoaded = isInterstitialAdLoaded,
            interstitialAdButtonClick = onInterstitialButtonClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MainMenu(
    isRewardedVideoLoaded: Boolean,
    isInterstitialAdLoaded: Boolean,
    rewardedVideoButtonClick: () -> Unit,
    interstitialAdButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Button(
                enabled = isRewardedVideoLoaded,
                onClick = rewardedVideoButtonClick,
            ) {
                Text(
                    text = if (isRewardedVideoLoaded) {
                        "Show rewarded video"
                    } else {
                        "Loading rewarded video"
                    }
                )
            }
        }
        item {
            Button(
                enabled = isInterstitialAdLoaded,
                onClick = interstitialAdButtonClick,
            ) {
                Text(
                    text = if (isInterstitialAdLoaded) {
                        "Show interstitial ad"
                    } else {
                        "Loading interstitial ad"
                    }
                )
            }
        }
    }
}

@Composable
private fun RewardDialog(
    onDismissDialog: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissDialog,
        confirmButton = {
            Button(onClick = onDismissDialog) {
                Text(text = "Close")
            }
        },
        title = {
            Text(text = "Reward dialog")
        },
        text = {
            Text(text = "You have received a reward")
        }
    )
}
