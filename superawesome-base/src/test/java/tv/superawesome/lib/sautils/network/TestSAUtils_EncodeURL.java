package tv.superawesome.lib.sautils.network;

import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_EncodeURL {

    @Test
    public void testEncodeURL () {
        // given
        String given1 = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/x7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4";
        String given2 = "";
        String given3 = null;
        String given4 = "https:/klsa9922:skllsa/2100921091/saas///";

        // when
        String expected1 = "https%3A%2F%2Fs3-eu-west-1.amazonaws.com%2Fsb-ads-video-transcoded%2Fx7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4";
        String expected2 = "";
        String expected3 = "";
        String expected4 = "https%3A%2Fklsa9922%3Askllsa%2F2100921091%2Fsaas%2F%2F%2F";

        // then
        String result1 = SAUtils.encodeURL(given1);
        String result2 = SAUtils.encodeURL(given2);
        String result3 = SAUtils.encodeURL(given3);
        String result4 = SAUtils.encodeURL(given4);

        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
        assertEquals(result4, expected4);
    }
}
