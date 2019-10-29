package tv.superawesome.lib.saevents.modules;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.saevents.SAMoatModule;
import tv.superawesome.lib.saevents.mocks.models.ModelFactory;
import tv.superawesome.lib.samodelspace.saad.SAAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestMoatModule extends TestModule {


    @Test
    public void test_MoatModule_IsMoatAllowed () {
        // given
        SAAd ad = ModelFactory.createDisplayAd(1000);

        // when
        SAMoatModule module = new SAMoatModule(ad, false);
        module.disableMoatLimiting();

        boolean isAllowed1 = module.isMoatAllowed();
        boolean isAllowed2 = module.isMoatAllowed();
        boolean isAllowed3 = module.isMoatAllowed();
        boolean isAllowed4 = module.isMoatAllowed();
        boolean isAllowed5 = module.isMoatAllowed();

        // then
        Assert.assertTrue(isAllowed1);
        Assert.assertTrue(isAllowed2);
        Assert.assertTrue(isAllowed3);
        Assert.assertTrue(isAllowed4);
        Assert.assertTrue(isAllowed5);
    }
}
