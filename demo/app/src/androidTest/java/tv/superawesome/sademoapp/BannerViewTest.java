package tv.superawesome.sademoapp;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import junit.framework.TestCase;

import org.json.JSONObject;

import tv.superawesome.sademoapp.mocks.FakeAdManager;
import tv.superawesome.superawesomesdk.view.Ad;
import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.BannerViewListener;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by connor.leigh-smith on 02/07/15.
 */
public class BannerViewTest extends InstrumentationTestCase {

    private static final int SLEEP_TIME = 200;
    private static Ad DISPLAY_AD;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.DISPLAY_AD = new Ad(new JSONObject("{\n" +
            "  \"line_item_id\":1,\n" +
            "  \"campaign_id\":1,\n" +
            "  \"creative\":{\n" +
            "    \"id\":1,\n" +
            "    \"format\":\"image_with_link\",\n" +
            "    \"click_url\": \"http://superawesome.tv\",\n" +
            "    \"details\": {\n" +
            "      \"image\":\"http://www.helpinghomelesscats.com/images/cat1.jpg\",\n" +
            "      \"width\":728,\n" +
            "      \"height\":90\n" +
            "    }\n" +
            "  }\n" +
            "}"));
//        System.setProperty("dexmaker.dexcache", activity.get.getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    public void testShouldLoadDisplayAd() throws java.lang.InterruptedException{
        FakeAdManager adManager = new FakeAdManager("");
        adManager.setResponse(DISPLAY_AD);
        BannerView bannerView = new BannerView(getInstrumentation().getContext(), "1", adManager);
        BannerViewListener listener = mock(BannerViewListener.class);
        bannerView.setListener(listener);
        bannerView.loadAd();
        Thread.sleep(SLEEP_TIME);

        //Tests always passing for some reason...
        verify(listener, times(1)).onAdLoaded(any(Ad.class));
        verify(listener, times(0)).onAdError(any(String.class));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
