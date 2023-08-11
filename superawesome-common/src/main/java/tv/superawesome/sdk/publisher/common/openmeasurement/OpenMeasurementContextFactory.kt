package tv.superawesome.sdk.publisher.common.openmeasurement

import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSessionContext
import com.iab.omid.library.superawesome.adsession.Partner
import tv.superawesome.sdk.publisher.common.components.Logger

internal class OpenMeasurementContextFactory(
    private val logger: Logger,
): OpenMeasurementContextFactoryType {

    override fun sessionContext(
        adView: WebView,
        adType: OpenMeasurementAdType,
        partner: Partner,
        customReferenceData: String?,
    ): AdSessionContext? =
        try {
            when (adType) {
                OpenMeasurementAdType.HTMLDisplay -> AdSessionContext.createHtmlAdSessionContext(
                    partner,
                    adView,
                    adView.url,
                    customReferenceData,
                )
                OpenMeasurementAdType.Video -> null
            }
        } catch (error: Exception) {
            logger.error("Unable to create session context", error)
            null
        }
}
