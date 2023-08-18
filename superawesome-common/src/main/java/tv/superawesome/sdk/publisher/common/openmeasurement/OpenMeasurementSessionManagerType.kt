package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.view.View
import android.webkit.WebView

/**
 * Interface for the OpenMeasurement Session Manger.
 */

internal interface OpenMeasurementSessionManagerType {

    fun setup(
        context: Context,
        webView: WebView,
    )
    fun start()
    fun finish()
    fun setAdView(view: View?)
    fun injectJS(context: Context, html: String): String
    fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    )
    fun sendAdLoaded()
    fun sendAdImpression()
}
