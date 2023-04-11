package tv.superawesome.lib.samodelspace.vastad;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import tv.superawesome.lib.ResourceReader;

public class SAVAST_2_Test {

    @Test
    public void testSAVASTAd1 () {
        
        String json = ResourceReader.readResource("mock_vast_response_4.json");

        SAVASTAd savastAd = new SAVASTAd(json);
        assertNotNull(savastAd);
        assertFalse(savastAd.isValid());

        String expected_vastRedirect = null;
        SAVASTAdType expected_vastType = SAVASTAdType.Invalid;
        String expected_mediaUrl = null;
        int expected_medias = 0;
        int expected_events = 0;

        assertEquals(expected_vastRedirect, savastAd.redirect);
        assertEquals(expected_vastType, savastAd.type);
        assertEquals(expected_mediaUrl, savastAd.url);
        assertNotNull(savastAd.media);
        assertEquals(expected_medias, savastAd.media.size());
        assertNotNull(savastAd.events);
        assertEquals(expected_events, savastAd.events.size());
    }

    @Test
    public void testSAVASTAd2 () {

        String json = ResourceReader.readResource("mock_vast_response_5.json");

        SAVASTAd savastAd = new SAVASTAd(json);
        assertNotNull(savastAd);
        assertTrue(savastAd.isValid());

        String expected_vastRedirect = null;
        SAVASTAdType expected_vastType = SAVASTAdType.InLine;
        String expected_mediaUrl = "https://ads.superawesome.tv/v2/demo_images/video.mp4";
        int expected_medias = 1;
        int expected_events = 2;

        assertEquals(expected_vastRedirect, savastAd.redirect);
        assertEquals(expected_vastType, savastAd.type);
        assertEquals(expected_mediaUrl, savastAd.url);
        assertNotNull(savastAd.media);
        assertEquals(expected_medias, savastAd.media.size());
        assertNotNull(savastAd.events);
        assertEquals(expected_events, savastAd.events.size());

        for (SAVASTEvent tracking : savastAd.events) {
            assertTrue(tracking.isValid());
        }
        for (SAVASTMedia media : savastAd.media) {
            assertTrue(media.isValid());
        }
    }


    @Test
    public void testSAVASTAd3 () {

        String json = ResourceReader.readResource("mock_vast_response_6.json");


        SAVASTAd savastAd = new SAVASTAd(json);
        assertNotNull(savastAd);
        assertTrue(savastAd.isValid());

        String expected_vastRedirect = null;
        SAVASTAdType expected_vastType = SAVASTAdType.Wrapper;
        String expected_mediaUrl = "https://ads.superawesome.tv/v2/demo_images/video.mp4";
        int expected_medias = 2;
        int expected_events = 3;

        assertEquals(expected_vastRedirect, savastAd.redirect);
        assertEquals(expected_vastType, savastAd.type);
        assertEquals(expected_mediaUrl, savastAd.url);
        assertNotNull(savastAd.media);
        assertEquals(expected_medias, savastAd.media.size());
        assertNotNull(savastAd.events);
        assertEquals(expected_events, savastAd.events.size());

        for (SAVASTEvent tracking : savastAd.events) {
            assertTrue(tracking.isValid());
        }
        for (SAVASTMedia media : savastAd.media) {
            assertTrue(media.isValid());
        }
    }

    @Test
    public void testSAVASTAd4 () {

        String json = ResourceReader.readResource("mock_vast_response_7.json");

        SAVASTAd savastAd = new SAVASTAd(json);
        assertNotNull(savastAd);
        assertFalse(savastAd.isValid());

        String expected_vastRedirect = null;
        SAVASTAdType expected_vastType = SAVASTAdType.Invalid;
        String expected_mediaUrl = null;
        int expected_medias = 0;
        int expected_events = 0;

        assertEquals(expected_vastRedirect, savastAd.redirect);
        assertEquals(expected_vastType, savastAd.type);
        assertEquals(expected_mediaUrl, savastAd.url);
        assertNotNull(savastAd.media);
        assertEquals(expected_medias, savastAd.media.size());
        assertNotNull(savastAd.events);
        assertEquals(expected_events, savastAd.events.size());
    }
}
