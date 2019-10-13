package tv.superawesome.lib.saevents.modules;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.SAServerModule;
import tv.superawesome.lib.saevents.events.SAServerEvent;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestSAServerModule extends TestModule {

    @Test
    public void test_SAServerModule_WithDisplayAd_Success () {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, 1000, true);

        // when
        module.triggerClickEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });

        module.triggerImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });

        module.triggerViewableImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });
    }

    @Test
    public void test_SAServerModule_WithDisplayAd_Failure () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, 1000, true);

        // when
        module.triggerClickEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });

        module.triggerImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });

        module.triggerViewableImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });
    }

    @Test
    public void test_SAServerModule_WithVideoAd_Success () {

        // given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, 1000, true);

        // when
        module.triggerClickEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });

        module.triggerImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });

        module.triggerViewableImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertTrue(success);
            }
        });
    }

    @Test
    public void test_SAServerModule_WithVideoAd_Failure () {

        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, 1000, true);

        // when
        module.triggerClickEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });

        module.triggerImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });

        module.triggerViewableImpressionEvent(new SAServerEvent.Listener() {
            @Override
            public void didTriggerEvent(boolean success) {

                // then
                Assert.assertFalse(success);
            }
        });
    }
}
