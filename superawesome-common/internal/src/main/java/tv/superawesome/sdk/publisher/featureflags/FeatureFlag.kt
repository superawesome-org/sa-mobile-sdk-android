package tv.superawesome.sdk.publisher.featureflags

interface FeatureFlag {

    /**
     * Fetches the feature flags and loads them into memory.
     *
     * @param query necessary query params.
     */
    suspend fun fetch(query: FeatureFlagsQuery)
}
