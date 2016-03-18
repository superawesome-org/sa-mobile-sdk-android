package tv.superawesome.lib.saevents;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.VideoView;

import org.json.JSONObject;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.models.SAAd;

//import com.moat.analytics.mobile.MoatFactory;
//import com.moat.analytics.mobile. NativeDisplayTracker;
//import com.moat.analytics.mobile.NativeVideoTracker;


/**
 * Class that sends events to the server (click, viewable impression, etc)
 */
public class SAEvents {

    /**
     * Static functions
     */
    private static boolean isSATrackingEnabled = true;
//    private static boolean isMoatTrackingEnabled = true;

    /**
     * Fire-and-forget event function
     * @param url - the event url to send the data to
     */
    public static void sendEventToURL(final String url) {
        if (!isSATrackingEnabled) return;

        SAUtils.asyncGet(url, new JSONObject(), new SAUtils.SANetListener() {
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

    /**
     * Wrapper around the singleton call - to keep the code uniform
     */
    public static void sendDisplayMoatEvent(Activity activity, View view, SAAd ad) {
//        if (!isMoatTrackingEnabled) return;
//        MoatEvents.getInstance().setActivity(activity);
//        MoatEvents.getInstance().sendDisplayMoatEvent(view, ad);
    }

    /**
     * Wrapper around the singleton call - to keep the code uniform
     */
    public static void sendVideoMoatEvent(Activity activity, VideoView video, MediaPlayer mp, SAAd ad){
//        if (!isMoatTrackingEnabled) return;
//        MoatEvents.getInstance().setActivity(activity);
//        MoatEvents.getInstance().sendVideoMoatEvent(video, mp, ad);
    }

    public static void sendVideoMoatComplete(SAAd ad) {
//        if (!isMoatTrackingEnabled) return;
//        MoatEvents.getInstance().sendVideoMoatComplete(ad);
    }

    public static void enableSATracking(){
        isSATrackingEnabled = true;
    }

    public static void disableSATracking() {
        isSATrackingEnabled = false;
    }

//    public static void enableMoatTracking() {
//        isMoatTrackingEnabled = true;
//    }
//
//    public static void disableMoatTracking() {
//        isMoatTrackingEnabled = false;
//    }

//    private static class MoatEvents {
//
//      /** Moat tracking hardcoded constants */
//      private final String MOAT_DISPLAY_PARTNER_CODE = "superawesomeinappdisplay731223424656";
//      private final String MOAT_VIDEO_PARTNER_CODE = "superawesomeinappvideo467548716573";
//
//        /** other variables */
//        private MoatFactory factory = null;
//        private HashMap<String, NativeVideoTracker> trackerDict;
//        private String displayCode = null;
//        private String videoCode = null;
//        private WeakReference<Activity> mActivityRef;
//
//        /** the singleton SuperAwesome instance */
//        private static MoatEvents instance = new MoatEvents();
//
//        /** make the constructor private so that this class cannot be instantiated */
//        private MoatEvents(){
//            trackerDict = new HashMap<>();
//            displayCode = MOAT_DISPLAY_PARTNER_CODE;
//            videoCode = MOAT_VIDEO_PARTNER_CODE;
//        }
//
//        protected void setActivity(Activity activity){
//            mActivityRef = new WeakReference<Activity> (activity);
//        }
//
//        /** Get the only object available */
//        public static MoatEvents getInstance(){
//            return instance;
//        }
//
//        /**
//         * Function that sends a moat tracking event
//         * @param view - the parent view
//         * @param ad - ad data to be sent
//         */
//        public void sendDisplayMoatEvent(View view, SAAd ad) {
//            /** check if moat is enabled */
//            if (!SuperAwesome.getInstance().isMoatTrackingEnabled()) {
//                return;
//            }
//
//            SALog.Log("Sending MOAT display event");
//
//            /** create factory */
//            factory = MoatFactory.create(mActivityRef.get());
//            NativeDisplayTracker moatDisplayTracker = factory.createNativeDisplayTracker(view, displayCode);
//
//            /** track data */
//            HashMap<String, String> adIds = new HashMap<String, String>();
//            adIds.put("moatClientLevel1", "SuperAwesome");
//            adIds.put("moatClientLevel2", "" + ad.campaignId);
//            adIds.put("moatClientLevel3", "" + ad.lineItemId);
//            adIds.put("moatClientLevel4", "" + ad.creative.creativeId);
//            adIds.put("moatClientSlicer1", "" + ad.app);
//            adIds.put("moatClientSlicer2", "" + ad.placementId);
//
//            /** track ads */
//            moatDisplayTracker.track(adIds);
//        }
//
//        /**
//         * Start sending video events
//         * @param video - the video view
//         * @param mp - the media player
//         * @param ad - ad data to send
//         */
//        public void sendVideoMoatEvent(VideoView video, MediaPlayer mp, SAAd ad){
//            /** check if moat is enabled */
//            if (!SuperAwesome.getInstance().isMoatTrackingEnabled()) {
//                return;
//            }
//
//            SALog.Log("Sending MOAT video event");
//
//            /** create video tracker object */
//            factory = MoatFactory.create(mActivityRef.get());
//            NativeVideoTracker moatVideoTracker = factory.createNativeVideoTracker(videoCode);
//
//            /** track data */
//            HashMap<String, String> adIds = new HashMap<String, String>();
//            adIds.put("moatClientLevel1", "SuperAwesome");
//            adIds.put("moatClientLevel2", "" + ad.campaignId);
//            adIds.put("moatClientLevel3", "" + ad.lineItemId);
//            adIds.put("moatClientLevel4", "" + ad.creative.creativeId);
//            adIds.put("moatClientSlicer1", "" + ad.app);
//            adIds.put("moatClientSlicer2", "" + ad.placementId);
//            moatVideoTracker.trackVideoAd(adIds, mp, video);
//
//            /** add the moat video tracker */
//            trackerDict.put("video_ad_tracker_" + ad.placementId, moatVideoTracker);
//        }
//
//        /**
//         * Send the Video complete event and remove the tracker from the dict
//         * @param ad
//         */
//        public void sendVideoMoatComplete(SAAd ad){
//            /** check if moat is enabled */
//            if (!SuperAwesome.getInstance().isMoatTrackingEnabled()) {
//                return;
//            }
//
//            SALog.Log("Sending MOAT video completed event");
//
//            /** go on */
//            HashMap<String, Object> params = new HashMap<String, Object>();
//            params.put("type", "AdVideoComplete");
//            NativeVideoTracker tracker = trackerDict.get("video_ad_tracker_" + ad.placementId);
//
//            if (tracker != null) {
//                tracker.dispatchEvent(params);
//                trackerDict.remove("video_ad_tracker_" + ad.placementId);
//            }
//        }
//    }
}
