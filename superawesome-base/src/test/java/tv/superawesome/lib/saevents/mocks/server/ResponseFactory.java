package tv.superawesome.lib.saevents.mocks.server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.SocketPolicy;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class ResponseFactory {

    public static MockResponse successResponse() {
        return new MockResponse().setBody("{}").setResponseCode(200);
    }

    public static MockResponse timeoutResponse () {
        return new MockResponse()
                .setBody("")
                .setSocketPolicy(SocketPolicy.NO_RESPONSE);
    }
}
