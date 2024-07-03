package tv.superawesome.lib.saevents.events.trigger;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.events.SAViewableImpressionEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class ViewableImpressionEventTrigger_Test extends EventTrigger_Test {

    final int timeout = 1;
    final long retryDelay = 0L;

    @Test
    public void test_ViewableImpression_triggerEvent_WithSuccess() {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAViewableImpressionEvent event = new SAViewableImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_ViewableImpression_triggerEvent_WithFailure() {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAViewableImpressionEvent event = new SAViewableImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }

    @Test
    public void test_givenAdRequestId_In_Ad_then_Event_Contains_AdRequestId() {
        // Given
        SAAd ad = ModelFactory.createDisplayAd(1000, "abc");
        SAViewableImpressionEvent event = new SAViewableImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // When
        SAServerEvent.Listener listener = success -> {
            assertTrue(success);
            assertTrue(server.getLine().contains("adRequestId=abc"));
        };

        event.triggerEvent(listener);
    }

    @Test
    public void test_givenNoAdRequestId_In_Ad_then_Event_Does_Not_Contain_AdRequestId() {
        // Given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAViewableImpressionEvent event = new SAViewableImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

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
