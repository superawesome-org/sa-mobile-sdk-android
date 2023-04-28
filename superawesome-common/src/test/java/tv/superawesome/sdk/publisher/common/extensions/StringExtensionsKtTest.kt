package tv.superawesome.sdk.publisher.common.extensions

import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import kotlin.test.assertEquals

internal class StringExtensionsKtTest : BaseTest() {

    @Test
    fun test_toMD5() {
        // Given
        val given = "TestString"

        // When
        val result = given.toMD5()

        // Then
        assertEquals("5b56f40f8828701f97fa4511ddcd25fb", result)
    }
}