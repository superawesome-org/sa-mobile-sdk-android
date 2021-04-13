package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Creative(
    val id: Int,
    var name: String? = null,
    val format: CreativeFormatType,
    var click_url: String? = null,
    val details: CreativeDetail,
    var bumper: Boolean? = null,
    var referral: CreativeReferral? = null
)

@Serializable
data class CreativeReferral(
    val utm_source: Int = -1,
    val utm_campaign: Int = -1,
    val utm_term: Int = -1,
    val utm_content: Int = -1,
    val utm_medium: Int = -1,
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "utm_source" to utm_source,
            "utm_campaign" to utm_campaign,
            "utm_term" to utm_term,
            "utm_content" to utm_content,
            "utm_medium" to utm_medium,
        )
    }
}

@Serializable
data class CreativeDetail(
    val url: String,
    val image: String? = null,
    val video: String,
    val placement_format: String,
    var tag: String? = null,
    val width: Int,
    val height: Int,
    val duration: Int,
    var vast: String? = null,
)

@Serializable
enum class CreativeFormatType {
    video,
    image_with_link,
    tag,
    rich_media
}
