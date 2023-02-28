package tv.superawesome.demoapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Features(val features: List<FeatureItem>)

@Serializable
data class FeatureItem(val type: FeatureType, val placements: List<PlacementItem>)

@Serializable
enum class FeatureType {
    @SerialName("banner")
    BANNER,

    @SerialName("interstitial")
    INTERSTITIAL,

    @SerialName("video")
    VIDEO
}

@Serializable
data class PlacementItem(
    var name: String = "",
    var placementId: Int = 0,
    var lineItemId: Int? = null,
    var creativeId: Int? = null,
    var type: FeatureType = FeatureType.BANNER
) {
    fun isFull(): Boolean = lineItemId != null && creativeId != null
}