package tv.superawesome.sdk.publisher.common.models

import kotlinx.serialization.Serializable

@Serializable
data class EventQuery(
        val placement: Int,
        val bundle: String,
        val creative: Int,
        val line_item: Int,
        val ct: ConnectionType,
        val sdkVersion: String,
        val rnd: Int,
        val type: EventType?,
        val no_image: Boolean?,
        val data: String?
)

@Serializable
data class EventRequest(
        val placementId: Int,
        val creativeId: Int,
        val lineItemId: Int,
        val type: EventType,
        val data: EventData? = null
)

@Serializable
data class EventData(
        val placement: Int,
        val line_item: Int,
        val creative: Int,
        val type: EventType
)

@Serializable
enum class EventType {
    impressionDownloaded,
    viewable_impression,
    parentalGateOpen,
    parentalGateClose,
    parentalGateFail,
    parentalGateSuccess
}