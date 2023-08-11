package tv.superawesome.lib.saevents.events.setup;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAImpressionEvent;
import tv.superawesome.lib.saevents.events.Event_Test;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAImpressionEventSetup_Test extends Event_Test {

    @Test
    public void test_SAImpression_Init () throws Exception {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);

        // when
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, 1, 0L, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("/impression", event.getEndpoint());

        // then - query
        Assert.assertNotNull(event.getQuery());

        JSONObject query = event.getQuery();

        Assert.assertEquals(9, query.length());
        Assert.assertEquals(2, query.get("ct"));
        Assert.assertEquals(2001, query.get("line_item"));
        Assert.assertEquals("rand", query.get("rnd"));
        Assert.assertEquals("1.0.0", query.get("sdkVersion"));
        Assert.assertEquals(1000, query.get("placement"));
        Assert.assertEquals("superawesome.tv.saadloaderdemo", query.get("bundle"));
        Assert.assertEquals(5001, query.get("creative"));
        Assert.assertEquals("impressionDownloaded", query.get("type"));
    }
}
