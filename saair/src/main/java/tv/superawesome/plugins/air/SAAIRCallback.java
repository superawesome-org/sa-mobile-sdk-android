package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class SAAIRCallback {


    public static void sendToAIR (FREContext context, JSONObject data) {
        if (context != null && data != null) {

            String packageString = data.toString();
            context.dispatchStatusEventAsync(packageString, "");

        }
    }

    public static void sendAdCallback(FREContext context, String name, int placementId, String callback) {

        JSONObject data = SAJsonParser.newObject(new Object[] {
                "name", name,
                "placementId", placementId,
                "callback", callback
        });
        sendToAIR(context, data);

    }

    public static void sendCPICallback (FREContext context, String name, boolean success, String callback) {

        JSONObject data = SAJsonParser.newObject(new Object[] {
                "name", name,
                "success", success,
                "callback", callback
        });
        sendToAIR(context, data);

    }

}
