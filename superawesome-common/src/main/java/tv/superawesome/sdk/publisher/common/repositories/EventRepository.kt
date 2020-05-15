package tv.superawesome.sdk.publisher.common.repositories

import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.datasources.AdDataSourceType
import tv.superawesome.sdk.publisher.common.models.EventRequest
import tv.superawesome.sdk.publisher.common.network.DataResult

interface EventRepositoryType {
    suspend fun impression(request: EventRequest): DataResult<Void>
    suspend fun click(request: EventRequest): DataResult<Void>
    suspend fun videoClick(request: EventRequest): DataResult<Void>
    suspend fun event(request: EventRequest): DataResult<Void>
}

class EventRepository(private val dataSource: AdDataSourceType,
                      private val adQueryMaker: AdQueryMakerType
) : EventRepositoryType {
    override suspend fun impression(request: EventRequest): DataResult<Void> =
            dataSource.impression(adQueryMaker.makeImpressionQuery(request))

    override suspend fun click(request: EventRequest): DataResult<Void> =
            dataSource.click(adQueryMaker.makeClickQuery(request))

    override suspend fun videoClick(request: EventRequest): DataResult<Void> =
            dataSource.videoClick(adQueryMaker.makeVideoClickQuery(request))

    override suspend fun event(request: EventRequest): DataResult<Void> =
            dataSource.event(adQueryMaker.makeEventQuery(request))
}