package tv.superawesome.demoapp;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import tv.superawesome.demoapp.mocks.FakeUrlLoader;
import tv.superawesome.sdk.AdLoader;
import tv.superawesome.sdk.AdLoaderListener;
import tv.superawesome.sdk.UrlLoader;
//import tv.superawesome.superawesomesdk.AdLoader;
//import tv.superawesome.superawesomesdk.UrlLoader;
//import tv.superawesome.superawesomesdk.models.SAAd;
//import tv.superawesome.superawesomesdk.AdLoaderListener;

import static org.mockito.Mockito.*;

/**
 * Created by connor.leigh-smith on 01/07/15.
 */
public class AdLoaderTest extends TestCase {
    private static final String JSON_IMAGE_WITH_LINK = "{\n" +
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
            "}";
    private static final String JSON_RICHMEDIA = "{\n" +
            "  \"line_item_id\":1,\n" +
            "  \"campaign_id\":1,\n" +
            "  \"creative\":{\n" +
            "    \"id\":1,\n" +
            "    \"format\":\"rich_media\",\n" +
            "    \"details\": {\n" +
            "      \"url\":\"https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html\",\n" +
            "      \"width\":728,\n" +
            "      \"height\":90\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static final int SLEEP_TIME = 200;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testShouldLoadValidDisplayAd() throws java.lang.InterruptedException{



//        AdLoaderListener listener = mock(AdLoaderListener.class);
//        UrlLoader urlLoader = new FakeUrlLoader(JSON_IMAGE_WITH_LINK);
//        AdLoader adLoader = new AdLoader(listener, urlLoader, null);
//
//        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
//        Thread.sleep(SLEEP_TIME);
//        ArgumentCaptor<SAAd> adArgumentCaptor = ArgumentCaptor.forClass(SAAd.class);
//        verify(listener, times(1)).onLoaded(adArgumentCaptor.capture());
//        verify(listener, times(0)).onError(any(String.class));
//
//        assertFalse(adArgumentCaptor.getValue().error);
//        assertEquals(SAAd.Format.IMAGE_WITH_LINK, adArgumentCaptor.getValue().format);
//        assertEquals("http://www.helpinghomelesscats.com/images/cat1.jpg", adArgumentCaptor.getValue().imageURL);
    }

    public void testShouldLoadValidRichMediaAd() throws java.lang.InterruptedException{
//        AdLoaderListener listener = mock(AdLoaderListener.class);
//        UrlLoader urlLoader = new FakeUrlLoader(JSON_RICHMEDIA);
//        UrlLoader urlLoader2 = new FakeUrlLoader("Dummy Response");
//        AdLoader adLoader = new AdLoader(listener, urlLoader, urlLoader2);
//
//        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
//        Thread.sleep(SLEEP_TIME*2);
//        ArgumentCaptor<SAAd> adArgumentCaptor = ArgumentCaptor.forClass(SAAd.class);
//        verify(listener, times(1)).onLoaded(adArgumentCaptor.capture());
//        verify(listener, times(0)).onError(any(String.class));
//
//        assertFalse(adArgumentCaptor.getValue().error);
//        assertEquals(SAAd.Format.RICH_MEDIA, adArgumentCaptor.getValue().format);
//        assertEquals("https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html", adArgumentCaptor.getValue().url);
//        assertEquals("Dummy Response", adArgumentCaptor.getValue().getContent());
    }


    public void testShouldCallErrorOnEmptyString() throws java.lang.InterruptedException{
//        AdLoaderListener listener = mock(AdLoaderListener.class);
//        FakeUrlLoader urlLoader = new FakeUrlLoader("");
//        AdLoader adLoader = new AdLoader(listener, urlLoader, null);
//
//        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
//        Thread.sleep(SLEEP_TIME);
//        verify(listener, times(0)).onLoaded(any(SAAd.class));
//        verify(listener, times(1)).onError(any(String.class));
    }

    public void testShouldCallErrorOnEmptyJSONObject() throws java.lang.InterruptedException{
//        AdLoaderListener listener = mock(AdLoaderListener.class);
//        FakeUrlLoader urlLoader = new FakeUrlLoader("{}");
//        AdLoader adLoader = new AdLoader(listener, urlLoader, null);
//
//        adLoader.loadAd("https://beta.ads.superawesome.tv/v2/ad/5222/");
//        Thread.sleep(SLEEP_TIME);
//        verify(listener, times(0)).onLoaded(any(SAAd.class));
//        verify(listener, times(1)).onError(any(String.class));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
