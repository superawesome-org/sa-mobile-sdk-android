package tv.superawesome.sdk.publisher.components

import io.mockk.mockk
import org.junit.Test
import tv.superawesome.sdk.publisher.base.BaseTest
import tv.superawesome.sdk.publisher.models.AdResponse
import kotlin.test.assertEquals

internal class AdStoreTest : BaseTest() {
    @Test
    fun test_nonExist_returnNull() {
        // Given
        val store = AdStore()
        val adResponse = AdResponse(placementId = 1, ad = mockk())

        // When
        store.put(adResponse)

        // Then
        assertEquals(store.consume(2), null)
    }

    @Test
    fun test_exist_returnItem() {
        // Given
        val store = AdStore()
        val adResponse = AdResponse(placementId = 1, ad = mockk())

        // When
        store.put(adResponse)

        // Then
        assertEquals(store.consume(1), adResponse)

        // And second time returns null
        assertEquals(store.consume(1), null)
    }
}