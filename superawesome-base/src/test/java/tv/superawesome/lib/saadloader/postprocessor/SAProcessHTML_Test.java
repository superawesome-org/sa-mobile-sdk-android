package tv.superawesome.lib.saadloader.postprocessor;


import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;

public class SAProcessHTML_Test {

    @Test
    public void test_SAProcessHTML_FormatCreativeIntoImageHTML_WithClick () {

        SAAd ad = new SAAd();
        ad.creative.format = SACreativeFormat.image;
        ad.creative.details.image = "https://ads.superawesome.tv/v2/demo_images/320x50.jpg";
        ad.creative.clickUrl = "http://hotnews.ro";
        ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoImageHTML(ad);

        assertNotNull(ad.creative.details.media.html);
        assertTrue(ad.creative.details.media.html.contains("<img src='https://ads.superawesome.tv/v2/demo_images/320x50.jpg' width='100%' height='100%' style='object-fit: contain;'/>"));
        assertTrue(ad.creative.details.media.html.contains("<a href='http://hotnews.ro' target='_blank'>"));
        assertTrue(ad.creative.details.media.html.contains("</a>"));
        assertTrue(ad.creative.details.media.html.equals("<a href='http://hotnews.ro' target='_blank'><img src='https://ads.superawesome.tv/v2/demo_images/320x50.jpg' width='100%' height='100%' style='object-fit: contain;'/></a>"));
    }

    @Test
    public void test_SAProcessHTML_FormatCreativeIntoImageHTML_WithoutClick () {

        SAAd ad = new SAAd();
        ad.creative.format = SACreativeFormat.image;
        ad.creative.details.image = "https://ads.superawesome.tv/v2/demo_images/320x50.jpg";
        ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoImageHTML(ad);

        assertNotNull(ad.creative.details.media.html);
        assertTrue(ad.creative.details.media.html.contains("<img src='https://ads.superawesome.tv/v2/demo_images/320x50.jpg' width='100%' height='100%' style='object-fit: contain;'/>"));
        assertFalse(ad.creative.details.media.html.contains("<a href='http://hotnews.ro' target='_blank'>"));
        assertFalse(ad.creative.details.media.html.contains("</a>"));
        assertTrue(ad.creative.details.media.html.equals("<img src='https://ads.superawesome.tv/v2/demo_images/320x50.jpg' width='100%' height='100%' style='object-fit: contain;'/>"));

    }

    @Test
    public void test_SAProcessHTML_FormatCreativeIntoRichMediaHTML () {

        SAAd ad = new SAAd();
        ad.placementId = 4091;
        ad.lineItemId = 2001;
        ad.creative.id = 2081;
        ad.creative.format = SACreativeFormat.rich;
        ad.creative.details.url = "https://s3-eu-west-1.amazonaws.com/sb-ads-uploads/rich-media/tNmFLJ7kGQWBbyORkIqTJ4oqykaGPU9w/rich-media/index.html";
        ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoRichMediaHTML(ad, 123456);

        assertNotNull(ad.creative.details.media.html);
        assertTrue(ad.creative.details.media.html.contains("<iframe style='padding:0;border:0;' width='100%' height='100%' src='https://s3-eu-west-1.amazonaws.com/sb-ads-uploads/rich-media/tNmFLJ7kGQWBbyORkIqTJ4oqykaGPU9w/rich-media/index.html?placement=4091&line_item=2001&creative=2081&rnd=123456'></iframe>"));
    }

    @Test
    public void test_SAProcessHTML_FormatCreativeIntoTagHTML1 () {

        SAAd ad = new SAAd();
        ad.creative.format = SACreativeFormat.tag;
        ad.creative.details.tag = "<!-- Beginning PassBack for Ad unit FK:Site-Skyscraper-Passback ### size: [[120,600]] -->\\\\n\\\\t<script type='text/javascript' src='http://www.googletagservices.com/tag/js/gpt.js'>\\\\n\\\\t\\\\tgoogletag.pubads().definePassback('1002534/FK:Site-Skyscraper-Passback', [[120,600]]).display();\\\\n\\\\t</script>\\\\n<!-- End Passback -->";
        ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoTagHTML(ad);

        assertNotNull(ad.creative.details.media.html);
        assertTrue(ad.creative.details.media.html.contains("<!-- Beginning PassBack for Ad unit FK:Site-Skyscraper-Passback ### size: [[120,600]] -->\\\\n\\\\t<script type='text/javascript' src='http://www.googletagservices.com/tag/js/gpt.js'>\\\\n\\\\t\\\\tgoogletag.pubads().definePassback('1002534/FK:Site-Skyscraper-Passback', [[120,600]]).display();\\\\n\\\\t</script>\\\\n<!-- End Passback -->"));
    }

    @Test
    public void test_SAProcessHTML_FormatCreativeIntoTagHTML2 () {

        SAAd ad = new SAAd();
        ad.creative.format = SACreativeFormat.tag;
        ad.creative.details.tag = "<A HREF=\"[click]https://ad.doubleclick.net/ddm/jump/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729?\"><IMG SRC=\"https://ad.doubleclick.net/ddm/ad/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=?\" BORDER=0 WIDTH=480 HEIGHT=320 ALT=\"Advertisement\"></A>";
        ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoTagHTML(ad);

        assertNotNull(ad.creative.details.media.html);
        assertTrue(ad.creative.details.media.html.contains("<A HREF=\"https://ad.doubleclick.net/ddm/jump/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729?\"><IMG SRC=\"https://ad.doubleclick.net/ddm/ad/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=?\" BORDER=0 WIDTH=480 HEIGHT=320 ALT=\"Advertisement\"></A>"));
    }

    @Test
    public void test_SAProcessHTML_FormatCreativeIntoTagHTML3 () {

        SAAd ad = new SAAd();
        ad.creative.format = SACreativeFormat.tag;
        ad.creative.details.tag = "<A HREF=\"[click]https://ad.doubleclick.net/ddm/jump/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729?\"><IMG SRC=\"https://ad.doubleclick.net/ddm/ad/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=?\" BORDER=0 WIDTH=480 HEIGHT=320 ALT=\"Advertisement\"></A>";
        ad.creative.clickUrl = "http://hotnews.ro";
        ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoTagHTML(ad);

        assertNotNull(ad.creative.details.media.html);
        assertTrue(ad.creative.details.media.html.contains("<A HREF=\"http://hotnews.ro&redir=https://ad.doubleclick.net/ddm/jump/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729?\"><IMG SRC=\"https://ad.doubleclick.net/ddm/ad/N304202.1915243SUPERAWESOME.TV/B10773905.144955457;sz=480x320;ord=1486394166729;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=?\" BORDER=0 WIDTH=480 HEIGHT=320 ALT=\"Advertisement\"></A>"));
    }
}
