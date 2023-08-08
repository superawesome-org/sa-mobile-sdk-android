package tv.superawesome.lib.saopenmeasurement

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
import tv.superawesome.sdk.publisher.SAVersion

internal object OMAdSessionBuilder {

    private const val PARTNER_NAME = "SuperAwesomeLtd"

    fun getHtmlAdSession(
        context: Context,
        webView: WebView?,
        customReferenceData: String?,
    ): AdSession {
        Omid.activate(context.applicationContext)
        val adSessionConfiguration: AdSessionConfiguration = createSessionConfig()
        val partner: Partner = createPartner()
        val adSessionContext: AdSessionContext =
            createAdSessionContext(partner, webView, customReferenceData)
        val adSession: AdSession =
            AdSession.createAdSession(adSessionConfiguration, adSessionContext)
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
        Partner.createPartner(PARTNER_NAME, SAVersion.getSDKVersionNumber())

    private fun createAdSessionContext(
        partner: Partner,
        webView: WebView?,
        customReferenceData: String?,
    ): AdSessionContext =
        AdSessionContext.createHtmlAdSessionContext(
            partner,
            webView,
            webView?.url,
            customReferenceData,
        )
}
