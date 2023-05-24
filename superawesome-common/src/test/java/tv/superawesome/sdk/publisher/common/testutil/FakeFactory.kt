package tv.superawesome.sdk.publisher.common.testutil

import tv.superawesome.sdk.publisher.common.models.*

internal object FakeFactory {
    const val exampleUrl = "https://www.superAwesome.com"
    const val exampleHtml = "<test></test>"
    const val exampleParamString = "id=12&name=tester"
    const val exampleVastUrl = "https://www.superAwesome.com"

    fun makeFakeAd(type: CreativeFormatType, vastUrl: String = exampleVastUrl) = Ad(
        publisherId = 0,
        campaignId = 0,
        moat = 0.0,
        campaignType = 123,
        showPadlock = false,
        lineItemId = 123,
        test = false,
        creative = Creative(
            id = 10,
            format = type,
            referral = CreativeReferral(),
            details = CreativeDetail(
                url = exampleUrl,
                video = "",
                placementFormat = "",
                width = 1,
                height = 1,
                duration = 1,
                image = exampleUrl,
                vast = vastUrl
            )
        )
    )

    fun makeVastAd(
        url: String? = "www.here.com",
        redDirect: String? = null,
        type: VastType = VastType.Invalid
    ) = VastAd(
        url,
        type = type,
        errorEvents = emptyList(),
        impressionEvents = emptyList(),
        redirect = redDirect,
        media = listOf(),
        clickThroughUrl = "",
        creativeViewEvents = listOf(),
        startEvents = listOf(),
        completeEvents = listOf(),
        firstQuartileEvents = listOf(),
        midPointEvents = listOf(),
        thirdQuartileEvents = listOf(),
        clickTrackingEvents = listOf(),
    )
}
