package tv.superawesome.sdk.publisher.testutil

import tv.superawesome.sdk.publisher.ad.AdConfig
import tv.superawesome.sdk.publisher.ad.FullScreenAdConfig
import tv.superawesome.sdk.publisher.ad.AdController
import tv.superawesome.sdk.publisher.ad.AdControllerFactory
import tv.superawesome.sdk.publisher.ad.DefaultAdController
import tv.superawesome.sdk.publisher.ad.DefaultAdEventHandler
import tv.superawesome.sdk.publisher.ad.DefaultAdPerformanceTrackerHandler
import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.PerformanceRepositoryType

class FakeAdControllerFactory(
    private val numberGenerator: NumberGeneratorType,
    private val logger: Logger,
    private val eventRepository: EventRepositoryType,
    private val performanceRepository: PerformanceRepositoryType,
    private val timeProvider: TimeProviderType,
) : AdControllerFactory {

    override fun make(
        adResponse: AdResponse,
        adConfig: AdConfig,
        listener: SAInterface?,
    ): AdController {
        val adEventhandler = DefaultAdEventHandler(eventRepository, adResponse)
        val adPerformanceTrackerHandler = DefaultAdPerformanceTrackerHandler(
            performanceRepository, timeProvider, adResponse
        )

        return DefaultAdController(
            adResponse = adResponse,
            listener = listener,
            numberGenerator = numberGenerator,
            logger = logger,
            adConfig = adConfig,
            adEventHandler = adEventhandler,
            adPerformanceTrackerHandler = adPerformanceTrackerHandler,
        )
    }
}
