package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.network.DataResult

internal interface AdRepositoryType {
    suspend fun getAd(placementId: Int, request: AdRequest): DataResult<AdResponse>
    suspend fun getAd(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ): DataResult<AdResponse>
}

internal class AdRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType,
    private val adProcessor: AdProcessorType
) : AdRepositoryType {
    override suspend fun getAd(placementId: Int, request: AdRequest): DataResult<AdResponse> =
        withContext(Dispatchers.IO) {
            when (val result = dataSource.getAd(placementId, adQueryMaker.makeAdQuery(request))) {
                is DataResult.Success -> adProcessor.process(placementId, result.value, request.options)
                is DataResult.Failure -> result
            }
        }

    override suspend fun getAd(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ): DataResult<AdResponse> = withContext(Dispatchers.IO) {
        when (
            val result =
                dataSource.getAd(placementId, lineItemId, creativeId, adQueryMaker.makeAdQuery(request))
        ) {
            is DataResult.Success -> adProcessor.process(placementId, result.value, request.options)
            is DataResult.Failure -> result
        }
    }
}
