package tv.superawesome.sdk.publisher.common.mocks

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class AdsMockServer {
    private var server: MockWebServer? = null

    init {
        server = MockWebServer()
    }

    fun url(): String = server?.url("").toString()

    fun start() {
        server?.start(64000)

        server?.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return handleRequest(request)
            }
        }
    }

    fun handleRequest(request: RecordedRequest): MockResponse {
        val line = request.requestLine
        return if (line.contains("/ad/1000")) ResponseFactory.adResponse("mock_ad_cpm_banner_response.json")
        else if (line.contains("/ad/1001")) ResponseFactory.adResponse("mock_ad_cpi_banner_response.json")
        else if (line.contains("/ad/1002")) ResponseFactory.adResponse("mock_ad_cpm_video_response.json")
        else if (line.contains("/ad/1003")) ResponseFactory.emptyResponse()
        else ResponseFactory.timeoutResponse()
    }

    fun shutdown() {
        server?.shutdown()
    }
}