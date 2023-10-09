package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType

/**
 * Default ad event handler, should work for any type of ad.
 * For VAST, use [VastAdEventHandler].
 */
class DefaultAdEventHandler(
    private val eventRepository: EventRepositoryType,
    override val adResponse: AdResponse,
) : AdEventHandler {

    override suspend fun click() {
        eventRepository.click(adResponse)
    }

    override suspend fun videoClick() {
        eventRepository.videoClick(adResponse)
    }

    override suspend fun triggerDwellTime() {
        eventRepository.dwellTime(adResponse)
    }

    override suspend fun triggerImpressionEvent() {
        eventRepository.impression(adResponse)
    }

    override suspend fun triggerViewableImpression() {
        eventRepository.viewableImpression(adResponse)
    }

    override suspend fun parentalGateOpen() {
        eventRepository.parentalGateOpen(adResponse)
    }

    override suspend fun parentalGateClose() {
        eventRepository.parentalGateClose(adResponse)
    }

    override suspend fun parentalGateSuccess() {
        eventRepository.parentalGateSuccess(adResponse)
    }

    override suspend fun parentalGateFail() {
        eventRepository.parentalGateFail(adResponse)
    }
}
