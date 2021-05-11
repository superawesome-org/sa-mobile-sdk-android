package tv.superawesome.sdk.publisher.common.components

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.ConnectionType
import tv.superawesome.sdk.publisher.common.models.VastType
import tv.superawesome.sdk.publisher.common.testutil.ResourceReader
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VastParserTest {

    val xmlParse = XmlParser()

    @MockK
    lateinit var connectionProvider: ConnectionProvider

    @InjectMockKs
    lateinit var vastParser: VastParser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { connectionProvider.findConnectionType() } returns ConnectionType.Cellular2g
    }

    @Test
    fun test_parse_response() {
        // arrange
        val file = ResourceReader.readResource("mock_vast_response.xml")

        // act
        val vast = vastParser.parse(file)

        // assert
        assertEquals("http://localhost:64000/resource/videoresource.mp4", vast?.url)
        assertNull(vast?.redirect)
        assertEquals(VastType.InLine, vast?.type)
        assertEquals(1, vast?.media?.size)
        assertEquals(expectedClickThroughUrl, vast?.clickThroughUrl)
        assertTrue(vast?.errorEvents?.contains(expectedError) ?: false)
        assertTrue(vast?.impressionEvents?.contains(expectedImpression) ?: false)
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=creativeView&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=4240693&device=web&country=GB"),
            vast?.creativeViewEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=start&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=3286915&device=web&country=GB"),
            vast?.startEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=firstQuartile&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=6712493&device=web&country=GB"),
            vast?.firstQuartileEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=midpoint&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=6657530&device=web&country=GB"),
            vast?.midPointEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=thirdQuartile&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=5158651&device=web&country=GB"),
            vast?.thirdQuartileEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=complete&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=2312316&device=web&country=GB"),
            vast?.completeEvents
        )
        assertEquals(emptyList(), vast?.clickTrackingEvents)
    }

    @Test
    fun test_parse_response1() {
        // arrange
        val file = ResourceReader.readResource("mock_vast_response_1.0.xml")

        // act
        val vast = vastParser.parse(file)

        // assert
        assertEquals("https://ads.superawesome.tv/v2/demo_images/video.mp4", vast?.url)
        assertNull(vast?.redirect)
        assertEquals(VastType.InLine, vast?.type)
        assertEquals(1, vast?.media?.size)
        assertEquals(expectedClickThroughUrl, vast?.clickThroughUrl)
        assertTrue(vast?.errorEvents?.contains(expectedError) ?: false)
        assertTrue(vast?.impressionEvents?.contains(expectedImpression) ?: false)
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=creativeView&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=4240693&device=web&country=GB"),
            vast?.creativeViewEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=start&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=3286915&device=web&country=GB"),
            vast?.startEvents
        )
        assertEquals(
            listOf(
                "https://ads.superawesome.tv/v2/video/tracking?event=firstQuartile&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=6712493&device=web&country=GB"
            ),
            vast?.firstQuartileEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=midpoint&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=6657530&device=web&country=GB"),
            vast?.midPointEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=thirdQuartile&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=5158651&device=web&country=GB"),
            vast?.thirdQuartileEvents
        )
        assertEquals(
            listOf("https://ads.superawesome.tv/v2/video/tracking?event=complete&placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=2312316&device=web&country=GB"),
            vast?.completeEvents
        )
        assertEquals(emptyList(), vast?.clickTrackingEvents)
    }

    @Test
    fun test_parse_response2() {
        // arrange
        val file = ResourceReader.readResource("mock_vast_response_2.0.xml")

        // act
        val vast = vastParser.parse(file)

        // assert
        assertNull(vast?.url)
        assertEquals("http://localhost:64000/vast/vast2.1.xml", vast?.redirect)
        assertEquals(VastType.Wrapper, vast?.type)
        assertEquals(0, vast?.media?.size)
        assertEquals(null, vast?.clickThroughUrl)
        assertEquals(
            listOf("https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=videoplayfailed[ERRORCODE]"),
            vast?.errorEvents
        )
        assertEquals(
            listOf("https://securepubads.g.doubleclick.net/pcs/view?xai=AKAOjsudXNPU1mlGLHKCsMsWXvJMA-P1raTNHoNCCGGKsEIoO0K0_vK6yG4c9ljyzhu5FQMGdyEUicJLBQKCRyx_sEnoz5wFViWgNfaRwD6nyiCE8k9n0EqN-uXCYnkf8si43JdcyPU-SIy1yK4SJhlaq6gJNJxCmltUHLdLxlF1oURUOpiCjNjjL_fXgGzZKZTM7UDQXz41DRY79VWad5B28diJyvJZocrzSgF8P4saGHdOdEL6eHUA6mKF3vbh2FrKXZO2aatPeCBx&amp;sig=Cg0ArKJSzPG8w4_h1rfnEAE&amp;adurl="),
            vast?.impressionEvents
        )
        assertEquals(
            listOf("https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=vast_creativeview&amp;ad_mt=[AD_MT]"),
            vast?.creativeViewEvents
        )
        assertEquals(
            listOf(
                "https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=part2viewed&amp;ad_mt=[AD_MT]",
                "https://video-ad-stats.googlesyndication.com/video/client_events?event=2&amp;web_property=ca-pub-3279133228669082&amp;cpn=[CPN]&amp;break_type=[BREAK_TYPE]&amp;slot_pos=[SLOT_POS]&amp;ad_id=[AD_ID]&amp;ad_sys=[AD_SYS]&amp;ad_len=[AD_LEN]&amp;p_w=[P_W]&amp;p_h=[P_H]&amp;mt=[MT]&amp;rwt=[RWT]&amp;wt=[WT]&amp;sdkv=[SDKV]&amp;vol=[VOL]&amp;content_v=[CONTENT_V]&amp;conn=[CONN]&amp;format=[FORMAT_NAMESPACE]_[FORMAT_TYPE]_[FORMAT_SUBTYPE]"
            ),
            vast?.startEvents
        )
        assertEquals(
            listOf("https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=videoplaytime25&amp;ad_mt=[AD_MT]"),
            vast?.firstQuartileEvents
        )
        assertEquals(
            listOf("https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=videoplaytime50&amp;ad_mt=[AD_MT]"),
            vast?.midPointEvents
        )
        assertEquals(
            listOf("https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=videoplaytime75&amp;ad_mt=[AD_MT]"),
            vast?.thirdQuartileEvents
        )
        assertEquals(
            listOf(
                "https://pubads.g.doubleclick.net/pagead/conversion/?ai=BLY-QpZJ4WL-oDcOLbcvpodAOoK2Q6wYAAAAQASCo3bsmOABYiKrYxtcBYLu-roPQCrIBFWRldmVsb3BlcnMuZ29vZ2xlLmNvbboBCjcyOHg5MF94bWzIAQXaAUhodHRwczovL2RldmVsb3BlcnMuZ29vZ2xlLmNvbS9pbnRlcmFjdGl2ZS1tZWRpYS1hZHMvZG9jcy9zZGtzL2h0bWw1L3RhZ3PAAgLgAgDqAiUvMTI0MzE5MDk2L2V4dGVybmFsL3NpbmdsZV9hZF9zYW1wbGVz-AL30R6AAwGQA9AFmAPwAagDAeAEAdIFBhCIrvTSApAGAaAGJNgHAOAHCg&amp;sigh=ClmLNunom9E&amp;label=videoplaytime100&amp;ad_mt=[AD_MT]",
                "https://video-ad-stats.googlesyndication.com/video/client_events?event=3&amp;web_property=ca-pub-3279133228669082&amp;cpn=[CPN]&amp;break_type=[BREAK_TYPE]&amp;slot_pos=[SLOT_POS]&amp;ad_id=[AD_ID]&amp;ad_sys=[AD_SYS]&amp;ad_len=[AD_LEN]&amp;p_w=[P_W]&amp;p_h=[P_H]&amp;mt=[MT]&amp;rwt=[RWT]&amp;wt=[WT]&amp;sdkv=[SDKV]&amp;vol=[VOL]&amp;content_v=[CONTENT_V]&amp;conn=[CONN]&amp;format=[FORMAT_NAMESPACE]_[FORMAT_TYPE]_[FORMAT_SUBTYPE]"
            ),
            vast?.completeEvents
        )
        assertEquals(
            listOf(
                "https://video-ad-stats.googlesyndication.com/video/client_events?event=6&amp;web_property=ca-pub-3279133228669082&amp;cpn=[CPN]&amp;break_type=[BREAK_TYPE]&amp;slot_pos=[SLOT_POS]&amp;ad_id=[AD_ID]&amp;ad_sys=[AD_SYS]&amp;ad_len=[AD_LEN]&amp;p_w=[P_W]&amp;p_h=[P_H]&amp;mt=[MT]&amp;rwt=[RWT]&amp;wt=[WT]&amp;sdkv=[SDKV]&amp;vol=[VOL]&amp;content_v=[CONTENT_V]&amp;conn=[CONN]&amp;format=[FORMAT_NAMESPACE]_[FORMAT_TYPE]_[FORMAT_SUBTYPE]",
                "https://pubads.g.doubleclick.net/pcs/click?xai=AKAOjsuyjdNJZ1zHVE5WfaJrEvrP7eK0VqSdNyGBRoMjMXd90VYE3xZVr3l5Kn0h166VefqEYqeNX_z_zObIjytcV-YGYRDvmnzU93x3Kplly4YHIdlHtXRrAE3AbaZAjN9HEjoTs4g6GZM7lc4KX_5OdCRwaEq-DuVxs0QZNkyJ5b8nCA3nkya8WzKLmAf_4sjx3e3aAanzjuaYc1__5LMi7hXLuYk_Bubh7HNPofn4y8PKVmnaOZGfaycMkFIr4pTd1DdQJ6Ma&amp;sig=Cg0ArKJSzOdaV5VR9GxbEAE&amp;urlfix=1"
            ),
            vast?.clickTrackingEvents
        )
    }

    private val expectedError =
        "https://ads.superawesome.tv/v2/video/error?placement=30479&amp;creative=-1&amp;line_item=-1&amp;sdkVersion=unknown&amp;rnd=3232269&amp;device=web&amp;country=GB&amp;code=[ERRORCODE]"
    private val expectedImpression =
        "https://ads.superawesome.tv/v2/video/impression?placement=30479&amp;creative=-1&amp;line_item=-1&amp;sdkVersion=unknown&amp;rnd=4538730&amp;device=web&amp;country=GB"
    private val expectedClickThroughUrl =
        "https://ads.superawesome.tv/v2/video/click?placement=30479&creative=-1&line_item=-1&sdkVersion=unknown&rnd=1809240&device=web&country=GB"
}
