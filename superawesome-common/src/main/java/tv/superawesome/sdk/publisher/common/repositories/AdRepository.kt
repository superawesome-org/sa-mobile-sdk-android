package tv.superawesome.sdk.publisher.common.repositories

import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.datasources.AdDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.network.DataResult

interface AdRepositoryType {
    suspend fun getAd(placementId: Int, request: AdRequest): DataResult<Ad>
}

class AdRepository(private val dataSource: AdDataSourceType,
                   private val adQueryMaker: AdQueryMakerType
) : AdRepositoryType {
    override suspend fun getAd(placementId: Int, request: AdRequest): DataResult<Ad> =
            dataSource.getAd(placementId, adQueryMaker.makeAdQuery(request))
}