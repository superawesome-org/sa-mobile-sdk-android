package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.network.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.EventData
import tv.superawesome.sdk.publisher.common.models.EventType
import tv.superawesome.sdk.publisher.common.network.DataResult

interface EventRepositoryType {
    suspend fun impression(adResponse: AdResponse): DataResult<Unit>
    suspend fun click(adResponse: AdResponse): DataResult<Unit>
    suspend fun videoClick(adResponse: AdResponse): DataResult<Unit>
    suspend fun parentalGateOpen(adResponse: AdResponse): DataResult<Unit>
    suspend fun parentalGateClose(adResponse: AdResponse): DataResult<Unit>
    suspend fun parentalGateSuccess(adResponse: AdResponse): DataResult<Unit>
    suspend fun parentalGateFail(adResponse: AdResponse): DataResult<Unit>
    suspend fun viewableImpression(adResponse: AdResponse): DataResult<Unit>
    suspend fun oneSecondDwellTime(adResponse: AdResponse): DataResult<Unit>
}

class EventRepository(
    private val dataSource: AwesomeAdsApiDataSourceType,
    private val adQueryMaker: AdQueryMakerType
) : EventRepositoryType {
    override suspend fun impression(adResponse: AdResponse): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.impression(adQueryMaker.makeImpressionQuery(adResponse))
        }

    override suspend fun click(adResponse: AdResponse): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.click(adQueryMaker.makeClickQuery(adResponse))
        }

    override suspend fun videoClick(adResponse: AdResponse): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.videoClick(adQueryMaker.makeVideoClickQuery(adResponse))
        }

    private suspend fun customEvent(type: EventType, adResponse: AdResponse): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            val data = EventData(
                adResponse.placementId,
                adResponse.ad.lineItemId,
                adResponse.ad.creative.id,
                type
            )
            dataSource.event(adQueryMaker.makeEventQuery(adResponse, data))
        }

    override suspend fun parentalGateOpen(adResponse: AdResponse): DataResult<Unit> =
        customEvent(EventType.ParentalGateOpen, adResponse)

    override suspend fun parentalGateClose(adResponse: AdResponse): DataResult<Unit> =
        customEvent(EventType.ParentalGateClose, adResponse)

    override suspend fun parentalGateSuccess(adResponse: AdResponse): DataResult<Unit> =
        customEvent(EventType.ParentalGateSuccess, adResponse)

    override suspend fun parentalGateFail(adResponse: AdResponse): DataResult<Unit> =
        customEvent(EventType.ParentalGateFail, adResponse)

    override suspend fun viewableImpression(adResponse: AdResponse): DataResult<Unit> =
        customEvent(EventType.ViewableImpression, adResponse)

    override suspend fun oneSecondDwellTime(adResponse: AdResponse): DataResult<Unit> =
        customEvent(EventType.DwellTime, adResponse)
}
