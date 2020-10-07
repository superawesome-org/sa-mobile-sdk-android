package tv.superawesome.sdk.publisher.common.components

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.models.VastEvent
import tv.superawesome.sdk.publisher.common.models.VastType
import tv.superawesome.sdk.publisher.common.testutil.ResourceReader
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class VastParserTest : BaseTest() {

    val xmlParse = XmlParser()

    @MockK
    lateinit var connectionProvider: ConnectionProvider

    @InjectMockKs
    lateinit var vastParser: VastParser

    @Test
    fun test_parse_response1() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_1.0.xml")

        val expectedError = "https://ads.superawesome.tv/v2/video/error?placement=30479&amp;creative=-1&amp;line_item=-1&amp;sdkVersion=unknown&amp;rnd=3232269&amp;device=web&amp;country=GB&amp;code=[ERRORCODE]"
        val expectedImpression = "https://ads.superawesome.tv/v2/video/impression?placement=30479&amp;creative=-1&amp;line_item=-1&amp;sdkVersion=unknown&amp;rnd=4538730&amp;device=web&amp;country=GB"
        val expectedClick = "https://ads.superawesome.tv/v2/video/click?placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=1809240&device=web&country=GB"

        // When
        val vast = vastParser.parse(file)

        var error: VastEvent? = null
        var impression: VastEvent? = null
        var click: VastEvent? = null
//        for (evt in vast.events) {
//            if (evt.event == "vast_error") error = evt
//            if (evt.event == "vast_impression") impression = evt
//            if (evt.event == "vast_click_through") click = evt
//        }

        // Then
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video.mp4", vast.url)
        assertEquals(null, vast.redirect)
        assertEquals(VastType.InLine, vast.type)
        assertEquals(1, vast.media.size)
//        assertEquals(15, vast.events.size)
        assertNotNull(error)
        assertEquals(expectedError, error.url)
        assertNotNull(impression)
        assertEquals(expectedImpression, impression.url)
        assertNotNull(click)
        assertEquals(expectedClick, click.url)
    }
}