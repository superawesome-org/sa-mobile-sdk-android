package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSession

/**
 * Interface for Open Measurement AdSession Factory
 */
internal interface OpenMeasurementAdSessionBuilderType {
    fun getHtmlAdSession(
        context: Context,
        webView: WebView,
        customReferenceData: String?,
    ): AdSession?
}
