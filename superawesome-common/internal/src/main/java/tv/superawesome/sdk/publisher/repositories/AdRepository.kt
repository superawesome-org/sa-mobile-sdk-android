package tv.superawesome.sdk.publisher.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.network.DataResult
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType

interface AdRepositoryType {
    suspend fun getAd(placementId: Int, request: AdRequest): DataResult<AdResponse>
    suspend fun getAd(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ): DataResult<AdResponse>
}

class AdRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType,
    private val adProcessor: tv.superawesome.sdk.publisher.components.AdProcessorType
) : AdRepositoryType {
    override suspend fun getAd(placementId: Int, request: AdRequest): DataResult<AdResponse> =
        withContext(Dispatchers.IO) {
            when (val result = dataSource.getAd(placementId, adQueryMaker.makeAdQuery(request))) {
                is DataResult.Success -> adProcessor.process(
                    placementId,
                    result.value,
                    request.options,
                    request.openRtbPartnerId
                )
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
            is DataResult.Success -> adProcessor.process(
                placementId,
                result.value,
                request.options,
                request.openRtbPartnerId,
            )
            is DataResult.Failure -> result
        }
    }
}
