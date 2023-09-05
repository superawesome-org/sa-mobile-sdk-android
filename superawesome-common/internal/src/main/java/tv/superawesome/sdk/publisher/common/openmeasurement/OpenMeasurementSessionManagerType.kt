package tv.superawesome.sdk.publisher.common.openmeasurement

import android.view.View
import android.webkit.WebView

/**
 * Interface for the OpenMeasurement Session Manger.
 */

interface OpenMeasurementSessionManagerType {

    fun setup(webView: WebView, adType: OpenMeasurementAdType)
    fun start()
    fun finish()
    fun setAdView(view: View?)
    fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    )
    fun sendAdLoaded()
    fun sendAdImpression()
}
