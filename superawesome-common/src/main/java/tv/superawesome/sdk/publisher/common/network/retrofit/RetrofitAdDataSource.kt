package tv.superawesome.sdk.publisher.common.network.retrofit

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.network.DataResult

@ExperimentalSerializationApi
class RetrofitAdDataSource(private val awesomeAdsApi: RetrofitAwesomeAdsApi) :
    AwesomeAdsApiDataSourceType {

    override suspend fun getAd(placementId: Int, query: AdQuery): DataResult<Ad> = try {
        DataResult.Success(awesomeAdsApi.ad(placementId, Properties.encodeToMap(query)))
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun getAd(lineItemId: Int, creativeId: Int, query: AdQuery): DataResult<Ad> =
        try {
            DataResult.Success(
                awesomeAdsApi.ad(
                    lineItemId,
                    creativeId,
                    Properties.encodeToMap(query)
                )
            )
        } catch (exception: Exception) {
            DataResult.Failure(exception)
        }

    override suspend fun impression(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(awesomeAdsApi.impression(Properties.encodeToMap(query)))
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun click(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(awesomeAdsApi.click(Properties.encodeToMap(query)))
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun videoClick(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(awesomeAdsApi.videoClick(Properties.encodeToMap(query)))
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun event(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(awesomeAdsApi.event(Properties.encodeToMap(query)))
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }
}