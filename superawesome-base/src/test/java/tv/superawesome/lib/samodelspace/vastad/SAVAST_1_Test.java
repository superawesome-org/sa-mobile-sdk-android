package tv.superawesome.lib.samodelspace.vastad;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import tv.superawesome.lib.ResourceReader;

public class SAVAST_1_Test {
    
    @Test
    public void testSAVASTMedia1 () {

        String json = ResourceReader.readResource("mock_vast_response_1.json");

        SAVASTMedia savastMedia = new SAVASTMedia(json);
        assertNotNull(savastMedia);

        String expected_type = "video/mp4";
        String expected_mediaUrl = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/c0sKSRTuPu8dDkok2HQTnLS1k3A6vL6c.mp4";
        int expected_bitrate = 720;
        int expected_width = 600;
        int expected_height = 480;

        assertEquals(expected_type, savastMedia.type);
        assertEquals(expected_mediaUrl, savastMedia.url);
        assertEquals(expected_bitrate, savastMedia.bitrate);
        assertEquals(expected_width, savastMedia.width);
        assertEquals(expected_height, savastMedia.height);
        assertTrue(savastMedia.isValid());
    }

    @Test
    public void testSAVASTMedia2 () {

        String json = ResourceReader.readResource("mock_vast_response_2.json");

        SAVASTMedia savastMedia = new SAVASTMedia(json);
        assertNotNull(savastMedia);

        String expected_type = "video/mp4";
        String expected_mediaUrl = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/c0sKSRTuPu8dDkok2HQTnLS1k3A6vL6c.mp4";
        int expected_bitrate = 0;
        int expected_width = 0;
        int expected_height = 480;

        assertEquals(expected_type, savastMedia.type);
        assertEquals(expected_mediaUrl, savastMedia.url);
        assertEquals(expected_bitrate, savastMedia.bitrate);
        assertEquals(expected_width, savastMedia.width);
        assertEquals(expected_height, savastMedia.height);
        assertTrue(savastMedia.isValid());
    }

    @Test
    public void testSAVASTMedia3 () {

        String json = ResourceReader.readResource("mock_vast_response_3.json");

        SAVASTMedia savastMedia = new SAVASTMedia(json);
        assertNotNull(savastMedia);

        String expected_type = null;
        String expected_mediaUrl = null;
        int expected_bitrate = 0;
        int expected_width = 0;
        int expected_height = 0;

        assertEquals(expected_type, savastMedia.type);
        assertEquals(expected_mediaUrl, savastMedia.url);
        assertEquals(expected_bitrate, savastMedia.bitrate);
        assertEquals(expected_width, savastMedia.width);
        assertEquals(expected_height, savastMedia.height);
        assertFalse(savastMedia.isValid());
    }

    @Test
    public void testSAVASTMedia4 () {

        String json = null;

        SAVASTMedia savastMedia = new SAVASTMedia(json);
        assertNotNull(savastMedia);

        String expected_type = null;
        String expected_mediaUrl = null;
        int expected_bitrate = 0;
        int expected_width = 0;
        int expected_height = 0;

        assertEquals(expected_type, savastMedia.type);
        assertEquals(expected_mediaUrl, savastMedia.url);
        assertEquals(expected_bitrate, savastMedia.bitrate);
        assertEquals(expected_width, savastMedia.width);
        assertEquals(expected_height, savastMedia.height);
        assertFalse(savastMedia.isValid());

    }
}
