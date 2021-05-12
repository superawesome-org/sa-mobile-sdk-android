package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.EventData
import tv.superawesome.sdk.publisher.common.models.EventType
import tv.superawesome.sdk.publisher.common.network.DataResult

interface EventRepositoryType {
    suspend fun impression(adResponse: AdResponse): DataResult<Void>
    suspend fun click(adResponse: AdResponse): DataResult<Void>
    suspend fun videoClick(adResponse: AdResponse): DataResult<Void>
    suspend fun parentalGateOpen(adResponse: AdResponse): DataResult<Void>
    suspend fun parentalGateClose(adResponse: AdResponse): DataResult<Void>
    suspend fun parentalGateSuccess(adResponse: AdResponse): DataResult<Void>
    suspend fun parentalGateFail(adResponse: AdResponse): DataResult<Void>
    suspend fun viewableImpression(adResponse: AdResponse): DataResult<Void>
    suspend fun oneSecondDwellTime(adResponse: AdResponse): DataResult<Void>
}

class EventRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType
) : EventRepositoryType {
    override suspend fun impression(adResponse: AdResponse): DataResult<Void> =
        withContext(Dispatchers.IO) {
            dataSource.impression(adQueryMaker.makeImpressionQuery(adResponse))
        }

    override suspend fun click(adResponse: AdResponse): DataResult<Void> =
        withContext(Dispatchers.IO) {
            dataSource.click(adQueryMaker.makeClickQuery(adResponse))
        }

    override suspend fun videoClick(adResponse: AdResponse): DataResult<Void> =
        withContext(Dispatchers.IO) {
            dataSource.videoClick(adQueryMaker.makeVideoClickQuery(adResponse))
        }

    private suspend fun customEvent(type: EventType, adResponse: AdResponse): DataResult<Void> =
        withContext(Dispatchers.IO) {
            val data = EventData(
                adResponse.placementId,
                adResponse.ad.lineItemId,
                adResponse.ad.creative.id,
                type
            )
            dataSource.event(adQueryMaker.makeEventQuery(adResponse, data))
        }

    override suspend fun parentalGateOpen(adResponse: AdResponse): DataResult<Void> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun parentalGateClose(adResponse: AdResponse): DataResult<Void> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun parentalGateSuccess(adResponse: AdResponse): DataResult<Void> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun parentalGateFail(adResponse: AdResponse): DataResult<Void> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun viewableImpression(adResponse: AdResponse): DataResult<Void> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun oneSecondDwellTime(adResponse: AdResponse): DataResult<Void> =
        customEvent(EventType.DwellTime, adResponse)
}
