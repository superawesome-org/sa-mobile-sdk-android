package tv.superawesome.lib.saevents;

import android.app.Application;
import android.util.Log;
import android.webkit.WebView;
import android.widget.VideoView;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sautils.SAUtils;

public class SAMoatModule {

    private static final String TAG = "SuperAwesome-Moat-Module";
    private static final String kMoatClass = "tv.superawesome.plugins.publisher.moat.SAMoatEvents";

    // boolean mostly used for tests, in order to not limit moat at all
    private boolean   moatLimiting = true;

    // a moat object
    private Class<?>  moatClass = null;
    private Object    moatInstance = null;

    // the ad object
    private final SAAd      ad;

    public SAMoatModule (SAAd ad, boolean doLog) {

        MyLog.mDoLog = doLog;

        // save the ad
        this.ad = ad;

        // create the moat class
        if (SAUtils.isClassAvailable(kMoatClass)) try {

            moatClass = Class.forName(kMoatClass);
            Constructor<?> moatConstructor = moatClass.getConstructor();
            moatInstance = moatConstructor.newInstance();
            MyLog.d(TAG, "Created SA Moat class instance " + moatInstance);
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not create SA Moat class instance because " + e.getMessage());
        } else {
            MyLog.e(TAG, "Could not create SA Moat class instance because " + kMoatClass + " is not available");
        }
    }

    public static void initMoat (Application application, boolean loggingEnabled) {
        if (SAUtils.isClassAvailable(kMoatClass)) try {
            Class<?> moatCls = Class.forName(kMoatClass);
            java.lang.reflect.Method method = moatCls.getMethod("initMoat", Application.class, boolean.class);
            method.invoke(moatCls, application, loggingEnabled);
            MyLog.d(TAG, "Initialised Moat instance successfully");
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not init Moat instance because " + e.getMessage());
        } else {
            MyLog.e(TAG,"Could not init Moat instance because " + kMoatClass + " is not available");
        }
    }

    /**
     * Method that determines if Moat is allowed.
     * Conditions are:
     *  - that the ad should be not null
     *  - that moatLimiting should be enabled
     *  - if it's enabled, the random moat number should be smaller than
     *    the moat threshold of the ad
     *
     * @return true or false
     */
    public boolean isMoatAllowed () {
        // here calc if moat should be displayed
        int moatIntRand = SAUtils.randomNumberBetween(0, 100);
        double moatRand = moatIntRand / 100.0;
        boolean response = ad != null && (moatRand < ad.moat || !moatLimiting);
        try {
            MyLog.i(TAG, "Is Moat allowed: moatRand="+moatRand + " | ad.moat="+ad.moat + " | moatLimiting="+moatLimiting + " | response="+response);
        } catch (Exception e) {
            MyLog.i(TAG, "Is Moat allowed: moatRand="+moatRand + " | ad.moat=null | moatLimiting="+moatLimiting + " | response="+response);
        }

        return response;
    }

    /**
     * Method that registers a Moat event object, according to the moat specifications
     *
     * @param view      the web view used by Moat to register events on (and that will contain
     *                  an ad at runtime)
     * @return          returns a MOAT specific string that will need to be inserted in the
     *                  web view so that the JS moat stuff works
     */
    public String startMoatTrackingForDisplay(WebView view) {

        boolean nullInstance = moatInstance != null;
        boolean isAllowed = isMoatAllowed();

        if (nullInstance && isAllowed) try {

            Map<String, String> adData = new HashMap<>();
            adData.put("advertiserId", "" + ad.advertiserId);
            adData.put("campaignId", "" + ad.campaignId);
            adData.put("lineItemId", "" + ad.lineItemId);
            adData.put("creativeId", "" + ad.creative.id);
            adData.put("app", "" + ad.appId);
            adData.put("placementId", "" + ad.placementId);
            adData.put("publisherId", "" + ad.publisherId);

            java.lang.reflect.Method method = moatClass.getMethod("startMoatTrackingForDisplay", WebView.class, Map.class);
            Object returnValue = method.invoke(moatInstance, view, adData);
            MyLog.d(TAG,"Called 'startMoatTrackingForDisplay' with response " + returnValue);
            return (String) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'startMoatTrackingForDisplay' because " + e.getMessage());
            return "";
        } else {
            MyLog.e(TAG, "Could not call 'startMoatTrackingForDisplay' because: Moat instance > " + nullInstance + " | isMoatAllowed > " + isAllowed);
            return "";
        }
    }

    /**
     * Unregister moat events for Display
     *
     * @return whether the removal was successful or not
     */
    public boolean stopMoatTrackingForDisplay() {

        if (moatInstance != null) try {

            java.lang.reflect.Method method = moatClass.getMethod("stopMoatTrackingForDisplay");
            Object returnValue = method.invoke(moatInstance);
            MyLog.d(TAG, "Called 'stopMoatTrackingForDisplay' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'stopMoatTrackingForDisplay' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'stopMoatTrackingForDisplay' because Moat instance is null");
            return false;
        }
    }

    public boolean startMoatTrackingForVideoPlayer(VideoView videoView, int duration, boolean isAllowed, boolean nullInstance){

        if (isAllowed && nullInstance) try {

            HashMap<String, String> adData = new HashMap<>();
            adData.put("advertiserId", "" + ad.advertiserId);
            adData.put("campaignId", "" + ad.campaignId);
            adData.put("lineItemId", "" + ad.lineItemId);
            adData.put("creativeId", "" + ad.creative.id);
            adData.put("app", "" + ad.appId);
            adData.put("placementId", "" + ad.placementId);
            adData.put("publisherId", "" + ad.publisherId);

            java.lang.reflect.Method method = moatClass.getMethod("startMoatTrackingForVideoPlayer", VideoView.class, Map.class, int.class);
            Object returnValue = method.invoke(moatInstance, videoView, adData, duration);
            MyLog.d(TAG,"Called 'startMoatTrackingForVideoPlayer' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'startMoatTrackingForVideoPlayer' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'startMoatTrackingForVideoPlayer' because: Moat instance > " + nullInstance + " | isMoatAllowed > " + isAllowed);
            return false;
        }
    }

    public boolean sendPlayingEvent (int position) {
        if (moatInstance != null) try {
            java.lang.reflect.Method method = moatClass.getMethod("sendPlayingEvent", int.class);
            Object returnValue = method.invoke(moatInstance, position);
            MyLog.d(TAG,"Called 'sendPlayingEvent' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'sendPlayingEvent' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'sendPlayingEvent' because Moat instance is null");
            return false;
        }
    }

    public boolean sendStartEvent (int position) {
        if (moatInstance != null) try {
            java.lang.reflect.Method method = moatClass.getMethod("sendStartEvent", int.class);
            Object returnValue = method.invoke(moatInstance, position);
            MyLog.d(TAG,"Called 'sendStartEvent' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'sendStartEvent' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'sendStartEvent' because Moat instance is null");
            return false;
        }
    }

    public boolean sendFirstQuartileEvent (int position) {
        if (moatInstance != null) try {
            java.lang.reflect.Method method = moatClass.getMethod("sendFirstQuartileEvent", int.class);
            Object returnValue = method.invoke(moatInstance, position);
            MyLog.d(TAG,"Called 'sendFirstQuartileEvent' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'sendFirstQuartileEvent' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'sendFirstQuartileEvent' because Moat instance is null");
            return false;
        }
    }

    public boolean sendMidpointEvent (int position) {
        if (moatInstance != null) try {
            java.lang.reflect.Method method = moatClass.getMethod("sendMidpointEvent", int.class);
            Object returnValue = method.invoke(moatInstance, position);
            MyLog.d(TAG,"Called 'sendMidpointEvent' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'sendMidpointEvent' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'sendMidpointEvent' because Moat instance is null");
            return false;
        }
    }

    public boolean sendThirdQuartileEvent (int position) {
        if (moatInstance != null) try {
            java.lang.reflect.Method method = moatClass.getMethod("sendThirdQuartileEvent", int.class);
            Object returnValue = method.invoke(moatInstance, position);
            MyLog.d(TAG,"Called 'sendThirdQuartileEvent' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'sendThirdQuartileEvent' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'sendThirdQuartileEvent' because Moat instance is null");
            return false;
        }
    }

    public boolean sendCompleteEvent (int position) {
        if (moatInstance != null) try {
            java.lang.reflect.Method method = moatClass.getMethod("sendCompleteEvent", int.class);
            Object returnValue = method.invoke(moatInstance, position);
            MyLog.d(TAG,"Called 'sendCompleteEvent' with response " + returnValue);
            return (Boolean) returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'sendCompleteEvent' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'sendCompleteEvent' because Moat instance is null");
            return false;
        }
    }

    /**
     * Method to unregister a Moat event for video
     *
     * @return whether the video moat event was killed off OK
     */
    public boolean stopMoatTrackingForVideoPlayer() {

        if (moatInstance != null) try {

            java.lang.reflect.Method method = moatClass.getMethod("stopMoatTrackingForVideoPlayer");
            Object returnValue = method.invoke(moatInstance);
            MyLog.d(TAG,"Called 'stopMoatTrackingForVideoPlayer' with response " + returnValue);
            return (Boolean) returnValue;

        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(TAG, "Could not call 'stopMoatTrackingForVideoPlayer' because " + e.getMessage());
            return false;
        } else {
            MyLog.e(TAG, "Could not call 'stopMoatTrackingForVideoPlayer' because Moat instance is null");
            return false;
        }
    }

    public void disableMoatLimiting () {
        moatLimiting = false;
    }

    private static class MyLog {

        static boolean mDoLog = true;

        static void d (String tag, String message) {
            if (mDoLog) {
                Log.d(tag, message);
            }
        }

        static void w (String tag, String message) {
            if (mDoLog) {
                Log.w(tag, message);
            }
        }

        static void i (String tag, String message) {
            if (mDoLog) {
                Log.i(tag, message);
            }
        }

        static void e (String tag, String message) {
            if (mDoLog) {
                Log.e(tag, message);
            }
        }
    }

    public boolean hasMoatInstance() {
        return moatInstance != null;
    }

    public boolean hasMoatClass() {
        return moatClass != null;
    }
}
