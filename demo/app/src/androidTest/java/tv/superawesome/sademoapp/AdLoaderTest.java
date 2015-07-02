package tv.superawesome.sademoapp;

import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;

import tv.superawesome.sademoapp.mocks.FakeUrlLoader;
import tv.superawesome.superawesomesdk.AdLoader;
import tv.superawesome.superawesomesdk.UrlLoader;
import tv.superawesome.superawesomesdk.view.Ad;
import tv.superawesome.superawesomesdk.view.AdLoaderListener;

import static org.mockito.Mockito.*;

/**
 * Created by connor.leigh-smith on 01/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdLoaderTest extends InstrumentationTestCase {
    private String ApiResponse_1 = "{\"line_item_id\":1, \"campaign_id\":1,\"creative\":{\"id\":1,\"format\":\"rich_media\",\"details\": {\"url\":\"https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html\",\"width\":970,\"height\":90}}}";

    private static final int SLEEP_TIME = 500;

    AdLoaderListener listener;
    UrlLoader urlLoader;
    AdLoader adLoader;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    public void testShouldCallOnLoaded() throws java.lang.InterruptedException{
        listener = mock(AdLoaderListener.class);
        urlLoader = new FakeUrlLoader("{\n" +
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
                "}");
        adLoader = new AdLoader(listener, urlLoader);

        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
        Thread.sleep(SLEEP_TIME);
        verify(listener, times(1)).onLoaded(any(Ad.class));
        verify(listener, times(0)).onError(any(String.class));
    }


    public void testShouldCallErrorOnEmptyString() throws java.lang.InterruptedException{
        listener = mock(AdLoaderListener.class);
        urlLoader = new FakeUrlLoader("");
        adLoader = new AdLoader(listener, urlLoader);

        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
        Thread.sleep(SLEEP_TIME);
        verify(listener, times(0)).onLoaded(any(Ad.class));
        verify(listener, times(1)).onError(any(String.class));
    }

    public void testShouldCallErrorOnEmptyJSONObject() throws java.lang.InterruptedException{
        listener = mock(AdLoaderListener.class);
        urlLoader = new FakeUrlLoader("{}");
        adLoader = new AdLoader(listener, urlLoader);

        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
        Thread.sleep(SLEEP_TIME);
        verify(listener, times(0)).onLoaded(any(Ad.class));
        verify(listener, times(1)).onError(any(String.class));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
