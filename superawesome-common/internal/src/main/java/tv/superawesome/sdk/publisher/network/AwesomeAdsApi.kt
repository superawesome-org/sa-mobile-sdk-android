package tv.superawesome.sdk.publisher.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import tv.superawesome.sdk.publisher.models.Ad

interface AwesomeAdsApi {
    @GET("ad/{placementId}")
    @JvmSuppressWildcards
    suspend fun ad(@Path("placementId") placementId: Int, @QueryMap query: Map<String, Any>): Ad

    @GET("ad/{placementId}/{lineItemId}/{creativeId}")
    @JvmSuppressWildcards
    suspend fun ad(
        @Path("placementId") placementId: Int,
        @Path("lineItemId") lineItemId: Int,
        @Path("creativeId") creativeId: Int,
        @QueryMap query: Map<String, Any>
    ): Ad

    @GET("impression")
    @JvmSuppressWildcards
    suspend fun impression(@QueryMap query: Map<String, Any>)

    @GET("click")
    @JvmSuppressWildcards
    suspend fun click(@QueryMap query: Map<String, Any>)

    @GET("video/click")
    @JvmSuppressWildcards
    suspend fun videoClick(@QueryMap query: Map<String, Any>)

    @GET("event")
    @JvmSuppressWildcards
    suspend fun event(@QueryMap query: Map<String, Any>)

    @GET("sdk/performance")
    @JvmSuppressWildcards
    suspend fun performance(@QueryMap metric: Map<String, Any>)
}
