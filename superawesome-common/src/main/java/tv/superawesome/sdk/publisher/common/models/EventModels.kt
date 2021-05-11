package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventQuery(
    val placement: Int,
    val bundle: String,
    val creative: Int,
    @SerialName("line_item") val lineItem: Int,
    val ct: ConnectionType,
    val sdkVersion: String,
    val rnd: Int,
    val type: EventType?,
    @SerialName("no_image") val noImage: Boolean?,
    val data: String?
)

@Serializable
data class EventData(
    val placement: Int,
    @SerialName("line_item") val lineItem: Int,
    val creative: Int,
    val type: EventType
)

@Serializable
enum class EventType {
    @SerialName("impressionDownloaded")
    ImpressionDownloaded,
    @SerialName("viewable_impression")
    ViewableImpression,
    @SerialName("parentalGateOpen")
    ParentalGateOpen,
    @SerialName("parentalGateClos")
    ParentalGateClose,
    @SerialName("parentalGateFail")
    ParentalGateFail,
    @SerialName("parentalGateSuccess")
    ParentalGateSuccess,
    @SerialName("custom.analytics.DWELL_TIME")
    DwellTime,
}
