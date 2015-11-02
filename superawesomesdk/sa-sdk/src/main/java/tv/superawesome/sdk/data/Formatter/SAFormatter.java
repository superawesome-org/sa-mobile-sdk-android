/**
 * @class: SAFormatter.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Formatter;

/**
 * Imports needed for the class implementation
 */
import tv.superawesome.sdk.data.Models.SACreative;

/**
 * Class that formats HTML based on Ad format
 */
public class SAFormatter {

    /**
     * @brief: This static function uses a creative's data to return a formatted HTML string
     * @param creative - the creative
     * @return - a HTML string for the Ad to be rendered
     */
    public static String formatCreativeDataIntoAdHTML(SACreative creative) {
        /**
         * based on the creative format, return the appropriate function
         * and leave the implementation details to each private function in part
         */
        switch (creative.format) {
            case image_with_link:{
                return SAFormatter.formatCreativeIntoImageHTML(creative);
                break;;
            }
            case video:{
                return SAFormatter.formatCreativeIntoVideoHTML(creative);
                break;;
            }
            case rich_media:{
                return SAFormatter.formatCreativeIntoRichMediaHTML(creative);
                break;;
            }
            case rich_media_resizing:{
                return SAFormatter.formatCreativeIntoRichMediaResizingHTML(creative);
                break;;
            }
            case tag:{
                return SAFormatter.formatCreativeIntoTagHTML(creative);
                break;
            }
            case swf:{
                return SAFormatter.formatCreativeIntoSWFHTML(creative);
                break;;
            }
        }
    }

    /**
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper image data
     * @param creative - creative data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoImageHTML(SACreative creative) {
        return "";
    }

    /**
     * @brief: This function is @deprectated. Video is handled by Google-IMA
     * @param creative - the creative to return
     * @return
     */
    private static String formatCreativeIntoVideoHTML(SACreative creative){
        return "";
    }

    /**
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper rich media data
     * @param creative - creative data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoRichMediaHTML(SACreative creative) {
        return "";
    }

    /**
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper rich media resizing data
     * @param creative - creative data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoRichMediaResizingHTML(SACreative creative) {
        return "";
    }

    /**
     * @brief: This function is @deprectated. SWF is not handled by AwesomeAds anymore
     * @param creative - creative data
     * @return - the formatted HTML string to be used by a WebView
     */
    private static String formatCreativeIntoSWFHTML(SACreative creative) {
        return "";
    }

    /**
     * @brief: load a special HTML file and parse & format it so that later on webviews will be
     * able to use it to display proper tag data
     * @param creative - creative data
     * @return - the formatted HTML string to be used by a WebView
     */
    private  static String formatCreativeIntoTagHTML(SACreative creative) {
        return "";
    }
}
