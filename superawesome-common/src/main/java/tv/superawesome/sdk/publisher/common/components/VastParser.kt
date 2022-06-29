package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.models.ConnectionQuality
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.models.VastMedia
import tv.superawesome.sdk.publisher.common.models.VastType

interface VastParserType {
    fun parse(data: String): VastAd?
}

class VastParser(
    private val parser: XmlParserType,
    private val connectionProvider: ConnectionProviderType
) : VastParserType {
    override fun parse(data: String): VastAd? {
        val document = parser.parse(data)
        val ad = parser.findFirst(document, "Ad") ?: return null
        val vastType = when {
            parser.exists(ad, "InLine") -> VastType.InLine
            parser.exists(ad, "Wrapper") -> VastType.Wrapper
            else -> VastType.Invalid
        }

        val redirectUrl = parser.findFirst(ad, "VASTAdTagURI")?.textContent
        val errors = parser.findAll(ad, "Error").mapNotNull { it.textContent }
        val impressions = parser.findAll(ad, "Impression").mapNotNull { it.textContent }

        parser.findFirst(ad, "Creative")?.let { creative ->
            val clickThrough = parser.findAll(creative, "ClickThrough")
                .firstOrNull()?.textContent?.replace("&amp;", "&")
                ?.replace("%3A", ":")?.replace("%2F", "/")

            val clickTracking = parser.findAll(creative, "ClickTracking").mapNotNull {
                it.textContent
            }
            val trackingElements = parser.findAll(creative, "Tracking")
            val creativeViews =
                trackingElements.filter { it.getAttribute("event") == "creativeView" }
                    .mapNotNull { it.textContent }
            val start = trackingElements.filter { it.getAttribute("event") == "start" }
                .mapNotNull { it.textContent }
            val firstQuartile =
                trackingElements.filter { it.getAttribute("event") == "firstQuartile" }
                    .mapNotNull { it.textContent }
            val midPoint = trackingElements.filter { it.getAttribute("event") == "midpoint" }
                .mapNotNull { it.textContent }
            val thirdQuartile =
                trackingElements.filter { it.getAttribute("event") == "thirdQuartile" }
                    .mapNotNull { it.textContent }
            val complete = trackingElements.filter { it.getAttribute("event") == "complete" }
                .mapNotNull { it.textContent }

            val media = parser.findAll(creative, "MediaFile").filter {
                it.getAttribute("type")?.contains("mp4") == true
            }.map {
                val bitrate = it.getAttribute("bitrate")?.toIntOrNull() ?: 0
                val width = it.getAttribute("width")?.toIntOrNull() ?: 0
                val height = it.getAttribute("height")?.toIntOrNull() ?: 0
                VastMedia(
                    it.getAttribute("type"),
                    it.textContent.replace(" ", ""),
                    bitrate,
                    width,
                    height
                )
            }

            val sortedMedia = media.sortedBy { it.bitrate }

            val url = when (connectionProvider.findConnectionType().findQuality()) {
                ConnectionQuality.Minimum -> sortedMedia.firstOrNull()?.url
                ConnectionQuality.Medium -> if (sortedMedia.size > 2) {
                    sortedMedia[sortedMedia.size / 2].url
                } else sortedMedia.lastOrNull()?.url
                ConnectionQuality.Maximum -> sortedMedia.lastOrNull()?.url
            }

            return VastAd(
                url = url,
                type = vastType,
                redirect = redirectUrl,
                errorEvents = errors,
                impressionEvents = impressions,
                clickThroughUrl = clickThrough,
                clickTrackingEvents = clickTracking,
                media = media,
                creativeViewEvents = creativeViews,
                startEvents = start,
                firstQuartileEvents = firstQuartile,
                midPointEvents = midPoint,
                thirdQuartileEvents = thirdQuartile,
                completeEvents = complete
            )
        }
        return null
    }
}
