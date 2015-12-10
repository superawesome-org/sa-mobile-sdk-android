/**
 * @class: SAHTMLParser.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 10/12/2015
 *
 */
package tv.superawesome.sdk.data.Parser;

/**
 * Imports needed for this class
 */
import tv.superawesome.sdk.data.Models.SAAd;

/**
 * Created by gabriel.coman on 10/12/15.
 */
public class SAHTMLParser {

    /**
     * @brief: This static function uses ad data to return a formatted HTML string
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
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper image data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoImageHTML(SAAd ad) {
        return "";
    }

    /**
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper rich media data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoRichMediaHTML(SAAd ad) {
        return "";
    }

    /**
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper tag data
     * @param ad - ad data
     * @return - the formatted HTML string to be used by a WebView
     */
    private  static String formatCreativeIntoTagHTML(SAAd ad) {
        return "";
    }
}
