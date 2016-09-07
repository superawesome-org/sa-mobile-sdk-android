package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

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
public class SAVideoAd extends Activity {

    // private vars
    private static Context context = null;
    private static SAAd ad = null;

    // the internal loader
    private SAEvents events = null;

    // subviews and associated views
    private SAVideoPlayer videoPlayer = null;
    private SAParentalGate gate;

    // private vars w/ a public interface
    private static SAInterface listener = null;
    private static boolean isParentalGateEnabled = false;
    private static boolean shouldShowCloseButton = true;
    private static boolean shouldAutomaticallyCloseAtEnd = true;
    private static boolean shouldShowSmallClickButton = false;
    private static boolean shouldLockOrientation = false;
    private static int     lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // local versions of the static vars
        final SAInterface listenerL = getListener();
        final boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        final boolean shouldShowCloseButtonL = getShouldShowCloseButton();
        final boolean shouldAutomaticallyCloseAtEndL = getShouldAutomaticallyCloseAtEnd();
        final boolean shouldShowSmallClickButtonL = getShouldShowSmallClickButton();
        final boolean shouldLockOrientationL = getShouldLockOrientation();
        int lockOrientationL = getLockOrientation();
        final SAAd adL = getAd();

        // start events
        events = new SAEvents ();
        events.setAd(adL);

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
                            if (listenerL != null) {
                                listenerL.SADidShowAd();
                            }

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
                            if (listenerL != null) {
                                listenerL.SADidNotShowAd();
                            }
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
                        gate = new SAParentalGate(SAVideoAd.this, this, adL);
                        gate.show();
                    } else {
                        click();
                    }
                }
            });

            // finally play the ad
            if (adL.creative.details.media.isOnDisk) {
                videoPlayer.playWithDiskURL(adL.creative.details.media.playableDiskUrl);
            } else {
                videoPlayer.playWithMediaURL(adL.creative.details.media.playableMediaUrl);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    public void click() {
        // get local
        SAInterface listenerL = getListener();
        SAAd adL = getAd();

        // call listener
        if (listenerL != null) {
            listenerL.SADidClickAd();
        }

        // send click tracking events
        events.sendEventsFor("click_tracking");
        events.sendEventsFor("custom_clicks");

        // switch between CPM & CPI campaigns
        // todo: this will fail since the click URL will be wrong for CPI campaigns
        String finalUrl = ad.creative.clickUrl;

        // if video CPI
        if (adL.saCampaignType == SACampaignType.CPI) {
            // send event
            events.sendEventToURL(adL.creative.clickUrl); // here clickUrl would have been the https://play.google.com&referrer=com.example.myapp

            // and get the actual creative click URL so that we can append to it and send referral
            // data to the store :(
            finalUrl = adL.creative.clickUrl;
            finalUrl += "&referrer=";
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", SuperAwesome.getInstance().getConfiguration(), // used to be ad.advertiserId
                    "utm_campaign", adL.campaignId,
                    "utm_term", adL.lineItemId,
                    "utm_content", adL.creative.id,
                    "utm_medium", adL.placementId
            });
            String referrerQuery = SAUtils.formGetQueryFromDict(referrerData);
            referrerQuery = referrerQuery.replace("&", "%26");
            referrerQuery = referrerQuery.replace("=", "%3D");

            // finally add the query
            finalUrl += referrerQuery;
        }

        Log.d("SuperAwesome", "Going to URL: " + finalUrl);

        // go to url
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
        context.startActivity(browserIntent);
    }

    public void pause () {
        videoPlayer.pausePlayer();
    }

    public void resume () {
        videoPlayer.resumePlayer();
    }

    private void close() {

        // get local
        SAInterface listenerL = getListener();

        // call listener
        if (listenerL != null) {
            listenerL.SADidCloseAd();
        }

        // unregister MOAT video
        events.unregisterVideoMoatEvent(ad.placementId);

        // delete the ad
        nullAd();

        // close
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public class interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void load(final int placementId) {
        SALoader loader = new SALoader();
        loader.loadAd(placementId, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {
                ad = saAd;

                if (ad != null) {
                    if (listener != null) {
                        listener.SADidLoadAd(placementId);
                    }
                } else {
                    if (listener != null) {
                        listener.SADidNotLoadAd(placementId);
                    }
                }
            }
        });
    }

    public static boolean hasAdAvailable () {
        return ad != null;
    }

    public static SAAd getAd() {
        return ad;
    }

    private static void nullAd () {
        ad = null;
    }

    private static boolean shouldShowPadlock() {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    public static void play(Context c) {
        context = c;
        if (ad != null && ad.creative.creativeFormat == SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAVideoAd.class);
            context.startActivity(intent);
        } else {
            if (listener != null) {
                listener.SADidNotShowAd();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters & getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListener(SAInterface value) {
        listener = value;
    }

    public static void setIsParentalGateEnabled (boolean value) {
        isParentalGateEnabled = value;
    }

    public static void setShouldShowCloseButton (boolean value) {
        shouldShowCloseButton = value;
    }

    public static void setShouldAutomaticallyCloseAtEnd (boolean value) {
        shouldAutomaticallyCloseAtEnd = value;
    }

    public static void setShouldShowSmallClickButton (boolean value) {
        shouldShowSmallClickButton = value;
    }

    public static void setShouldLockOrientation (boolean value) {
        shouldLockOrientation = value;
    }

    public static void setLockOrientation (int value) {
        lockOrientation = value;
    }

    public static SAInterface getListener () {
        return listener;
    }

    public static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    public static boolean getShouldShowCloseButton () {
        return shouldShowCloseButton;
    }

    public static boolean getShouldAutomaticallyCloseAtEnd () {
        return shouldAutomaticallyCloseAtEnd;
    }

    public static boolean getShouldShowSmallClickButton () {
        return shouldShowSmallClickButton;
    }

    public static boolean getShouldLockOrientation () {
        return shouldLockOrientation;
    }

    public static int getLockOrientation () {
        return lockOrientation;
    }
}
