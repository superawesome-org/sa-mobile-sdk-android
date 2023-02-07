package tv.superawesome.sdk.publisher.common.network.retrofit

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.extensions.mergeToMap
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.network.DataResult

@ExperimentalSerializationApi
class RetrofitAdDataSource(private val awesomeAdsApi: RetrofitAwesomeAdsApi) :
    AwesomeAdsApiDataSourceType {

    override suspend fun getAd(placementId: Int, query: AdQuery): DataResult<Ad> = try {
        DataResult.Success(
            awesomeAdsApi.ad(
                placementId,
                Properties.mergeToMap(query, query.options)
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun getAd(placementId: Int, lineItemId: Int, creativeId: Int, query: AdQuery): DataResult<Ad> =
        try {
            DataResult.Success(
                awesomeAdsApi.ad(
                    placementId,
                    lineItemId,
                    creativeId,
                    Properties.mergeToMap(query, query.options)
                )
            )
        } catch (exception: Exception) {
            DataResult.Failure(exception)
        }

    override suspend fun impression(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(
            awesomeAdsApi.impression(
                Properties.mergeToMap(query, query.options)
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun click(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(
            awesomeAdsApi.click(
                Properties.mergeToMap(query, query.options)
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun videoClick(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(
            awesomeAdsApi.videoClick(
                Properties.mergeToMap(query, query.options)
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }

    override suspend fun event(query: EventQuery): DataResult<Void> = try {
        DataResult.Success(
            awesomeAdsApi.event(
                Properties.mergeToMap(query, query.options)
            )
        )
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }
}