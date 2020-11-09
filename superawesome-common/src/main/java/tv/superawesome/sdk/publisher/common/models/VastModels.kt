package tv.superawesome.sdk.publisher.common.models

class VastAd(
        var url: String? = null,
        var redirect: String? = null,
        var type: VastType = VastType.Invalid,
        var media: MutableList<VastMedia> = mutableListOf(),
        var clickThroughUrl: String? = null,
        var errorEvents: MutableList<String> = mutableListOf(),
        var impressionEvents: MutableList<String> = mutableListOf(),
        var creativeViewEvents: MutableList<String> = mutableListOf(),
        var startEvents: MutableList<String> = mutableListOf(),
        var firstQuartileEvents: MutableList<String> = mutableListOf(),
        var midPointEvents: MutableList<String> = mutableListOf(),
        var thirdQuartileEvents: MutableList<String> = mutableListOf(),
        var completeEvents: MutableList<String> = mutableListOf(),
        var clickTrackingEvents: MutableList<String> = mutableListOf(),
) {
    fun merge(from: VastAd?): VastAd {
        if (from == null) return this

        this.url = from.url ?: this.url
        this.clickThroughUrl = from.clickThroughUrl ?: this.clickThroughUrl
        this.errorEvents.addAll(from.errorEvents)
        this.impressionEvents.addAll(from.impressionEvents)
        this.creativeViewEvents.addAll(from.creativeViewEvents)
        this.startEvents.addAll(from.startEvents)
        this.firstQuartileEvents.addAll(from.firstQuartileEvents)
        this.midPointEvents.addAll(from.midPointEvents)
        this.thirdQuartileEvents.addAll(from.thirdQuartileEvents)
        this.completeEvents.addAll(from.completeEvents)
        this.clickTrackingEvents.addAll(from.clickTrackingEvents)
        this.media.addAll(from.media)

        return this
    }

    fun addMedia(media: VastMedia) {
        this.media.add(media)
    }

    fun addEvent(event: VastEvent) {
        when (event.event) {
            "vast_click_through" -> clickThroughUrl = event.url
            "vast_error" -> errorEvents.add(event.url)
            "vast_impression" -> impressionEvents.add(event.url)
            "vast_creativeView" -> creativeViewEvents.add(event.url)
            "vast_start" -> startEvents.add(event.url)
            "vast_firstQuartile" -> firstQuartileEvents.add(event.url)
            "vast_midpoint" -> midPointEvents.add(event.url)
            "vast_thirdQuartile" -> thirdQuartileEvents.add(event.url)
            "vast_complete" -> completeEvents.add(event.url)
            "vast_click_tracking" -> clickTrackingEvents.add(event.url)
        }
    }

    fun sortedMedia(): List<VastMedia> = media.sortedBy { it.bitrate }
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

data class VastEvent(val event: String, val url: String)