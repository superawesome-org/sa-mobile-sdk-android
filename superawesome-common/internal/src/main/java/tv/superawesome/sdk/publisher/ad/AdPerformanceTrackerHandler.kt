package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdResponse

interface AdPerformanceTrackerHandler {

    val adResponse: AdResponse

    fun startTimerForLoadTime()
    suspend fun trackLoadTime()
    fun startTimerForDwellTime()
    suspend fun trackDwellTime()
    fun startTimerForCloseButtonPressed()
    suspend fun trackCloseButtonPressed()
}
