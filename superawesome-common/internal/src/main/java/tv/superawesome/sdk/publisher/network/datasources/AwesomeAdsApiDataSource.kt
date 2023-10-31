package tv.superawesome.sdk.publisher.network.datasources

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdQueryBundle
import tv.superawesome.sdk.publisher.models.EventQueryBundle
import tv.superawesome.sdk.publisher.models.PerformanceMetric
import tv.superawesome.sdk.publisher.network.AwesomeAdsApi

interface AwesomeAdsApiDataSourceType {

    suspend fun getAd(placementId: Int, query: AdQueryBundle): Result<Ad>

    suspend fun getAd(
        placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle
    ): Result<Ad>

    suspend fun impression(query: EventQueryBundle): Result<Unit>

    suspend fun click(query: EventQueryBundle): Result<Unit>

    suspend fun videoClick(query: EventQueryBundle): Result<Unit>

    suspend fun event(query: EventQueryBundle): Result<Unit>

    suspend fun performance(metric: PerformanceMetric): Result<Unit>
}

@ExperimentalSerializationApi
@Suppress("TooGenericExceptionCaught")
class AwesomeAdsApiDataSource(
    private val awesomeAdsApi: AwesomeAdsApi,
    private val json: Json,
    private val mockWebServer: MockWebServer,
    private val context: Context,
): AwesomeAdsApiDataSourceType {

    init {
        context.assets.open("json.json").use { inputStream ->
            val vast = String(inputStream.readBytes())
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(vast)
            )
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

    }

    override suspend fun getAd(placementId: Int, query: AdQueryBundle): Result<Ad> =
        runCatching { awesomeAdsApi.ad(placementId, query.build()) }

    override suspend fun getAd(
        placementId: Int, lineItemId: Int, creativeId: Int, query: AdQueryBundle
    ): Result<Ad> = runCatching {
        awesomeAdsApi.ad(placementId, lineItemId, creativeId, query.build())
    }

    override suspend fun impression(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.impression(query.build())
    }

    override suspend fun click(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.click(query.build())
    }

    override suspend fun videoClick(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.videoClick(query.build())
    }

    override suspend fun event(query: EventQueryBundle): Result<Unit> = runCatching {
        awesomeAdsApi.event(query.build())
    }

    override suspend fun performance(metric: PerformanceMetric): Result<Unit> = runCatching {
        awesomeAdsApi.performance(metric.build(json))
    }
}
