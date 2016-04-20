/**
 * @class: SALoader.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.loader;

/**
 * Imports needed for this implementation
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.lib.sautils.*;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.parser.SAParser;

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
        final String endpoint = SuperAwesome.getInstance().getBaseURL() + "/ad/" + placementId;
        JSONObject queryJson = new JSONObject();
        try {
            queryJson.put("test", SuperAwesome.getInstance().isTestingEnabled());
            queryJson.put("sdkVersion", SuperAwesome.getInstance().getSDKVersion());
            queryJson.put("rnd", SAUtils.getCacheBuster());
            queryJson.put("bundle", SuperAwesome.getInstance().getApplicationContext().getPackageName());
            queryJson.put("name", SAUtils.getAppLabel());
            queryJson.put("dauid", SuperAwesome.getInstance().getDAUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** send a standard GET request */
        SANetwork network = new SANetwork();
        network.asyncGet(endpoint, queryJson, new SANetwork.SANetListener() {
            @Override
            public void success(final Object data) {
                /** edge null case (no network, etc) */
                if (data == null) {
                    failAd(listener, placementId);
                    return;
                }

                /** form the json object to parse */
                JSONObject dataJson = null;
                try {
                    dataJson = new JSONObject(data.toString());
                    SAAd parsedAd = SAParser.parseDictionaryIntoAd(dataJson, placementId);

                    /** success case */
                    if (parsedAd != null) {
                        parsedAd.adJson = data.toString();
                        SALoaderExtra extra = new SALoaderExtra();
                        extra.getExtraData(parsedAd, new SALoaderExtraListener() {
                            @Override
                            public void extraDone(SAAd finalAd) {
                                if (listener != null) {
                                    listener.didLoadAd(finalAd);
                                }
                            }
                        });
                    }
                    /** error case */
                    else {
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
