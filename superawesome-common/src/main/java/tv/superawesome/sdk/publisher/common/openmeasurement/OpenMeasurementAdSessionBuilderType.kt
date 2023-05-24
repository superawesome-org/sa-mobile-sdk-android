package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSession

interface OpenMeasurementAdSessionBuilderType {
    fun getHtmlAdSession(
        context: Context,
        webView: WebView,
        customReferenceData: String?,
    ): AdSession?
}

internal class DefaultOpenMeasurementAdSessionBuilder: OpenMeasurementAdSessionBuilderType {
    override fun getHtmlAdSession(
        context: Context,
        webView: WebView,
        customReferenceData: String?,
    ): AdSession? = null
}
