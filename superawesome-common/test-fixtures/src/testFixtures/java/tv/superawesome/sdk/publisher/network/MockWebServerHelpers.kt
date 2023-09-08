package tv.superawesome.sdk.publisher.network

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import tv.superawesome.sdk.publisher.testutil.ResourceReader

fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val data = ResourceReader.readResource(fileName)

    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(data)
    )
}
