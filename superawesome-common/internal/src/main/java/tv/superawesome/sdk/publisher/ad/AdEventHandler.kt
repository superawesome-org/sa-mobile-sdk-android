package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdResponse

/**
 * Handles ad events, such as impressions, clicks and more.
 */
interface AdEventHandler {

    /**
     * The ad that will be tracked.
     */
    val adResponse: AdResponse

    /**
     * Sends a click event.
     */
    suspend fun click()

    /**
     * Sends a click event for videos.
     */
    suspend fun videoClick()

    /**
     * Sends an impression event.
     */
    suspend fun triggerImpressionEvent()

    /**
     * Sends a viewable impression event.
     */
    suspend fun triggerViewableImpression()

    /**
     * Sends a dwell time event.
     */
    suspend fun triggerDwellTime()

    /**
     * Sends a parental gate opened event.
     */
    suspend fun parentalGateOpen()

    /**
     * Sends a parental gate closed event.
     */
    suspend fun parentalGateClose()

    /**
     * Sends a parental gate success event.
     */
    suspend fun parentalGateSuccess()

    /**
     * Sends a parental gate failure event.
     */
    suspend fun parentalGateFail()
}
