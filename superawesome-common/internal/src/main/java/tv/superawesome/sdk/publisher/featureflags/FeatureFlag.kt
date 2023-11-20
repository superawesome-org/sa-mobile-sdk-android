package tv.superawesome.sdk.publisher.featureflags

interface FeatureFlag {

    /**
     * Fetches the feature flags and loads them into memory.
     */
    suspend fun fetch()
}
