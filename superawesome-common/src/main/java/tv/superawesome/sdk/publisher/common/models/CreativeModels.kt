package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Creative(
        val id: Int,
        val name: String?,
        val format: CreativeFormatType,
        val click_url: String,
        val details: CreativeDetail
)

@Serializable
data class CreativeDetail(
        val url: String,
        val image: String,
        val video: String,
        val placement_format: String,
        val tag: String?,
        val width: Int,
        val height: Int,
        val transcodedVideos: String?,
        val duration: Int,
        val vast: String?
)

@Serializable
enum class CreativeFormatType {
    video,
    image_with_link,
    tag,
    rich_media
}