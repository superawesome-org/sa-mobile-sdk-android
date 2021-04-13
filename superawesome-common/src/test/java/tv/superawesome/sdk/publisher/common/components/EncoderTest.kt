package tv.superawesome.sdk.publisher.common.components

import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.CreativeReferral
import kotlin.test.assertEquals

class EncoderTest {
    private val encoder: EncoderType = Encoder()

    @Test
    fun test() {
        // Given
        val given1 = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/x7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4"
        val given2 = ""
        val given3: String? = null
        val given4 = "Gunhan Sancar"

        // When
        val result1 = encoder.encodeUri(given1)
        val result2 = encoder.encodeUri(given2)
        val result3 = encoder.encodeUri(given3)
        val result4 = encoder.encodeUri(given4)

        // Then
        assertEquals("https%3A%2F%2Fs3-eu-west-1.amazonaws.com%2Fsb-ads-video-transcoded%2Fx7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4", result1)
        assertEquals("", result2)
        assertEquals("", result3)
        assertEquals("Gunhan+Sancar", result4)
    }

    @Test
    fun testEncodeUrlParamsFromObject() {
        // Given
        val referral = CreativeReferral(1, 33, -1, -1, 28000)

        // When
        val result = encoder.encodeUrlParamsFromObject(referral.toMap())

        // Then
        assertEquals("utm_source%3D1%26utm_campaign%3D33%26utm_term%3D-1%26utm_content%3D-1%26utm_medium%3D28000", result)
    }
}
