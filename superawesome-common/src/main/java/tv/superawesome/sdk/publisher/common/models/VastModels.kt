package tv.superawesome.sdk.publisher.common.models

data class VastAd(
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
    fun merge(from: VastAd): VastAd =
        copy(
            url = from.url ?: url,
            clickThroughUrl = from.clickThroughUrl ?: clickThroughUrl,
            errorEvents = errorEvents.toMutableList().also { it.addAll(from.errorEvents) },
            impressionEvents = impressionEvents.toMutableList()
                .also { it.addAll(from.impressionEvents) },
            creativeViewEvents = creativeViewEvents.toMutableList()
                .also { it.addAll(from.creativeViewEvents) },
            startEvents = startEvents.toMutableList().also { it.addAll(from.startEvents) },
            firstQuartileEvents = firstQuartileEvents.toMutableList()
                .also { it.addAll(from.firstQuartileEvents) },
            midPointEvents = midPointEvents.toMutableList().also { it.addAll(from.midPointEvents) },
            thirdQuartileEvents = thirdQuartileEvents.toMutableList()
                .also { it.addAll(from.thirdQuartileEvents) },
            completeEvents = completeEvents.toMutableList().also { it.addAll(from.completeEvents) },
            clickTrackingEvents = clickTrackingEvents.toMutableList()
                .also { it.addAll(from.clickTrackingEvents) },
            media = this.media.toMutableList().also { it.addAll(from.media) },
            redirect = null
        )
}

enum class VastType {
    Invalid, InLine, Wrapper
}

data class VastMedia(
    val type: String?,
    val url: String?,
    val bitrate: Int?,
    val width: Int?,
    val height: Int?,
)