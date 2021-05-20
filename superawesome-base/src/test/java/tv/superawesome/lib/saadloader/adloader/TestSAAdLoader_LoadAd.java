package tv.superawesome.lib.saadloader.adloader;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.concurrent.Executor;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saadloader.mocks.executors.MockExecutor;
import tv.superawesome.lib.saadloader.mocks.servers.ads.MockAdsServer;
import tv.superawesome.lib.saadloader.mocks.session.MockSession;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACampaignType;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SADetails;
import tv.superawesome.lib.samodelspace.saad.SAMedia;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAAdLoader_LoadAd {

    private Executor executor = null;
    private ISASession session = null;
    private MockAdsServer server = null;

    @Before
    public void setUp () throws Throwable {
        executor = new MockExecutor();

        server = new MockAdsServer();
        server.start();

        session = new MockSession(server.url());
    }

    @After
    public void tearDown () throws Throwable {
        server.shutdown();
    }

    @Test
    public void test_SAAdLoader_LoadAd_WithBannerCPMAd () {
        // given
        Context context = mock(Context.class);

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1000, session, response -> {

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
            assertEquals(0.2, ad.moat, 0.05);
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

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1001, session, response -> {

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
            assertEquals(0.2, ad.moat, 0.05);
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

        final FileOutputStream outputStream = new FileOutputStream("diskfile.png");

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);
        when(prefs.edit().putString(anyString(), anyString())).thenReturn(editor);
        when(editor.commit()).thenReturn(true);
        when(context.openFileOutput(anyString(), anyInt())).thenReturn(outputStream);

        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1002, session, response -> {

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
            assertEquals(0.2, ad.moat, 0.05);
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

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1005, session, response -> {

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

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1006, session, response -> {

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

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1003, session, response -> {

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

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(1004, session, response -> {

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

        // when
        SALoader loader = new SALoader(context, executor, true, 1000);

        // then
        loader.loadAd(50000, session, response -> {

            assertNotNull(response);
            assertNotNull(response.ads);
            assertEquals(0, response.ads.size());
        });
    }
}
