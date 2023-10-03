package tv.superawesome.sdk.publisher.ad

import tv.superawesome.sdk.publisher.models.AdResponse

interface AdEventHandler {

    val adResponse: AdResponse

    suspend fun click()

    suspend fun videoClick()

    suspend fun triggerImpressionEvent()

    suspend fun triggerViewableImpression()

    suspend fun triggerDwellTime()

    suspend fun parentalGateOpen()

    suspend fun parentalGateClose()

    suspend fun parentalGateSuccess()

    suspend fun parentalGateFail()
}
