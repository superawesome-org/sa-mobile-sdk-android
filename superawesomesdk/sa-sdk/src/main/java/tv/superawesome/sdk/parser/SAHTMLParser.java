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
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.models.SAAd;

/**
 * Created by gabriel.coman on 10/12/15.
 */
public class SAHTMLParser {

    private static final String imageHTML = "<!DOCTYPE html><html><head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no\"/><title>SuperAwesome Image Template</title>\n" +
            "    <style>\n" +
            "        html, body { box-sizing: border-box; width: 100%; height: 100%; padding: 0px; margin: 0px; overflow: hidden; }\n" +
            "        img#sa_image { width: _WIDTH_px; height: _HEIGHT_px; border: 0; -webkit-transform-origin: 0 0; -webkit-transform: scale(_SCALE_); position:absolute; }\n" +
            "    </style></head><body><a href='hrefURL' target=\"_blank\"><img id='sa_image' src='imageURL'/></a></body></html>";
    private static final String richMediaHTML = "<!DOCTYPE html><html><head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no\"/><title>SuperAwesome Rich Media Template</title>\n" +
            "    <style>\n" +
            "        html, body { box-sizing: border-box; width: 100%; height: 100%; padding: 0px; margin: 0px; overflow: hidden; }\n" +
            "        iframe { width: _WIDTH_px; height: _HEIGHT_px; border: 0; -webkit-transform-origin: 0 0; -webkit-transform: scale(_SCALE_); position:absolute; }\n" +
            "    </style>\n" +
            "</head><body><iframe src='richMediaURL'></iframe></body></html>";
    private static final String tagHTML = "<!DOCTYPE html><html><head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no\"/><title>SuperAwesome 3rd Party Tag Template</title>\n" +
            "    <style>\n" +
            "        html, body { box-sizing: border-box; width: 100%; height: 100%; padding: 0px; margin: 0px; overflow: hidden; }\n" +
            "        div#inner { width: _WIDTH_px; height: _HEIGHT_px; border: 0; -webkit-transform-origin: 0 0; -webkit-transform: scale(_SCALE_); position:absolute; }\n" +
            "    </style>\n" +
            "</head><body><div id=\"inner\">tagdata</div></body></html>";

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
        String htmlString = imageHTML;
        String click = (ad.creative.clickURL != null ? ad.creative.clickURL : ad.creative.trackingURL);
        htmlString = htmlString.replace("hrefURL", click);
        htmlString = htmlString.replace("imageURL", ad.creative.details.image);
        return htmlString;
    }

    /**
     * load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper rich media data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoRichMediaHTML(SAAd ad) {
        String htmlString = richMediaHTML;
        String richMediaURL = ad.creative.details.url +
                "?placement=" + ad.placementId +
                "&line_item=" + ad.lineItemId +
                "&creative=" + ad.creative.creativeId +
                "&rnd=" + SAUtils.getCacheBuster();

        return htmlString.replace("richMediaURL", richMediaURL);
    }

    /**
     * load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper tag data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private  static String formatCreativeIntoTagHTML(SAAd ad) {
        String htmlString = tagHTML;

        String tagString = ad.creative.details.tag;
        tagString = tagString.replace("[click]", ad.creative.trackingURL + "&redir=");
        try {
            tagString = tagString.replace("[click_enc]", SAUtils.encodeURL(ad.creative.trackingURL));
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        tagString = tagString.replace("[keywords]", "");
        tagString = tagString.replace("[timestamp]", "");
        tagString = tagString.replace("target=\"_blank\"", "");
        tagString = tagString.replace("“", "\"");

        return htmlString.replace("tagdata", tagString);
    }
}
