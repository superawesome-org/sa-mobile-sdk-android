/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
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
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sasession.SASessionInterface;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerClickInterface;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerEvent;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerEventInterface;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
public class SAVideoAd extends Activity {

    // the ad
    private SAAd ad = null;

    // the internal loader
    private SAEvents events = null;

    private SAVideoPlayer videoPlayer = null;
    private SAParentalGate gate;

    // private vars w/ a public interface
    private static HashMap<Integer, Object> ads = new HashMap<>();
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };

    private static boolean isParentalGateEnabled            = SuperAwesome.getInstance().defaultParentalGate();
    private static boolean shouldShowCloseButton            = SuperAwesome.getInstance().defaultCloseButton();
    private static boolean shouldAutomaticallyCloseAtEnd    = SuperAwesome.getInstance().defaultCloseAtEnd();
    private static boolean shouldShowSmallClickButton       = SuperAwesome.getInstance().defaultSmallClick();
    private static boolean isTestingEnabled                 = SuperAwesome.getInstance().defaultTestMode();
    private static boolean isBackButtonEnabled              = SuperAwesome.getInstance().defaultBackButton();
    private static SAOrientation orientation                = SuperAwesome.getInstance().defaultOrientation();
    private static SAConfiguration configuration            = SuperAwesome.getInstance().defaultConfiguration();

    /**********************************************************************************************
     * Activity initialization & instance methods
     **********************************************************************************************/

    /**
     * Overridden "onCreate" method, part of the Activity standard set of methods.
     * Here is the part where the activity / video ad gets configured
     *
     * @param savedInstanceState previous saved state
     */
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
        int padlockId = getResources().getIdentifier("padlock_button", "id", packageName);

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
        Button padlock = (Button) findViewById(padlockId);
        padlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
                SAVideoAd.this.startActivity(browserIntent);
            }
        });
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
                public void saVideoPlayerDidReceiveEvent(SAVideoPlayerEvent saVideoPlayerEvent) {
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
                public void saVideoPlayerDidReceiveClick() {
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

    /**
     * Overridden "onSaveInstanceState" method of the activity
     *
     * @param outState the outgoing state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Overridden "onBackPressed" method of the activity
     * Depending on how the ad is customised, this will lock the back button or it will allow it.
     * If it allows it, it's going to also send an "adClosed" event back to the SDK user
     */
    @Override
    public void onBackPressed() {
        boolean isBackButtonEnabledL = getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            SAInterface listenerL = getListener();
            listenerL.onEvent(ad.placementId, SAEvent.adClosed);
            super.onBackPressed();
        }
    }

    /**********************************************************************************************
     * Custom instance methods
     **********************************************************************************************/

    /**
     * Method that handles a click on the ad surface
     */
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
        if (ad.campaignType == SACampaignType.CPI) {

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
                startActivity(browserIntent);
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
                startActivity(browserIntent);
            }
        }
    }

    /**
     * Method that handles what should happen when the video pauses
     */
    public void pause () {
        videoPlayer.pausePlayer();
    }

    /**
     * Method that handles what should happen when the video resumes
     */
    public void resume () {
        videoPlayer.resumePlayer();
    }

    /**
     * Method that determines if an ad should display a padlock over it's content to indicate
     * it has been properly approved by SuperAwesome
     *
     * @return true or false
     */
    private boolean shouldShowPadlock() {
        return ad.creative.format != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    /**
     * Method that closes the interstitial ad
     */
    private void close() {

        // get local
        SAInterface listenerL = getListener();

        // call listener
        listenerL.onEvent(ad.placementId, SAEvent.adClosed);

        // unregister MOAT video
        events.unregisterVideoMoatEvent();

        // delete the ad
        removeAdFromLoadedAds(ad);

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    /**********************************************************************************************
     * Public class interface
     **********************************************************************************************/

    /**
     * Static method that loads an ad into the video queue.
     * Ads can only be loaded once and then can be reloaded after they've been played.
     *
     * @param placementId   the Ad placement id to load data for
     * @param context       the current context
     */
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
                public void didFindSessionReady() {

                    // after session is OK - start loading
                    loader.loadAd(placementId, session, new SALoaderInterface() {
                        @Override
                        public void saDidLoadAd(SAResponse response) {

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

    /**
     * Static method that returns whether ad data for a certain placement has already been loaded
     *
     * @param placementId   the Ad placement id to check for
     * @return              true or false
     */
    public static boolean hasAdAvailable (int placementId) {
        Object object = ads.get(placementId);
        return object != null && object instanceof SAAd;
    }

    /**
     * Static method that, if an ad data is loaded, will play the content for the user
     *
     * @param placementId   the Ad placement id to play an ad for
     * @param context       the current context (activity or fragment)
     */
    public static void play(int placementId, Context context) {
        // try to get the ad that fits the placement id
        SAAd adL = (SAAd) ads.get(placementId);

        // try to start the activity
        if (adL != null && adL.creative.format == SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAVideoAd.class);
            intent.putExtra("ad", adL.writeToJson().toString());
            context.startActivity(intent);
        } else {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        }
    }

    /**
     * Private static method that removes an already played ad from the ad queue so that it can't
     * be played again until it is reloaded
     *
     * @param ad the current ad, since I need the palcement Id from it
     */
    private static void removeAdFromLoadedAds (SAAd ad) {
        ads.remove(ad.placementId);
    }

    /**********************************************************************************************
     * Setters & Getters
     **********************************************************************************************/

    public static void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public static void enableParentalGate () {
        setParentalGate(true);
    }

    public static void disableParentalGate () {
        setParentalGate(false);
    }

    public static void enableTestMode () {
        setTestMode(true);
    }

    public static void disableTestMode () {
        setTestMode(false);
    }

    public static void setConfigurationProduction () {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public static void setConfigurationStaging () {
        setConfiguration(SAConfiguration.STAGING);
    }

    public static void setOrientationAny () {
        setOrientation(SAOrientation.ANY);
    }

    public static void setOrientationPortrait () {
        setOrientation(SAOrientation.PORTRAIT);
    }

    public static void setOrientationLandscape () {
        setOrientation(SAOrientation.LANDSCAPE);
    }

    public static void enableBackButton () {
        setBackButton(true);
    }

    public static void disableBackButton () {
        setBackButton(false);
    }

    public static void enableCloseButton () {
        setCloseButton(true);
    }

    public static void disableCloseButton () {
        setCloseButton(false);
    }

    public static void enableCloseAtEnd () {
        setCloseAtEnd(true);
    }

    public static void disableCloseAtEnd () {
        setCloseAtEnd(false);
    }

    public static void enableSmallClickButton () {
        setSmallClick(true);
    }

    public static void disableSmallClickButton () {
        setSmallClick(false);
    }

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

    public static void setParentalGate (boolean value) {
        isParentalGateEnabled = value;
    }

    public static void setTestMode (boolean value) {
        isTestingEnabled = value;
    }

    public static void setConfiguration (SAConfiguration value) {
        configuration = value;
    }

    public static void setOrientation (SAOrientation value) {
        orientation = value;
    }

    public static void setBackButton (boolean value) {
        isBackButtonEnabled = value;
    }

    public static void setCloseButton (boolean value) {
        shouldShowCloseButton = value;
    }

    public static void setCloseAtEnd (boolean value) {
        shouldAutomaticallyCloseAtEnd = value;
    }

    public static void setSmallClick (boolean value) {
        shouldShowSmallClickButton = value;
    }
}
