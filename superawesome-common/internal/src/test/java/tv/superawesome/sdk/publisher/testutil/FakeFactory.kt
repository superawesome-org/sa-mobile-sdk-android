package tv.superawesome.sdk.publisher.testutil

import io.mockk.mockk
import tv.superawesome.sdk.publisher.ad.DefaultAdController
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.Creative
import tv.superawesome.sdk.publisher.models.CreativeDetail
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.models.CreativeReferral
import tv.superawesome.sdk.publisher.models.VastAd
import tv.superawesome.sdk.publisher.models.VastMedia
import tv.superawesome.sdk.publisher.models.VastType


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
        type: VastType = VastType.Invalid,
        media: VastMedia? = null
    ) = VastAd(
        url,
        type = type,
        errorEvents = listOf("$url/error"),
        impressionEvents = listOf("$url/impression"),
        redirect = redirect,
        media = media?.let { listOf(it) } ?: listOf(),
        clickThroughUrl = "$url/clickThrough",
        creativeViewEvents = listOf("$url/creativeView"),
        startEvents = listOf("$url/start"),
        completeEvents = listOf("$url/complete1", "$url/complete2"),
        firstQuartileEvents = listOf("$url/firstQuartile"),
        midPointEvents = listOf("$url/midpoint"),
        thirdQuartileEvents = listOf("$url/thirdQuartile"),
        clickTrackingEvents = listOf("$url/clickTracking", "$url/clickTracking2"),
    )

    fun makeFakeAdResponse() = AdResponse(
        placementId = 1,
        ad = makeFakeAd(CreativeFormatType.Tag),
    )
}
