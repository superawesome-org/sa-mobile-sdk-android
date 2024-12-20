package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.VastEventRepository
import tv.superawesome.sdk.publisher.repositories.VastEventRepositoryType

/**
 * Event handler for VAST ads.
 */
class VastAdEventHandler(
    private val dataSource: NetworkDataSourceType,
    private val eventRepository: EventRepositoryType,
    private val defaultAdEventHandler: DefaultAdEventHandler,
) : AdEventHandler by defaultAdEventHandler {

    private val vastEventRepository: VastEventRepositoryType? by lazy {
        adResponse.vast?.let { vastAd ->
            VastEventRepository(vastAd, dataSource)
        }
    }

    override suspend fun videoClick() {
        vastEventRepository?.clickTracking()
        if (!doesAdContainVASTClickThroughUrlWithVideoClickEvent()) {
            eventRepository.videoClick(adResponse)
        }
    }

    @Suppress("FunctionMaxLength")
    private fun doesAdContainVASTClickThroughUrlWithVideoClickEvent() =
        adResponse.vast?.let { vastAd ->
            !vastAd.clickThroughUrl.isNullOrEmpty() &&
                    vastAd.clickThroughUrl.contains("/video/click")
        } ?: false
}
