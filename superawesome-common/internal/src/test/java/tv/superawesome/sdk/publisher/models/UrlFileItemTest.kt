package tv.superawesome.sdk.publisher.models

import org.junit.Assert.assertEquals
import org.junit.Test
import tv.superawesome.sdk.publisher.extensions.toMD5

class UrlFileItemTest {

    @Test
    fun `UrlFileItem fileName output is as expected`() {
        // Given
        val url = "http://www.superawesome.com"

        // When
        val sut = UrlFileItem(url)

        // Then
        assertEquals("${url.toMD5()}.com", sut.fileName)
    }
}