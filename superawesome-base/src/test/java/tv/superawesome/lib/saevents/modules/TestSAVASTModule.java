package tv.superawesome.lib.saevents.modules;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.SAVASTModule;
import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestSAVASTModule extends TestModule {

    @Test
    public void test_SAVASTModule_Success () {
        // given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAVASTModule module = new SAVASTModule(ad, super.executor, 1000, true);

        // when
        module.triggerVASTImpressionEvent(success -> Assert.assertTrue(success));

        module.triggerVASTStartEvent(success -> Assert.assertTrue(success));

        module.triggerVASTCreativeViewEvent(success -> Assert.assertTrue(success));

        module.triggerVASTErrorEvent(success -> Assert.assertTrue(success));

        module.triggerVASTFirstQuartileEvent(success -> Assert.assertTrue(success));

        module.triggerVASTMidpointEvent(success -> Assert.assertTrue(success));

        module.triggerVASTThirdQuartileEvent(success -> Assert.assertTrue(success));

        module.triggerVASTCompleteEvent(success -> Assert.assertTrue(success));

        module.triggerVastClickThroughEvent(success -> Assert.assertTrue(success));


        module.triggerVASTClickTrackingEvent(success -> Assert.assertTrue(success));
    }

    @Test
    public void test_SAVASTModule_Failure () {
        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAVASTModule module = new SAVASTModule(ad, super.executor, 1000, true);

        // when
        module.triggerVASTImpressionEvent(success -> Assert.assertFalse(success));

        module.triggerVASTStartEvent(success -> Assert.assertFalse(success));

        module.triggerVASTCreativeViewEvent(success -> Assert.assertFalse(success));

        module.triggerVASTErrorEvent(success -> Assert.assertFalse(success));

        module.triggerVASTFirstQuartileEvent(success -> Assert.assertFalse(success));

        module.triggerVASTMidpointEvent(success -> Assert.assertFalse(success));

        module.triggerVASTThirdQuartileEvent(success -> Assert.assertFalse(success));

        module.triggerVASTCompleteEvent(success -> Assert.assertFalse(success));

        module.triggerVastClickThroughEvent(success -> Assert.assertFalse(success));


        module.triggerVASTClickTrackingEvent(success -> Assert.assertFalse(success));
    }
}
