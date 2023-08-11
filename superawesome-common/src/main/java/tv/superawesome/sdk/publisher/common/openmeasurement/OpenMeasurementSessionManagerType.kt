package tv.superawesome.sdk.publisher.common.openmeasurement

import android.view.View
import android.webkit.WebView

internal interface OpenMeasurementSessionManagerType {

    fun setup(webView: WebView)
    fun start()
    fun finish()
    fun setAdView(view: View?)
    fun injectJS(html: String): String
    fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    )
    fun sendAdLoaded()
    fun sendAdImpression()
}
