/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.publisher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sabumperpage.SABumperPage;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACampaignType;
import tv.superawesome.lib.saparentalgate.SAParentalGate;
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;
import tv.superawesome.lib.savideoplayer.SAVideoPlayerClickInterface;
import tv.superawesome.lib.savideoplayer.chrome.SAMediaController;
import tv.superawesome.lib.savideoplayer.media.SAMediaControl;
import tv.superawesome.lib.savideoplayer.media.SAMediaControlDelegate;

/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
public class SAVideoActivity extends Activity implements SAMediaControlDelegate {

    // the ad
    private SAAd ad = null;

    // the internal loader
//    private static SASession session = null;
//    private SAEvents events = null;

    private RelativeLayout parent = null;
    private SAMediaController chrome;
    private ImageButton closeButton = null;
    private SAVideoPlayer videoPlayer = null;

    private Long currentClickThreshold = 0L;

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
//        final SAInterface listenerL = SAVideoAd.getListener();
        final boolean isParentalGateEnabledL = SAVideoAd.getIsParentalGateEnabled();
        final boolean shouldShowCloseButtonL = SAVideoAd.getShouldShowCloseButton();

//        final boolean shouldAutomaticallyCloseAtEndL = SAVideoAd.getShouldAutomaticallyCloseAtEnd();
        final boolean shouldShowSmallClickButtonL = SAVideoAd.getShouldShowSmallClickButton();
        final SAOrientation orientationL = SAVideoAd.getOrientation();
        final boolean isMoatLimitingEnabledL = SAVideoAd.getMoatLimitingState();
        Bundle bundle = getIntent().getExtras();
        String adString = bundle.getString("ad");
        ad = new SAAd(SAJsonParser.newObject(adString));

        // make sure direction is locked
        switch (orientationL) {
            case ANY: setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); break;
            case PORTRAIT: setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); break;
            case LANDSCAPE: setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); break;
        }

//        // start events
//        events = new SAEvents();
//        events.setAd(this, session, ad);
//        if (!isMoatLimitingEnabledL) {
//            events.disableMoatLimiting();
//        }

        // create main content for activity
        parent = new RelativeLayout(this);
        parent.setId(SAUtils.randomNumberBetween(1000000, 1500000));
        parent.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(parent);

        chrome = new SAMediaController(this);
        chrome.padlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
                startActivity(browserIntent);
            }
        });

        videoPlayer = new SAVideoPlayer(this);
        videoPlayer.shouldShowPadlock(ad.isPadlockVisible);
        videoPlayer.setShouldShowSmallClickButton(shouldShowSmallClickButtonL);
        videoPlayer.setMediaControl(SAVideoAd.control);
        videoPlayer.setChromeControl(chrome);
        parent.addView(videoPlayer);

        // create the close button
        closeButton = new ImageButton(this);
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap());
        closeButton.setPadding(0, 0, 0, 0);
        closeButton.setBackgroundColor(Color.TRANSPARENT);
        closeButton.setVisibility(shouldShowCloseButtonL ? View.VISIBLE : View.GONE);
        closeButton.setScaleType(ImageView.ScaleType.FIT_XY);
        float fp = SAUtils.getScaleFactor(this);
        RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams((int) (30 * fp), (int) (30* fp));
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeButton.setLayoutParams(buttonLayout);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        parent.addView(closeButton);

        SAVideoAd.control.addDelegate(this);

//        videoPlayer.setEventListener(new SAVideoPlayerEventInterface() {
//            @Override
//            public void saVideoPlayerDidReceiveEvent(SAVideoPlayerEvent saVideoPlayerEvent, int time, int duration) {
//                switch (saVideoPlayerEvent) {
//
//                    case Video_Prepared: {
//
//                        break;
//                    }
//                    case Video_Start: {
//
////                        Log.d("SuperAwesome", "EVENT: Video_Start");
//
//                        // TODO: FIX THIS
////                        boolean result = events.startMoatTrackingForVideoPlayer(videoPlayer.getVideoView(), duration);
//
////                        // send callback
////                        if (listenerL != null) {
////                            listenerL.onEvent(ad.placementId, SAEvent.adShown);
////                        } else {
////                            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adShown");
////                        }
////
////                        // send vast events - including impression
////                        events.triggerVASTImpressionEvent();
////                        events.triggerVASTStartEvent();
////                        events.triggerVASTCreativeViewEvent();
////
////                        // moat
////                        events.sendMoatPlayingEvent(time);
////                        events.sendMoatStartEvent(time);
//
//                        break;
//                    }
//                    case Video_2s: {
////                        Log.d("SuperAwesome", "EVENT: Video_2s");
//
//                        // TODO: FIX THIS
////                        boolean isViewable = events.isChildInRect(videoPlayer);
////                        if (isViewable) {
////                            events.triggerViewableImpressionEvent();
////                        }
//                        break;
//                    }
//                    case Video_1_4: {
////                        Log.d("SuperAwesome", "EVENT: Video_1_4");
//
////                        events.sendMoatFirstQuartileEvent(time);
////                        events.triggerVASTFirstQuartileEvent();
//                        break;
//                    }
//                    case Video_1_2: {
////                        Log.d("SuperAwesome", "EVENT: Video_1_2");
//
////                        events.sendMoatMidpointEvent(time);
////                        events.triggerVASTMidpointEvent();
//                        break;
//                    }
//                    case Video_3_4: {
////                        Log.d("SuperAwesome", "EVENT: Video_3_4");
//
////                        events.sendMoatThirdQuartileEvent(time);
////                        events.triggerVASTThirdQuartileEvent();
//                        break;
//                    }
//                    case Video_End: {
////                        Log.d("SuperAwesome", "EVENT: Video_End");
//
////                        // send events
////                        events.sendMoatCompleteEvent(duration);
////                        events.triggerVASTCompleteEvent();
////
////                        // send an ad ended event
////                        if (listenerL != null) {
////                            listenerL.onEvent(ad.placementId, SAEvent.adEnded);
////                        } else {
////                            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adEnded");
////                        }
//
////                        // make btn visible
////                        closeButton.setVisibility(View.VISIBLE);
////
////                        // auto close
////                        if (shouldAutomaticallyCloseAtEndL) {
////                            close();
////                        }
//
//                        break;
//                    }
//                    case Video_15s:{
////                        Log.d("SuperAwesome", "EVENT: Video_15s");
//                        // do nothing
//                        break;
//                    }
//                    case Video_Error: {
////                        Log.d("SuperAwesome", "EVENT: Video_Error");
//
////                        // send events
////                        events.stopMoatTrackingForVideoPlayer();
////                        events.triggerVASTErrorEvent();
////
////                        // ad failed to show
////                        if (listenerL != null) {
////                            listenerL.onEvent(ad.placementId, SAEvent.adFailedToShow);
////                        } else {
////                            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adFailedToShow");
////                        }
//
////                         close this whole
////                        close();
//
//                        break;
//                    }
//                }
//            }
//        });

        videoPlayer.setClickListener(new SAVideoPlayerClickInterface() {
            @Override
            public void onClick(View v) {

                // check to see if there is a click through url
                final String destinationUrl;

                // if the campaign is a CPI one, get the normal CPI url so that
                // we can append the "referrer data" to it (since most likely
                // "click_through" will have a redirect)
                if (ad.campaignType == SACampaignType.CPI) {
                    destinationUrl = ad.creative.clickUrl;
                } else {
                    // TODO: FIX THIS
                    destinationUrl = "";
//                    destinationUrl = events.getVASTClickThroughEvent();
                }

                if (destinationUrl != null) {
                    // check for parental gate on click
                    if (isParentalGateEnabledL) {
                        SAParentalGate.setListener(new SAParentalGate.Interface() {
                            @Override
                            public void parentalGateOpen() {
                                SAVideoActivity.this.pause();
                                // TODO: FIX THIS
//                                events.triggerPgOpenEvent();
                            }

                            @Override
                            public void parentalGateSuccess() {
                                // TODO: FIX THIS
//                                events.triggerPgSuccessEvent();
                                click(destinationUrl);
                                SAVideoActivity.this.pause();
                            }

                            @Override
                            public void parentalGateFailure() {
                                // TODO: FIX THIS
//                                events.triggerPgFailEvent();
                                SAVideoActivity.this.resume();
                            }

                            @Override
                            public void parentalGateCancel() {
                                // TODO: FIX THIS
//                                events.triggerPgCloseEvent();
                                SAVideoActivity.this.resume();
                            }
                        });
                        SAParentalGate.show(SAVideoActivity.this);
                    } else {
                        click(destinationUrl);
                    }
                }
            }
        });

        Log.d("SuperAwesome", "EVENT: Video_Prepared");
        videoPlayer.play(this, ad.creative.details.media.path);
    }

    /**
     * Overridden "onBackPressed" method of the activity
     * Depending on how the ad is customised, this will lock the back button or it will allow it.
     * If it allows it, it's going to also send an "adClosed" event back to the SDK user
     */
    @Override
    public void onBackPressed() {
        boolean isBackButtonEnabledL = SAVideoAd.getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            close();
            super.onBackPressed();
        }
    }

    /**********************************************************************************************
     * Custom instance methods
     **********************************************************************************************/

    /**
     * Method that handles a click on the ad surface
     */
    public void click(final String destination) {

        boolean isBumperPageEnabledL = SAVideoAd.getIsBumperPageEnabled();

        if (isBumperPageEnabledL || ad.creative.bumper) {
            SABumperPage.setListener(new SABumperPage.Interface() {
                @Override
                public void didEndBumper() {
                    handleUrl(destination);
                }
            });
            SABumperPage.play(this);
        } else {
            handleUrl(destination);
        }
    }

    private void handleUrl (String destination) {

        Long currentTime = System.currentTimeMillis()/1000;
        Long diff = Math.abs(currentTime - currentClickThreshold);

        if (diff < SADefaults.defaultClickThreshold()) {
            Log.d("AwesomeAds-2", "Current diff is " + diff);
            return;
        }

        currentClickThreshold = currentTime;

        Log.d("AwesomeAds-2", "Going to " + destination);

        // get local
        SAInterface listenerL = SAVideoAd.getListener();
        // call listener
        if (listenerL != null) {
            listenerL.onEvent(ad.placementId, SAEvent.adClicked);
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adClicked");
        }

        // send vast click tracking events
        // TODO: FIX THIS
//        events.triggerVASTClickTrackingEvent();

        // send only in case of CPI where we'll use the direct click url
        // TODO: FIX THIS
//        if (ad.campaignType == SACampaignType.CPI) {
//            events.triggerVASTClickThroughEvent();
//        }

        // if it's a CPI campaign
        destination += ad.campaignType == SACampaignType.CPI ? ("&referrer=" + ad.creative.referral.writeToReferralQuery()) : "";

        // start browser
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(destination)));
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Method that handles what should happen when the video pauses
     */
    public void pause () {
        try {
            SAVideoAd.control.pause();
        } catch (Exception ignored) {}
    }

    /**
     * Method that handles what should happen when the video resumes
     */
    public void resume () {
        try {
            SAVideoAd.control.resume();
        } catch (Exception ignored) {}
    }

    /**
     * Method that closes the interstitial ad
     */
    private void close() {

        // get local
        SAInterface listenerL = SAVideoAd.getListener();

        // close moat
        // TODO: FIX THIS
//        events.stopMoatTrackingForVideoPlayer();

        // call listener
        if (listenerL != null) {
            listenerL.onEvent(ad.placementId, SAEvent.adClosed);
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adClosed");
        }

        // close
        SAParentalGate.close();

        // delete the ad
        SAVideoAd.removeAd(ad.placementId);

        // close the video player
        videoPlayer.close();

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SAMediaControlDelegate
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPrepared(SAMediaControl saMediaControl) {

    }

    @Override
    public void onTimeUpdated(SAMediaControl saMediaControl, int time, int duration) {
        chrome.setTime(time, duration);
    }

    @Override
    public void onMediaComplete(SAMediaControl saMediaControl, int i, int i1) {
        // make btn visible
        closeButton.setVisibility(View.VISIBLE);

        // auto close
        if (SAVideoAd.getShouldAutomaticallyCloseAtEnd()) {
            close();
        }
//        saMediaControl.removeDelegate(this);
    }

    @Override
    public void onError(SAMediaControl saMediaControl, Throwable throwable, int i, int i1) {
//        saMediaControl.removeDelegate(this);
        close();
    }
}
