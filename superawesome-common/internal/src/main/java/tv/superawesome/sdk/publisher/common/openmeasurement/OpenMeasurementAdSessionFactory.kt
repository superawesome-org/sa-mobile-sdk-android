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
 * Builds and configures the Open Measurement AdSession.
 */
class OpenMeasurementAdSessionFactory(
    private val omidActivator: OmidActivatorType,
    private val sdkInfo: SdkInfoType,
    private val contextBuilder: OpenMeasurementContextFactoryType,
): OpenMeasurementAdSessionFactoryType {
    /**
     * Gets the OM AdSession object.
     * @param webView The web view that is presenting the ad.
     * @param customReferenceData Additional info for the context.
     * @return The ad session object for the given web view.
     */
    override fun getHtmlAdSession(
        webView: WebView,
        customReferenceData: String?,
    ): AdSession {
        omidActivator.activate()
        val adSessionConfiguration: AdSessionConfiguration = createHTMLSessionConfig()
        return getSession(webView, adSessionConfiguration, customReferenceData)
    }

    override fun getHtmlVideoAdSession(
        webView: WebView,
        customReferenceData: String?,
    ): AdSession {
        val adSessionConfiguration: AdSessionConfiguration = createSessionConfigForVideo()
        return getSession(webView, adSessionConfiguration, customReferenceData)
    }

    private fun getSession(
        adView: WebView,
        adSessionConfiguration: AdSessionConfiguration,
        customReferenceData: String?,
    ): AdSession {
        val partner: Partner = createPartner()
        val adSessionContext = contextBuilder.sessionContext(
            adView,
            OpenMeasurementAdType.HTMLDisplay,
            partner,
            customReferenceData,
        )
        val adSession: AdSession = AdSession.createAdSession(
            adSessionConfiguration,
            adSessionContext,
        )
        adSession.registerAdView(adView)
        return adSession
    }

    private fun createHTMLSessionConfig(): AdSessionConfiguration =
        AdSessionConfiguration.createAdSessionConfiguration(
            CreativeType.HTML_DISPLAY,
            ImpressionType.BEGIN_TO_RENDER,
            Owner.NATIVE,
            Owner.NONE,
            false,
        )

    private fun createSessionConfigForVideo(): AdSessionConfiguration =
        AdSessionConfiguration.createAdSessionConfiguration(
            CreativeType.DEFINED_BY_JAVASCRIPT,
            ImpressionType.DEFINED_BY_JAVASCRIPT,
            Owner.JAVASCRIPT,
            Owner.JAVASCRIPT,
            false,
        )

    private fun createPartner(): Partner =
        Partner.createPartner(PARTNER_NAME, sdkInfo.versionNumber)
}

internal const val PARTNER_NAME = "SuperAwesomeLtd"
