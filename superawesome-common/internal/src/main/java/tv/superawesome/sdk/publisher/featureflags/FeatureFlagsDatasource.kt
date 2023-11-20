package tv.superawesome.sdk.publisher.featureflags

/**
 * Data source to obtain feature flags.
 */
interface FeatureFlagsDatasource {

    /**
     * Gets the feature flags and returns it.
     */
    suspend fun getFlags(): FeatureFlags
}
