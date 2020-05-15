package tv.superawesome.sdk.publisher.networking.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import tv.superawesome.sdk.publisher.common.models.Ad

interface RetrofitAwesomeAdsApi {
    @GET("/ad/{placementId}")
    suspend fun ad(@Path("placementId") placementId: Int, @QueryMap(encoded = true) query: Map<String, Any>): Ad

    @GET("/impression")
    suspend fun impression(@QueryMap(encoded = true) query: Map<String, Any>): Void

    @GET("/click")
    suspend fun click(@QueryMap(encoded = true) query: Map<String, Any>): Void

    @GET("/video/click")
    suspend fun videoClick(@QueryMap(encoded = true) query: Map<String, Any>): Void

    @GET("/event")
    suspend fun event(@QueryMap(encoded = true) query: Map<String, Any>): Void
}