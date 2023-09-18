package tv.superawesome.lib.saevents.events.trigger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAClickEventTrigger_Test extends EventTrigger_Test {

    final int timeout = 10;
    final long retryDelay = 0L;

    @Test
    public void test_SAClickEvent_triggerEvent_WithDisplayAdSuccess() {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAClickEvent_triggerEvent_WithDisplayAdFailure() {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }

    @Test
    public void test_SAClickEvent_triggerEvent_WithVideoAdSuccess() {

        // given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAClickEvent_triggerEvent_WithVideoAdFailure() {

        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }

    @Test
    public void test_givenQueryOptions_thenFilled() {
        // Given
        Map<String, Object> options = new HashMap<>();
        options.put("openRtbPartnerId", "xyz");

        SAAd ad = ModelFactory.createVideoAd(1000, options);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // When
        event.triggerEvent(Assert::assertTrue);

        // Then
        assertTrue(server.getLine().contains("openRtbPartnerId=xyz"));
    }

    @Test
    public void test_givenNoQueryOptions_thenNotFilled() {
        // Given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // When
        event.triggerEvent(Assert::assertTrue);

        // Then
        assertFalse(server.getLine().contains("openRtbPartnerId=xyz"));
    }

    @Test
    public void test_givenAdRequestId_In_Ad_then_Event_Contains_AdRequestId() {
        // Given
        SAAd ad = ModelFactory.createDisplayAd(1000, "abc");
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // When
        event.triggerEvent(Assert::assertTrue);

        // Then
        assertTrue(server.getLine().contains("adRequestId=abc"));
    }

    @Test
    public void test_givenNoAdRequestId_In_Ad_then_Event_Does_Not_Contain_AdRequestId() {
        // Given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // When
        event.triggerEvent(Assert::assertTrue);

        // Then
        JSONObject query = event.getQuery();

        Throwable exception = assertThrows(
            JSONException.class, () -> {
                query.get("adRequestId");
            }
        );

        Assert.assertEquals("No value for adRequestId", exception.getMessage());
    }
}
