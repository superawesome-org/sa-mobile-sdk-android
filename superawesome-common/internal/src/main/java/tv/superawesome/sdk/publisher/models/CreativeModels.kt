package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Creative(
    val id: Int,
    val name: String? = null,
    val format: CreativeFormatType,
    @SerialName("click_url") val clickUrl: String? = null,
    val details: CreativeDetail,
    val bumper: Boolean? = null,
    val referral: CreativeReferral? = null,
)

@Serializable
data class CreativeReferral(
    @SerialName("utm_source") val utmSource: Int = -1,
    @SerialName("utm_campaign") val utmCampaign: Int = -1,
    @SerialName("utm_term") val utmTerm: Int = -1,
    @SerialName("utm_content") val utmContent: Int = -1,
    @SerialName("utm_medium") val utmMedium: Int = -1,
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "utm_source" to utmSource,
            "utm_campaign" to utmCampaign,
            "utm_term" to utmTerm,
            "utm_content" to utmContent,
            "utm_medium" to utmMedium,
        )
    }
}

@Serializable
data class CreativeDetail(
    val url: String? = null,
    val image: String? = null,
    val video: String? = null,
    @SerialName("placement_format") val placementFormat: String,
    var tag: String? = null,
    val width: Int,
    val height: Int,
    val duration: Int,
    var vast: String? = null,
)

@Serializable
enum class CreativeFormatType {
    @SerialName("video")
    Video,

    @SerialName("image_with_link")
    ImageWithLink,

    @SerialName("tag")
    Tag,

    @SerialName("rich_media")
    RichMedia
}
