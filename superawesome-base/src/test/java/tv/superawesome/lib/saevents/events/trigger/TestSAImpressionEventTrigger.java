package tv.superawesome.lib.saevents.events.trigger;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.events.SAImpressionEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestSAImpressionEventTrigger extends TestEventTrigger {

    @Test
    public void test_SAImpression_triggerEvent_WithSuccess () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, 1000, true);

        // when
        event.triggerEvent(success -> {

            // then
            Assert.assertTrue(success);
        });
    }

    @Test
    public void test_SAImpression_triggerEvent_WithFailure () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, 1000, true);

        // when
        event.triggerEvent(success -> {

            // then
            Assert.assertFalse(success);
        });
    }
}
