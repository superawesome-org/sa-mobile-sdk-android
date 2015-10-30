/**
 * @class: SACreative.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Models;

/**
 * @brief:
 * The creative contains essential ad information like format, click url
 * and such
 */
public class SACreative {

    /** the creative ID is a unique ID associated by the server with this Ad */
    public int creativeId;

    /** name of the creative - set by the user in the dashboard */
    public String name;

    /** agreed upon CPM; not really a useful field */
    public int cpm;

    /**
     * the creative format defines the type of ad (image, video, rich media, tag, etc)
     * and is an enum defined in SACreativeFormat.h
     */
    public SACreativeFormat format;

    /** the impression URL; not really useful */
    public String impressionURL;

    /** the click URL; used by the SDK to point users to the webpage behind the ad */
    public String clickURL;

    /** must be always true for real ads */
    public boolean approved;

    /** pointer to a SADetails object containing even more creative information */
    public SADetails details;
}
