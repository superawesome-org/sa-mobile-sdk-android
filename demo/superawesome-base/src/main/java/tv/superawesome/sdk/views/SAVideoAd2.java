package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.HashMap;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerClickInterface;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerEvent;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerEventInterface;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Created by gabriel.coman on 26/08/16.
 */
public class SAVideoAd2 extends Activity implements SAViewInterface {

    private SALoader loader = null;
    private SAAd ad = null;
    private Context context = null;
    private SAInterface listener = null;
    private int ticks = 0;
    private int viewabilityCount = 0;
    private Runnable viewabilityRunnable = null;

    private boolean isParentalGateEnabled = false;
    private boolean shouldShowCloseButton = true;
    private boolean shouldAutomaticallyCloseAtEnd = true;
    private boolean shouldShowSmallClickButton = false;
    private boolean shouldLockOrientation = false;
    private int     lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public SAVideoAd2 () {
        initialize();
    }

    // main constructor
    public SAVideoAd2 (Context c) {
        context = c;
        initialize();
    }

    private void initialize () {
        loader = new SALoader();
    }

    public void setListener(SAInterface listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    public void setIsParentalGateEnabled (boolean value) {
        isParentalGateEnabled = value;
    }

    public void setShouldShowCloseButton (boolean value) {
        shouldShowCloseButton = value;
    }

    public void setShouldAutomaticallyCloseAtEnd (boolean value) {
        shouldAutomaticallyCloseAtEnd = value;
    }

    public void setShouldLockOrientation (boolean value) {
        shouldLockOrientation = value;
    }

    public void setLockOrientation (int value) {
        lockOrientation = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String adJson = extras.getString("SAVideoAd_ad");
            JSONObject jsonObject = SAJsonParser.newObject(adJson);
            this.ad = new SAAd(jsonObject);
            this.isParentalGateEnabled = extras.getBoolean("SAVideoAd_isParentalGateEnabled");
            this.shouldShowCloseButton = extras.getBoolean("SAVideoAd_shouldShowCloseButton");
            this.shouldAutomaticallyCloseAtEnd = extras.getBoolean("SAVideoAd_shouldAutomaticallyCloseAtEnd");
            this.shouldShowSmallClickButton = extras.getBoolean("SAVideoAd_shouldShowSmallClickButton");
            this.shouldLockOrientation = extras.getBoolean("SAVideoAd_shouldLockOrientation");
            this.lockOrientation = extras.getByte("SAVideoAd_lockOrientation");
        }

        String packageName = SAApplication.getSAApplicationContext().getPackageName();
        int activity_sa_videoId = getResources().getIdentifier("activity_sa_video", "layout", packageName);
        int video_playerId = getResources().getIdentifier("sa_videoplayer_id", "id", packageName);
        int close_btnId = getResources().getIdentifier("video_close", "id", packageName);
        int padlockId = getResources().getIdentifier("video_padlock_image", "id", packageName);

        // set content view
        setContentView(activity_sa_videoId);

        if (savedInstanceState == null) {

            // lock orientation
            if (shouldLockOrientation) {
                setRequestedOrientation(lockOrientation);
            }

            // close btn
            Button closeBtn = (Button) findViewById(close_btnId);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    close();
                }
            });

            // padlock
            ImageView padlock = (ImageView) findViewById(padlockId);
            padlock.setVisibility(shouldShowPadlock() ? View.VISIBLE : View.INVISIBLE);



            // video player
            final SAVideoPlayer videoPlayer = (SAVideoPlayer) getFragmentManager().findFragmentById(video_playerId);
            videoPlayer.setEventListener(new SAVideoPlayerEventInterface() {
                @Override
                public void SAVideoPlayerEventHandled(SAVideoPlayerEvent saVideoPlayerEvent) {
                    switch (saVideoPlayerEvent) {
                        case Video_Start: {
                            SAEvents.sendEventsFor(ad.creative.events, "impression");
                            SAEvents.sendEventsFor(ad.creative.events, "start");
                            SAEvents.sendEventsFor(ad.creative.events, "creativeView");

                            /** moat for video */
                            HashMap<String, String> adData = new HashMap<>();
                            adData.put("advertiserId", "" + ad.advertiserId);
                            adData.put("campaignId", "" + ad.campaignId);
                            adData.put("lineItemId", "" + ad.lineItemId);
                            adData.put("creativeId", "" + ad.creative.id);
                            adData.put("app", "" + ad.app);
                            adData.put("placementId", "" + ad.placementId);
                            adData.put("publisherId", "" + ad.publisherId);
                            SAEvents.registerVideoMoatEvent(SAVideoAd2.this, videoPlayer.getVideoPlayer(), videoPlayer.getMediaPlayer(), adData);

                            // viewable impression
                            viewabilityRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (ticks >= 2) {
                                        SAEvents.sendEventsFor(ad.creative.events, "viewable_impr");
                                    } else {
                                        ticks++;
                                        // postDelayed(viewabilityRunnable, 1000);
                                    }
                                }
                            };
                            // SAVideoAd2.postDelayed(viewabilityRunnable, 1000);

                            break;
                        }
                        case Video_1_4: {
                            SAEvents.sendEventsFor(ad.creative.events, "firstQuartile");
                            break;
                        }
                        case Video_1_2: {
                            SAEvents.sendEventsFor(ad.creative.events, "midpoint");
                            break;
                        }
                        case Video_3_4: {
                            SAEvents.sendEventsFor(ad.creative.events, "thirdQuartile");
                            break;
                        }
                        case Video_End: {
                            SAEvents.sendEventsFor(ad.creative.events, "complete");
                            if (shouldAutomaticallyCloseAtEnd) {
                                close();
                            }
                            break;
                        }
                        case Video_Error: {
                            SAEvents.sendEventsFor(ad.creative.events, "error");
                            close();
                            if (listener != null) {
                                listener.adFailedToShow(0);
                            }
                            break;
                        }
                    }
                }
            });
            videoPlayer.setClickListener(new SAVideoPlayerClickInterface() {
                @Override
                public void SAVideoPlayerClickHandled() {
                    /** check for PG */
                    if (isParentalGateEnabled) {
                        // PG stuff
                    } else {
                        click();
                    }
                }
            });

            // finally play the ad
            if (ad.creative.details.media.isOnDisk) {
                videoPlayer.playWithDiskURL(ad.creative.details.media.playableDiskUrl);
            } else {
                videoPlayer.playWithMediaURL(ad.creative.details.media.playableMediaUrl);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SAViewInterface implementation
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void load(final int placementId) {
        loader.loadAd(placementId, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {
                ad = saAd;
                if (saAd != null) {
                    if (listener != null) {
                        listener.adWasLoaded(placementId);
                    }
                } else {
                    if (listener != null) {
                        listener.adWasLoaded(placementId);
                    }
                }
            }
        });
    }

    @Override
    public void setAd(SAAd ad) {
        this.ad = ad;
    }

    @Override
    public SAAd getAd() {
        return this.ad;
    }

    @Override
    public boolean shouldShowPadlock() {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    @Override
    public void play() {
        if (ad != null && ad.creative.creativeFormat == SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAVideoAd2.class);
            intent.putExtra("SAVideoAd_ad", ad.writeToJson().toString());
            intent.putExtra("SAVideoAd_isParentalGateEnabled", isParentalGateEnabled);
            intent.putExtra("SAVideoAd_shouldShowCloseButton", shouldShowCloseButton);
            intent.putExtra("SAVideoAd_shouldAutomaticallyCloseAtEnd ", shouldAutomaticallyCloseAtEnd );
            intent.putExtra("SAVideoAd_shouldShowSmallClickButton", shouldShowSmallClickButton);
            intent.putExtra("SAVideoAd_shouldLockOrientation", shouldLockOrientation);
            intent.putExtra("SAVideoAd_lockOrientation", lockOrientation);
            context.startActivity(intent);
        } else {
            if (listener != null) {
                listener.adFailedToShow(0);
            }
        }
    }

    @Override
    public void click() {
        /** call listener */
        if (listener != null) {
            listener.adWasClicked(ad.placementId);
        }

        /** send click tracking events */
        SAEvents.sendEventsFor(ad.creative.events, "click_tracking");
        SAEvents.sendEventsFor(ad.creative.events, "custom_clicks");

        /** switch between CPM & CPI campaigns */
        String finalUrl = ad.creative.clickUrl; // destinationURL

        // if video CPI
        if (ad.saCampaignType == SACampaignType.CPI) {
            // then send an event to the current "destinationURL" taken from the VAST Tag, so that
            // a click will be counted
            // @Warn: This might cause a double-click !?!
            SAEvents.sendEventToURL(ad.creative.clickUrl); // here clickUrl would have been the https://play.google.com&referrer=com.example.myapp

            // and get the actual creative click URL so that we can append to it and send referral
            // data to the store :(
            finalUrl = ad.creative.clickUrl;
            finalUrl += "&referrer=";
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", SuperAwesome.getInstance().getConfiguration(), // used to be ad.advertiserId
                    "utm_campaign", ad.campaignId,
                    "utm_term", ad.lineItemId,
                    "utm_content", ad.creative.id,
                    "utm_medium", ad.placementId
            });
            String referrerQuery = SAUtils.formGetQueryFromDict(referrerData);
            referrerQuery = referrerQuery.replace("&", "%26");
            referrerQuery = referrerQuery.replace("=", "%3D");

            // finally add the query
            finalUrl += referrerQuery;
        }

        Log.d("SuperAwesome", "Going to URL: " + finalUrl);

        /** go-to-url */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
        startActivity(browserIntent);
    }

    @Override
    public void resize(int width, int height) {
        // do nothing
    }

    @Override
    public void close() {
        // delete the ad
        ad = null;
        listener = null;

        // close
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}

//public class SAVideoAd2 implements SAViewInterface {
//
//    private Context context;
//    private SALoader loader;
//    private static WeakReference<Activity> mActivityRef;
//    private static SAVideoAdDataHolder holder;
//
//    public SAVideoAd2 (Context context) {
//        this.context = context;
//        holder = new SAVideoAdDataHolder();
//        loader = new SALoader();
//    }
//
//    protected static void updateActivity(Activity activity){
//        mActivityRef = new WeakReference<> (activity);
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//    // SAViewInterface
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//
//    @Override
//    public void load(final int placementId) {
//        // load the ad
//        loader.loadAd(placementId, new SALoaderInterface() {
//            @Override
//            public void didLoadAd(SAAd saAd) {
//
//                // get the ad
//                holder._refAd = saAd;
//
//                // callbacks
//                if (saAd != null) {
//                    if (holder._refAdListener != null) {
//                        holder._refAdListener.adWasLoaded(placementId);
//                    }
//                } else {
//                    if (holder._refAdListener != null) {
//                        holder._refAdListener.adWasNotLoaded(placementId);
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void setAd(SAAd ad) {
//        holder._refAd = ad;
//    }
//
//    @Override
//    public SAAd getAd() {
//        return holder._refAd;
//    }
//
//    @Override
//    public boolean shouldShowPadlock() {
//        return false;
//    }
//
//    @Override
//    public void play() {
//        if (holder._refAd != null && holder._refAd.creative.creativeFormat == SACreativeFormat.video) {
//            Intent intent = new Intent(context, SAVideoAdActivity.class);
//            context.startActivity(intent);
//        } else {
//            if (holder._refAdListener != null) {
//                holder._refAdListener.adFailedToShow(0);
//            }
//        }
//    }
//
//    @Override
//    public void click() {
//        // do nothing
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        // do nothing
//    }
//
//    @Override
//    public void close() {
//        if (mActivityRef != null) {
//            mActivityRef.get().onBackPressed();
//        }
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//    // Inner activity
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//
//    public static class SAVideoAdActivity extends Activity implements SAViewInterface {
//
//        private SAAd ad = null;
//        private SAVideoPlayer videoPlayer = null;
//        private boolean isParentalGateEnabled = false;
//
//        @Override
//        public void onSaveInstanceState(Bundle savedInstanceState) {
//            super.onSaveInstanceState(savedInstanceState);
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            // take the holder ad
//            ad = holder._refAd;
//
//            /** update parent class weak ref to point to this activity */
//            SAVideoAd2.updateActivity(this);
//
//            /** load resource */
//            String packageName = SAApplication.getSAApplicationContext().getPackageName();
//            int activity_sa_videoId = getResources().getIdentifier("activity_sa_video", "layout", packageName);
//            int video_playerId = getResources().getIdentifier("sa_videoplayer_id", "id", packageName);
//
//            // set content view
//            setContentView(activity_sa_videoId );
//
//            if (savedInstanceState == null) {
//                videoPlayer = (SAVideoPlayer) getFragmentManager().findFragmentById(video_playerId);
//                videoPlayer.setEventListener(new SAVideoPlayerEventInterface() {
//                    @Override
//                    public void SAVideoPlayerEventHandled(SAVideoPlayerEvent saVideoPlayerEvent) {
//                        switch (saVideoPlayerEvent) {
//                            case Video_Start: {
//                                break;
//                            }
//                            case Video_1_4: {
//                                break;
//                            }
//                            case Video_1_2: {
//                                break;
//                            }
//                            case Video_3_4: {
//                                break;
//                            }
//                            case Video_End: {
//                                break;
//                            }
//                            case Video_Error: {
//                                break;
//                            }
//                        }
//                    }
//                });
//                videoPlayer.setClickListener(new SAVideoPlayerClickInterface() {
//                    @Override
//                    public void SAVideoPlayerClickHandled() {
//                        /** check for PG */
//                        if (isParentalGateEnabled) {
////                            gate = new SAParentalGate(getContext(), SAVideoAd.this, ad);
////                            gate.setListener(parentalGateListener);
////                            gate.show();
//                        } else {
//                             click();
//                        }
//                    }
//                });
//
//                if (ad.creative.details.media.isOnDisk) {
//                    videoPlayer.playWithDiskURL(ad.creative.details.media.playableDiskUrl);
//                } else {
//                    videoPlayer.playWithDiskURL(ad.creative.details.media.playableMediaUrl);
//                }
//            }
//        }
//
//        public void closeVideo(View v){
//            // remove the ad!
//            ad = null;
//            holder._refAd = null;
//
////            /** close listener */
////            if (adListener != null){
////                adListener.adWasClosed(ad.placementId);
////            }
//
//            // close this
//            // videoPlayer.pausePlayer();
//
//            /**
//             * call super.onBackPressed() to close the activity because it's own onBackPressed()
//             * method is overridden to do nothing e.g. so as not to be closed by the user
//             */
//            super.onBackPressed();
//
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//        }
//
//        @Override
//        public void onBackPressed() {
//            /** do nothing */
//        }
//
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            ad = null;
//        }
//
//        @Override
//        public void load(int placementId) {
//            // nothing
//        }
//
//        @Override
//        public void setAd(SAAd ad) {
//            this.ad = ad;
//        }
//
//        @Override
//        public SAAd getAd() {
//            return this.ad;
//        }
//
//        @Override
//        public boolean shouldShowPadlock() {
//            return false;
//        }
//
//        @Override
//        public void play() {
//
//        }
//
//        @Override
//        public void click() {
//
//        }
//
//        @Override
//        public void resize(int width, int height) {
//
//        }
//
//        @Override
//        public void close() {
//
//        }
//    }
//}
//
//class SAVideoAdDataHolder {
//    public SAAd _refAd;
//    public boolean _refIsParentalGateEnabled = true;
//    public boolean _refShouldShowCloseButton = true;
//    public boolean _refShouldAutomaticallyCloseAtEnd = true;
//    public boolean _refShouldLockOrientation = false;
//    public boolean _refShouldShowSmallClickButton = false;
//    public int _refLockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//    public SAInterface _refAdListener;
//
//    SAVideoAdDataHolder(){
//        // basic contructor
//    }
//}
