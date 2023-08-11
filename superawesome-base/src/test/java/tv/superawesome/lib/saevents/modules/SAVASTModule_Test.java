package tv.superawesome.lib.saevents.modules;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.SAVASTModule;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAVASTModule_Test extends Module_Test {

    final int timeout = 1;
    final long retryDelay = 0L;

    @Test
    public void test_SAVASTModule_Success () {
        // given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAVASTModule module = new SAVASTModule(ad, super.executor, timeout, retryDelay, true);

        // when
        module.triggerVASTImpressionEvent(Assert::assertTrue);

        module.triggerVASTStartEvent(Assert::assertTrue);

        module.triggerVASTCreativeViewEvent(Assert::assertTrue);

        module.triggerVASTErrorEvent(Assert::assertTrue);

        module.triggerVASTFirstQuartileEvent(Assert::assertTrue);

        module.triggerVASTMidpointEvent(Assert::assertTrue);

        module.triggerVASTThirdQuartileEvent(Assert::assertTrue);

        module.triggerVASTCompleteEvent(Assert::assertTrue);

        module.triggerVastClickThroughEvent(Assert::assertTrue);

        module.triggerVASTClickTrackingEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAVASTModule_Failure () {
        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAVASTModule module = new SAVASTModule(ad, super.executor, timeout, retryDelay, true);

        // when
        module.triggerVASTImpressionEvent(Assert::assertFalse);

        module.triggerVASTStartEvent(Assert::assertFalse);

        module.triggerVASTCreativeViewEvent(Assert::assertFalse);

        module.triggerVASTErrorEvent(Assert::assertFalse);

        module.triggerVASTFirstQuartileEvent(Assert::assertFalse);

        module.triggerVASTMidpointEvent(Assert::assertFalse);

        module.triggerVASTThirdQuartileEvent(Assert::assertFalse);

        module.triggerVASTCompleteEvent(Assert::assertFalse);

        module.triggerVastClickThroughEvent(Assert::assertFalse);


        module.triggerVASTClickTrackingEvent(Assert::assertFalse);
    }
}
