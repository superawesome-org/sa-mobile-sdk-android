/**
 * @class: SASender.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Sender;

/**
 * Imports needed to implement this class
 */
import com.google.gson.JsonObject;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Network.SANetListener;
import tv.superawesome.sdk.data.Network.SANetwork;
import tv.superawesome.sdk.data.Parser.SASerializer;

/**
 * Class that sends events to the server (click, viewable impression, etc)
 */
public class SASender {

    /**
     * @brief: Function that posts a generic event to the Ad server
     * @param endpoing - the endpoint
     * @param type - the event type
     * @param ad - the ad from which valuable event-specific data will be taken
     */
    private static void postGenericEvent(String endpoint, SAEventType type, SAAd ad) {

        /** prepare the URL */
        String url = SuperAwesome.getInstance().getBaseURL() + "/" + endpoint;

        /** prepare data to send */
        JsonObject dict = SASerializer.serializeAdEssentials(ad);

        /** add type */
        if (type != SAEventType.NoAd) {
            if (type == SAEventType.viewable_impression) {
                dict.addProperty("type", SAEventType.viewable_impression.toString());
            } else {
                dict.addProperty("type", "custom" + type.toString());
            }
        }

        /** add value */
        if (ad.creative.details.value > 0) {
            JsonObject minidict = new JsonObject();
            minidict.addProperty("value", ad.creative.details.value);
            dict.add("details", minidict);
        }

        /** send post */
        SANetwork.sendPOST(endpoint, dict, null);
    }

    /**
     * @brief: post viewable_impression event to the Ad server - when an Ad is seen
     * @param ad - the ad data
     */
    public static void postEventViewableImpression(SAAd ad) {
        SASender.postGenericEvent("event", SAEventType.viewable_impression, ad);
    }

    /**
     * @brief: post custom ad failed event to the Ad server
     * @param ad - the ad data
     */
    public static void postEventAdFailedToView(SAAd ad) {
        SASender.postGenericEvent("event", SAEventType.AdFailedToView, ad);
    }

    /**
     * @brief: post custom ad rate event
     * @param ad - the ad data
     * @param value - the rating associated with the ad
     */
    public static void postEventAdRate(SAAd ad, int value) {
        SAAd _ad = ad;
        _ad.creative.details.value = value;
        SASender.postGenericEvent("event", SAEventType.AdRate, _ad);
    }

    /**
     * @brief: post custom parental gate canceled event
     * @param ad - the ad data
     */
    public static void postEventPGCancel(SAAd ad) {
        SASender.postGenericEvent("event", SAEventType.AdPGCancel, ad);
    }

    /**
     * @brief: post custom parental gate success event
     * @param ad - the ad data
     */
    public static void postEventPGSuccess(SAAd ad) {
        SASender.postGenericEvent("event", SAEventType.AdPGSuccess, ad);
    }

    /**
     * @brief: post custom parental gate error event
     * @param ad - the ad data
     */
    public static void postEventPGError(SAAd ad) {
        SASender.postGenericEvent("event", SAEventType.AdPGError, ad);
    }

    /**
     * @brief: post custom parental gate click event
     * @param ad - the ad data
     */
    public static void postEventClick(SAAd ad) {

        /** send URL */
        String url = SuperAwesome.getInstance().getBaseURL() + "/click";
        JsonObject jsondata = SASerializer.serializeAdEssentials(ad);

        /** send GET for click */
        SANetwork.sendGET(url, jsondata, null);
    }
}
