package tv.superawesome.lib.sautils.network;

import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_IsValidURL {

    @Test
    public void testIsValidURL () {
        // given
        String given1 = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/x7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4";
        String given2 = "";
        String given3 = null;
        String given4 = "jkskjasa";
        String given5 = "https://google.com";

        // then
        boolean result1 = SAUtils.isValidURL(given1);
        boolean result2 = SAUtils.isValidURL(given2);
        boolean result3 = SAUtils.isValidURL(given3);
        boolean result4 = SAUtils.isValidURL(given4);
        boolean result5 = SAUtils.isValidURL(given5);

        assertTrue(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertFalse(result4);
        assertTrue(result5);
    }
}
