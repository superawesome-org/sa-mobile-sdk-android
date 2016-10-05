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
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.util.HashMap;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.samodelspace.SAResponse;
import tv.superawesome.lib.samodelspace.SATracking;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sasession.SASessionInterface;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerClickInterface;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerEvent;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerEventInterface;
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Created by gabriel.coman on 26/08/16.
 */
public class SAVideoAd extends Activity {

    // the ad
    private SAAd ad = null;

    // the internal loader
    private SAEvents events = null;

    private SAVideoPlayer videoPlayer = null;
    private SAParentalGate gate;

    // private vars
    private static Context context = null;

    // private vars w/ a public interface
    private static HashMap<Integer, Object> ads = new HashMap<>();
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };
    private static boolean isParentalGateEnabled = true;
    private static boolean shouldShowCloseButton = true;
    private static boolean shouldAutomaticallyCloseAtEnd = true;
    private static boolean shouldShowSmallClickButton = false;
    private static boolean isTestingEnabled = false;
    private static boolean isBackButtonEnabled = false;
    private static SAOrientation orientation = SAOrientation.ANY;
    private static SAConfiguration configuration = SAConfiguration.PRODUCTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // local versions of the static vars
        final SAInterface listenerL = getListener();
        final boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        final boolean shouldShowCloseButtonL = getShouldShowCloseButton();
        final boolean shouldAutomaticallyCloseAtEndL = getShouldAutomaticallyCloseAtEnd();
        final boolean shouldShowSmallClickButtonL = getShouldShowSmallClickButton();
        final SAOrientation orientationL = getOrientation();
        Bundle bundle = getIntent().getExtras();
        String adString = bundle.getString("ad");
        ad = new SAAd(SAJsonParser.newObject(adString));

        // start events
        events = new SAEvents (this);
        events.setAd(ad);

        String packageName = this.getPackageName();
        int activity_sa_videoId = getResources().getIdentifier("activity_sa_video", "layout", packageName);
        int video_playerId = getResources().getIdentifier("sa_videoplayer_id", "id", packageName);
        int close_btnId = getResources().getIdentifier("video_close", "id", packageName);
        int padlockId = getResources().getIdentifier("video_padlock_image", "id", packageName);

        // set content view
        setContentView(activity_sa_videoId);

        // activity relative layout
        RelativeLayout videoLayout = (RelativeLayout) findViewById(activity_sa_videoId);

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

            // make sure direction is locked
            switch (orientationL) {
                case ANY:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    break;
                case PORTRAIT:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case LANDSCAPE:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
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
                            events.sendViewableImpressionForVideo(videoPlayer.getContainerView());

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
        boolean isBackButtonEnabledL = getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            super.onBackPressed();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Custom instance methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void click() {

        // get local
        SAInterface listenerL = getListener();
        SAConfiguration configurationL = getConfiguration();

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
            events.sendEventsFor("install");

            // form the final URL for referral data
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", configurationL.ordinal(),
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

    public static void load(final int placementId, Context context) {

        if (!ads.containsKey(placementId)) {

            // set a placeholder
            ads.put(placementId, new Object());

            // create a loader
            final SALoader loader = new SALoader(context);

            // create a current session
            final SASession session = new SASession (context);
            session.setTestMode(isTestingEnabled);
            session.setConfiguration(configuration);
            session.setVersion(SuperAwesome.getInstance().getSDKVersion());
            session.prepareSession(new SASessionInterface() {
                @Override
                public void sessionReady() {

                    // after session is OK - start loading
                    loader.loadAd(placementId, session, new SALoaderInterface() {
                        @Override
                        public void didLoadAd(SAResponse response) {

                            // find out the real valid
                            boolean isValid = response.isValid();
                            SAAd first = isValid ? response.ads.get(0) : null;
                            isValid = first != null && isValid && first.creative.details.media.isOnDisk;

                            // put the correct value
                            if (isValid) {
                                ads.put(placementId, first);
                            }
                            // remove existing
                            else {
                                ads.remove(placementId);
                            }

                            // call listener
                            listener.onEvent(placementId, isValid ? SAEvent.adLoaded : SAEvent.adFailedToLoad);
                        }
                    });
                }
            });

        } else {
            listener.onEvent(placementId, SAEvent.adFailedToLoad);
        }
    }

    public static boolean hasAdAvailable (int placementId) {
        Object object = ads.get(placementId);
        return object != null && object instanceof SAAd;
    }

    public static void play(int placementId, Context c) {
        // capture context
        context = c;

        // try to get the ad that fits the placement id
        SAAd adL = (SAAd) ads.get(placementId);

        // try to start the activity
        if (adL != null && adL.creative.creativeFormat == SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAVideoAd.class);
            intent.putExtra("ad", adL.writeToJson().toString());
            context.startActivity(intent);
        } else {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        }
    }

    private static void removeAdFromLoadedAds (SAAd ad) {
        ads.remove(ad.placementId);
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
        configuration = SAConfiguration.PRODUCTION;
    }

    public static void setConfigurationStaging () {
        configuration = SAConfiguration.STAGING;
    }

    public static void setOrientationAny () {
        orientation = SAOrientation.ANY;
    }

    public static void setOrientationPortrait () {
        orientation = SAOrientation.PORTRAIT;
    }

    public static void setOrientationLandscape () {
        orientation = SAOrientation.LANDSCAPE;
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

    public static void enableBackButton () {
        isBackButtonEnabled = true;
    }

    public static void disableBackButton () {
        isBackButtonEnabled = false;
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

    private static SAOrientation getOrientation () {
        return orientation;
    }

    private static SAConfiguration getConfiguration () {
        return configuration;
    }

    private static boolean getIsBackButtonEnabled () {
        return isBackButtonEnabled;
    }
}
