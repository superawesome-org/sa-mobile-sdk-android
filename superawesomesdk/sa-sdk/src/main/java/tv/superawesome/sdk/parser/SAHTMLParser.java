/**
 * @class: SAHTMLParser.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 10/12/2015
 *
 */
package tv.superawesome.sdk.parser;

/**
 * Imports needed for this class
 */

import java.io.IOException;
import java.net.URISyntaxException;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.models.SAAd;

/**
 * Created by gabriel.coman on 10/12/15.
 */
public class SAHTMLParser {

    /**
     * This static function uses ad data to return a formatted HTML string
     * @param ad - the ad
     * @return - a HTML string for the Ad to be rendered
     */
    public static String formatCreativeDataIntoAdHTML(SAAd ad) {
        /**
         * based on the creative format, return the appropriate function
         * and leave the implementation details to each private function in part
         */
        switch (ad.creative.format) {
            case invalid:{
                return null;
            }
            case image:{
                return SAHTMLParser.formatCreativeIntoImageHTML(ad);
            }
            case video:{
                return null;
            }
            case rich:{
                return SAHTMLParser.formatCreativeIntoRichMediaHTML(ad);
            }
            case tag:{
                return SAHTMLParser.formatCreativeIntoTagHTML(ad);
            }
            default:{
                return null;
            }
        }
    }

    /**
     * load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper image data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoImageHTML(SAAd ad) {
        try {
            String htmlString = SAUtils.openAssetAsString("html/displayImage.html");
            htmlString = htmlString.replace("hrefURL", ad.creative.clickURL);
            htmlString = htmlString.replace("imageURL", ad.creative.details.image);
            return htmlString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper rich media data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoRichMediaHTML(SAAd ad) {
        try {
            String htmlString = SAUtils.openAssetAsString("html/displayRichMedia.html");

            String richMediaURL = ad.creative.details.url +
            "?placement=" + ad.placementId +
            "&line_item=" + ad.lineItemId +
            "&creative=" + ad.creative.creativeId +
            "&rnd=" + SAUtils.getCacheBuster();

            return htmlString.replace("richMediaURL", richMediaURL);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper tag data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private  static String formatCreativeIntoTagHTML(SAAd ad) {
        try {
            String htmlString = SAUtils.openAssetAsString("html/displayTag.html");

            String tagString = ad.creative.details.tag;
            tagString = tagString.replace("[click]", ad.creative.trackingURL + "&redir=");
            try {
                tagString = tagString.replace("[click_enc]", SAUtils.encodeURL(ad.creative.trackingURL));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            tagString = tagString.replace("[keywords]", "");
            tagString = tagString.replace("[timestamp]", "");
            tagString = tagString.replace("target=\"_blank\"", "");
            tagString = tagString.replace("“", "\"");

            return htmlString.replace("tagdata", tagString);
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }
}
