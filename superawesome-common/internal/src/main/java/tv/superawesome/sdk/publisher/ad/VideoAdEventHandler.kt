package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.VastEventRepository
import tv.superawesome.sdk.publisher.repositories.VastEventRepositoryType

class VideoAdEventHandler(
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
        eventRepository.videoClick(adResponse)
        vastEventRepository?.clickTracking()
    }
}
