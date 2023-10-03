package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType

class DefaultAdLoader(
    private val adConfig: AdConfig,
    private val logger: Logger,
    private val adRepository: AdRepositoryType,
) : AdLoader {

    override suspend fun load(placementId: Int, request: AdRequest): Result<AdResponse> {
        logger.info("load($placementId) thread:${Thread.currentThread()}")
        return adRepository.getAd(placementId, request)
    }

    override suspend fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        request: AdRequest
    ): Result<AdResponse> {
        logger.info("load($placementId) thread:${Thread.currentThread()}")
        return adRepository.getAd(placementId, lineItemId, creativeId, request)
    }
}
