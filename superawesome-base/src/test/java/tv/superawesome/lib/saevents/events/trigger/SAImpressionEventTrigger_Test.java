package tv.superawesome.lib.saevents.events.trigger;

import org.junit.Assert;
import org.junit.Test;

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
    public void test_SAImpression_triggerEvent_WithSuccess () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAImpression_triggerEvent_WithFailure () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAImpressionEvent event = new SAImpressionEvent(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        event.triggerEvent(Assert::assertFalse);
    }
}
