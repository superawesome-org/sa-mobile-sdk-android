package tv.superawesome.sdk.publisher.featureflags

import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Retrofit interface for fetching feature flags.
 */
interface AdServerFeatureFlagsApi {

    /**
     * Gets the feature flags.
     */
    @GET("/featureFlags.json")
    @JvmSuppressWildcards
    suspend fun getFlags(
        @QueryMap query: Map<String, Any>,
    ): FeatureFlags
}
