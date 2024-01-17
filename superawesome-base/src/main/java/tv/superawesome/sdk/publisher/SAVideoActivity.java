/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.publisher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import tv.superawesome.lib.saclosewarning.SACloseWarning;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.saparentalgate.SAParentalGate;
import tv.superawesome.lib.satiming.SACountDownTimer;
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.publisher.state.CloseButtonState;
import tv.superawesome.sdk.publisher.video.AdVideoPlayerControllerView;
import tv.superawesome.sdk.publisher.video.VideoUtils;
import tv.superawesome.sdk.publisher.videoPlayer.ExoPlayerController;
import tv.superawesome.sdk.publisher.videoPlayer.IVideoPlayer;
import tv.superawesome.sdk.publisher.videoPlayer.IVideoPlayerController;
import tv.superawesome.sdk.publisher.videoPlayer.VideoPlayer;
import tv.superawesome.sdk.publisher.videoPlayer.VideoPlayerController;

/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
public class SAVideoActivity extends Activity implements
        IVideoPlayer.Listener,
        SAVideoEvents.Listener,
        SAVideoClick.Listener {

    // fed-in data
    private SAAd ad = null;
    private VideoConfig videoConfig = null;
    private SAInterface listenerRef = null;
    // derived objects
    private IVideoPlayerController control = null;
    private SAVideoEvents videoEvents = null;
    private SAVideoClick videoClick = null;

    private ImageButton closeButton = null;
    private ImageButton volumeButton = null;
    private VideoPlayer videoPlayer = null;

    private Boolean completed = false;
    private SACountDownTimer closeButtonDelayTimer;
    private SACountDownTimer freezeFailSafeTimer;
    private final SACountDownTimer failSafeTimer = new SACountDownTimer();

    private final SACountDownTimer.Listener failSafeListener = () -> {
        didFailSafeTimeOut();
        SAVideoAd.getPerformanceMetrics().trackCloseButtonFallbackShown(ad);
    };

    private final SACountDownTimer.Listener freezeFailSafeListener = () -> {
        didFailSafeTimeOut();
        SAVideoAd.getPerformanceMetrics().trackFreezeFallbackShown(ad);
    };

    private final Long freezeTimerTimeout =
            AwesomeAds.featureFlagsManager.getFeatureFlags().getVideoStabilityFailsafeTimeout();

    private static final Long FREEZE_TIMER_INTERVAL = 500L;

    /**
     * Overridden "onCreate" method, part of the Activity standard set of methods.
     * Here is the part where the activity / video ad gets configured
     *
     * @param savedInstanceState previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // get values from the intent
        Intent myIntent = getIntent();
        ad = myIntent.getParcelableExtra("ad");
        videoConfig = myIntent.getParcelableExtra("config");

        // get listener & events from static ad context
        listenerRef = SAVideoAd.getListener();
        SAEvents events = SAVideoAd.getEvents();

        // setup derived objects
        videoEvents = new SAVideoEvents(events, this);
        videoClick = new SAVideoClick(ad, videoConfig.isParentalGateEnabled, videoConfig.isBumperPageEnabled, events);
        videoClick.setListener(this);
        // make sure direction is locked
        switch (videoConfig.orientation) {
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

        int size = RelativeLayout.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);

        // create main content for activity
        RelativeLayout parent = new RelativeLayout(this);
        parent.setId(SAUtils.randomNumberBetween(1000000, 1500000));
        parent.setLayoutParams(params);
        setContentView(parent);

        AdVideoPlayerControllerView chrome = new AdVideoPlayerControllerView(this);
        chrome.shouldShowPadlock(videoConfig.shouldShowPadlock);
        chrome.setShouldShowSmallClickButton(videoConfig.shouldShowSmallClick);
        chrome.setClickListener(view -> {
            videoClick.handleAdClick(view, null);
            sendEvent(SAEvent.adClicked);
        });
        chrome.padlock.setOnClickListener(view -> videoClick.handleSafeAdClick(view));

        videoPlayer = new VideoPlayer(this);
        if (isExoPlayerEnabled()) {
            control = new ExoPlayerController(videoPlayer);
        } else {
            control = new VideoPlayerController();
        }
        videoPlayer.setLayoutParams(params);
        videoPlayer.setController(control);
        videoPlayer.setControllerView(chrome);
        videoPlayer.setBackgroundColor(Color.BLACK);
        videoPlayer.setContentDescription("Ad content");
        parent.addView(videoPlayer);

        videoPlayer.setListener(this);

        // create the close button
        closeButton = new ImageButton(this);
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap());
        closeButton.setPadding(0, 0, 0, 0);
        closeButton.setBackgroundColor(Color.TRANSPARENT);
        closeButton.setScaleType(ImageView.ScaleType.FIT_XY);
        closeButton.setVisibility(videoConfig.closeButtonState == CloseButtonState.VisibleImmediately.INSTANCE ?
                View.VISIBLE : View.GONE);
        float scale = SAUtils.getScaleFactor(this);
        RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams((int) (30 * scale), (int) (30 * scale));
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeButton.setLayoutParams(buttonLayout);
        closeButton.setOnClickListener(v -> onCloseAction());
        closeButton.setContentDescription("Close");
        parent.addView(closeButton);

        // create volume button
        volumeButton = new ImageButton(this);
        setMuted(videoConfig.shouldMuteOnStart);
        volumeButton.setPadding(0, 0, 0, 0);
        volumeButton.setBackgroundColor(Color.TRANSPARENT);
        volumeButton.setScaleType(ImageView.ScaleType.FIT_XY);
        volumeButton.setVisibility(videoConfig.shouldMuteOnStart ? View.VISIBLE : View.GONE);
        RelativeLayout.LayoutParams volumeLayout = new RelativeLayout.LayoutParams((int) (40 * scale), (int) (40 * scale));
        volumeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        volumeLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        volumeButton.setLayoutParams(volumeLayout);
        volumeButton.setOnClickListener(v -> onVolumeAction());
        volumeButton.setContentDescription("Volume");
        parent.addView(volumeButton);

        try {
            Uri fileUri = new VideoUtils().getUriFromFile(this, ad.creative.details.media.path);
            control.playAsync(this, fileUri);
        } catch (Exception e) {
            Log.e("SuperAwesome", "Unable to play video", e);
            finish();
            return;
        }

        if (videoConfig.closeButtonState instanceof CloseButtonState.Custom) {
            closeButtonDelayTimer = new SACountDownTimer(videoConfig.closeButtonDelayTimer);
            closeButtonDelayTimer.setListener(() -> closeButton.setVisibility(View.VISIBLE));
        }

        failSafeTimer.setListener(failSafeListener);
    }

    /**
     // Lifecycle Methods
     */

    @Override
    protected void onResume() {
        super.onResume();
        if (control != null && control.getCurrentIVideoPosition() > 0) {
            control.start();
        }
        if (freezeFailSafeTimer != null) {
            freezeFailSafeTimer.start();
        }
        failSafeTimer.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (closeButtonDelayTimer != null) {
            closeButtonDelayTimer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (closeButtonDelayTimer != null) {
            closeButtonDelayTimer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        SAParentalGate.close();
        SACloseWarning.close();
        failSafeTimer.stop();
        if (closeButtonDelayTimer != null) {
            closeButtonDelayTimer.stop();
        }
        if (freezeFailSafeTimer != null) {
            freezeFailSafeTimer.stop();
            freezeFailSafeTimer = null;
        }
        super.onDestroy();

        // close the video player
        videoPlayer.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (control != null) {
            control.pause();
        }
        if (freezeFailSafeTimer != null) {
            freezeFailSafeTimer.pause();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        videoPlayer.updateLayout(width, height);
    }

    /**
     * Overridden "onBackPressed" method of the activity
     * Depending on how the ad is customised, this will lock the back button or it will allow it.
     * If it allows it, it's going to also send an "adClosed" event back to the SDK user
     */
    @Override
    public void onBackPressed() {
        if (videoConfig.isBackButtonEnabled) {
            onCloseAction();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // IVideoPlayer.Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPrepared(@NonNull IVideoPlayer videoPlayer, int time, int duration) {

        videoEvents.prepare(videoPlayer, time, duration);

        sendEvent(SAEvent.adShown);

        failSafeTimer.stop();
        if (closeButtonDelayTimer != null) {
            closeButtonDelayTimer.start();
        }
    }

    @Override
    public void onTimeUpdated(@NonNull IVideoPlayer videoPlayer, int time, int duration) {
        if (freezeFailSafeTimer != null) {
            freezeFailSafeTimer.stop();
        }
        videoEvents.time(videoPlayer, time, duration);

        freezeFailSafeTimer = new SACountDownTimer(freezeTimerTimeout, FREEZE_TIMER_INTERVAL);
        freezeFailSafeTimer.setListener(freezeFailSafeListener);
        freezeFailSafeTimer.start();
    }

    @Override
    public void onComplete(@NonNull IVideoPlayer videoPlayer, int time, int duration) {
        completed = true;
        videoEvents.complete(videoPlayer, time, duration);
        closeButton.setVisibility(View.VISIBLE);

        sendEvent(SAEvent.adEnded);

        if (videoConfig.shouldCloseAtEnd) {
            close();
        }

        if (freezeFailSafeTimer != null) {
            freezeFailSafeTimer.stop();
            freezeFailSafeTimer = null;
        }
    }

    @Override
    public void onError(@NonNull IVideoPlayer videoPlayer, @NonNull Throwable throwable, int time, int duration) {
        videoEvents.error(videoPlayer, time, duration);

        sendEvent(SAEvent.adFailedToShow);

        if (freezeFailSafeTimer != null) {
            freezeFailSafeTimer.stop();
            freezeFailSafeTimer = null;
        }

        close();
    }

    // Failsafe listener

    private void didFailSafeTimeOut() {
        Log.d("SuperAwesome", "Detected frozen video, failsafe mechanism active");
        // Override the close button click behaviour when showing the close button as
        // a fail safe
        closeButton.setOnClickListener(v -> failSafeCloseAction());
        closeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPlay(@NonNull IVideoPlayer player) {
        sendEvent(SAEvent.adPlaying);
    }

    @Override
    public void onPause(@NonNull IVideoPlayer player) {
        sendEvent(SAEvent.adPaused);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Custom private methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method that closes the ad via the fail safe timer
     */
    private void failSafeCloseAction() {
        sendEvent(SAEvent.adEnded);
        close();
    }

    private void onCloseAction() {
        if (videoConfig.shouldShowCloseWarning && !completed) {
            if (control != null) {
                control.pause();
            }
            SACloseWarning.setListener(new SACloseWarning.Interface() {
                @Override
                public void onResumeSelected() {
                    control.start();
                }

                @Override
                public void onCloseSelected() {
                    close();
                }
            });
            SACloseWarning.show(this);
        } else {
            close();
        }
    }

    private void onVolumeAction() {
        if (control != null) {
            setMuted(!control.isMuted());
        }
    }

    private void setMuted(Boolean muted) {
        volumeButton.setTag(muted ? "MUTED" : "UNMUTED");
        volumeButton.setImageBitmap(
            muted ? SAImageUtils.createVolumeOffBitmap() : SAImageUtils.createVolumeOnBitmap()
        );
        if (control != null) {
            control.setMuted(muted);
        }
    }

    /**
     * Method that closes the Video ad
     */
    private void close() {

        videoEvents.listener = null;
        failSafeTimer.stop();
        if (closeButtonDelayTimer != null) {
            closeButtonDelayTimer.stop();
        }

        // call listener
        sendEvent(SAEvent.adClosed);

        // close
        SACloseWarning.close();
        SAParentalGate.close();

        if (videoClick != null) {
            videoClick.close();
        }

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        removeListenerRef();
    }

    private void removeListenerRef() {
        listenerRef = null;
    }

    @Override
    public void hasBeenVisible() {
        closeButton.setVisibility(videoConfig.closeButtonState.isVisible() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void didRequestPlaybackPause() {
        if (control != null) {
            control.pause();
        }
    }

    @Override
    public void didRequestPlaybackResume() {
        if (control != null) {
            control.start();
        }
    }

    @VisibleForTesting
    public void forceVideoPause() {
        control.pause();
    }

    private void sendEvent(SAEvent event) {
        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, event);
            Log.d("SAVideoActivity", "Event callback: " + event);
        }
    }

    private boolean isExoPlayerEnabled() {
        return AwesomeAds.featureFlagsManager.getFeatureFlags().isExoPlayerEnabled();
    }
}