package tv.superawesome.sdk.publisher.common.openmeasurement

import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSessionContext
import com.iab.omid.library.superawesome.adsession.Partner

internal interface OpenMeasurementContextBuilderType {
    fun sessionContext(
        adView: WebView,
        adType: OpenMeasurementAdType,
        partner: Partner,
        customReferenceData: String?,
    ): AdSessionContext?
}

internal class DefaultOpenMeasurementContextBuilder: OpenMeasurementContextBuilderType {
   override fun sessionContext(
        adView: WebView,
        adType: OpenMeasurementAdType,
        partner: Partner,
        customReferenceData: String?,
    ): AdSessionContext? = null
}
