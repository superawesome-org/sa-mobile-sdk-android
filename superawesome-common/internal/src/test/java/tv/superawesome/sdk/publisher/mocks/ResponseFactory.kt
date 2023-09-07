package tv.superawesome.sdk.publisher.mocks

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy
import tv.superawesome.sdk.publisher.testutil.ResourceReader

object ResponseFactory {
    fun adResponse(json: String?): MockResponse {
        val responseBody: String = ResourceReader.readResource(json)
        return MockResponse().setBody(responseBody)
    }

    fun timeoutResponse(): MockResponse = MockResponse()
        .setBody("")
        .setSocketPolicy(SocketPolicy.NO_RESPONSE)

    fun malformedResponse(): MockResponse = adResponse("mock_ad_malformed_response.json")
        .setResponseCode(200)

    fun emptyResponse(): MockResponse = adResponse("mock_ad_empty_response.json")
}