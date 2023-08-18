package tv.superawesome.sdk.publisher.common.openmeasurement

import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSession

/**
 * Interface for Open Measurement AdSession Factory.
 */
internal interface OpenMeasurementAdSessionFactoryType {
    fun getHtmlAdSession(
        webView: WebView,
        customReferenceData: String?,
    ): AdSession?
}
