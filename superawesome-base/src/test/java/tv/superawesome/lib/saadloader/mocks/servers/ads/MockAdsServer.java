package tv.superawesome.lib.saadloader.mocks.servers.ads;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import tv.superawesome.lib.saadloader.mocks.servers.MockAbstractWebServer;
import tv.superawesome.lib.saadloader.mocks.servers.ResponseFactory;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class MockAdsServer extends MockAbstractWebServer {

    @Override
    public MockResponse handleRequest(RecordedRequest request) {
        String line = request.getRequestLine();
        if (line.contains("/ad/1000"))
            return ResponseFactory.adResponse("mock_ad_cpm_banner_response.json");
        else if (line.contains("/ad/1001"))
            return ResponseFactory.adResponse("mock_ad_cpi_banner_response.json");
        else if (line.contains("/ad/1002"))
            return ResponseFactory.adResponse("mock_ad_cpm_video_response.json");
        else if (line.contains("/vast/vast.xml"))
            return ResponseFactory.vastResponse("mock_vast_response.xml");
        else if (line.contains("/resource/videoresource.mp4"))
            return ResponseFactory.fileResponse("videoresource.mp4");
        else if (line.contains("/ad/1003"))
            return ResponseFactory.emptyResponse();
        else if (line.contains("/ad/1004"))
            return ResponseFactory.malformedResponse();
        else if (line.contains("/ad/1005"))
            return ResponseFactory.adResponse("mock_ad_cpm_video_bad_response.json");
        else if (line.contains("/ad/1006"))
            return ResponseFactory.adResponse("mock_ad_cpm_video_unreachable_video_resource.json");
        else if (line.contains("/vast/vast_unreachable_video"))
            return ResponseFactory.adResponse("mock_vast_unreachable_video_response.xml");
        else if (line.contains("/featureFlags/featureFlags.json"))
            if (isErrorEnabled) {
                return ResponseFactory.fileResponse("feature_flags_error.json");
            } else {
                return ResponseFactory.fileResponse("feature_flags_success.json");
            }
        else
            return ResponseFactory.timeoutResponse();
    }
}
