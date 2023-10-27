package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.ExperimentalSerializationApi
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
    val parentalGateOn: Boolean,
    val bumperPageOn: Boolean,
    val closeWarning: Boolean?,
    val orientation: Int?,
    val closeAtEnd: Boolean?,
    val muteOnStart: Boolean?,
    val showMore: Boolean?,
    val startDelay: Int?,
    val closeButton: Int?,
    val backButtonEnabled: Boolean?,
) {

    /**
     * Parses this object into a JSON string.
     */
    fun toJsonString(): String = json.encodeToString(serializer(), this)

    @OptIn(ExperimentalSerializationApi::class)
    companion object {

        private val json = Json {
            explicitNulls = false
        }

        /**
         * Creates a [PublisherConfiguration] from the [AdConfig].
         */
        fun fromAdConfig(adConfig: AdConfig): PublisherConfiguration =
            PublisherConfiguration(
                parentalGateOn = adConfig.isParentalGateEnabled,
                bumperPageOn = adConfig.isBumperPageEnabled,
                closeWarning = adConfig.shouldShowCloseWarning,
                orientation = adConfig.orientation?.ordinal,
                closeAtEnd = adConfig.shouldCloseAtEnd,
                muteOnStart = adConfig.shouldMuteOnStart,
                showMore = adConfig.shouldShowSmallClick,
                startDelay = adConfig.startDelay?.value,
                closeButton = adConfig.closeButtonState?.value,
                backButtonEnabled = adConfig.isBackButtonEnabled
            )
    }
}
