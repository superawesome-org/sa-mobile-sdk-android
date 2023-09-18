package tv.superawesome.lib.saevents.events.setup;

import static org.junit.Assert.assertThrows;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.events.Event_Test;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class SAClickEventSetup_Test extends Event_Test {

    final int timeout = 1;
    final long retryDelay = 0L;

    @Test
    public void test_ClickEvent_Init_WithDisplayAd() throws Throwable {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000, "abc");

        // when
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("/click", event.getEndpoint());

        // then - query
        Assert.assertNotNull(event.getQuery());

        JSONObject query = event.getQuery();

        Assert.assertEquals(2, query.get("ct"));
        Assert.assertEquals(2001, query.get("line_item"));
        Assert.assertEquals(123456, query.get("rnd"));
        Assert.assertEquals("1.0.0", query.get("sdkVersion"));
        Assert.assertEquals(1000, query.get("placement"));
        Assert.assertEquals("superawesome.tv.saadloaderdemo", query.get("bundle"));
        Assert.assertEquals(5001, query.get("creative"));
        Assert.assertEquals("abc", query.get("adRequestId"));
    }

    @Test
    public void test_ClickEvent_Init_WithDisplayAd__WithoutAdRequestId() throws Throwable {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);

        // when
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("/click", event.getEndpoint());

        // then - query
        Assert.assertNotNull(event.getQuery());

        JSONObject query = event.getQuery();

        Assert.assertEquals(2, query.get("ct"));
        Assert.assertEquals(2001, query.get("line_item"));
        Assert.assertEquals(123456, query.get("rnd"));
        Assert.assertEquals("1.0.0", query.get("sdkVersion"));
        Assert.assertEquals(1000, query.get("placement"));
        Assert.assertEquals("superawesome.tv.saadloaderdemo", query.get("bundle"));
        Assert.assertEquals(5001, query.get("creative"));

        Throwable exception = assertThrows(
            JSONException.class, () -> {
                query.get("adRequestId");
            }
        );

        Assert.assertEquals("No value for adRequestId", exception.getMessage());
    }

    @Test
    public void test_ClickEvent_Init_WithVideoAd() throws Throwable {
        // given
        SAAd ad = ModelFactory.createVideoAd(1000, "abc");

        // when
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("/video/click", event.getEndpoint());

        // then - query
        Assert.assertNotNull(event.getQuery());

        JSONObject query = event.getQuery();

        Assert.assertEquals(2, query.get("ct"));
        Assert.assertEquals(2001, query.get("line_item"));
        Assert.assertEquals(123456, query.get("rnd"));
        Assert.assertEquals("1.0.0", query.get("sdkVersion"));
        Assert.assertEquals(1000, query.get("placement"));
        Assert.assertEquals("superawesome.tv.saadloaderdemo", query.get("bundle"));
        Assert.assertEquals(5001, query.get("creative"));
        Assert.assertEquals("abc", query.get("adRequestId"));
    }

    @Test
    public void test_ClickEvent_Init_WithVideoAd_WithoutAdRequestId() throws Throwable {
        // given
        SAAd ad = ModelFactory.createVideoAd(1000);

        // when
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("/video/click", event.getEndpoint());

        // then - query
        Assert.assertNotNull(event.getQuery());

        JSONObject query = event.getQuery();

        Assert.assertEquals(2, query.get("ct"));
        Assert.assertEquals(2001, query.get("line_item"));
        Assert.assertEquals(123456, query.get("rnd"));
        Assert.assertEquals("1.0.0", query.get("sdkVersion"));
        Assert.assertEquals(1000, query.get("placement"));
        Assert.assertEquals("superawesome.tv.saadloaderdemo", query.get("bundle"));
        Assert.assertEquals(5001, query.get("creative"));

        Throwable exception = assertThrows(
            JSONException.class, () -> {
                query.get("adRequestId");
            }
        );

       Assert.assertEquals("No value for adRequestId", exception.getMessage());
    }
}
