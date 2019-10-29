package tv.superawesome.lib.saadloader.mocks.servers;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.SocketPolicy;
import okio.Buffer;
import tv.superawesome.lib.saadloader.testutils.ResourceReader;

/**
 * Created by gabriel.coman on 03/05/2018.
 */
public class ResponseFactory {

    public static MockResponse adResponse(String json) {
        String responseBody = ResourceReader.readString(json);
        return new MockResponse().setBody(responseBody);
    }

    public static MockResponse vastResponse(String xml) {
        String responseBody = ResourceReader.readString(xml);
        return new MockResponse().setBody(responseBody);
    }

    public static MockResponse fileResponse(String file){
        Buffer responseBody;
        try {
            responseBody = ResourceReader.readBuffer(file);
            return new MockResponse().setBody(responseBody);
        } catch (Exception e) {
            return timeoutResponse();
        }
    }

    public static MockResponse malformedResponse() {
        return adResponse("mock_ad_malformed_response.json").setResponseCode(200);
    }

    public static MockResponse emptyResponse () {
        return adResponse("mock_ad_empty_response.json");
    }

    public static MockResponse timeoutResponse () {
        return new MockResponse()
                .setBody("")
                .setSocketPolicy(SocketPolicy.NO_RESPONSE);
    }
}
