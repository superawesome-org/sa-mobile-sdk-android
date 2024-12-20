package tv.superawesome.lib.saevents.events.setup;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.events.Event_Test;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAServerEventSetup_Test extends Event_Test {

    @Test
    public void test_SAServerEvent_Init () throws Throwable {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);

        // when
        SAServerEvent event = new SAServerEvent(ad, super.session, super.executor, 1, 0, true);

        // then - url
        Assert.assertNotNull(event);

        Assert.assertNotNull(event.getUrl());
        Assert.assertEquals("http://localhost:64000", event.getUrl());
        Assert.assertEquals(session.getBaseUrl(), event.getUrl());

        // then - header
        Assert.assertNotNull(event.getHeader());

        Assert.assertEquals("application/json", event.getHeader().getString("Content-Type"));
    }
}
