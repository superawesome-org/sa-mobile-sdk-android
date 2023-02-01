package tv.superawesome.lib.samodelspace.saad;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.ResourceReader;
import tv.superawesome.lib.samodelspace.vastad.SAVASTAdType;
import tv.superawesome.lib.samodelspace.vastad.SAVASTEvent;
import tv.superawesome.lib.samodelspace.vastad.SAVASTMedia;

public class TestSAAd_7 {

    private SAAd result;

    @Before
    public void setUp() {
        String json = ResourceReader.readResource("mock_ad_response_6.json");
        result = new SAAd(100, 0, json);
    }

    @Test
    public void test_SAAd () {

        int expected_error = 0;
        int expected_advertiserId = 1;
        int expected_publisherId = 1;
        int expected_appId = 1484;
        int expected_lineItemId = 932;
        int expected_campaignId = 0;
        int expected_placementId = 481;
        SACampaignType expected_campaignType = SACampaignType.CPM;
        String expected_device = null;
        boolean expected_isTest = false;
        boolean expected_isFallback = false;
        boolean expected_isFill = false;
        boolean expected_isHouse = false;
        boolean expected_isSafeAdApproved = true;
        boolean expected_isPadlockVisible = true;

        assertNotNull(result);
        assertEquals(result.error, expected_error);
        assertEquals(result.advertiserId, expected_advertiserId);
        assertEquals(result.publisherId, expected_publisherId);
        assertEquals(result.appId, expected_appId);
        assertEquals(result.lineItemId, expected_lineItemId);
        assertEquals(result.campaignId, expected_campaignId);
        assertEquals(result.placementId, expected_placementId);
        assertEquals(result.campaignType, expected_campaignType);
        assertEquals(result.device, expected_device);
        assertEquals(result.isTest, expected_isTest);
        assertEquals(result.isFallback, expected_isFallback);
        assertEquals(result.isFill, expected_isFill);
        assertEquals(result.isHouse, expected_isHouse);
        assertEquals(result.isSafeAdApproved, expected_isSafeAdApproved);
        assertEquals(result.isPadlockVisible, expected_isPadlockVisible);

    }

    @Test
    public void test_SACreative () {

        int expected_creative_id = 4907;
        String expected_creative_name = null;
        int expected_creative_cpm = 0;
        SACreativeFormat expected_creative_format = SACreativeFormat.video;
        boolean expected_creative_live = false;
        boolean expected_creative_approved = false;
        String expected_creative_payload = null;
        String expected_creative_clickUrl =  "https://superawesome.tv";
        String expected_creative_installUrl = null;
        String expected_creative_impressionUrl = null;
        String expected_creative_bundle = null;
        List<String> expected_creative_osTarget = new ArrayList<>();

        assertNotNull(result.creative);
        assertEquals(result.creative.id, expected_creative_id);
        assertEquals(result.creative.name, expected_creative_name);
        assertEquals(result.creative.cpm, expected_creative_cpm);
        assertEquals(result.creative.format, expected_creative_format);
        assertEquals(result.creative.live, expected_creative_live);
        assertEquals(result.creative.approved, expected_creative_approved);
        assertEquals(result.creative.payload, expected_creative_payload);
        assertEquals(result.creative.clickUrl, expected_creative_clickUrl);
        assertEquals(result.creative.installUrl, expected_creative_installUrl);
        assertEquals(result.creative.impressionUrl, expected_creative_impressionUrl);
        assertEquals(result.creative.bundle, expected_creative_bundle);
        assertEquals(result.creative.osTarget, expected_creative_osTarget);

    }

    @Test
    public void test_SAReferral () {

        int expected_referral_configuration = 0;
        int expected_referral_campaignId = 0;
        int expected_referral_lineItemId = 932;
        int expected_referral_creativeId = 4907;
        int expected_referral_placementId = 481;

        assertNotNull(result.creative.referral);
        assertEquals(result.creative.referral.configuration, expected_referral_configuration);
        assertEquals(result.creative.referral.campaignId, expected_referral_campaignId);
        assertEquals(result.creative.referral.lineItemId, expected_referral_lineItemId);
        assertEquals(result.creative.referral.creativeId, expected_referral_creativeId);
        assertEquals(result.creative.referral.placementId, expected_referral_placementId);
        assertTrue(result.creative.referral.writeToReferralQuery().contains("utm_source%3D0"));
        assertTrue(result.creative.referral.writeToReferralQuery().contains("utm_campaign%3D0"));
        assertTrue(result.creative.referral.writeToReferralQuery().contains("utm_term%3D932"));
        assertTrue(result.creative.referral.writeToReferralQuery().contains("utm_content%3D4907"));
        assertTrue(result.creative.referral.writeToReferralQuery().contains("utm_medium%3D481"));

    }

    @Test
    public void test_SADetails () {

        int expected_details_width = 600;
        int expected_details_height = 480;
        String expected_details_name = null;
        String expected_details_format =  "video";
        int expected_details_bitrate = 0;
        int expected_details_duration = 32;
        int expected_details_value = 0;
        String expected_details_image =  "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/l2UWsR6EWLZ8amjR8dTierr9hNS1mkOP.mp4";
        String expected_details_video =  "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/l2UWsR6EWLZ8amjR8dTierr9hNS1mkOP.mp4";
        String expected_details_tag = null;
        String expected_details_zip = null;
        String expected_details_url = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/l2UWsR6EWLZ8amjR8dTierr9hNS1mkOP.mp4";
        String expected_details_cdn = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/";
        String expected_details_base = "https://s3-eu-west-1.amazonaws.com";
        String expected_details_vast = "https://ads.staging.superawesome.tv/v2/video/vast/481/932/4907/?sdkVersion=ios_5.2.3&rnd=621706701&dauid=8798453893251470766&device=phone";

        assertNotNull(result.creative.details);
        assertEquals(result.creative.details.width, expected_details_width);
        assertEquals(result.creative.details.height, expected_details_height);
        assertEquals(result.creative.details.name, expected_details_name);
        assertEquals(result.creative.details.format, expected_details_format);
        assertEquals(result.creative.details.bitrate, expected_details_bitrate);
        assertEquals(result.creative.details.duration, expected_details_duration);
        assertEquals(result.creative.details.value, expected_details_value);
        assertEquals(result.creative.details.image, expected_details_image);
        assertEquals(result.creative.details.video, expected_details_video);
        assertEquals(result.creative.details.tag, expected_details_tag);
        assertEquals(result.creative.details.zip, expected_details_zip);
        assertEquals(result.creative.details.url, expected_details_url);
        assertEquals(result.creative.details.cdn, expected_details_cdn);
        assertEquals(result.creative.details.base, expected_details_base);
        assertEquals(result.creative.details.vast, expected_details_vast);
    }

    @Test
    public void test_SAMedia () {

        String expected_media_html = null;
        String expected_media_path =  "samov_19410.mp4";
        String expected_media_url =  "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/l2UWsR6EWLZ8amjR8dTierr9hNS1mkOP.mp4";
        String expected_media_type =  "video/mp4";
        boolean expected_media_isDownloaded = true;

        assertNotNull(result.creative.details.media);
        assertEquals(result.creative.details.media.html, expected_media_html);
        assertEquals(result.creative.details.media.path, expected_media_path);
        assertEquals(result.creative.details.media.url, expected_media_url);
        assertEquals(result.creative.details.media.type, expected_media_type);
        assertEquals(result.creative.details.media.isDownloaded, expected_media_isDownloaded);
    }

    @Test
    public void test_SAVASTAd () {

        String expected_vastad_redirect = null;
        String expected_vastad_url = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/l2UWsR6EWLZ8amjR8dTierr9hNS1mkOP.mp4";
        SAVASTAdType expected_vastad_type = SAVASTAdType.InLine;
        List<SAVASTMedia> expected_vastad_media = new ArrayList<>();
        SAVASTMedia media = new SAVASTMedia();
        media.type = "video/mp4";
        media.url = "https://s3-eu-west-1.amazonaws.com/sb-ads-video-transcoded/l2UWsR6EWLZ8amjR8dTierr9hNS1mkOP.mp4";
        media.bitrate = 720;
        media.width = 600;
        media.height = 480;
        expected_vastad_media.add(media);
        List<SAVASTEvent> expected_vastad_events = new ArrayList<>();
        SAVASTEvent event1 = new SAVASTEvent();
        event1.event = "creativeView";
        event1.URL = "https://ads.staging.superawesome.tv/v2/4907/creativeView";
        SAVASTEvent event2 = new SAVASTEvent();
        event2.event = "start";
        event2.URL = "https://ads.staging.superawesome.tv/v2/4907/start";
        expected_vastad_events.add(event1);
        expected_vastad_events.add(event2);

        assertNotNull(result.creative.details.media.vastAd);
        assertEquals(result.creative.details.media.vastAd.redirect, expected_vastad_redirect);
        assertEquals(result.creative.details.media.vastAd.url, expected_vastad_url);
        assertEquals(result.creative.details.media.vastAd.type, expected_vastad_type);
        assertNotNull(result.creative.details.media.vastAd.media);
        assertNotNull(result.creative.details.media.vastAd.events);
        assertEquals(result.creative.details.media.vastAd.media.size(), expected_vastad_media.size());
        assertEquals(result.creative.details.media.vastAd.events.size(), expected_vastad_events.size());

        SAVASTMedia resMedia = result.creative.details.media.vastAd.media.get(0);
        assertNotNull(resMedia);
        assertEquals(media.url, resMedia.url);
        assertEquals(media.type, resMedia.type);
        assertEquals(media.bitrate, resMedia.bitrate);
        assertEquals(media.width, resMedia.width);
        assertEquals(media.height, resMedia.height);

        SAVASTEvent resEvent1 = result.creative.details.media.vastAd.events.get(0);
        assertEquals(event1.event, resEvent1.event);
        assertEquals(event1.URL, resEvent1.URL);
        SAVASTEvent resEvent2 = result.creative.details.media.vastAd.events.get(1);
        assertEquals(event2.event, resEvent2.event);
        assertEquals(event2.URL, resEvent2.URL);

    }
}
