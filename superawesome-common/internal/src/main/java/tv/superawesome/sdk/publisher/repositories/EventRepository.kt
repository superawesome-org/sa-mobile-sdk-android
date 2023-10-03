package tv.superawesome.sdk.publisher.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.EventData
import tv.superawesome.sdk.publisher.models.EventType
import tv.superawesome.sdk.publisher.network.datasources.AwesomeAdsApiDataSourceType

interface EventRepositoryType {
    suspend fun impression(adResponse: AdResponse): Result<Unit>
    suspend fun click(adResponse: AdResponse): Result<Unit>
    suspend fun videoClick(adResponse: AdResponse): Result<Unit>
    suspend fun parentalGateOpen(adResponse: AdResponse): Result<Unit>
    suspend fun parentalGateClose(adResponse: AdResponse): Result<Unit>
    suspend fun parentalGateSuccess(adResponse: AdResponse): Result<Unit>
    suspend fun parentalGateFail(adResponse: AdResponse): Result<Unit>
    suspend fun viewableImpression(adResponse: AdResponse): Result<Unit>
    suspend fun dwellTime(adResponse: AdResponse): Result<Unit>
}

class EventRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType
) : EventRepositoryType {
    override suspend fun impression(adResponse: AdResponse): Result<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.impression(adQueryMaker.makeImpressionQuery(adResponse))
        }

    override suspend fun click(adResponse: AdResponse): Result<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.click(adQueryMaker.makeClickQuery(adResponse))
        }

    override suspend fun videoClick(adResponse: AdResponse): Result<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.videoClick(adQueryMaker.makeVideoClickQuery(adResponse))
        }

    private suspend fun customEvent(
        type: EventType,
        adResponse: AdResponse,
        value: Int? = null,
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            val data = EventData(
                adResponse.placementId,
                adResponse.ad.lineItemId,
                adResponse.ad.creative.id,
                type,
                value = value,
            )
            dataSource.event(adQueryMaker.makeEventQuery(adResponse, data))
        }

    override suspend fun parentalGateOpen(adResponse: AdResponse): Result<Unit> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun parentalGateClose(adResponse: AdResponse): Result<Unit> =
        customEvent(EventType.ParentalGateClose, adResponse)

    override suspend fun parentalGateSuccess(adResponse: AdResponse): Result<Unit> =
        customEvent(EventType.ParentalGateSuccess, adResponse)

    override suspend fun parentalGateFail(adResponse: AdResponse): Result<Unit> =
        customEvent(EventType.ParentalGateFail, adResponse)

    override suspend fun viewableImpression(adResponse: AdResponse): Result<Unit> =
        customEvent(EventType.ViewableImpression, adResponse)

    override suspend fun dwellTime(adResponse: AdResponse): Result<Unit> =
        customEvent(EventType.DwellTime, adResponse, value = 5)
}
