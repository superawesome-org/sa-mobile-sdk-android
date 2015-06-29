package tv.superawesome.superawesomesdk;

import android.test.ActivityUnitTestCase;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;

import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.BannerViewListener;

/**
 * Created by balazs.kiss on 29/06/2015.
 */
public class BannerViewTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testBannerLoad() throws java.lang.InterruptedException{
        final CountDownLatch done = new CountDownLatch(1);

        BannerView bannerView = new BannerView(getContext(), "1");
        bannerView.setListener(new BannerViewListener() {
            @Override
            public void onAdLoaded() {
                assertTrue(false);
                done.countDown();
            }

            @Override
            public void onAdError(String message) {
                assertTrue(false);
                done.countDown();
            }
        });
        done.await();
        assertTrue(false);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
