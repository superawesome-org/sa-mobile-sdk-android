package tv.superawesome.sdk.publisher.common.openmeasurement

import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSession
import com.iab.omid.library.superawesome.adsession.AdSessionConfiguration
import com.iab.omid.library.superawesome.adsession.CreativeType
import com.iab.omid.library.superawesome.adsession.ImpressionType
import com.iab.omid.library.superawesome.adsession.Owner
import com.iab.omid.library.superawesome.adsession.Partner
import tv.superawesome.sdk.publisher.common.components.SdkInfoType

/**
 * OpenMeasurementAdSessionBuilder - Builds and configures the Open Measurement AdSession
 */
internal class OpenMeasurementAdSessionFactory(
    private val omidActivator: OmidActivatorType,
    private val sdkInfo: SdkInfoType,
    private val contextBuilder: OpenMeasurementContextFactoryType,
): OpenMeasurementAdSessionFactoryType {
    /**
     * getHtmlAdSession - gets the OM AdSession object
     * @param webView - the web view that is presenting the ad
     * @param customReferenceData - additional info for the context
     * @return the ad session object for the given web view
     */
    override fun getHtmlAdSession(
        webView: WebView,
        customReferenceData: String?,
    ): AdSession {
        omidActivator.activate()
        val adSessionConfiguration: AdSessionConfiguration = createSessionConfig()
        val partner: Partner = createPartner()
        val adSessionContext = contextBuilder.sessionContext(
            webView,
            OpenMeasurementAdType.HTMLDisplay,
            partner,
            customReferenceData,
        )

        val adSession: AdSession = AdSession.createAdSession(
            adSessionConfiguration,
            adSessionContext,
        )
        adSession.registerAdView(webView)
        return adSession
    }

    private fun createSessionConfig(): AdSessionConfiguration =
        AdSessionConfiguration.createAdSessionConfiguration(
            CreativeType.HTML_DISPLAY,
            ImpressionType.BEGIN_TO_RENDER,
            Owner.NATIVE,
            Owner.NONE,
            false,
        )

    private fun createPartner(): Partner =
        Partner.createPartner(PARTNER_NAME, sdkInfo.versionNumber)

    companion object {
        private const val PARTNER_NAME = "SuperAwesomeLtd"
    }
}
