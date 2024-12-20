package tv.superawesome.sdk.publisher.ad

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.extensions.isValidVastAd
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType

/**
 * Base ad manager, creates any type of ad.
 */
abstract class BaseAdManager(
    private val adRepository: AdRepositoryType,
    private val logger: Logger,
    private val adControllerFactory: AdControllerFactory,
    private val adStore: AdControllerStore,
) : AdManager {

    override var listener: SAInterface? = null

    abstract override val adConfig: AdConfig

    override suspend fun load(
        placementId: Int,
        adRequest: AdRequest,
    ) = doLoad(placementId) {
        adRepository.getAd(
            placementId = placementId,
            request = adRequest,
            adConfig = adConfig,
        )
    }

    override suspend fun load(
        placementId: Int,
        lineItemId: Int,
        creativeId: Int,
        adRequest: AdRequest,
    ) = doLoad(placementId) {
        adRepository.getAd(
            placementId = placementId,
            lineItemId = lineItemId,
            creativeId = creativeId,
            request = adRequest,
            adConfig = adConfig,
        )
    }

    override fun hasAdAvailable(placementId: Int): Boolean = adStore.peek(placementId) != null

    override fun removeController(placementId: Int) {
        adStore.consume(placementId)
    }

    override fun clearCache() {
        adStore.clear()
    }

    private suspend fun doLoad(
        placementId: Int,
        loader: suspend () -> Result<AdResponse>,
    ) {
        if (hasAdAvailable(placementId)) {
            withContext(Dispatchers.Main) {
                listener?.onEvent(placementId, SAEvent.adAlreadyLoaded)
            }
        } else {
            loader().mapCatching { adResponse ->
                if (!adResponse.isValidVastAd) throw AdExceptions.MissingVastTagError
                adResponse
            }.fold(
                onSuccess = { adResponse ->
                    logger.success("Loaded ad $placementId successfully")
                    adControllerFactory.make(adResponse, adConfig, listener).also {
                        adStore.put(it)
                    }
                    withContext(Dispatchers.Main) {
                        listener?.onEvent(adResponse.placementId, SAEvent.adLoaded)
                    }
                },
                onFailure = { error ->
                    logger.error("Failed to load ad $placementId", error)
                    withContext(Dispatchers.Main) {
                        listener?.onEvent(placementId, SAEvent.adFailedToLoad)
                    }
                }
            )
        }
    }
}
