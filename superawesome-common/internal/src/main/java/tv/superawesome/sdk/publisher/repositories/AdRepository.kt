package tv.superawesome.sdk.publisher.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType

interface AdRepositoryType {
    suspend fun getAd(placementId: Int, request: AdRequest): Result<AdResponse>
    suspend fun getAd(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ): Result<AdResponse>
}

class AdRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType,
    private val adProcessor: tv.superawesome.sdk.publisher.components.AdProcessorType
) : AdRepositoryType {
    override suspend fun getAd(placementId: Int, request: AdRequest): Result<AdResponse> =
        withContext(Dispatchers.IO) {
            println("getAd($placementId)")
            dataSource.getAd(placementId, adQueryMaker.makeAdQuery(request)).map { ad ->
                adProcessor.process(placementId, ad, request.options, request.openRtbPartnerId)
            }
        }

    override suspend fun getAd(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ): Result<AdResponse> = withContext(Dispatchers.IO) {
        dataSource.getAd(
            placementId,
            lineItemId,
            creativeId,
            adQueryMaker.makeAdQuery(request)
        ).map { ad ->
            adProcessor.process(placementId, ad, request.options, request.openRtbPartnerId)
        }
    }
}
