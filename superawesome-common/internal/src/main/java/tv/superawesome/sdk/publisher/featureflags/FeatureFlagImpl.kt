package tv.superawesome.sdk.publisher.featureflags

import tv.superawesome.sdk.publisher.components.Logger

/**
 * Default implementation of a feature flag manager.
 *
 * @property datasource data source to obtain feature flags.
 * @property logger a logger.
 */
class FeatureFlagImpl(
    private val datasource: FeatureFlagsDatasource,
    private val logger: Logger,
) : FeatureFlag {

    /**
     * Feature flags.
     */
    var flags: FeatureFlags = FeatureFlags()
        private set

    override suspend fun fetch(query: FeatureFlagsQuery) {
        try {
            flags = datasource.getFlags(query)
        } catch (e: Exception) {
            logger.error("Failed to fetch feature flags", e)
        }
    }
}
