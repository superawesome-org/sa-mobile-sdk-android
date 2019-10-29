package tv.superawesome.lib.sautils.network;

import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_FindBaseURLFromResourceURL {

    @Test
    public void testFindBaseURLFromResourceURL () {
        // given
        String given1 = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/x7XkGy43vim5P1OpldlOUuxk2cuKsDSn.mp4";
        String given2 = "https://s3-eu-west-1.amazonaws.com/sb-ads-uploads/images/cvWABPEIS7vUEtlv5U17MwaTNhRARYjB.png";
        String given3 = "https://s3-eu-west-1.amazonaws.com/sb-ads-uploads/rich-media/tNmFLJ7kGQWBbyORkIqTJ4oqykaGPU9w/rich-media/index.html";
        String given4 = null;
        String given5 = "https:/klsa9922:skllsa/2100921091/saas///";

        // when
        String expected1 = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/";
        String expected2 = "https://s3-eu-west-1.amazonaws.com/sb-ads-uploads/images/";
        String expected3 = "https://s3-eu-west-1.amazonaws.com/sb-ads-uploads/rich-media/tNmFLJ7kGQWBbyORkIqTJ4oqykaGPU9w/rich-media/";
        String expected4 = null;
        String expected5 = null;

        // then
        String result1 = SAUtils.findBaseURLFromResourceURL(given1);
        String result2 = SAUtils.findBaseURLFromResourceURL(given2);
        String result3 = SAUtils.findBaseURLFromResourceURL(given3);
        String result4 = SAUtils.findBaseURLFromResourceURL(given4);
        String result5 = SAUtils.findBaseURLFromResourceURL(given5);

        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
        assertEquals(result4, expected4);
        assertEquals(result5, expected5);
    }
}
