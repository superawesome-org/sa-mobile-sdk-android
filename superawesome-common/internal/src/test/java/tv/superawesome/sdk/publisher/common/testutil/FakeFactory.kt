package tv.superawesome.sdk.publisher.common.testutil

import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.Creative
import tv.superawesome.sdk.publisher.common.models.CreativeDetail
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.CreativeReferral
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.models.VastType


internal object FakeFactory {
    const val exampleUrl = "https://www.superAwesome.com"
    const val exampleHtml = "<test></test>"
    const val exampleParamString = "id=12&name=tester"
    const val exampleVastUrl = "https://www.superAwesome.com"

    fun makeFakeAd(type: CreativeFormatType, vastUrl: String = exampleVastUrl) = Ad(
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
        ),
        random = ""
    )

    fun makeVastAd(
        url: String? = "www.here.com",
        redirect: String? = null,
        type: VastType = VastType.Invalid
    ) = VastAd(
        url,
        type = type,
        errorEvents = listOf("$url/error"),
        impressionEvents = listOf("$url/impression"),
        redirect = redirect,
        media = emptyList(),
        clickThroughUrl = "$url/clickThrough",
        creativeViewEvents = listOf("$url/creativeView"),
        startEvents = listOf("$url/start"),
        completeEvents = listOf("$url/complete1", "$url/complete2"),
        firstQuartileEvents = listOf("$url/firstQuartile"),
        midPointEvents = listOf("$url/midpoint"),
        thirdQuartileEvents = listOf("$url/thirdQuartile"),
        clickTrackingEvents = listOf("$url/clickTracking", "$url/clickTracking2"),
    )
}
