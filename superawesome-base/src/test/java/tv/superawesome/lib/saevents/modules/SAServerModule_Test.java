package tv.superawesome.lib.saevents.modules;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.SAServerModule;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class SAServerModule_Test extends Module_Test {

    final int timeout = 1;
    final long retryDelay = 0L;
    
    @Test
    public void test_SAServerModule_WithDisplayAd_Success () {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        module.triggerClickEvent(Assert::assertTrue);

        // then
        module.triggerImpressionEvent(Assert::assertTrue);

        // then
        module.triggerViewableImpressionEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAServerModule_WithDisplayAd_Failure () {

        // given
        SAAd ad = ModelFactory.createDisplayAd(1001);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        module.triggerClickEvent(Assert::assertFalse);

        // then
        module.triggerImpressionEvent(Assert::assertFalse);

        // then
        module.triggerViewableImpressionEvent(Assert::assertFalse);
    }

    @Test
    public void test_SAServerModule_WithVideoAd_Success () {

        // given
        SAAd ad = ModelFactory.createVideoAd(1000);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        module.triggerClickEvent(Assert::assertTrue);

        // then
        module.triggerImpressionEvent(Assert::assertTrue);

        // then
        module.triggerViewableImpressionEvent(Assert::assertTrue);
    }

    @Test
    public void test_SAServerModule_WithVideoAd_Failure () {

        // given
        SAAd ad = ModelFactory.createVideoAd(1001);
        SAServerModule module = new SAServerModule(ad, super.session, super.executor, timeout, retryDelay, true);

        // when
        // then
        module.triggerClickEvent(Assert::assertFalse);

        // then
        module.triggerImpressionEvent(Assert::assertFalse);

        // then
        module.triggerViewableImpressionEvent(Assert::assertFalse);
    }
}
