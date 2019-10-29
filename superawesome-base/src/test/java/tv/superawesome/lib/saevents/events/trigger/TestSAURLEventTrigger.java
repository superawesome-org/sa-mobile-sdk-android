package tv.superawesome.lib.saevents.events.trigger;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.events.SAURLEvent;
import tv.superawesome.lib.saevents.events.SAViewableImpressionEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestSAURLEventTrigger extends TestEventTrigger {

    @Test
    public void test_URLEvent_triggerEvent_WithSuccess () {

        // given
        String url = "http://localhost:64000/api/url?placement=1000";
        SAURLEvent event = new SAURLEvent(url, super.executor, 1000, true);

        // when
        event.triggerEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });
    }

    @Test
    public void test_URLEvent_triggerEvent_WithFailure () {

        // given
        String url = "http://localhost:64000/api/url?placement=1001";
        SAURLEvent event = new SAURLEvent(url, super.executor, 1000, true);

        // when
        event.triggerEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });
    }

}
