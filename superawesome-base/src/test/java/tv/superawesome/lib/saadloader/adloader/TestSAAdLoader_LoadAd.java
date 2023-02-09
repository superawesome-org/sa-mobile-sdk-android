package tv.superawesome.lib.saadloader.adloader;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.mocks.executors.MockExecutor;
import tv.superawesome.lib.saadloader.mocks.servers.ads.MockAdsServer;
import tv.superawesome.lib.saadloader.mocks.session.MockSession;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACampaignType;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SADetails;
import tv.superawesome.lib.samodelspace.saad.SAMedia;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sautils.SAClock;
import tv.superawesome.sdk.publisher.QueryAdditionalOptions;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAAdLoader_LoadAd {

    private Executor executor = null;
    private ISASession session = null;
    private MockAdsServer server = null;
    private Map<String, Object> initialOptions = new HashMap<String, Object>() {{
        put("key1", "value1");
        put("key2", 2);
    }};

    private Map<String, Object> additionalOptions = new HashMap<String, Object>() {{
        put("key3", "value3");
        put("key4", 4);
    }};

    private Map<String, Object> combinedOptions = new HashMap<String, Object>() {{
        put("key1", "value1");
        put("key2", 2);
        put("key3", "value3");
        put("key4", 4);
    }};

    @Before
    public void setUp () throws Throwable {
        executor = new MockExecutor();

        server = new MockAdsServer();
        server.start();

        session = new MockSession(server.url());
        QueryAdditionalOptions.Companion.setInstance(null);
    }

    @After
    public void tearDown () throws Throwable {
        server.shutdown();
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithBannerCPMAd () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1000, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertTrue(ad.isValid());

            // main ad
            assertEquals(4, ad.advertiserId);
            assertEquals(4, ad.publisherId);
            assertFalse(ad.isFill);
            assertFalse(ad.isFallback);
            assertFalse(ad.isHouse);
            assertTrue(ad.isSafeAdApproved);
            assertTrue(ad.isPadlockVisible);
            assertEquals(SACampaignType.CPM, ad.campaignType);
            assertEquals(44855, ad.lineItemId);
            assertEquals(32862, ad.campaignId);
            assertEquals(31570, ad.appId);
            assertTrue(ad.isTest);
            assertEquals("phone", ad.device);

            // creative
            SACreative creative = ad.creative;

            assertNotNull(creative);

            assertEquals(230903, creative.id);
            assertEquals("Moat New SDK - Banner Creative", creative.name);
            assertEquals(SACreativeFormat.image, creative.format);
            assertNull(creative.impressionUrl);
            assertEquals("http://superawesome.tv", creative.clickUrl);

            // details
            SADetails details = creative.details;

            assertEquals("https://sa-beta-ads-uploads-superawesome.netdna-ssl.com/images/a3wKV14OSmxTAUOs7W2iXdnOHl8psm9z.jpg", details.url);
            assertEquals("https://sa-beta-ads-uploads-superawesome.netdna-ssl.com/images/a3wKV14OSmxTAUOs7W2iXdnOHl8psm9z.jpg", details.image);
            assertNull(details.vast);
            assertEquals("mobile_display", details.format);
            assertEquals(320, details.width);
            assertEquals(50, details.height);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithBannerCPIAd () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1001, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertTrue(ad.isValid());

            // main ad
            assertEquals(4, ad.advertiserId);
            assertEquals(4, ad.publisherId);
            assertFalse(ad.isFill);
            assertFalse(ad.isFallback);
            assertFalse(ad.isHouse);
            assertTrue(ad.isSafeAdApproved);
            assertTrue(ad.isPadlockVisible);
            assertEquals(SACampaignType.CPI, ad.campaignType);
            assertEquals(44855, ad.lineItemId);
            assertEquals(32862, ad.campaignId);
            assertEquals(31570, ad.appId);
            assertTrue(ad.isTest);
            assertEquals("phone", ad.device);

            // creative
            SACreative creative = ad.creative;

            assertNotNull(creative);

            assertEquals(230903, creative.id);
            assertEquals("Moat New SDK - Banner Creative", creative.name);
            assertEquals(SACreativeFormat.image, creative.format);
            assertNull(creative.impressionUrl);
            assertEquals("http://superawesome.tv", creative.clickUrl);

            // details
            SADetails details = creative.details;

            assertEquals("https://sa-beta-ads-uploads-superawesome.netdna-ssl.com/images/a3wKV14OSmxTAUOs7W2iXdnOHl8psm9z.jpg", details.url);
            assertEquals("https://sa-beta-ads-uploads-superawesome.netdna-ssl.com/images/a3wKV14OSmxTAUOs7W2iXdnOHl8psm9z.jpg", details.image);
            assertNull(details.vast);
            assertEquals("mobile_display", details.format);
            assertEquals(320, details.width);
            assertEquals(50, details.height);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithVideoAd () throws Exception {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        SAClock clockMock = mock(SAClock.class);

        final FileOutputStream outputStream = new FileOutputStream("diskfile.png");

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);
        when(prefs.edit().putString(anyString(), anyString())).thenReturn(editor);
        when(editor.commit()).thenReturn(true);
        when(context.openFileOutput(anyString(), anyInt())).thenReturn(outputStream);

        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1002, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertTrue(ad.isValid());

            // main ad
            assertEquals(4, ad.advertiserId);
            assertEquals(4, ad.publisherId);
            assertFalse(ad.isFill);
            assertFalse(ad.isFallback);
            assertFalse(ad.isHouse);
            assertTrue(ad.isSafeAdApproved);
            assertTrue(ad.isPadlockVisible);
            assertEquals(SACampaignType.CPM, ad.campaignType);
            assertEquals(44855, ad.lineItemId);
            assertEquals(32862, ad.campaignId);
            assertEquals(31570, ad.appId);
            assertTrue(ad.isTest);
            assertEquals("phone", ad.device);

            // creative
            SACreative creative = ad.creative;

            assertNotNull(creative);

            assertEquals(230904, creative.id);
            assertNull(creative.name);
            assertEquals(SACreativeFormat.video, creative.format);
            assertNull(creative.impressionUrl);
            assertEquals("http://superawesome.tv", creative.clickUrl);

            // details
            SADetails details = creative.details;

            assertNotNull(details);

            assertEquals("/dkopqAGR8eYBV5KNQP7wH9UQniqbG4Ga.mp4", details.url);
            assertEquals("/dkopqAGR8eYBV5KNQP7wH9UQniqbG4Ga.mp4", details.image);
            assertEquals("http://localhost:64000/vast/vast.xml", details.vast);
            assertEquals("video", details.format);
            assertEquals(600, details.width);
            assertEquals(480, details.height);

            // more
            SAMedia media = details.media;

            assertNotNull(media);
            assertEquals("videoresource.mp4", media.path);
            assertTrue(media.isDownloaded);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithVideoAdResponseButNoVASTTagResponse () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);
        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1005, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertFalse(ad.isValid());

            SACreative creative = ad.creative;

            assertNotNull(creative);
            assertEquals(SACreativeFormat.video, creative.format);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithVideoAdResponseAndVASTResponseButNoVideoResource () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1006, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertFalse(ad.isValid());

            SACreative creative = ad.creative;

            assertNotNull(creative);
            assertEquals(SACreativeFormat.video, creative.format);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithEmptyAd () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1003, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertFalse(ad.isValid());

            SACreative creative = ad.creative;

            assertNotNull(creative);
            assertEquals(SACreativeFormat.invalid, creative.format);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithMalformedResponse () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1004, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertNotNull(ad);
            assertFalse(ad.isValid());

            SACreative creative = ad.creative;

            assertNotNull(creative);
            assertEquals(SACreativeFormat.invalid, creative.format);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithTimeoutResponse () {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(50000, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(0, response.ads.size());
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_With_No_Options() {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1006, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertTrue(ad.requestOptions.isEmpty());
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_With_Initial_Options_Only() {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);
        QueryAdditionalOptions.Companion.setInstance(new QueryAdditionalOptions(initialOptions));

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1006, session, null, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertEquals(ad.requestOptions, initialOptions);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_With_Additional_Options_Only() {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1006, session, additionalOptions, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertEquals(ad.requestOptions, additionalOptions);
        });
    }

    @Test
    public void test_SAAdLoader_LoadAd_With_Initial_Options_And_Additional_Options() {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);
        QueryAdditionalOptions.Companion.setInstance(new QueryAdditionalOptions(initialOptions));

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);

        // then
        loader.loadAd(1006, session, additionalOptions, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            assertEquals(ad.requestOptions, combinedOptions);
        });
    }

    @Test
    public void test_SAAdLoader_Additional_Options_Can_Override_Initial_Options_When_Keys_Conflict() {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);
        QueryAdditionalOptions.Companion.setInstance(new QueryAdditionalOptions(initialOptions));

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);
        Map<String, Object> additionalOptions = new HashMap<String, Object>() {{
            put("key1", "x");
        }};

        // then
        loader.loadAd(1006, session, additionalOptions, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            Map<String, Object> expectedOptions = new HashMap<String, Object>() {{
                put("key1", "x");
                put("key2", 2);
            }};

            assertEquals(ad.requestOptions, expectedOptions);
        });
    }

    @Test
    public void test_SAAdLoader_Unsuitable_Types_Are_Not_Included() {
        // given
        Context context = mock(Context.class);
        SAClock clockMock = mock(SAClock.class);
        QueryAdditionalOptions.Companion.setInstance(new QueryAdditionalOptions(initialOptions));

        // when
        SALoader loader = new SALoader(context, executor, true, 1000, clockMock);
        Map<String, Object> additionalOptions = new HashMap<String, Object>() {{
            put("key3", new Activity());
            put("key4", 4);
        }};

        // then
        loader.loadAd(1006, session, additionalOptions, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(1, response.ads.size());

            SAAd ad = response.ads.get(0);

            Map<String, Object> expectedOptions = new HashMap<String, Object>() {{
                put("key1", "value1");
                put("key2", 2);
                put("key4", 4);
            }};

            assertEquals(ad.requestOptions, expectedOptions);
        });
    }
}
