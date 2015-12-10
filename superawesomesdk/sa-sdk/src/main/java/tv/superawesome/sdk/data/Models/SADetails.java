/**
 * @class: SACreative.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Models;

/**
 * Useful imports for this class
 **/
import tv.superawesome.sdk.aux.SALog;

/**
 * @brief:
 * The SADetails class contains fine grained information about the creative
 * of an ad (such as width, iamge, vast, tag, etc)
 * Depending on the format of the creative, some fields are essential,
 * and some are optional
 *
 * This dependency is regulated by SAValidator.h
 */
public class SADetails {

    /**
     * the width & height of the creative; can be applied to images, banners,
     * rich-media, etc
     * there are cases when this is 1 x 1 - which indicates a relative-size
     * creative
     */
    public int width;
    public int height;

    /** in case creative format is image_with_link, this is the URL of the image */
    public String image;

    /** name of the creative */
    public String name;

    /** in case creative format is video, this is the URL of the video to be streamed */
    public String video;

    /** in case creative format is video, this is the video bitrate */
    public int bitrate;

    /** in case creative format is video, this is the total duration */
    public int duration;

    /** in case creative format is video, this is the associated vast tag */
    public String vast;

    /** in case creative format is tag, this is the JS tag */
    public String tag;

    /**
     * in case creative format is rich media, this is the URL to the zip with all
     * media resources; at the moment it's not used, but could be used when doing
     * truly preloaded ads
     */
    public String zip;

    /** in case creative format is rich media, this is the URL of the rich media */
    public String url;

    /**
     * this is the placement format, defined in SAPlacementFormat.h
     * as of now, it's kind of useless
     */
    public String placementFormat;

    /** aux value needed when sending ad data like rating and such */
    public int value;

    /** aux print function */
    public void print() {
        String printout = "";
        printout += "\t\t width: " + width;
        printout += "\t\t height: " + height;
        printout += "\t\t image: " + image;
        printout += "\t\t name: " + name;
        printout += "\t\t video: " + video;
        printout += "\t\t bitrate: " + bitrate;
        printout += "\t\t duration: " + duration;
        printout += "\t\t vast: " + vast;
        printout += "\t\t tag: " + tag;
        printout += "\t\t placementFormat: " + placementFormat;
        printout += "\t\t zip: " + zip;
        printout += "\t\t url: " + url;
        printout += "\t\t value: " + value;
        SALog.Log(printout);
    }
}
