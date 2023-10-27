package tv.superawesome.sdk.publisher.ad

import android.graphics.Color
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import tv.superawesome.sdk.publisher.components.AdControllerStore
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.network.enqueueResponse
import tv.superawesome.sdk.publisher.repositories.AdRepositoryType
import tv.superawesome.sdk.publisher.testutil.TestLogger
import tv.superawesome.sdk.publisher.testutil.fakeAdRequest
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class InterstitialAdManagerTest : MockServerTest(), KoinTest {

    private val adRepository by inject<AdRepositoryType>()
    private val adControllerFactory by inject<AdControllerFactory>()
    private val adControllerStore by inject<AdControllerStore>()

    private val dispatcher = StandardTestDispatcher()

    private val sut by lazy {
        InterstitialAdManager(adRepository, TestLogger(), adControllerFactory, adControllerStore)
    }

    @Before
    override fun setup() {
        super.setup()
        mockkStatic(Color::class)
        every { Color.rgb(any<Int>(), any<Int>(), any<Int>()) } returns 0
        Dispatchers.setMain(dispatcher)
        startKoin { modules(testCommonModule(mockServer)) }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        unmockkStatic(Color::class)
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `loading with placementId should store an ad controller and send adLoaded event`() = runTest {
        // arrange
        val placementId = 1234
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        var listenedPlacementId = 0
        var listenedEvent = SAEvent.adFailedToLoad
        sut.listener = SAInterface { pId, e ->
            listenedEvent = e
            listenedPlacementId = pId
        }

        // act
        sut.load(placementId, fakeAdRequest())

        // assert
        assertNotNull(adControllerStore.peek(placementId))
        assertTrue(sut.hasAdAvailable(placementId))
        assertEquals(1234, listenedPlacementId)
        assertEquals(SAEvent.adLoaded, listenedEvent)
    }

    @Test
    fun `loading with all ids should store an ad controller and send adLoaded event`() = runTest {
        // arrange
        val placementId = 1234
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        var listenedPlacementId = 0
        var listenedEvent = SAEvent.adFailedToLoad
        sut.listener = SAInterface { pId, e ->
            listenedEvent = e
            listenedPlacementId = pId
        }

        // act
        sut.load(placementId, 0, 0, fakeAdRequest())

        // assert
        assertNotNull(adControllerStore.peek(placementId))
        assertTrue(sut.hasAdAvailable(placementId))
        assertEquals(1234, listenedPlacementId)
        assertEquals(SAEvent.adLoaded, listenedEvent)
    }

    @Test
    fun `loading an invalid VAST ad should send an adFailedToLoad event`() = runTest {
        // arrange
        val placementId = 1234
        mockServer.enqueueResponse("mock_ad_response_invalid_vast.json", 200)
        var listenedPlacementId = 0
        var listenedEvent = SAEvent.adLoaded
        sut.listener = SAInterface { pId, e ->
            listenedEvent = e
            listenedPlacementId = pId
        }

        // act
        sut.load(placementId, fakeAdRequest())

        // assert
        assertEquals(1234, listenedPlacementId)
        assertEquals(SAEvent.adFailedToLoad, listenedEvent)
    }

    @Test
    fun `server issue when loading should send an adFailedToLoad event`() = runTest {
        // arrange
        val placementId = 1234
        mockServer.enqueue(MockResponse().setResponseCode(500))
        var listenedPlacementId = 0
        var listenedEvent = SAEvent.adLoaded
        sut.listener = SAInterface { pId, e ->
            listenedEvent = e
            listenedPlacementId = pId
        }

        // act
        sut.load(placementId, fakeAdRequest())

        // assert
        assertEquals(1234, listenedPlacementId)
        assertEquals(SAEvent.adFailedToLoad, listenedEvent)
    }

    @Test
    fun `removing a loaded controller should clear it from the store`() = runTest {
        // arrange
        val placementId = 1234
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        sut.load(placementId, 0, 0, fakeAdRequest())

        // act
        sut.removeController(placementId)

        // assert
        assertNull(adControllerStore.peek(placementId))
        assertFalse(sut.hasAdAvailable(placementId))
    }

    @Test
    fun `clearing cache removes all loaded ads`() = runTest {
        // arrange
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        mockServer.enqueueResponse("mock_ad_response_banner.json", 200)
        sut.load(1, 0, 0, fakeAdRequest())
        sut.load(2, 0, 0, fakeAdRequest())

        // act
        sut.clearCache()

        // assert
        assertFalse(sut.hasAdAvailable(1))
        assertFalse(sut.hasAdAvailable(2))
    }
}
