package tv.superawesome.lib.saevents.events.trigger;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.events.SAImpressionEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAImpressionEventTrigger_Test extends EventTrigger_Test {

    final int timeout = 1;
    final long retryDelay = 0L;

    @Test
    public void test_SAImpression_triggerEvent_WithSuccess() {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAImpression_triggerEvent_WithFailure() {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }

    @Test
    public void test_givenAdRequestId_In_Ad_then_Event_Contains_AdRequestId() {
        // Given
        SAAd ad = ModelFactory.createDisplayAd(1000, "abc");
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // When
        event.triggerEvent(Assert::assertTrue);

        // Then
        assertTrue(server.getLine().contains("adRequestId=abc"));
    }

    @Test
    public void test_givenNoAdRequestId_In_Ad_then_Event_Does_Not_Contain_AdRequestId() {
        // Given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

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
