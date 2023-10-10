package tv.superawesome.sdk.publisher.network.datasources

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

open class MockServerTest {

    protected val mockServer = MockWebServer()

    @Before
    open fun setup() {
        mockServer.start()
    }

    @After
    open fun tearDown() {
        mockServer.shutdown()
    }
}
