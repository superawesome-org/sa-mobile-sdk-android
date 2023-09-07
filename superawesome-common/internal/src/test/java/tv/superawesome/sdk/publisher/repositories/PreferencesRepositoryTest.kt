package tv.superawesome.sdk.publisher.repositories

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import tv.superawesome.sdk.publisher.testutil.InMemorySharedPreferences
import kotlin.test.assertEquals

class PreferencesRepositoryTest {

    private val context = mockk<Context> {
        every { getSharedPreferences(any(), any()) } returns InMemorySharedPreferences()
    }

    private val sut = PreferencesRepository(context)

    @Test
    fun `when setting user-agent, it should be set`() {
        // Given
        val userAgent = "UA"

        // When
        sut.userAgent = userAgent

        // Then
        assertEquals(userAgent, sut.userAgent)
    }

    @Test
    fun `when setting dau, it should be set`() {
        // Given
        val dau = "DAU"

        // When
        sut.dauUniquePart = dau

        // Then
        assertEquals(dau, sut.dauUniquePart)
    }
}
