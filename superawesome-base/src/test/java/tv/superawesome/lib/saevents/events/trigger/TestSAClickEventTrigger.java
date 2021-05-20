package tv.superawesome.lib.saevents.events.trigger;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestSAClickEventTrigger extends TestEventTrigger {

    @Test
    public void test_SAClickEvent_triggerEvent_WithDisplayAdSuccess () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, 1000, true);

        // when
        event.triggerEvent(success -> {

            // then
            Assert.assertTrue(success);
        });
    }

    @Test
    public void test_SAClickEvent_triggerEvent_WithDisplayAdFailure () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, 1000, true);

        // when
        event.triggerEvent(success -> {

            // then
            Assert.assertFalse(success);
        });
    }

    @Test
    public void test_SAClickEvent_triggerEvent_WithVideoAdSuccess () {

        // given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, 1000, true);

        // when
        event.triggerEvent(success -> {

            // then
            Assert.assertTrue(success);
        });
    }

    @Test
    public void test_SAClickEvent_triggerEvent_WithVideoAdFailure () {

        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAClickEvent event = new SAClickEvent(ad, super.session, super.executor, 1000, true);

        // when
        event.triggerEvent(success -> {

            // then
            Assert.assertFalse(success);
        });
    }
}
