package tv.superawesome.sdk.publisher.components

import org.junit.Test
import tv.superawesome.sdk.publisher.models.ConnectionType
import tv.superawesome.sdk.publisher.models.VastAd
import tv.superawesome.sdk.publisher.models.VastType
import tv.superawesome.sdk.publisher.testutil.FakeConnectionProvider
import tv.superawesome.sdk.publisher.testutil.ResourceReader
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.fail

class VastParserTest {

    private val xmlParser = XmlParser()

    private val connectionProvider = FakeConnectionProvider()

    private val sut = VastParser(xmlParser, connectionProvider)
    @Test
    fun `vast parser can parse a VAST xml`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_1.0.xml")

        val expectedError =
            "https://ads.superawesome.tv/v2/video/error?placement=30479&amp;creative=-1&amp;line_item=-1&amp;sdkVersion=unknown&amp;rnd=3232269&amp;device=web&amp;country=GB&amp;code=[ERRORCODE]"
        val expectedImpression =
            "https://ads.superawesome.tv/v2/video/impression?placement=30479&amp;creative=-1&amp;line_item=-1&amp;sdkVersion=unknown&amp;rnd=4538730&amp;device=web&amp;country=GB"
        val expectedClick =
            "https://ads.superawesome.tv/v2/video/click?placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=1809240&device=web&country=GB"

        // When
        val vast = sut.parse(file) ?: fail("Parser returned null")

        // Then
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video.mp4", vast.url)
        assertEquals(null, vast.redirect)
        assertEquals(VastType.InLine, vast.type)
        assertEquals(1, vast.media.size)
        assertEquals(8, vast.events.size)
        assertNotEquals(emptyList(), vast.errorEvents)
        assertEquals(expectedError, vast.errorEvents[0])
        assertNotEquals(emptyList(), vast.impressionEvents)
        assertEquals(expectedImpression, vast.impressionEvents[0])
        assertEquals(expectedClick, vast.clickThroughUrl)
    }

    @Test
    fun `parser gets lowest bitrate mp4 for poorest connection quality`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_2.1.xml")
        FakeConnectionProvider.connectionType = ConnectionType.Cellular2g

        // When
        val vast = sut.parse(file)

        // Then
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video_3.mp4", vast?.url)
    }

    @Test
    fun `parser gets medium bitrate mp4 for medium connection quality`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_2.1.xml")
        FakeConnectionProvider.connectionType = ConnectionType.Cellular3g

        // When
        val vast = sut.parse(file)

        // Then
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video_7.mp4", vast?.url)
    }

    @Test
    fun `parser gets highest bitrate mp4 for best connection quality`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_2.1.xml")
        FakeConnectionProvider.connectionType = ConnectionType.Wifi

        // When
        val vast = sut.parse(file)

        // Then
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video_1.mp4", vast?.url)
    }

    @Test
    fun `parser returns type wrapper for wrapper ad`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_5.2.xml")

        // When
        val vast = sut.parse(file)

        // Then
        assertEquals(VastType.Wrapper, vast?.type)
    }

    @Test
    fun `parser returns type invalid for unknown ad`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_invalid_type.xml")

        // When
        val vast = sut.parse(file)

        // Then
        assertEquals(VastType.Invalid, vast?.type)
    }

    @Test
    fun `parser returns null for invalid vast xml`() {
        // Given
        val file = ResourceReader.readResource("mock_vast_response_4.0.xml")

        // When
        val vast = sut.parse(file)

        // Then
        assertNull(vast)
    }

    val VastAd.events: List<String>
        get() = completeEvents + errorEvents + clickTrackingEvents + impressionEvents +
                creativeViewEvents + firstQuartileEvents + midPointEvents + startEvents +
                thirdQuartileEvents
}