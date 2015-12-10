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
import tv.superawesome.sdk.data.Network.SANetListener;
import tv.superawesome.sdk.data.Network.SANetwork;

/**
 * Class that sends events to the server (click, viewable impression, etc)
 */
public class SASender {

    /**
     * @brief: Fire-and-forget event function
     * @param url - the event url to send the data to
     */
    public static void sendEventToURL(String url) {
        SANetwork.sendGET(url, new JsonObject(), new SANetListener() {
            @Override
            public void success(Object data) {
                // do nothing
            }

            @Override
            public void failure() {
                // do nothing
            }
        });
    }
}
