package tv.superawesome.sdk.publisher

/**
 * Model holding all feature flags.
 *
 * @property isAdResponseVASTEnabled whether the VAST tag in ad response feature is enabled.
 */
data class FeatureFlags(
    val isAdResponseVASTEnabled: Boolean
)
