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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.lib.sanetwork.*;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Parser.SAParser;
import tv.superawesome.sdk.data.Parser.SAParserListener;
import tv.superawesome.sdk.data.Validator.SAValidator;

/**
 * This class gathers all the other parts of the "data" package and unifies the whole loading
 * experience for the user
 */
public class SALoader {

    /**
     * @brief: the function that actually loads the Ad
     * @param placementId - the placement ID a user might want to preload an Ad for
     * @param listener - a reference to the listener
     */
    public static void loadAd(final int placementId, final SALoaderListener listener){

        /** form the endpoint */
        String endpoint = SuperAwesome.getInstance().getBaseURL() + "/ad/" + placementId;
        JsonObject queryJson = new JsonObject();
        queryJson.addProperty("test", SuperAwesome.getInstance().isTestingEnabled());
        queryJson.addProperty("sdkVersion", SuperAwesome.getInstance().getSDKVersion());
        queryJson.addProperty("rnd", SAURLUtils.getCacheBuster());

        /** send a standard GET request */
        SANetwork.sendGET(endpoint, queryJson, new SANetListener() {
            @Override
            public void success(Object data) {

                System.out.println(data);

                /** form the json object to parse */
                JsonParser jsonParser = new JsonParser();
                JsonObject dataJson = (JsonObject)jsonParser.parse(data.toString());

                if (dataJson != null) {
                   SAParser.parseDictionaryIntoAd(dataJson, placementId, new SAParserListener() {
                       @Override
                       public void parsedAd(SAAd ad) {
                           boolean isValid = SAValidator.isAdDataValid(ad);

                           if (ad != null) {
                               ad.print();
                           } else  {
                               SALog.Log("Ad " + placementId + " is empty");
                           }

                           if (isValid){
                               if (listener != null){
                                   listener.didLoadAd(ad);
                               }
                           } else {
                               if (listener != null){
                                   listener.didFailToLoadAdForPlacementId(placementId);
                               }
                           }
                       }
                   });

                }
                /** case json had some kind of error */
                else {
                    if (listener != null){
                        listener.didFailToLoadAdForPlacementId(placementId);
                    }
                }
            }

            @Override
            public void failure() {
                if (listener != null) {
                    listener.didFailToLoadAdForPlacementId(placementId);
                }
            }
        });
    }
}
