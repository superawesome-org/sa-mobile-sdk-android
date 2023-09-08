package com.superawesome.composeexample.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.superawesome.composeexample.R
import com.superawesome.composeexample.enums.FeatureType
import com.superawesome.composeexample.models.FeatureItem
import com.superawesome.composeexample.models.PlacementItem
import com.superawesome.composeexample.ui.theme.SAMobileSDKAndroidTheme
import com.superawesome.composeexample.views.FeaturesList
import tv.superawesome.sdk.publisher.AwesomeAds

class FeaturesActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SAMobileSDKAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                backgroundColor = MaterialTheme.colors.primary,
                                title = { Text(stringResource(R.string.features_title)) }
                            )
                        },
                        content = { _ ->
                            val title = resources.getString(R.string.version_title)
                            val version = AwesomeAds.info()?.versionNumber ?: ""
                            Column {
                                Text(
                                    text = "$title $version",
                                    modifier = Modifier
                                        .fillMaxWidth(1.0f)
                                        .padding(
                                            start = 16.dp,
                                            top = 10.dp,
                                            bottom = 0.dp,
                                            end = 10.dp
                                        ),
                                )
                                FeaturesList(
                                    features = features,
                                    onTapFeature = { feature ->
                                        val intent = Intent(
                                            this@FeaturesActivity,
                                            FeatureDetailActivity::class.java,
                                        )
                                        intent.putExtra(
                                            FeatureDetailActivity.featureExtraKey,
                                            feature
                                        )
                                        startActivity(intent)
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    companion object {
        val features = listOf(
            FeatureItem(
                type = FeatureType.Banner,
                placements = listOf(
                    PlacementItem(
                        type = FeatureType.Banner,
                        placementId = 82088,
                    ),
                    PlacementItem(
                        name = "Solid Colour",
                        type = FeatureType.Banner,
                        placementId = 88001,
                    ),
                    PlacementItem(
                        name = "Test Multi ID",
                        type = FeatureType.Banner,
                        placementId = 82088,
                        lineItemId = 176803,
                        creativeId = 499387,
                    )
                )
            ),
            FeatureItem(
                type = FeatureType.Interstitial,
                placements = listOf(
                    PlacementItem(
                        name = "Flat Colour Portrait",
                        type = FeatureType.Interstitial,
                        placementId = 87892,
                    ),
                    PlacementItem(
                        name = "Portrait",
                        type = FeatureType.Interstitial,
                        placementId = 82089,
                    ),
                    PlacementItem(
                        name = "via KSF",
                        type = FeatureType.Interstitial,
                        placementId = 82088,
                    ),
                    PlacementItem(
                        name = "Flat Colour via KSF",
                        type = FeatureType.Interstitial,
                        placementId = 87970,
                    ),
                    PlacementItem(
                        name = "Test Multi ID",
                        type = FeatureType.Interstitial,
                        placementId = 82089,
                        lineItemId = 176803,
                        creativeId = 503038,
                    )
                )
            ),
            FeatureItem(
                type = FeatureType.Video,
                placements = listOf(
                    PlacementItem(
                        name = "PopJam VPAID",
                        type = FeatureType.Video,
                        placementId = 93969,
                    ),
                    PlacementItem(
                        name = "PopJam Celtra VPAID Ad",
                        type = FeatureType.Video,
                        placementId = 90636
                    ),
                    PlacementItem(
                        name = "VAST Flat Colour",
                        type = FeatureType.Video,
                        placementId = 88406,
                    ),
                    PlacementItem(
                        name = "VPAID Flat Colour",
                        type = FeatureType.Video,
                        placementId = 89056,
                    ),
                    PlacementItem(
                        name = "Direct Flat Colour",
                        type = FeatureType.Video,
                        placementId = 87969,
                    ),
                    PlacementItem(
                        name = "Direct",
                        type = FeatureType.Video,
                        placementId = 82090,
                    ),
                    PlacementItem(
                        name = "VAST", type = FeatureType.Video,
                        placementId = 84777,
                        lineItemId = 178822,
                        creativeId = 503585,
                    ),
                    PlacementItem(
                        name = "VPAID via KSF",
                        type = FeatureType.Video,
                        placementId = 84798,
                    ),
                    PlacementItem(
                        name = "Test Multi ID",
                        type = FeatureType.Video,
                        placementId = 82090,
                        lineItemId = 176803,
                        creativeId = 499385,
                    ),
                ),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SAMobileSDKAndroidTheme {
        FeaturesList(features = FeaturesActivity.features, onTapFeature = {})
    }
}
