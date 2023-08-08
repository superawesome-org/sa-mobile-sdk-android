package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.view.View
import android.webkit.WebView

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

internal class DefaultOpenMeasurementSessionManager: OpenMeasurementSessionManagerType {

    override fun setup(
        context: Context,
        webView: WebView,
    ) = Unit

    override fun start() = Unit

    override fun finish() = Unit

    override fun setAdView(view: View?) = Unit

    override fun injectJS(
        context: Context,
        html: String,
    ): String = ""

    override fun addFriendlyObstruction(
        view: View,
        purpose: FriendlyObstructionType,
        reason: String?,
    ) = Unit

    override fun sendAdLoaded() = Unit

    override fun sendAdImpression()  = Unit
}
