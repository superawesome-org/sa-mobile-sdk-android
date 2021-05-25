package tv.superawesome.lib.sagdprisminorsdk.isMinor.requests;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import tv.superawesome.lib.sagdprisminorsdk.minor.network.HTTPMethod;
import tv.superawesome.lib.sagdprisminorsdk.minor.requests.GetIsMinorRequest;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class TestGetIsMinorRequest {

    private String bundleId;
    private String dateOfBirth;
    private String endpoint;
    private HTTPMethod method;

    //class to be tested
    private GetIsMinorRequest getIsMinorRequest;

    @Test
    public void test_GetIsMinor_Request_Init() {

        //given
        bundleId = "tv.superawesome.bundleId";
        dateOfBirth = "2012-02-02";
        endpoint = "v1/countries/child-age";

        method = HTTPMethod.GET;

        //when
        getIsMinorRequest = new GetIsMinorRequest(bundleId, dateOfBirth);

        //then
        assertNotNull(bundleId);
        assertNotNull(dateOfBirth);
        assertNotNull(endpoint);

        assertNotNull(getIsMinorRequest);

        assertEquals(method, getIsMinorRequest.getMethod());
        assertEquals(endpoint, getIsMinorRequest.getEndpoint());

        JSONObject body = getIsMinorRequest.getBody();
        assertNotNull(body);


        JSONObject header = getIsMinorRequest.getHeader();
        assertNotNull(header);

        JSONObject query = getIsMinorRequest.getQuery();
        assertNotNull(query);
        String testBundleId = SAJsonParser.getString(query, "bundleId");
        String testDob = SAJsonParser.getString(query, "dob");
        assertNotNull(testBundleId);
        assertNotNull(testDob);
        assertEquals(testBundleId, bundleId);
        assertEquals(testDob, dateOfBirth);

    }

    @After
    public void unSetup() throws Throwable {
        getIsMinorRequest = null;
    }

}
