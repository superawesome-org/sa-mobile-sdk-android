package tv.superawesome.lib.saevents.events.setup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestSAServerEventSetup extends TestEventSetup {

    @Test
    public void test_SAServerEvent_Init () throws Throwable {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);

        // when
        SAServerEvent event = new SAServerEvent(ad, super.session, super.executor, 1000, true);

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
