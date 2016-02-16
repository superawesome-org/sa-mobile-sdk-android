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

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.lib.sanetwork.*;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Parser.SAParser;
import tv.superawesome.sdk.data.Validator.SAValidator;

/**
 * This class gathers all the other parts of the "data" package and unifies the whole loading
 * experience for the user
 */
public class SALoader {

    /**
     * the function that actually loads the Ad
     * @param placementId - the placement ID a user might want to preload an Ad for
     * @param listener - a reference to the listener
     */
    public void loadAd(final int placementId, final SALoaderListener listener){

        /** form the endpoint */
        String endpoint = SuperAwesome.getInstance().getBaseURL() + "/ad/" + placementId;
        JSONObject queryJson = new JSONObject();
        try {
            queryJson.put("test", SuperAwesome.getInstance().isTestingEnabled());
            queryJson.put("sdkVersion", SuperAwesome.getInstance().getSDKVersion());
            queryJson.put("rnd", SAURLUtils.getCacheBuster());
            if (SuperAwesome.getInstance().getDAUID() != 0) {
                queryJson.put("dauid", SuperAwesome.getInstance().getDAUID());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** send a standard GET request */
        SANetwork network = new SANetwork();
        network.sendGET(endpoint, queryJson, new SANetListener() {
            @Override
            public void success(final Object data) {

                /** form the json object to parse */
                JSONObject dataJson = null;
                try {
                    dataJson = new JSONObject(data.toString());

                    SAAd parsedAd = SAParser.parseDictionaryIntoAd(dataJson, placementId);
                    boolean isValid = SAValidator.isAdDataValid(parsedAd);

                    if (isValid) {

                        parsedAd.adJson = data.toString();

                        if (listener != null) {
                            listener.didLoadAd(parsedAd);
                        }
                    } else {
                        failAd(listener, placementId);
                    }
                } catch (JSONException e) {
                    failAd(listener, placementId);
                }
            }

            @Override
            public void failure() {
                failAd(listener, placementId);
            }
        });
    }

    /**
     * Common failure function for all the myriad ways the class can fail
     * @param _listener
     * @param placementId
     */
    private void failAd(SALoaderListener _listener, int placementId){
        if (_listener != null){
            _listener.didFailToLoadAdForPlacementId(placementId);
        }
    }
}
