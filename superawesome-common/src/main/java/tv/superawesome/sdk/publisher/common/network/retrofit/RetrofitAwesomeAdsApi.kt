package tv.superawesome.sdk.publisher.common.network.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import tv.superawesome.sdk.publisher.common.models.Ad

interface RetrofitAwesomeAdsApi {
    @GET("ad/{placementId}")
    @JvmSuppressWildcards
    suspend fun ad(@Path("placementId") placementId: Int, @QueryMap query: Map<String, Any>): Ad

    @GET("ad/{placementId}/{lineItemId}/{creativeId}")
    @JvmSuppressWildcards
    suspend fun ad(
        @Path("placementId") placementId: Int,
        @Path("lineItemId") lineItemId: Int,
        @Path("creativeId") creativeId: Int,
        @QueryMap query: Map<String, Any>): Ad

    @GET("impression")
    @JvmSuppressWildcards
    suspend fun impression(@QueryMap query: Map<String, Any>): Void

    @GET("click")
    @JvmSuppressWildcards
    suspend fun click(@QueryMap query: Map<String, Any>): Void

    @GET("video/click")
    @JvmSuppressWildcards
    suspend fun videoClick(@QueryMap query: Map<String, Any>): Void

    @GET("event")
    @JvmSuppressWildcards
    suspend fun event(@QueryMap query: Map<String, Any>): Void
}