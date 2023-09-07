package tv.superawesome.sdk.publisher.components

import org.junit.Test
import tv.superawesome.sdk.publisher.models.CreativeReferral
import kotlin.test.assertEquals

internal class EncoderTest {
    private val encoder = Encoder()

    @Test
    fun test() {
        // Given
        val given1 =
            "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/x7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4"
        val given2 = ""
        val given3: String? = null
        val given4 = "Gunhan Sancar"

        // When
        val result1 = encoder.encodeUri(given1)
        val result2 = encoder.encodeUri(given2)
        val result3 = encoder.encodeUri(given3)
        val result4 = encoder.encodeUri(given4)

        // Then
        assertEquals(
            "https%3A%2F%2Fs3-eu-west-1.amazonaws.com%2Fsb-ads-video-transcoded%2Fx7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4",
            result1
        )
        assertEquals("", result2)
        assertEquals("", result3)
        assertEquals("Gunhan+Sancar", result4)
    }

    @Test
    fun test_encodeUrlParamsFromObject() {
        val given = CreativeReferral(
            utmSource = 1,
            utmCampaign = 2,
            utmTerm = 3,
            utmContent = 4,
            utmMedium = 5
        )

        // When
        val result = encoder.encodeUrlParamsFromObject(given.toMap())

        assertEquals(
            "utm_source%3D1%26utm_campaign%3D2%26utm_term%3D3%26utm_content%3D4%26utm_medium%3D5",
            result
        )
    }
}