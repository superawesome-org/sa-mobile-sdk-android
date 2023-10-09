package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdResponse

/**
 * Defines a performance tracker, provides functions to track some performance metrics such as
 * ad load time, ad dwell time and close button time.
 */
interface AdPerformanceTrackerHandler {

    /**
     * The ad which will be tracked.
     */
    val adResponse: AdResponse

    /**
     * Starts the timer for tracking ad load time.
     */
    fun startTimerForLoadTime()

    /**
     * Submits the ad load timer in its current state.
     */
    suspend fun trackLoadTime()

    /**
     * Starts the timer for ad dwell time.
     */
    fun startTimerForDwellTime()

    /**
     * Submits the timer for ad dwell time in its current state.
     */
    suspend fun trackDwellTime()

    /**
     * Starts the timer for close button pressed.
     */
    fun startTimerForCloseButtonPressed()

    /**
     * Submits ths timer for close button pressed in its current state.
     */
    suspend fun trackCloseButtonPressed()
}
