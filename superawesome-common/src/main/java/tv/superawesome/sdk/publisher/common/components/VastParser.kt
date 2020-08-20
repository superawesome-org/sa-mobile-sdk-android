package tv.superawesome.sdk.publisher.common.components

import org.w3c.dom.Element
import tv.superawesome.sdk.publisher.common.models.*

interface VastParserType {
    fun parse(data: String): VastAd
}

class VastParser(private val parser: XmlParserType, private val connectionProvider: ConnectionProviderType) : VastParserType {
    override fun parse(data: String): VastAd {
        val vastAd = VastAd()
        val document = parser.parse(data)
        val ad = parser.findFirst(document, "Ad") ?: return vastAd

        if (parser.exists(ad, "InLine")) vastAd.type = VastType.InLine
        if (parser.exists(ad, "Wrapper")) vastAd.type = VastType.Wrapper

        parser.findFirst(ad, "VASTAdTagURI")?.let {
            vastAd.redirect = it.textContent
        }

        parser.findAll(ad, "Error").forEach {
            vastAd.addEvent(VastEvent("vast_error", it.textContent))
        }

        parser.findAll(ad, "Impression").forEach {
            vastAd.addEvent(VastEvent("vast_impression", it.textContent))
        }

        parseCreative(ad, vastAd)

        findBestUrlForConnection(vastAd)

        return vastAd
    }

    private fun parseCreative(ad: Element, vastAd: VastAd) {
        val creative = parser.findFirst(ad, "Creative")

        if (creative != null) {
            parser.findAll(creative, "ClickThrough").forEach {
                val url = it.textContent
                        .replace("&amp;", "&")
                        .replace("%3A", ":")
                        .replace("%2F", "/")
                vastAd.addEvent(VastEvent("vast_click_through", url))
            }

            parser.findAll(creative, "ClickTracking").forEach {
                vastAd.addEvent(VastEvent("vast_click_tracking", it.textContent))
            }

            parser.findAll(creative, "Tracking").forEach {
                val url = "vast_${it.getAttribute("event")}"
                vastAd.addEvent(VastEvent("vast_click_tracking", url))
            }

            parser.findAll(creative, "MediaFile").forEach {
                if (it.getAttribute("type")?.contains("mp4") != true) {
                    return@forEach
                }
                val bitrate = it.getAttribute("bitrate")?.toIntOrNull() ?: 0
                val width = it.getAttribute("width")?.toIntOrNull() ?: 0
                val height = it.getAttribute("height")?.toIntOrNull() ?: 0
                val media = VastMedia(
                        it.getAttribute("type"),
                        it.textContent.replace(" ", ""),
                        bitrate,
                        width,
                        height
                )
                vastAd.addMedia(media)
            }
        }
    }

    private fun findBestUrlForConnection(vastAd: VastAd) {
        val sortedMedia = vastAd.sortedMedia()
        when (connectionProvider.findConnectionType().findQuality()) {
            ConnectionQuality.Minimum -> vastAd.url = sortedMedia.firstOrNull()?.url
            ConnectionQuality.Medium -> {
                if (sortedMedia.size > 2) {
                    vastAd.url = sortedMedia[sortedMedia.size / 2].url
                }
            }
            ConnectionQuality.Maximum -> vastAd.url = sortedMedia.lastOrNull()?.url
        }
        if (vastAd.url == null) {
            vastAd.url = vastAd.media.lastOrNull()?.url
        }
    }

}