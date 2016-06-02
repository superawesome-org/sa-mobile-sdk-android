package tv.superawesome.lib.saevents;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.moat.analytics.mobile.MoatFactory;
import com.moat.analytics.mobile.NativeDisplayTracker;
import com.moat.analytics.mobile.NativeVideoTracker;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import tv.superawesome.sdk.models.SAAd;

/**
 * Created by gabriel.coman on 01/06/16.
 */
public class SAMoatEvents {

    /** Moat tracking hardcoded constants */
    private final String MOAT_DISPLAY_PARTNER_CODE = "superawesomeinappdisplay731223424656";
    private final String MOAT_VIDEO_PARTNER_CODE = "superawesomeinappvideo467548716573";

    /** other variables */
    private MoatFactory factory = null;
    private HashMap<String, NativeVideoTracker> trackerDict;

    /** the singleton SuperAwesome instance */
    private static SAMoatEvents instance = new SAMoatEvents();

    /** make the constructor private so that this class cannot be instantiated */
    private SAMoatEvents(){
        trackerDict = new HashMap<>();
    }

    /** Get the only object available */
    public static SAMoatEvents getInstance(){
        return instance;
    }

    /**
     * Function that sends a moat tracking event
     * @param view - the parent view
     * @param ad - ad data to be sent
     */
    public void sendDisplayMoatEvent(Activity activity, View view, SAAd ad) {

        /** create factory */
        factory = MoatFactory.create(activity);
        NativeDisplayTracker moatDisplayTracker = factory.createNativeDisplayTracker(view, MOAT_DISPLAY_PARTNER_CODE);

        /** track data */
        HashMap<String, String> adIds = new HashMap<String, String>();
        adIds.put("moatClientLevel1", "SuperAwesome");
        adIds.put("moatClientLevel2", "" + ad.campaignId);
        adIds.put("moatClientLevel3", "" + ad.lineItemId);
        adIds.put("moatClientLevel4", "" + ad.creative.id);
        adIds.put("moatClientSlicer1", "" + ad.app);
        adIds.put("moatClientSlicer2", "" + ad.placementId);

        /** track ads */
        moatDisplayTracker.track(adIds);

        Log.d("SuperAwesome", "[AA :: Info] Register Moat Display Event");
    }

    /**
     * Start sending video events
     * @param video - the video view
     * @param mp - the media player
     * @param ad - ad data to send
     */
    public void sendVideoMoatEvent(Activity activity, VideoView video, MediaPlayer mp, SAAd ad){

        /** create video tracker object */
        factory = MoatFactory.create(activity);
        NativeVideoTracker moatVideoTracker = factory.createNativeVideoTracker(MOAT_VIDEO_PARTNER_CODE);

        /** track data */
        HashMap<String, String> adIds = new HashMap<String, String>();
        adIds.put("moatClientLevel1", "SuperAwesome");
        adIds.put("moatClientLevel2", "" + ad.campaignId);
        adIds.put("moatClientLevel3", "" + ad.lineItemId);
        adIds.put("moatClientLevel4", "" + ad.creative.id);
        adIds.put("moatClientSlicer1", "" + ad.app);
        adIds.put("moatClientSlicer2", "" + ad.placementId);
        moatVideoTracker.trackVideoAd(adIds, mp, video);

        /** add the moat video tracker */
        trackerDict.put("video_ad_tracker_" + ad.placementId, moatVideoTracker);

        Log.d("SuperAwesome", "[AA :: Info] Register Moat Video Event");
    }

    /**
     * Send the Video complete event and remove the tracker from the dict
     * @param ad
     */
    public void sendVideoMoatComplete(SAAd ad){

        /** go on */
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("type", "AdVideoComplete");
        NativeVideoTracker tracker = trackerDict.get("video_ad_tracker_" + ad.placementId);

        Log.d("SuperAwesome", "[AA :: Info] Remove Moat Video Event");

        if (tracker != null) {
            tracker.dispatchEvent(params);
            trackerDict.remove("video_ad_tracker_" + ad.placementId);
        }
    }
}
