package tv.superawesome.sdk.publisher.featureflags

import kotlinx.serialization.Serializable

/**
 * Model holding all feature flags.
 *
 * @property isAdResponseVASTEnabled whether the VAST tag in ad response feature is enabled. Defaults to `false`.
 */
@Serializable
data class FeatureFlags(
    val isAdResponseVASTEnabled: Boolean = false
)
