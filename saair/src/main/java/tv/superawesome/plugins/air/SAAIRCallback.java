package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class SAAIRCallback {

    public static void sendToAIR(FREContext context, String name, int placementId, String callback) {

        if (context != null) {

            JSONObject data = SAJsonParser.newObject(new Object[] {
                    "name", name,
                    "placementId", placementId,
                    "callback", callback
            });

            String packageString = data.toString();

            context.dispatchStatusEventAsync(packageString, "");

        }
    }

}
