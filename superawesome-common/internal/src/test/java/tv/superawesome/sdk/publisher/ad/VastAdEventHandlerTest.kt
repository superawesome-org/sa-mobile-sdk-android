package tv.superawesome.sdk.publisher.ad

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.EventData
import tv.superawesome.sdk.publisher.models.EventType
import tv.superawesome.sdk.publisher.models.VastAd
import tv.superawesome.sdk.publisher.models.VastType
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.repositories.EventRepositoryType
import tv.superawesome.sdk.publisher.repositories.VastEventRepositoryType
import tv.superawesome.sdk.publisher.testutil.decodeDataParams
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class VastAdEventHandlerTest : MockServerTest(), KoinTest {

    private val eventRepository by inject<EventRepositoryType>()
    private val dataSource by inject<NetworkDataSourceType>()
    private val adResponse = AdResponse(
        placementId = 12345,
        ad = mockk(relaxed = true),
        vast = mockk {
            every { clickTrackingEvents } returns listOf(mockServer.url("vastClick").toString())
        }
    )

    private val sut by lazy {
        VastAdEventHandler(
            dataSource,
            eventRepository,
            DefaultAdEventHandler(eventRepository, adResponse)
        )
    }

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
    fun `videoClick and vast events are sent on videoClick()`() = runTest {
        // arrange
        mockServer.enqueue(MockResponse().setResponseCode(200))
        mockServer.enqueue(MockResponse().setResponseCode(200))

        // act
        sut.videoClick()

        // assert
        val endpoint = mockServer.takeRequest().requestUrl?.pathSegments
        assertEquals(listOf("video", "click"), endpoint)

        val endpointVast = mockServer.takeRequest().requestUrl?.pathSegments
        assertEquals(listOf("vastClick"), endpointVast)
    }
}
