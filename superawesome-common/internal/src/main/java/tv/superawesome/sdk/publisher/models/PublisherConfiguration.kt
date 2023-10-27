package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.ad.AdConfig

/**
 * Configurations that are passed to the backend, we call them publisher configuration since it's
 * what's set by each publisher when using the SDK.
 */
@Serializable
data class PublisherConfiguration(
    @SerialName("closeButton")
    val closeButtonState: Int?,
    val orientation: Int?,
    val parentalGateOn: Boolean,
    val bumperPageOn: Boolean,
) {

    /**
     * Parses this object into a JSON string.
     */
    fun toJsonString(): String = Json.encodeToString(serializer(), this)

    companion object {

        /**
         * Creates a [PublisherConfiguration] from the [AdConfig].
         */
        fun fromAdConfig(adConfig: AdConfig): PublisherConfiguration =
            PublisherConfiguration(
                closeButtonState = adConfig.closeButtonState?.value,
                orientation = adConfig.orientation?.ordinal,
                parentalGateOn = adConfig.isParentalGateEnabled,
                bumperPageOn = adConfig.isBumperPageEnabled,
            )
    }
}
