package tv.superawesome.sdk.publisher.common.models

internal data class VastAd(
    var url: String? = null,
    val redirect: String?,
    val type: VastType,
    val media: List<VastMedia>,
    val clickThroughUrl: String?,
    val errorEvents: List<String>,
    val impressionEvents: List<String>,
    val creativeViewEvents: List<String>,
    val startEvents: List<String>,
    val firstQuartileEvents: List<String>,
    val midPointEvents: List<String>,
    val thirdQuartileEvents: List<String>,
    val completeEvents: List<String>,
    val clickTrackingEvents: List<String>,
) {
    fun merge(fromAd: VastAd?): VastAd {
        val from = fromAd ?: return this
        return copy(
            url = from.url ?: url,
            clickThroughUrl = from.clickThroughUrl ?: clickThroughUrl,
            errorEvents = errorEvents + from.errorEvents,
            impressionEvents = impressionEvents + from.impressionEvents,
            creativeViewEvents = creativeViewEvents + from.creativeViewEvents,
            startEvents = startEvents + from.startEvents,
            firstQuartileEvents = firstQuartileEvents + from.firstQuartileEvents,
            midPointEvents = midPointEvents + from.midPointEvents,
            thirdQuartileEvents = thirdQuartileEvents + from.thirdQuartileEvents,
            completeEvents = completeEvents + from.completeEvents,
            clickTrackingEvents = clickTrackingEvents + from.clickTrackingEvents,
            media = media + from.media,
            redirect = null
        )
    }
}

internal enum class VastType {
    Invalid, InLine, Wrapper
}

internal data class VastMedia(
    val type: String?,
    val url: String?,
    val bitrate: Int?,
    val width: Int?,
    val height: Int?,
)
