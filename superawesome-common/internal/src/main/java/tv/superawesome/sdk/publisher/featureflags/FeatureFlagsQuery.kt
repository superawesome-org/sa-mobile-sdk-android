package tv.superawesome.sdk.publisher.featureflags

import tv.superawesome.sdk.publisher.components.DeviceCategory

/**
 * Object to hold the FF query params.
 */
data class FeatureFlagsQuery(
    val sdkVersion: String,
    val bundle: String,
    val language: String,
    val device: DeviceCategory,
)
