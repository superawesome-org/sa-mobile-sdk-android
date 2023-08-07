package tv.superawesome.sdk.publisher.common.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.VastAd

@Suppress("ComplexInterface")
internal interface VastEventRepositoryType {
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

@Suppress("TooManyFunctions")
internal class VastEventRepository(
    private val vastAd: VastAd,
    private val dataSource: NetworkDataSourceType
) : VastEventRepositoryType {

    private suspend fun triggerEvent(url: String) {
        withContext(Dispatchers.IO) {
            dataSource.getData(url)
        }
    }

    private suspend fun customEvent(urls: List<String>) {
        urls.forEach { triggerEvent(it) }
    }

    override suspend fun clickThrough() {
        val url: String = vastAd.clickThroughUrl ?: return
        triggerEvent(url)
    }

    override suspend fun error() {
        customEvent(vastAd.errorEvents)
    }

    override suspend fun impression() {
        customEvent(vastAd.impressionEvents)
    }

    override suspend fun creativeView() {
        customEvent(vastAd.creativeViewEvents)
    }

    override suspend fun start() {
        customEvent(vastAd.startEvents)
    }

    override suspend fun firstQuartile() {
        customEvent(vastAd.firstQuartileEvents)
    }

    override suspend fun midPoint() {
        customEvent(vastAd.midPointEvents)
    }

    override suspend fun thirdQuartile() {
        customEvent(vastAd.thirdQuartileEvents)
    }

    override suspend fun complete() {
        customEvent(vastAd.completeEvents)
    }

    override suspend fun clickTracking() {
        customEvent(vastAd.clickTrackingEvents)
    }
}
