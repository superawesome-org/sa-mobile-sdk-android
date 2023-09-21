package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.properties.Properties
import tv.superawesome.sdk.publisher.extensions.mergeToMap

data class EventQueryBundle(
    val parameters: EventQuery,
    val options: Map<String, Any>?,
) {
    fun build(): Map<String, Any> = Properties.mergeToMap(parameters, options)
}

@Serializable
data class EventQuery(
    val placement: Int,
    val bundle: String,
    val creative: Int,
    @SerialName("line_item") val lineItem: Int,
    val ct: Int,
    val sdkVersion: String,
    val rnd: String,
    val type: EventType?,
    @SerialName("no_image") val noImage: Boolean?,
    val data: String?,
    val adRequestId: String?,
    val openRtbPartnerId: String?,
)

@Serializable
data class EventData(
    val placement: Int,
    @SerialName("line_item") val lineItem: Int,
    val creative: Int,
    val type: EventType,
)

@Serializable
enum class EventType {
    @SerialName("impressionDownloaded")
    ImpressionDownloaded,

    @SerialName("viewable_impression")
    ViewableImpression,

    @SerialName("parentalGateOpen")
    ParentalGateOpen,

    @SerialName("parentalGateClose")
    ParentalGateClose,

    @SerialName("parentalGateFail")
    ParentalGateFail,

    @SerialName("parentalGateSuccess")
    ParentalGateSuccess,

    @SerialName("viewTime")
    DwellTime,
}
