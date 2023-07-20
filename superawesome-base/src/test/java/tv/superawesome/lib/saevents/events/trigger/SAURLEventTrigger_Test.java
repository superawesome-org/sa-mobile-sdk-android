package tv.superawesome.lib.saevents.events.trigger;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAURLEvent;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAURLEventTrigger_Test extends EventTrigger_Test {
    
    final int timeout = 1;
    final long retryDelay = 0L;

    @Test
    public void test_URLEvent_triggerEvent_WithSuccess () {

        // given
        String url = "http://localhost:64000/api/url?placement=1000";
        SAURLEvent event = new SAURLEvent(url, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_URLEvent_triggerEvent_WithFailure () {

        // given
        String url = "http://localhost:64000/api/url?placement=1001";
        SAURLEvent event = new SAURLEvent(url, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }

}
