package tv.superawesome.sdk.publisher.common.network.datasources

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

open class MockServerTest {

    protected val mockServer = MockWebServer()

    @Before
    fun setup() {
        mockServer.start()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}
