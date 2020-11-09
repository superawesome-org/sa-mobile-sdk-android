package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.components.DispatcherProviderType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AdRepositoryType {
    suspend fun getAd(placementId: Int, request: AdRequest): DataResult<AdResponse>
}

class AdRepository(
        private val dataSource: AwesomeAdsApiDataSourceType,
        private val adQueryMaker: AdQueryMakerType,
        private val adProcessor: AdProcessorType,
        private val dispatcherProvider: DispatcherProviderType,
) : AdRepositoryType {
    override suspend fun getAd(placementId: Int, request: AdRequest): DataResult<AdResponse> =
            withContext(dispatcherProvider.io) {
                when (val result = dataSource.getAd(placementId, adQueryMaker.makeAdQuery(request))) {
                    is DataResult.Success -> adProcessor.process(placementId, result.value)
                    is DataResult.Failure -> result
                }
            }
}