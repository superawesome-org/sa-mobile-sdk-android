package tv.superawesome.sdk.publisher.networking.retrofit

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Properties
import tv.superawesome.sdk.publisher.common.datasources.AdDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.network.DataResult

class RetrofitAdDataSource(private val awesomeAdsApi: RetrofitAwesomeAdsApi) : AdDataSourceType {

    @ImplicitReflectionSerializer
    override suspend fun getAd(placementId: Int, query: AdQuery): DataResult<Ad> = try {
        DataResult.success(awesomeAdsApi.ad(placementId, Properties.store(query)))
    } catch (exception: Exception) {
        DataResult.failure(exception)
    }

    @ImplicitReflectionSerializer
    override suspend fun impression(query: EventQuery): DataResult<Void> = try {
        DataResult.success(awesomeAdsApi.impression(Properties.store(query)))
    } catch (exception: Exception) {
        DataResult.failure(exception)
    }

    @ImplicitReflectionSerializer
    override suspend fun click(query: EventQuery): DataResult<Void> = try {
        DataResult.success(awesomeAdsApi.click(Properties.store(query)))
    } catch (exception: Exception) {
        DataResult.failure(exception)
    }

    @ImplicitReflectionSerializer
    override suspend fun videoClick(query: EventQuery): DataResult<Void> = try {
        DataResult.success(awesomeAdsApi.videoClick(Properties.store(query)))
    } catch (exception: Exception) {
        DataResult.failure(exception)
    }

    @ImplicitReflectionSerializer
    override suspend fun event(query: EventQuery): DataResult<Void> = try {
        DataResult.success(awesomeAdsApi.event(Properties.store(query)))
    } catch (exception: Exception) {
        DataResult.failure(exception)
    }
}