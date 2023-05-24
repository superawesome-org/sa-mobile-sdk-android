package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.models.Ad
import java.util.Date
import kotlin.random.Random

internal interface HtmlFormatterType {
    fun formatImageIntoHtml(ad: Ad): String
    fun formatRichMediaIntoHtml(placementId: Int, ad: Ad): String
    fun formatTagIntoHtml(ad: Ad): String
}

internal class HtmlFormatter(
    private val numberGenerator: NumberGeneratorType,
    private val encoder: EncoderType
) : HtmlFormatterType {

    override fun formatImageIntoHtml(ad: Ad): String {
        val img =
            "<img src='${ad.creative.details.image ?: ""}' width='100%' height='100%' style='object-fit: contain;'/>"

        if (ad.creative.clickUrl != null) {
            return "<a href='${ad.creative.clickUrl}' target='_blank'>$img</a>"
        }
        return "<html><head></head><body>$img</body></html>"
    }

    override fun formatRichMediaIntoHtml(placementId: Int, ad: Ad): String {
        var iasUrl = ""
        
        if (ad.moat > 0 && numberGenerator.nextDoubleForMoat() < ad.moat) {
            iasUrl = "<script src=\"https://pixel.adsafeprotected.com/jload"
            iasUrl += "?anId=" + "931553"
            iasUrl += "&chanId=$placementId"
            iasUrl += "&campId=${ad.creative.details.width}x${ad.creative.details.height}"
            iasUrl += "&pubId=${ad.publisherId}"
            iasUrl += "&placementId=${ad.lineItemId}"
            iasUrl += "&pubOrder=${ad.campaignId}"
            iasUrl += "&pubCreative=${ad.creative.id}"
            iasUrl += "\"></script>"
        }

        val url =
            "${ad.creative.details.url}?placement=$placementId&line_item=${ad.lineItemId}&creative=${ad.creative.id}&rnd=${numberGenerator.nextIntForCache()}"
        return "<html><head>${iasUrl}</head><body><iframe class='omid-element' style='padding:0;border:0;' width='100%' height='100%' src='$url'></iframe></body></html>"
    }

    override fun formatTagIntoHtml(ad: Ad): String {
        var tag = ad.creative.details.tag ?: ""

        ad.creative.clickUrl?.let { clickUrl ->
            tag = tag.replace("[click]", "$clickUrl&redir=")
                .replace("[click_enc]", encoder.encodeUri(clickUrl))
        } ?: run {
            tag = tag.replace("[click]", "")
                .replace("[click_enc]", "")
        }

        tag = tag.replace("[keywords]", "")
            .replace("[timestamp]", "${System.currentTimeMillis()}")
            .replace("target=\"_blank\"", "")
            .replace("“", "\"")
            .replace("\\t", "")
            .replace("\\n", "")
            .replace("\t", "")

        return tag
    }
}
