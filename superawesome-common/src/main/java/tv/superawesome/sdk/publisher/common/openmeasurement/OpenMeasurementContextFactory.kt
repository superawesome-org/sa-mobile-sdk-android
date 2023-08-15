package tv.superawesome.sdk.publisher.common.openmeasurement

import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSessionContext
import com.iab.omid.library.superawesome.adsession.Partner
import tv.superawesome.sdk.publisher.common.components.Logger

/**
 * Factory for the Open Measurement AdSession Context.
 */
internal class OpenMeasurementContextFactory(
    private val logger: Logger,
): OpenMeasurementContextFactoryType {

    /**
     * Factory for the Open Measurement AdSession Context.
     * @param adView The view containing the ad.
     * @param adType The type of ad context being generated.
     * @param partner The OM partner object.
     * @param customReferenceData Any additional params in a JSON string.
     * @return Ad session context for specified ad type.
     */
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
        } catch (error: IllegalArgumentException) {
            logger.error("Unable to create session context", error)
            null
        }
}
