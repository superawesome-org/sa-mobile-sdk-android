package tv.superawesome.sdk.publisher.common.components

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.models.VastType
import tv.superawesome.sdk.publisher.common.testutil.ResourceReader
import kotlin.test.assertEquals


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

        // Then
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video.mp4", vast.url)
        assertEquals(null, vast.redirect)
        assertEquals(VastType.InLine, vast.type)
        assertEquals(1, vast.media.size)
    }
}