package tv.superawesome.lib.saevents;

import org.json.JSONObject;

import tv.superawesome.lib.sautils.SANetInterface;
import tv.superawesome.lib.sautils.SANetwork;


/**
 * Class that sends events to the server (click, viewable impression, etc)
 */
public class SAEvents {

    /**
     * Static functions
     */
    private static boolean isSATrackingEnabled = true;

    /**
     * Fire-and-forget event function
     *
     * @param url - the event url to send the data to
     */
    public static void sendEventToURL(final String url) {
        if (!isSATrackingEnabled) return;

        SANetwork network = new SANetwork();
        network.asyncGet(url, new JSONObject(), new SANetInterface() {
            @Override
            public void success(Object data) {
                /** do nothing */
            }

            @Override
            public void failure() {
                /** do nothing */
            }
        });
    }

    public static void enableSATracking() {
        isSATrackingEnabled = true;
    }

    public static void disableSATracking() {
        isSATrackingEnabled = false;
    }
}
