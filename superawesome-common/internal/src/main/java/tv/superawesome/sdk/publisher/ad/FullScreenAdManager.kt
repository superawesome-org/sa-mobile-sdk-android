package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType

/**
 * Fullscreen ad manager, creates interstitials and video ads.
 */
class FullScreenAdManager(
    adRepository: AdRepositoryType,
    logger: Logger,
    adControllerFactory: AdControllerFactory,
    adStore: AdControllerStore,
) : BaseAdManager(adRepository, logger, adControllerFactory, adStore) {

    override val adConfig: FullScreenAdConfig = FullScreenAdConfig()
}
