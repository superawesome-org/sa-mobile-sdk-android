package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType

/**
 * Interstitial ad manager, creates interstitials ads.
 */
class InterstitialAdManager(
    adRepository: AdRepositoryType,
    logger: Logger,
    adControllerFactory: AdControllerFactory,
    adStore: AdControllerStore,
) : BaseAdManager(adRepository, logger, adControllerFactory, adStore) {

    override val adConfig: InterstitialAdConfig = InterstitialAdConfig()
}
