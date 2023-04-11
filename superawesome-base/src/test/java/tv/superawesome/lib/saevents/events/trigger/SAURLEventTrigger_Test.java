package tv.superawesome.lib.saevents.events.trigger;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAURLEvent;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAURLEventTrigger_Test extends EventTrigger_Test {

    @Test
    public void test_URLEvent_triggerEvent_WithSuccess () {

        // given
        String url = "http://localhost:64000/api/url?placement=1000";
        SAURLEvent event = new SAURLEvent(url, super.executor, 1000, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_URLEvent_triggerEvent_WithFailure () {

        // given
        String url = "http://localhost:64000/api/url?placement=1001";
        SAURLEvent event = new SAURLEvent(url, super.executor, 1000, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }

}
