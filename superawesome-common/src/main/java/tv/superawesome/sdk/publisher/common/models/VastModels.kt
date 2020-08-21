package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.Serializable

data class VastAd(var url: String? = null,
                  var redirect: String? = null,
                  var type: VastType = VastType.Invalid,
                  var events: MutableList<VastEvent> = mutableListOf(),
                  var media: MutableList<VastMedia> = mutableListOf()) {

    fun merge(from: VastAd?): VastAd {
        if (from == null) return this

        this.url = from.url ?: this.url
        this.events.addAll(from.events)
        this.media.addAll(from.media)

        return this
    }

    fun addMedia(media: VastMedia) {
        this.media.add(media)
    }

    fun addEvent(event: VastEvent) {
        this.events.add(event)
    }

    fun sortedMedia(): List<VastMedia> = media.sortedBy(VastMedia::bitrate)
}

enum class VastType {
    Invalid, InLine, Wrapper
}

data class VastMedia(
        var type: String?,
        var url: String?,
        var bitrate: Int?,
        var width: Int?,
        var height: Int?
)

@Serializable
data class VastEvent(var event: String, var url: String)

