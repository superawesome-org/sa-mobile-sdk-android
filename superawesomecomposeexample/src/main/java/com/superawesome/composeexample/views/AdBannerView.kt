package com.superawesome.composeexample.views

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.superawesome.composeexample.R
import com.superawesome.composeexample.models.PlacementItem
import tv.superawesome.sdk.publisher.common.models.SAEvent
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView

@Composable
fun AdBannerView(
    placement: PlacementItem?,
    isTestModeEnabled: Boolean,
    isBumperEnabled: Boolean,
    isParentGateEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AndroidView(factory = { ctx ->
            BannerView(ctx).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setListener { _, event ->
                    when (event) {
                        SAEvent.AdLoaded -> this.play()
                        else -> Unit
                    }
                }
                setBackgroundColor(resources.getColor(R.color.cardview_dark_background))
            }
        }, update = {
            if (isTestModeEnabled) it.enableTestMode() else it.disableTestMode()
            if (isBumperEnabled) it.enableBumperPage() else it.disableBumperPage()
            if (isParentGateEnabled) it.enableParentalGate() else it.disableParentalGate()

            placement?.let { placement ->
                if (placement.lineItemId != null && placement.creativeId != null) {
                    it.load(
                        placementId = placement.placementId,
                        lineItemId = placement.lineItemId,
                        creativeId = placement.creativeId,
                    )
                } else {
                    it.load(placementId = placement.placementId)
                }
            }
        })
    }
}
