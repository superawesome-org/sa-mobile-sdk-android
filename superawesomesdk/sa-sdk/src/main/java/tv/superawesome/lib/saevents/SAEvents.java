package tv.superawesome.lib.saevents;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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
        if (!SAUtils.isClassAvailable("tv.superawesome.samoat.SAMoatEvents")) return;

        HashMap<String, String> adIds = new HashMap<String, String>();
        adIds.put("campaignId", "" + ad.campaignId);
        adIds.put("lineItemId", "" + ad.lineItemId);
        adIds.put("creativeId", "" + ad.creative.id);
        adIds.put("app", "" + ad.app);
        adIds.put("placementId", "" + ad.placementId);

        try {
            Class<?> moat = Class.forName("tv.superawesome.samoat.SAMoatEvents");
            java.lang.reflect.Method method = moat.getMethod("getInstance");
            Object moatInstance = method.invoke(moat);
            java.lang.reflect.Method method1 = moat.getMethod("sendDisplayMoatEvent", Activity.class, View.class, HashMap.class);
            method1.invoke(moatInstance, activity, view, adIds);
        } catch (ClassNotFoundException e) {
            // failure
        } catch (NoSuchMethodException e) {
            // failure
        } catch (InvocationTargetException e) {
            // failure
        } catch (IllegalAccessException e) {
            // failure;
        }
    }

    public static void sendVideoMoatEvent(Activity activity, VideoView video, MediaPlayer mp, SAAd ad){
        if (!SAUtils.isClassAvailable("tv.superawesome.samoat.SAMoatEvents")) return;

        HashMap<String, String> adIds = new HashMap<String, String>();
        adIds.put("campaignId", "" + ad.campaignId);
        adIds.put("lineItemId", "" + ad.lineItemId);
        adIds.put("creativeId", "" + ad.creative.id);
        adIds.put("app", "" + ad.app);
        adIds.put("placementId", "" + ad.placementId);

        try {
            Class<?> moat = Class.forName("tv.superawesome.samoat.SAMoatEvents");
            java.lang.reflect.Method method = moat.getMethod("getInstance");
            Object moatInstance = method.invoke(moat);
            java.lang.reflect.Method method1 = moat.getMethod("sendVideoMoatEvent", Activity.class, VideoView.class, MediaPlayer.class, HashMap.class);
            method1.invoke(moatInstance, activity, video, mp, adIds);
        } catch (ClassNotFoundException e) {
            // failure
        } catch (NoSuchMethodException e) {
            // failure
        } catch (InvocationTargetException e) {
            // failure
        } catch (IllegalAccessException e) {
            // failure;
        }
    }

    public static void sendVideoMoatComplete(SAAd ad) {
        if (!SAUtils.isClassAvailable("tv.superawesome.samoat.SAMoatEvents")) return;

        try {
            Class<?> moat = Class.forName("tv.superawesome.samoat.SAMoatEvents");
            java.lang.reflect.Method method = moat.getMethod("getInstance");
            Object moatInstance = method.invoke(moat);
            java.lang.reflect.Method method1 = moat.getMethod("sendVideoMoatComplete", String.class);
            method1.invoke(moatInstance, "" + ad.placementId);
        } catch (ClassNotFoundException e) {
            // failure
        } catch (NoSuchMethodException e) {
            // failure
        } catch (InvocationTargetException e) {
            // failure
        } catch (IllegalAccessException e) {
            // failure;
        }
    }
}
