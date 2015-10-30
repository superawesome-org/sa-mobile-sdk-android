/**
 * @class: SALoader.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Loader;

/**
 * Imports needed for this implementation
 */
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import java.util.HashMap;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Network.SANetListener;
import tv.superawesome.sdk.data.Network.SANetwork;
import tv.superawesome.sdk.data.Parser.SAParser;
import tv.superawesome.sdk.data.Validator.SAValidator;

/**
 * This class gathers all the other parts of the "data" package and unifies the whole loading
 * experience for the user
 */
public class SALoader {

    /** the singleton SALoader instance */
    private static SALoader instance = new SALoader();

    /** make the constructor private so that this class cannot be instantiated */
    private SALoader(){}

    /** Get the only object available */
    public static SALoader getInstance(){
        return instance;
    }

    /**
     * @brief: publicly exposed function that pre-loads ads
     * @param placementId - the placement ID a user might want to preload an Ad for
     */
    public void preloadAd(int placementId) {

    }

    /**
     * @brief: the function that actually loads the Ad
     * @param placementId - the placement ID a user might want to preload an Ad for
     * @param listener - a reference to the listener
     */
    public void loadAd(final int placementId, final SALoaderListener listener){

        /** form the endpoint */
        String endpoint = SuperAwesome.getInstance().getBaseURL() + "/ad/" + placementId;
        JsonObject queryJson = new JsonObject();
        queryJson.addProperty("test", SuperAwesome.getInstance().isTestingEnabled());

        /** send a standard GET request */
        SANetwork.sendGET(endpoint, queryJson, new SANetListener() {
            @Override
            public void success(Object data) {

                System.out.println(data);

                /** form the json object to parse */
                JsonParser jsonParser = new JsonParser();
                JsonObject dataJson = (JsonObject)jsonParser.parse(data.toString());

                if (dataJson != null) {

                    /** use the SAParser class to parse the generic Json to SA SDK models */
                    SAAd ad = SAParser.parseAdWithMap(dataJson);
                    ad.placementId = placementId;
                    ad.creative = SAParser.parseCreativeWithMap(dataJson);
                    if (ad.creative != null) {
                        ad.creative.details = SAParser.parseDetailsWithMap(dataJson);
                    }

                    /** if everything is OK, validate Ad */
                    if (SAValidator.isAdDataValid(ad) == true ){

                        ad.adHTML = "";

                        if (listener != null) {
                            listener.didPreloadAd(ad, placementId);
                        }
                    }
                    else {
                        if (listener != null) {
                            listener.didFailToPreloadAd(placementId);
                        }
                    }
                }
            }

            @Override
            public void failure() {
                if (listener != null) {
                    listener.didFailToPreloadAd(placementId);
                }
            }
        });
    }
}
