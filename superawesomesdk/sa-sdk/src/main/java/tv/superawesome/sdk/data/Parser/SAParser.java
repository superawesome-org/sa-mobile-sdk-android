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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import tv.superawesome.lib.sanetwork.*;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Models.SACreative;
import tv.superawesome.sdk.data.Models.SACreativeFormat;
import tv.superawesome.sdk.data.Models.SADetails;
import tv.superawesome.lib.sautils.*;

/**
 * This is a class of static functions that make it easy to parse Ad responses from the
 * server into SuperAwesome SDK models
 */
public class SAParser {

    /**
     * This function performs the Basic integrity check on a piece of data loaded from the
     * internet
     * @param dict
     */
    private static boolean performIntegrityCheck(JsonObject dict){
        if (!SAUtils.isJSONEmpty(dict)){
            JsonObject creative = dict.getAsJsonObject("creative");
            if (!SAUtils.isJSONEmpty(creative)){
                JsonObject details = creative.getAsJsonObject("details");
                if (!SAUtils.isJSONEmpty(details)){
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /**
     * This static function parses the general Ad info (passed as a map) into a SAAd object
     * @param dict a json of values from the server
     * @return a SAAd object
     */
    private static SAAd parseAdWithDictionary(JsonObject dict) {
        SAAd ad = new SAAd();

        Object errorObj = dict.get("error");
        Object lineItemIdObj = dict.get("line_item_id");
        Object campaignIdObj = dict.get("campaign_id");
        Object isTestObj = dict.get("test");
        Object isFallbackObj = dict.get("is_fallback");
        Object isFillObj = dict.get("is_fill");

        ad.error = (errorObj != null ? Integer.parseInt(errorObj.toString().replace("\"","")) : -1);
        ad.lineItemId = (lineItemIdObj != null ? Integer.parseInt(lineItemIdObj.toString().replace("\"","")) : -1);
        ad.campaignId = (campaignIdObj != null ? Integer.parseInt(campaignIdObj.toString().replace("\"","")) : -1);

        ad.isTest = (isTestObj == null || Boolean.parseBoolean(isTestObj.toString().replace("\"","")));
        ad.isFallback = (isFallbackObj == null || Boolean.parseBoolean(isFallbackObj.toString().replace("\"","")));
        ad.isFill = (isFillObj != null && Boolean.parseBoolean(isFillObj.toString().replace("\"","")));

        return ad;
    }

    /**
     * This static function parses the creative Ad info (passed as a map) into a SACreative object
     * @param cdict a json of values from the server
     * @return a SACreative object
     */
    private static SACreative parseCreativeWithDictionary(JsonObject cdict) {

        SACreative creative = new SACreative();

        Object creativeIdObj = cdict.get("id");
        Object nameObj = cdict.get("name");
        Object cpmObj = cdict.get("cpm");
        Object baseFormatObj = cdict.get("format");
        Object impressionUrlObj = cdict.get("impression_url");
        Object clickUrlObj = cdict.get("click_url");
        Object approvedObj = cdict.get("approved");

        creative.creativeId = (creativeIdObj != null ? Integer.parseInt(creativeIdObj.toString().replace("\"","")) : -1);
        creative.name = (nameObj != null ? nameObj.toString().replace("\"", "") : null);
        creative.cpm = (cpmObj != null ? Integer.parseInt(cpmObj.toString().replace("\"", "")) : 0);
        creative.impressionURL = (impressionUrlObj != null ? impressionUrlObj.toString().replace("\"", "") : null);
        creative.clickURL = (clickUrlObj != null ? clickUrlObj.toString().replace("\"", "") : null);
        creative.approved = (approvedObj != null ? Boolean.parseBoolean(approvedObj.toString().replace("\"", "")) : false);
        creative.baseFormat = (baseFormatObj != null ? baseFormatObj.toString().replace("\"", "") : null);

        return creative;
    }

    /**
     * This static function parses the creative details Ad info (passed as a map) into a SADetails object
     * @param ddict a json of values from the server
     * @return a SADetails object
     */
    private static SADetails parseDetailsWithDictionary(JsonObject ddict) {

        SADetails details = new SADetails();

        Object widthObj = ddict.get("width");
        Object heightObj = ddict.get("height");
        Object imageObj = ddict.get("image");
        Object valueObj = ddict.get("value");
        Object nameObj = ddict.get("name");
        Object videoObj = ddict.get("video");
        Object bitrateObj = ddict.get("bitrate");
        Object durationObj = ddict.get("duration");
        Object vastObj = ddict.get("vast");
        Object tagObj = ddict.get("tag");
        Object placementFormatObj = ddict.get("placement_format");
        Object zipFileObj = ddict.get("zip_file");
        Object urlObj = ddict.get("url");

        details.width = (widthObj != null ? Integer.parseInt(widthObj.toString().replace("\"","")) : 0);
        details.height = (heightObj != null ? Integer.parseInt(heightObj.toString().replace("\"","")) : 0);
        details.image = (imageObj != null ? imageObj.toString().replace("\"", "") : null);
        details.value = (valueObj != null ? Integer.parseInt(valueObj.toString().replace("\"","")) : 0);
        details.name = (nameObj != null ? nameObj.toString().replace("\"", "") : null);
        details.video = (videoObj != null ? videoObj.toString().replace("\"", "") : null);
        details.bitrate = (bitrateObj != null ? Integer.parseInt(bitrateObj.toString().replace("\"","")) : 0);
        details.duration = (durationObj != null ? Integer.parseInt(durationObj.toString().replace("\"","")) : 0);
        details.vast = (vastObj != null ? vastObj.toString().replace("\"", "") : null);
        details.tag = (tagObj != null ? tagObj.toString().replace("\"", "") : null);
        details.zip = (zipFileObj != null ? zipFileObj.toString().replace("\"", "") : null);
        details.url = (urlObj != null ? urlObj.toString().replace("\"", "") : null);
        details.placementFormat = (placementFormatObj != null ? placementFormatObj.toString().replace("\"", "") : null);

        return details;
    }

    /**
     * Parses a dictionary received from the server into a valid Ad object
     * @param dict - the dictionary to parse
     * @param placementId = the placement id - just used because it's not returned by the ad server
     * @param listener - the listener that actually calls the callback to finish the method
     */
    public static void parseDictionaryIntoAd(JsonObject dict, int placementId, final SAParserListener listener) {
        /** perform integrity check */
        if (!SAParser.performIntegrityCheck(dict)){
            SALog.Log("Did not pass integrity check");
            listener.parsedAd(null);
            return;
        }

        /** extract dicts */
        JsonObject adict = dict;
        JsonObject cdict = dict.getAsJsonObject("creative");
        JsonObject ddict = cdict.getAsJsonObject("details");

        /** parse base ad stuff */
        SAAd ad = SAParser.parseAdWithDictionary(adict);
        ad.placementId = placementId;
        ad.creative = SAParser.parseCreativeWithDictionary(cdict);
        ad.creative.details = SAParser.parseDetailsWithDictionary(ddict);

        /** prform the next steps of the parsing */
        ad.creative.format = SACreativeFormat.invalid;
        if (ad.creative.baseFormat.equals("image_with_link")) ad.creative.format = SACreativeFormat.image;
        else if (ad.creative.baseFormat.equals("video")) ad.creative.format = SACreativeFormat.video;
        else if (ad.creative.baseFormat.contains("rich_media")) ad.creative.format = SACreativeFormat.rich;
        else if (ad.creative.baseFormat.contains("tag")) ad.creative.format = SACreativeFormat.tag;

        /** create the tracking URL */
        JsonObject trackingDict = new JsonObject();
        trackingDict.addProperty("placement", ad.placementId);
        trackingDict.addProperty("line_item", ad.lineItemId);
        trackingDict.addProperty("creative", ad.creative.creativeId);
        trackingDict.addProperty("sdkVersion", SuperAwesome.getInstance().getSDKVersion());
        trackingDict.addProperty("rnd", SAURLUtils.getCacheBuster());
        ad.creative.trackingURL = SuperAwesome.getInstance().getBaseURL() + "/click?" + SAURLUtils.formGetQueryFromDict(trackingDict);

        /** create the viewable impression URL */
        JsonObject impressionDict1 = new JsonObject();
        impressionDict1.addProperty("placement", ad.placementId);
        impressionDict1.addProperty("line_item", ad.lineItemId);
        impressionDict1.addProperty("creative", ad.creative.creativeId);
        impressionDict1.addProperty("type", "viewable_impression");
        JsonObject impressionDict2 = new JsonObject();
        impressionDict2.addProperty("sdkVersion", SuperAwesome.getInstance().getSDKVersion());
        impressionDict2.addProperty("rnd", SAURLUtils.getCacheBuster());
        impressionDict2.addProperty("data", SAURLUtils.encodeDictAsJsonDict(impressionDict1));
        ad.creative.viewableImpressionURL = SuperAwesome.getInstance().getBaseURL() + "/event?" + SAURLUtils.formGetQueryFromDict(impressionDict2);

        /** create the click URL */
        switch (ad.creative.format){
            /** simple image case */
            case image:{
                ad.creative.fullClickURL = ad.creative.trackingURL + "&redir=" + ad.creative.clickURL;
                ad.creative.isFullClickURLReliable = true;
                ad.adHTML = SAHTMLParser.formatCreativeDataIntoAdHTML(ad);
                listener.parsedAd(ad);
                break;
            }
            /**
             * simple video case here - the burden will be on the vast player to deal with all
             * the complexities of vast stuff
             * */
            case video:{
                listener.parsedAd(ad);
                break;
            }
            /** the same - rich media and tag */
            case rich:
            case tag:{
                if (ad.creative.clickURL != null && SAURLUtils.isValidURL(ad.creative.clickURL)){
                    ad.creative.fullClickURL = ad.creative.trackingURL + "&redir=" + ad.creative.clickURL;
                    ad.creative.isFullClickURLReliable = true;
                } else {
                    ad.creative.fullClickURL = null;
                    ad.creative.isFullClickURLReliable = false;
                }
                ad.adHTML = SAHTMLParser.formatCreativeDataIntoAdHTML(ad);
                listener.parsedAd(ad);
                break;
            }
            default:
                break;
        }
    }

}
