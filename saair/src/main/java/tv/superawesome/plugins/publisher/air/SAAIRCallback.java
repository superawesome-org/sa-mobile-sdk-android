/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.air;

import com.adobe.fre.FREContext;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRCallback {


    /**
     * Method that tries to send back data to an Adobe AIR app
     *
     * @param context   current FREContext object
     * @param data      the data package
     */
    public static void sendToAIR (FREContext context, JSONObject data) {
        if (context != null && data != null) {

            String packageString = data.toString();
            context.dispatchStatusEventAsync(packageString, "");

        }
    }

    /**
     * Method that sends ad data back to an Adobe AIR app
     *
     * @param context       current FREContext object
     * @param name          name of the Ad to send back data to
     * @param placementId   the placement Id
     * @param callback      the callback name
     */
    public static void sendAdCallback(FREContext context, String name, int placementId, String callback) {

        JSONObject data = SAJsonParser.newObject(
                "name", name,
                "placementId", placementId,
                "callback", callback);
        sendToAIR(context, data);

    }
}
