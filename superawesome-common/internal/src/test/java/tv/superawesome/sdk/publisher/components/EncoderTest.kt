package tv.superawesome.sdk.publisher.components

import org.junit.Test
import kotlin.test.assertEquals

class EncoderTest {

    private val sut = Encoder()

    @Test
    fun `returns empty is the string being encoded is null`() {
        // arrange
        val input = null

        // act
        val output = sut.encodeUri(input)

        // assert
        assertEquals("", output)
    }

    @Test
    fun `returns a URL encoded string if string is not empty`() {
        // arrange
        val input = "!#test$%"

        // act
        val output = sut.encodeUri(input)

        // assert
        assertEquals("%21%23test%24%25", output)
    }

    @Test
    fun `returns empty if the charset isn't supported`() {
        // arrange
        val sut = Encoder("09aodsjas")

        // act
        val output = sut.encodeUri("123")

        // assert
        assertEquals("", output)
    }

    @Test
    fun `encode dictionary into one string`() {
        // arrange
        val input = mapOf(
            "param1" to 1,
            "param2" to "2",
            "param3" to true,
        )

        // act
        val output = sut.encodeUrlParamsFromObject(input)

        // assert
        assertEquals("param1%3D1%26param2%3D2%26param3%3Dtrue", output)
    }
}