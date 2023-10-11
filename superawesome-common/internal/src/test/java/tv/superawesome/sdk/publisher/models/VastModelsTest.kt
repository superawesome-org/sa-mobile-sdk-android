package tv.superawesome.sdk.publisher.models

import org.junit.Assert
import org.junit.Test
import tv.superawesome.sdk.publisher.testutil.FakeFactory

class VastModelsTest {

    @Test
    fun `Merging VastAds results in a VastAd with the expected values`() {
        // Given
        val media1 = VastMedia(
            type = "video/mp4",
            url = "www.resource.com",
            bitrate = 720,
            width = 600,
            height = 480
        )

        val media2 = VastMedia(
            type = "video/mp4",
            url = "www.resource2.com",
            bitrate = 540,
            width = 600,
            height = 480
        )

        val first = FakeFactory.makeVastAd(
            media = media1
        )
        val second = FakeFactory.makeVastAd(
            url = "www.there.com",
            type = VastType.InLine,
            media = media2
        )

        // When
        val sut = first.merge(second)

        // Then
        Assert.assertEquals(
            "www.there.com",
            sut.url
        )
        Assert.assertEquals(
            "www.there.com/clickThrough",
            sut.clickThroughUrl
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/error",
                "www.there.com/error"
            ),
            sut.errorEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/impression",
                "www.there.com/impression"
            ),
            sut.impressionEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/creativeView",
                "www.there.com/creativeView"
            ),
            sut.creativeViewEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/start",
                "www.there.com/start"
            ),
            sut.startEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/firstQuartile",
                "www.there.com/firstQuartile"
            ),
            sut.firstQuartileEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/midpoint",
                "www.there.com/midpoint"
            ),
            sut.midPointEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/thirdQuartile",
                "www.there.com/thirdQuartile"
            ),
            sut.thirdQuartileEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/complete1",
                "www.here.com/complete2",
                "www.there.com/complete1",
                "www.there.com/complete2"
            ),
            sut.completeEvents
        )
        Assert.assertEquals(
            listOf(
                "www.here.com/clickTracking",
                "www.here.com/clickTracking2",
                "www.there.com/clickTracking",
                "www.there.com/clickTracking2"
            ),
            sut.clickTrackingEvents
        )
        Assert.assertEquals(listOf(media1, media2), sut.media)
        Assert.assertNull(sut.redirect)
    }
}