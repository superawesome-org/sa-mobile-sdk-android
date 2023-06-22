/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.saadloader.postprocessor;

import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Class that formats a specific HTML "website" for each type of ad that needs displaying.
 *  - For image ads a document with mostly an <img/> tag
 *  - For rich media ads a document with an <iframe> to load the content in
 *  - For external tags a div to write the tag in
 */
public class SAProcessHTML {

    /**
     * Method that loads a special HTML file and parse & format it so that later
     * on web views will be able to use it to display proper image data
     *
     * @param ad    ad data (as an SAAd object)
     * @return      the formatted HTML string to be used by a WebView
     */
    public static String formatCreativeIntoImageHTML(SAAd ad) {
        String htmlString = "<a href='_HREF_URL_' target='_blank'><img src='_IMAGE_URL_' width='100%' height='100%' style='object-fit: contain;'/></a>";

        if (ad.creative.details.image != null) {
            htmlString = htmlString.replace("_IMAGE_URL_", ad.creative.details.image);
        }

        if (ad.creative.clickUrl != null) {
            return htmlString
                .replace("_HREF_URL_", ad.creative.clickUrl);
        }
        else {
            return htmlString
                .replace("<a href='_HREF_URL_' target='_blank'>", "")
                .replace("</a>", "");
        }
    }

    /**
     * Method that loads a special HTML file and parse & format it so that later
     * on web views will be able to use it to display proper rich media data
     *
     * @param ad    ad data (as an SAAd object)
     * @return      the formatted HTML string to be used by a WebView
     */
    public static String formatCreativeIntoRichMediaHTML(SAAd ad, int random) {
        String jscode = "window.AAGlobalData = " +
            "{placement: " + ad.placementId +
            ", campaign: " + ad.campaignId +
            ", line_item: " + ad.lineItemId +
            ", creative: " + ad.creative.id +
            ", openRtbPartnerId: " + ad.openRTBPartnerId + "};";
        String htmlString = "<iframe style='padding:0; border:0;' width='100%' height='100%' src='_RICH_MEDIA_URL_' srcdoc='<script>" +
            jscode +
            "</script>'></iframe>";
        String richMediaURL = ad.creative.details.url +
            "?placement=" + ad.placementId +
            "&line_item=" + ad.lineItemId +
            "&creative=" + ad.creative.id +
            "&rnd=" + random;

        return htmlString.replace("_RICH_MEDIA_URL_", richMediaURL);
    }

    /**
     * Method that loads a special HTML file and parse & format it so that later
     * on web views will be able to use it to display proper tag data
     * @param ad    ad data (as an SAAd object)
     * @return      the formatted HTML string to be used by a WebView
     */
    public static String formatCreativeIntoTagHTML(SAAd ad) {
        String htmlString = "_TAGDATA_";

        String tagString = ad.creative.details.tag;
        if (ad.creative.clickUrl != null) {
            tagString = tagString
                .replace("[click]", ad.creative.clickUrl + "&redir=")
                .replace("[click_enc]", SAUtils.encodeURL(ad.creative.clickUrl));
        } else {
            tagString = tagString
                .replace("[click]", "")
                .replace("[click_enc]", "");
        }
        tagString = tagString.replace("[keywords]", "");
        tagString = tagString.replace("[timestamp]", "");
        tagString = tagString.replace("target=\"_blank\"", "");
        tagString = tagString.replace("“", "\"");

        return htmlString.replace("_TAGDATA_", tagString);
    }
}
