package tv.superawesome.sdk.publisher.components

import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import tv.superawesome.sdk.publisher.ad.AdControllerFactory
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import kotlin.test.assertEquals

internal class DefaultAdControllerStoreTest : MockServerTest(), KoinTest {

    private val fakeAdControllerFactory by inject<AdControllerFactory>()

    @Before
    override fun setup() {
        super.setup()
        startKoin { modules(testCommonModule(mockServer)) }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        stopKoin()
    }

    @Test
    fun test_nonExist_returnNull() {
        // Given
        val store = DefaultAdControllerStore()
        val adResponse = AdResponse(placementId = 1, ad = mockk())
        val controller = fakeAdControllerFactory.make(adResponse, mockk(),null)

        // When
        store.put(controller)

        // Then
        assertEquals(store.consume(2), null)
    }

    @Test
    fun test_exist_returnItem() {
        // Given
        val store = DefaultAdControllerStore()
        val adResponse = AdResponse(placementId = 1, ad = mockk())
        val controller = fakeAdControllerFactory.make(adResponse, mockk(), null)

        // When
        store.put(controller)

        // Then
        assertEquals(store.consume(1), controller)

        // And second time returns null
        assertEquals(store.consume(1), null)
    }
}