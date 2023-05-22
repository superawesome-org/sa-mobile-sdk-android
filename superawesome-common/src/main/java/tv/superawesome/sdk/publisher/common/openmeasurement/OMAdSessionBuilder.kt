package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.webkit.WebView
import com.iab.omid.library.superawesome.Omid
import com.iab.omid.library.superawesome.adsession.AdSession
import com.iab.omid.library.superawesome.adsession.AdSessionConfiguration
import com.iab.omid.library.superawesome.adsession.AdSessionContext
import com.iab.omid.library.superawesome.adsession.CreativeType
import com.iab.omid.library.superawesome.adsession.ImpressionType
import com.iab.omid.library.superawesome.adsession.Owner
import com.iab.omid.library.superawesome.adsession.Partner
import tv.superawesome.sdk.publisher.common.components.SdkInfoType

interface OMAdSessionBuilderType {
    fun getHtmlAdSession(
        context: Context,
        webView: WebView?,
        customReferenceData: String?,
    ): AdSession
}

class OMAdSessionBuilder(
    private val sdkInfo: SdkInfoType,
): OMAdSessionBuilderType {

    override fun getHtmlAdSession(
        context: Context,
        webView: WebView?,
        customReferenceData: String?,
    ): AdSession {
        ensureOmidActivated(context)
        val adSessionConfiguration: AdSessionConfiguration =
            AdSessionConfiguration.createAdSessionConfiguration(
                CreativeType.HTML_DISPLAY,
                ImpressionType.BEGIN_TO_RENDER,
                Owner.NATIVE,
                Owner.NONE,
                false,
            )
        val partner: Partner = getPartner()
        val adSessionContext: AdSessionContext = AdSessionContext.createHtmlAdSessionContext(
            partner,
            webView,
            null,
            customReferenceData,
        )
        val adSession: AdSession = AdSession.createAdSession(adSessionConfiguration, adSessionContext)
        adSession.registerAdView(webView)
        return adSession
    }

    /**
     * Lazily activate the OMID API.
     *
     * @param context any context
     */
    private fun ensureOmidActivated(context: Context) {
        Omid.activate(context.applicationContext)
    }

    private fun getPartner(): Partner =
        Partner.createPartner(PARTNER_NAME, sdkInfo.versionNumber)

    companion object {
        private const val PARTNER_NAME = "SuperAwesomeLtd"
    }
}
