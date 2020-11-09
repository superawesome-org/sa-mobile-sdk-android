package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.components.DispatcherProviderType
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.AdResponse

interface VastEventRepositoryType {
    suspend fun clickThrough()
    suspend fun error()
    suspend fun impression()
    suspend fun creativeView()
    suspend fun start()
    suspend fun firstQuartile()
    suspend fun midPoint()
    suspend fun thirdQuartile()
    suspend fun complete()
    suspend fun clickTracking()
}

class VastEventRepository(
        private val adResponse: AdResponse,
        private val dataSource: NetworkDataSourceType,
        private val dispatcherProvider: DispatcherProviderType,
) : VastEventRepositoryType {

    private suspend fun triggerEvent(url: String) {
        withContext(dispatcherProvider.io) {
            dataSource.getData(url)
        }
    }

    private suspend fun customEvent(urls: List<String>?) {
        urls?.forEach { triggerEvent(it) }
    }

    override suspend fun clickThrough() {
        val url: String = adResponse.vast?.clickThroughUrl ?: return
        triggerEvent(url)
    }

    override suspend fun error() {
        customEvent(adResponse.vast?.errorEvents)
    }

    override suspend fun impression() {
        customEvent(adResponse.vast?.impressionEvents)
    }

    override suspend fun creativeView() {
        customEvent(adResponse.vast?.creativeViewEvents)
    }

    override suspend fun start() {
        customEvent(adResponse.vast?.startEvents)
    }

    override suspend fun firstQuartile() {
        customEvent(adResponse.vast?.firstQuartileEvents)
    }

    override suspend fun midPoint() {
        customEvent(adResponse.vast?.midPointEvents)
    }

    override suspend fun thirdQuartile() {
        customEvent(adResponse.vast?.thirdQuartileEvents)
    }

    override suspend fun complete() {
        customEvent(adResponse.vast?.completeEvents)
    }

    override suspend fun clickTracking() {
        customEvent(adResponse.vast?.clickTrackingEvents)
    }
}