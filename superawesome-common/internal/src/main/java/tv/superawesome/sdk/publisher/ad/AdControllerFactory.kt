package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.components.Logger
import tv.superawesome.sdk.publisher.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.components.TimeProviderType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.PerformanceRepositoryType

class AdControllerFactory(
    private val numberGenerator: NumberGeneratorType,
    private val logger: Logger,
    private val dataSource: NetworkDataSourceType,
    private val eventRepository: EventRepositoryType,
    private val performanceRepository: PerformanceRepositoryType,
    private val timeProvider: TimeProviderType
) {

    fun make(
        adResponse: AdResponse,
        adConfig: AdConfig,
        listener: SAInterface?,
    ): AdController {
        val defaultAdEventHandler = DefaultAdEventHandler(eventRepository, adResponse)

        val adEventHandler = if (adResponse.isVideo()) {
            VideoAdEventHandler(dataSource, eventRepository, defaultAdEventHandler)
        } else {
            defaultAdEventHandler
        }

        val performanceTrackerHandler = DefaultAdPerformanceTrackerHandler(
            performanceRepository, timeProvider, adResponse
        )

        return DefaultAdController(
            adResponse = adResponse,
            adConfig = adConfig,
            listener = listener,
            numberGenerator = numberGenerator,
            logger = logger,
            adEventHandler = adEventHandler,
            adPerformanceTrackerHandler = performanceTrackerHandler,
        )
    }
}
