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
        module.triggerVASTImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTStartEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTCreativeViewEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTErrorEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTFirstQuartileEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTMidpointEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTThirdQuartileEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVASTCompleteEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });

        module.triggerVastClickThroughEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });


        module.triggerVASTClickTrackingEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertTrue(success);
            }
        });
    }

    @Test
    public void test_SAVASTModule_Failure () {
        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAVASTModule module = new SAVASTModule(ad, super.executor, 1000, true);

        // when
        module.triggerVASTImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTStartEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTCreativeViewEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTErrorEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTFirstQuartileEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTMidpointEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTThirdQuartileEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVASTCompleteEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });

        module.triggerVastClickThroughEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });


        module.triggerVASTClickTrackingEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {
                Assert.assertFalse(success);
            }
        });
    }
}
