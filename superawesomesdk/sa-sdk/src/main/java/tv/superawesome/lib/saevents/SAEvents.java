package tv.superawesome.lib.saevents;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.VideoView;

import org.json.JSONObject;

import tv.superawesome.lib.sautils.SANetworkInterface;
import tv.superawesome.lib.sautils.SANetwork;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.models.SAAd;

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
        network.asyncGet(url, new JSONObject(), new SANetworkInterface() {
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

    public static void sendDisplayMoatEvent(Activity activity, View view, SAAd ad) {
        if (!SAUtils.isClassAvailable("com.moat.analytics.mobile.MoatFactory")) return;
        SAMoatEvents.getInstance().sendDisplayMoatEvent(activity, view, ad);
    }

    public static void sendVideoMoatEvent(Activity activity, VideoView video, MediaPlayer mp, SAAd ad){
        if (!SAUtils.isClassAvailable("com.moat.analytics.mobile.MoatFactory")) return;
        SAMoatEvents.getInstance().sendVideoMoatEvent(activity, video, mp, ad);
    }

    public static void sendVideoMoatComplete(SAAd ad) {
        if (!SAUtils.isClassAvailable("com.moat.analytics.mobile.MoatFactory")) return;
        SAMoatEvents.getInstance().sendVideoMoatComplete(ad);
    }
}
