/**
 * @class: SAParser.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Parser;

/**
 * Imports needed for this implementation
 */
import com.bee7.gamewall.video.exoplayer.DemoPlayer;
import com.google.gson.JsonObject;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Models.SACreative;
import tv.superawesome.sdk.data.Models.SACreativeFormat;
import tv.superawesome.sdk.data.Models.SADetails;
import tv.superawesome.sdk.data.Models.SAPlacementFormat;

/**
 * @brief: This is a class of static functions that make it easy to parse Ad responses from the
 * server into SuperAwesome SDK models
 */
public class SAParser {

    /**
     * @brief: This static function parses the general Ad info (passed as a map) into a SAAd object
     * @param dict a json of values from the server
     * @return a SAAd object
     */
    public static SAAd parseAdWithMap(JsonObject dict) {
        SAAd ad = new SAAd();

        Object errorObj = dict.get("error");
        Object lineItemIdObj = dict.get("line_item_id");
        Object campaignIdObj = dict.get("campaign_id");
        Object isTestObj = dict.get("test");
        Object isFallbackObj = dict.get("is_fallback");
        Object isFillObj = dict.get("is_fill");

        ad.error = (errorObj != null ? Integer.parseInt(errorObj.toString()) : -1);
        ad.lineItemId = (lineItemIdObj != null ? Integer.parseInt(lineItemIdObj.toString()) : -1);
        ad.campaignId = (campaignIdObj != null ? Integer.parseInt(campaignIdObj.toString()) : -1);

        ad.isTest = (isTestObj != null ? Boolean.parseBoolean(isTestObj.toString()) : true);
        ad.isFallback = (isFallbackObj != null ? Boolean.parseBoolean(isFallbackObj.toString()) : true);
        ad.isFill = (isFillObj != null ? Boolean.parseBoolean(isFillObj.toString()) : false);

        return ad;
    }

    /**
     * @brief: This static function parses the creative Ad info (passed as a map) into a SACreative object
     * @param maindict a json of values from the server
     * @return a SACreative object
     */
    public static SACreative parseCreativeWithMap(JsonObject maindict) {

        /** do a quick checkup to see if creative exists as a sub-Json in maindict */
        Object creativeObj = maindict.get("creative");
        if (creativeObj == null) {
            return  null;
        }

        JsonObject dict = (JsonObject)creativeObj;
        SACreative creative = new SACreative();

        Object creativeIdObj = dict.get("id");
        Object nameObj = dict.get("name");
        Object cpmObj = dict.get("cpm");
        Object formatObj = dict.get("format");
        Object impressionUrlObj = dict.get("impression_url");
        Object clickUrlObj = dict.get("click_url");
        Object approvedObj = dict.get("approved");

        creative.creativeId = (creativeIdObj != null ? Integer.parseInt(creativeIdObj.toString()) : -1);
        creative.name = (nameObj != null ? nameObj.toString() : null);
        creative.cpm = (cpmObj != null ? Integer.parseInt(cpmObj.toString()) : 0);
        creative.impressionURL = (impressionUrlObj != null ? impressionUrlObj.toString() : null);
        creative.clickURL = (clickUrlObj != null ? clickUrlObj.toString() : "http://superawesome.tv");
        creative.approved = (approvedObj != null ? Boolean.parseBoolean(approvedObj.toString()) : false);
        creative.format = SACreativeFormat.format_unknown;

        if (formatObj.toString().equals(SACreativeFormat.image_with_link.toString())) {
            creative.format = SACreativeFormat.image_with_link;
        } else if (formatObj.toString().equals(SACreativeFormat.video.toString())) {
            creative.format = SACreativeFormat.video;
        } else if (formatObj.toString().equals(SACreativeFormat.rich_media.toString())) {
            creative.format = SACreativeFormat.rich_media;
        } else if (formatObj.toString().equals(SACreativeFormat.rich_media_resizing.toString())) {
            creative.format = SACreativeFormat.rich_media_resizing;
        } else if (formatObj.toString().equals(SACreativeFormat.swf.toString())) {
            creative.format = SACreativeFormat.swf;
        } else if (formatObj.toString().equals(SACreativeFormat.tag.toString())) {
            creative.format = SACreativeFormat.tag;
        }

        return creative;
    }

    /**
     * @brief: This static function parses the creative details Ad info (passed as a map) into a SADetails object
     * @param dict a json of values from the server
     * @return a SADetails object
     */
    public static SADetails parseDetailsWithMap(JsonObject maindict) {

        /** do a quick checkup to see if creative exists as a sub-Json in maindict */
        Object creativeObj = maindict.get("creative");
        if (creativeObj == null) {
            return  null;
        }

        Object detailsObj = ((JsonObject)creativeObj).get("details");
        if (detailsObj == null) {
            return null;
        }

        JsonObject dict = (JsonObject)detailsObj;
        SADetails details = new SADetails();

        Object widthObj = dict.get("width");
        Object heightObj = dict.get("height");
        Object imageObj = dict.get("image");
        Object valueObj = dict.get("value");
        Object nameObj = dict.get("name");
        Object videoObj = dict.get("video");
        Object bitrateObj = dict.get("bitrate");
        Object durationObj = dict.get("duration");
        Object vastObj = dict.get("vast");
        Object tagObj = dict.get("tag");
        Object placementFormatObj = dict.get("placement_format");
        Object zipFileObj = dict.get("zip_file");
        Object urlObj = dict.get("url");

        details.width = (widthObj != null ? Integer.parseInt(widthObj.toString()) : 0);
        details.height = (heightObj != null ? Integer.parseInt(heightObj.toString()) : 0);
        details.image = (imageObj != null ? imageObj.toString() : null);
        details.value = (valueObj != null ? Integer.parseInt(valueObj.toString()) : 0);
        details.name = (nameObj != null ? nameObj.toString() : null);
        details.video = (videoObj != null ? videoObj.toString() : null);
        details.bitrate = (bitrateObj != null ? Integer.parseInt(bitrateObj.toString()) : 0);
        details.duration = (durationObj != null ? Integer.parseInt(durationObj.toString()) : 0);
        details.vast = (vastObj != null ? vastObj.toString() : null);
        details.tag = (tagObj != null ? tagObj.toString() : null);
        details.zip = (zipFileObj != null ? zipFileObj.toString() : null);
        details.url = (urlObj != null ? urlObj.toString() : null);

        if (placementFormatObj != null) {
            if (placementFormatObj.toString().equals(SAPlacementFormat.web_display)) {
                details.placementFormat = SAPlacementFormat.web_display;
            } else if (placementFormatObj.toString().equals(SAPlacementFormat.floor_display)) {
                details.placementFormat = SAPlacementFormat.floor_display;
            }
        }

        return details;
    }

}
