/**
 * @class: SACreative.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Models;

/**
 * imports for this class
 */
import tv.superawesome.lib.sautils.*;

/**
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
    public String baseFormat;
    public SACreativeFormat format;

    /** the impression URL; not really useful */
    public String impressionURL;

    /** the viewable impression URL - used to send impression data to the Ad server */
    public String viewableImpressionURL;

    /**
     * the click URL taken from the Ad server - usually it's used in conjunction with the
     * tracking URL to form the complete URL
     * */
    public String clickURL;

    /** the tracking URL is used to send clicks to the Ad server */
    public String trackingURL;

    /**
     * the fullclick URL is usually trackingURL + clickURL
     * unless we're dealing with rich-media or 3rd party tags - which the ad server does not always
     * provide the correct clickURL and we have to obtain it at runtime from the WebView
     */
    public String fullClickURL;

    /**
     * says if the click URL is valid or not
     */
    public boolean isFullClickURLReliable;

    /**
     *
     */
    public String parentalGateClickURL;

    /**
     * here for completnes' sake - is only used by the AIR SDK (for now)
     */
    public String videoCompleteURL;

    /** must be always true for real ads */
    public boolean approved;

    /** pointer to a SADetails object containing even more creative information */
    public SADetails details;

    /** aux print function */
    public void print() {
        String printout = " \nCREATIVE:\n";
        printout += "\t creativeId: " + creativeId + "\n";
        printout += "\t name: " + name + "\n";
        printout += "\t cpm: " + cpm + "\n";
        printout += "\t baseFormat: " + baseFormat + "\n";
        printout += "\t format: " + format.toString() + "\n";
        printout += "\t impressionURL: " + impressionURL + "\n";
        printout += "\t viewableImpressionURL: " + viewableImpressionURL + "\n";
        printout += "\t clickURL: " + clickURL + "\n";
        printout += "\t trackingURL: " + trackingURL + "\n";
        printout += "\t fullClickURL: " + fullClickURL + "\n";
        printout += "\t isFullClickURLReliable: " + isFullClickURLReliable + "\n";
        printout += "\t parentalGateClickURL: " + parentalGateClickURL + "\n";
        printout += "\t approved: " + approved + "\n";
        SALog.Log(printout);
        details.print();
    }
}
