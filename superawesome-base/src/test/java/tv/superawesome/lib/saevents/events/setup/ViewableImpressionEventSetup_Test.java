package tv.superawesome.lib.saevents.events.setup;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAViewableImpressionEvent;
import tv.superawesome.lib.saevents.events.Event_Test;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class ViewableImpressionEventSetup_Test extends Event_Test {

    @Test
    public void test_ViewableImpression_Init () throws Exception {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);

        // when
        SAViewableImpressionEvent event = new SAViewableImpressionEvent(ad, super.session, super.executor, 1, 0, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("/event", event.getEndpoint());

        // then - query
        Assert.assertNotNull(event.getQuery());

        JSONObject query = event.getQuery();

        Assert.assertEquals(2, query.get("ct"));
        Assert.assertEquals(123456, query.get("rnd"));
        Assert.assertEquals("1.0.0", query.get("sdkVersion"));
        Assert.assertEquals("superawesome.tv.saadloaderdemo", query.get("bundle"));
        Assert.assertEquals("abc", query.get("adRequestId"));

        String stringData = query.getString("data");

        String result = java.net.URLDecoder.decode(stringData, "UTF-8");
        JSONObject data = new JSONObject(result);

        Assert.assertEquals(1000, data.get("placement"));
        Assert.assertEquals(2001, data.get("line_item"));
        Assert.assertEquals(5001, data.get("creative"));
        Assert.assertEquals("viewable_impression", data.get("type"));
    }
}
