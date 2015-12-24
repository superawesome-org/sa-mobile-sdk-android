/**
 * @class: SASender.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.lib.sanetwork;

/**
 * Imports needed to implement this class
 */
import com.google.gson.JsonObject;

import tv.superawesome.lib.sautils.SALog;

/**
 * Class that sends events to the server (click, viewable impression, etc)
 */
public class SASender {

    /**
     * @brief: Fire-and-forget event function
     * @param url - the event url to send the data to
     */
    public static void sendEventToURL(final String url) {
        SANetwork.sendGET(url, new JsonObject(), new SANetListener() {
            @Override
            public void success(Object data) {
                SALog.Log("Success " + url);
            }

            @Override
            public void failure() {
                // do nothing
            }
        });
    }
}
