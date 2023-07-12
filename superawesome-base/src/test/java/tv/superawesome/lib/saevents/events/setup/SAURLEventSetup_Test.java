package tv.superawesome.lib.saevents.events.setup;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAURLEvent;
import tv.superawesome.lib.saevents.events.Event_Test;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class SAURLEventSetup_Test extends Event_Test {

    @Test
    public void test_SAURLEvent_Init () {
        // given
        String url = "https://api.url/abc";

        // when
        SAURLEvent event = new SAURLEvent(url, super.executor, 1, 0, true);

        // then - endpoint
        Assert.assertNotNull(event.getEndpoint());
        Assert.assertEquals("", event.getEndpoint());

        // then - url
        Assert.assertNotNull(event.getUrl());
        Assert.assertEquals("https://api.url/abc", event.getUrl());
    }
}
