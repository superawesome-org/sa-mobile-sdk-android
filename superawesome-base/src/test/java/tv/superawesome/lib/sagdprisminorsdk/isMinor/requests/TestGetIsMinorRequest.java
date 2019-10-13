package tv.superawesome.sagdprisminorsdk.isMinor.requests;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.sagdprisminorsdk.minor.network.HTTPMethod;
import tv.superawesome.sagdprisminorsdk.minor.requests.GetIsMinorRequest;

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
        Assert.assertNotNull(bundleId);
        Assert.assertNotNull(dateOfBirth);
        Assert.assertNotNull(endpoint);

        Assert.assertNotNull(getIsMinorRequest);

        Assert.assertEquals(method, getIsMinorRequest.getMethod());
        Assert.assertEquals(endpoint, getIsMinorRequest.getEndpoint());

        JSONObject body = getIsMinorRequest.getBody();
        Assert.assertNotNull(body);


        JSONObject header = getIsMinorRequest.getHeader();
        Assert.assertNotNull(header);

        JSONObject query = getIsMinorRequest.getQuery();
        Assert.assertNotNull(query);
        String testBundleId = SAJsonParser.getString(query, "bundleId");
        String testDob = SAJsonParser.getString(query, "dob");
        Assert.assertNotNull(testBundleId);
        Assert.assertNotNull(testDob);
        Assert.assertEquals(testBundleId, bundleId);
        Assert.assertEquals(testDob, dateOfBirth);

    }

    @After
    public void unSetup() throws Throwable {
        getIsMinorRequest = null;
    }

}
