package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.samodelspace.SATracking;
import tv.superawesome.lib.sasession.SASession;
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
public class SAVideoAd extends Activity {

    // the ad
    private SAAd ad = null;

    // the internal loader
    private SAEvents events = null;

    // subviews and associated views
    private SAVideoPlayer videoPlayer = null;
    private SAParentalGate gate;

    // private vars
    private static Context context = null;

    // private vars w/ a public interface
    private static List<SAAd> ads = new ArrayList<>();
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };
    private static boolean isParentalGateEnabled = true;
    private static boolean shouldShowCloseButton = true;
    private static boolean shouldAutomaticallyCloseAtEnd = true;
    private static boolean shouldShowSmallClickButton = false;
    private static boolean shouldLockOrientation = false;
    private static int     lockOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    private static boolean isTestingEnabled = false;
    private static int     configuration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("SuperAwesome", "ON CREATE");

        // local versions of the static vars
        final SAInterface listenerL = getListener();
        final boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        final boolean shouldShowCloseButtonL = getShouldShowCloseButton();
        final boolean shouldAutomaticallyCloseAtEndL = getShouldAutomaticallyCloseAtEnd();
        final boolean shouldShowSmallClickButtonL = getShouldShowSmallClickButton();
        final boolean shouldLockOrientationL = getShouldLockOrientation();
        int lockOrientationL = getLockOrientation();
        Bundle bundle = getIntent().getExtras();
        ad = bundle.getParcelable("ad");

        // start events
        events = new SAEvents ();
        events.setAd(ad);

        String packageName = SAApplication.getSAApplicationContext().getPackageName();
        int activity_sa_videoId = getResources().getIdentifier("activity_sa_video", "layout", packageName);
        int video_playerId = getResources().getIdentifier("sa_videoplayer_id", "id", packageName);
        int close_btnId = getResources().getIdentifier("video_close", "id", packageName);
        int padlockId = getResources().getIdentifier("video_padlock_image", "id", packageName);

        // set content view
        setContentView(activity_sa_videoId);

        // close btn
        Button closeBtn = (Button) findViewById(close_btnId);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        closeBtn.setVisibility(shouldShowCloseButtonL ? View.VISIBLE : View.INVISIBLE);

        // padlock
        ImageView padlock = (ImageView) findViewById(padlockId);
        padlock.setVisibility(shouldShowPadlock() ? View.VISIBLE : View.INVISIBLE);

        if (savedInstanceState == null) {

            // lock orientation
            if (shouldLockOrientationL) {
                setRequestedOrientation(lockOrientationL);
            }

            // video player
            videoPlayer = (SAVideoPlayer) getFragmentManager().findFragmentById(video_playerId);
            videoPlayer.setShouldShowSmallClickButton(shouldShowSmallClickButtonL);
            videoPlayer.setEventListener(new SAVideoPlayerEventInterface() {
                @Override
                public void SAVideoPlayerEventHandled(SAVideoPlayerEvent saVideoPlayerEvent) {
                    switch (saVideoPlayerEvent) {
                        case Video_Start: {
                            // send callback
                            listenerL.onEvent(ad.placementId, SAEvent.adShown);

                            // send other events
                            events.sendEventsFor("impression");
                            events.sendEventsFor("start");
                            events.sendEventsFor("creativeView");

                            // send viewable
                            events.sendViewableForFullscreen();

                            // moat
                            events.registerVideoMoatEvent(SAVideoAd.this, videoPlayer.getVideoPlayer(), videoPlayer.getMediaPlayer());

                            break;
                        }
                        case Video_1_4: {
                            events.sendEventsFor("firstQuartile");
                            break;
                        }
                        case Video_1_2: {
                            events.sendEventsFor("midpoint");
                            break;
                        }
                        case Video_3_4: {
                            events.sendEventsFor("thirdQuartile");
                            break;
                        }
                        case Video_End: {
                            events.sendEventsFor("complete");
                            if (shouldAutomaticallyCloseAtEndL) {
                                close();
                            }
                            break;
                        }
                        case Video_Error: {
                            events.sendEventsFor("error");
                            close();
                            listenerL.onEvent(ad.placementId, SAEvent.adFailedToShow);
                            break;
                        }
                    }
                }
            });
            videoPlayer.setClickListener(new SAVideoPlayerClickInterface() {
                @Override
                public void SAVideoPlayerClickHandled() {
                    // check for parental gate on click
                    if (isParentalGateEnabledL) {
                        gate = new SAParentalGate(SAVideoAd.this, SAVideoAd.this, ad);
                        gate.show();
                    } else {
                        click();
                    }
                }
            });

            // finally play the ad
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (ad.creative.details.media.isOnDisk) {
                        videoPlayer.playWithDiskURL(ad.creative.details.media.playableDiskUrl);
                    } else {
                        videoPlayer.playWithMediaURL(ad.creative.details.media.playableMediaUrl);
                    }
                }
            };
            handler.postDelayed(runnable, 250);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    public void click() {

        // get local
        SAInterface listenerL = getListener();
        int configurationL = getConfiguration();

        // call listener
        listenerL.onEvent(ad.placementId, SAEvent.adClicked);

        // in CPI we:
        //  - take the click URL provided by the Ad and redirect to it
        //  - send an event to "click_through"
        //  - send events to "click_tracking"
        //  - send all "custom_clicks" events
        if (ad.saCampaignType == SACampaignType.CPI) {

            // send events
            events.sendEventsFor("click_tracking");
            events.sendEventsFor("custom_clicks");
            events.sendEventsFor("click_through");

            // form the final URL for referral data
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", configurationL, // used to be ad.advertiserId
                    "utm_campaign", ad.campaignId,
                    "utm_term", ad.lineItemId,
                    "utm_content", ad.creative.id,
                    "utm_medium", ad.placementId
            });
            String referrerQuery = SAUtils.formGetQueryFromDict(referrerData);
            referrerQuery = referrerQuery.replace("&", "%26");
            referrerQuery = referrerQuery.replace("=", "%3D");

            // go to the URL
            if (ad.creative.clickUrl != null) {
                String finalURL = ad.creative.clickUrl + "&referrer=" + referrerQuery;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalURL));
                context.startActivity(browserIntent);
            }
        }
        // in CPM we:
        //  - take the "click_through" URL provided by VAST and redirect to it
        //  - send all "click_tracking" events
        //  - send all "custom_clicks" events
        else {
            // send the events
            events.sendEventsFor("click_tracking");
            events.sendEventsFor("custom_clicks");

            // get the final go-to URL
            String finalURL = null;
            for (SATracking tracking : ad.creative.events) {
                if (tracking.event.equals("click_through")) {
                    finalURL = tracking.URL;
                }
            }

            // go to the URL
            if (finalURL != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalURL));
                context.startActivity(browserIntent);
            }
        }
    }

    public void pause () {
        videoPlayer.pausePlayer();
    }

    public void resume () {
        videoPlayer.resumePlayer();
    }

    private boolean shouldShowPadlock() {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    private void close() {

        // get local
        SAInterface listenerL = getListener();

        // call listener
        listenerL.onEvent(ad.placementId, SAEvent.adClosed);

        // unregister MOAT video
        events.unregisterVideoMoatEvent(ad.placementId);

        // delete the ad
        removeAdFromLoadedAds(ad);

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public class interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void load(final int placementId) {

        // get a new session
        SASession session = new SASession ();
        session.setConfiguration(configuration);
        session.setTest(isTestingEnabled);
        session.setVersion(SuperAwesome.getInstance().getSDKVersion());
        session.setDauId(SuperAwesome.getInstance().getDAUID());

        // create a new loader
        SALoader loader = new SALoader();
        loader.loadAd(placementId, session, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {

                Log.d("SuperAwesome", "Ad " + saAd.creative.details.media.writeToJson().toString());

                // add to the array queue
                if (saAd != null) {
                    ads.add(saAd);
                }

                // call event
                listener.onEvent(placementId, saAd != null ? SAEvent.adLoaded : SAEvent.adFailedToLoad);

            }
        });
    }

    public static boolean hasAdAvailable (int placementId) {
        Boolean hasAd = false;
        for (SAAd ad : ads) {
            if (ad.placementId == placementId) {
                hasAd = true;
                break;
            }
        }
        return hasAd;
    }

    public static void play(int placementId, Context c) {
        // capture context
        context = c;

        // try to get the ad that fits the placement id
        SAAd adL = null;
        for (SAAd ad : ads) {
            if (ad.placementId == placementId) {
                adL = ad;
            }
        }

        // try to start the activity
        if (adL != null && adL.creative.creativeFormat == SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAVideoAd.class);
            intent.putExtra("ad", adL);
            context.startActivity(intent);
        } else {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        }
    }

    private static void removeAdFromLoadedAds (SAAd ad) {
        // have to do this because when I send the ad to the activity, it gets serialized /
        // de-serialized into a new instance
        SAAd toRemove = null;
        for (SAAd ad1 : ads) {
            if (ad1.placementId == ad.placementId) {
                toRemove = ad1;
            }
        }
        if (toRemove != null) {
            ads.remove(toRemove);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters & getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public static void enableParentalGate () {
        isParentalGateEnabled = true;
    }

    public static void disableParentalGate () {
        isParentalGateEnabled = false;
    }

    public static void enableTestMode () {
        isTestingEnabled = true;
    }

    public static void disableTestMode () {
        isTestingEnabled = false;
    }

    public static void setConfigurationProduction () {
        configuration = SASession.CONFIGURATION_PRODUCTION;
    }

    public static void setConfigurationStaging () {
        configuration = SASession.CONFIGURATION_STAGING;
    }

    public static void setOrientationAny () {
        shouldLockOrientation = false;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public static void setOrientationPortrait () {
        shouldLockOrientation = true;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    public static void setOrientationLandscape () {
        shouldLockOrientation = true;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    public static void enableCloseButton () {
        shouldShowCloseButton = true;
    }

    public static void disableCloseButton () {
        shouldShowCloseButton = false;
    }

    public static void enableCloseAtEnd () {
        shouldAutomaticallyCloseAtEnd = true;
    }

    public static void disableCloseAtEnd () {
        shouldAutomaticallyCloseAtEnd = false;
    }

    public static void enableSmallClickButton () {
        shouldShowSmallClickButton = true;
    }

    public static void disableSmallClickButton () {
        shouldShowSmallClickButton = false;
    }

    // private static methods to handle static vars

    private static SAInterface getListener () {
        return listener;
    }

    private static boolean getIsTestEnabled () {
        return isTestingEnabled;
    }

    private static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    private static boolean getShouldShowCloseButton () {
        return shouldShowCloseButton;
    }

    private static boolean getShouldAutomaticallyCloseAtEnd () {
        return shouldAutomaticallyCloseAtEnd;
    }

    private static boolean getShouldShowSmallClickButton () {
        return shouldShowSmallClickButton;
    }

    private static boolean getShouldLockOrientation () {
        return shouldLockOrientation;
    }

    private static int getLockOrientation () {
        return lockOrientation;
    }

    private static int getConfiguration () {
        return configuration;
    }
}
