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
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

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
    private final IVideoPlayerController control = new VideoPlayerController();
    private SAVideoEvents videoEvents = null;
    private SAVideoClick videoClick = null;

    private ImageButton closeButton = null;
    private ImageButton volumeButton = null;
    private VideoPlayer videoPlayer = null;

    private Boolean completed = false;
    private SACountDownTimer failSafeTimer = new SACountDownTimer();
    private SACountDownTimer closeButtonDelayTimer;

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
            control.pause();
            videoClick.handleAdClick(view, null);
            if (listenerRef != null) {
                listenerRef.onEvent(ad.placementId, SAEvent.adClicked);
            }
            Log.d("SAVideoActivity", "Event callback: " + SAEvent.adClicked);
        });
        chrome.padlock.setOnClickListener(view -> videoClick.handleSafeAdClick(view));

        videoPlayer = new VideoPlayer(this);
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
        } catch (Exception ignored) {
        }

        if (videoConfig.closeButtonState instanceof CloseButtonState.Custom) {
            closeButtonDelayTimer = new SACountDownTimer(videoConfig.closeButtonDelayTimer);
            closeButtonDelayTimer.setListener(() -> {
                closeButton.setVisibility(View.VISIBLE);
            });
        }

        failSafeTimer.setListener(() -> {
            // Override the close button click behaviour when showing the close button as
            // a fail safe
            closeButton.setOnClickListener(v -> failSafeCloseAction());
            closeButton.setVisibility(View.VISIBLE);
            SAVideoAd.getPerformanceMetrics().trackCloseButtonFallbackShown(ad);
        });
    }

    /**
     // Lifecycle Methods
     */

    @Override
    protected void onStart() {
        super.onStart();
        if (control.getCurrentIVideoPosition() > 0) {
            control.start();
        }
        failSafeTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        failSafeTimer.pause();
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
        super.onDestroy();

        // close the video player
        videoPlayer.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        control.pause();
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
    // VideoPlayer.VisibilityListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPrepared(@NonNull IVideoPlayer videoPlayer, int time, int duration) {

        videoEvents.prepare(videoPlayer, time, duration);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adShown);
            Log.d("SAVideoActivity", "Event callback: " + SAEvent.adShown);
        }
        failSafeTimer.stop();
        if (closeButtonDelayTimer != null) {
            closeButtonDelayTimer.start();
        }
    }

    @Override
    public void onTimeUpdated(@NonNull IVideoPlayer videoPlayer, int time, int duration) {
        videoEvents.time(videoPlayer, time, duration);
    }

    @Override
    public void onComplete(@NonNull IVideoPlayer videoPlayer, int time, int duration) {
        completed = true;
        videoEvents.complete(videoPlayer, time, duration);
        closeButton.setVisibility(View.VISIBLE);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adEnded);
            Log.d("SAVideoActivity", "Event callback: " + SAEvent.adEnded);
        }

        if (videoConfig.shouldCloseAtEnd) {
            close();
        }
    }

    @Override
    public void onError(@NonNull IVideoPlayer videoPlayer, @NonNull Throwable throwable, int time, int duration) {
        videoEvents.error(videoPlayer, time, duration);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adFailedToShow);
        }

        close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Custom private methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method that closes the ad via the fail safe timer
     */
    private void failSafeCloseAction() {
        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adEnded);
            Log.d("SAVideoActivity", "Event callback: " + SAEvent.adEnded);
        }
        close();
    }
    private void onCloseAction() {
        if (videoConfig.shouldShowCloseWarning && !completed) {
            control.pause();
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
        setMuted(!control.isMuted());
    }

    private void setMuted(Boolean muted) {
        volumeButton.setTag(muted ? "MUTED" : "UNMUTED");
        volumeButton.setImageBitmap(
            muted ? SAImageUtils.createVolumeOffBitmap() : SAImageUtils.createVolumeOnBitmap()
        );
        control.setMuted(muted);
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
        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adClosed);
            Log.d("SAVideoActivity", "Event callback: " + SAEvent.adClosed);
        }

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
        control.pause();
    }

    @Override
    public void didRequestPlaybackResume() {
        control.start();
    }
}

class VideoConfig implements Parcelable {

    final boolean shouldShowPadlock;
    final boolean isParentalGateEnabled;
    final boolean isBumperPageEnabled;
    final boolean shouldShowSmallClick;
    final boolean isBackButtonEnabled;
    final boolean shouldCloseAtEnd;
    final boolean shouldMuteOnStart;
    final CloseButtonState closeButtonState;
    final long closeButtonDelayTimer;
    final boolean shouldShowCloseWarning;
    final SAOrientation orientation;

    VideoConfig(boolean shouldShowPadlock,
                boolean isParentalGateEnabled,
                boolean isBumperPageEnabled,
                boolean shouldShowSmallClick,
                boolean isBackButtonEnabled,
                boolean shouldCloseAtEnd,
                boolean shouldMuteOnStart,
                CloseButtonState closeButtonState,
                long closeButtonDelayTimer,
                boolean shouldShowCloseWarning,
                SAOrientation orientation) {
        this.shouldShowPadlock = shouldShowPadlock;
        this.isParentalGateEnabled = isParentalGateEnabled;
        this.isBumperPageEnabled = isBumperPageEnabled;
        this.shouldShowSmallClick = shouldShowSmallClick;
        this.isBackButtonEnabled = isBackButtonEnabled;
        this.shouldCloseAtEnd = shouldCloseAtEnd;
        this.shouldMuteOnStart = shouldMuteOnStart;
        this.closeButtonState = closeButtonState;
        this.closeButtonDelayTimer = closeButtonDelayTimer;
        this.shouldShowCloseWarning = shouldShowCloseWarning;
        this.orientation = orientation;
    }

    protected VideoConfig(Parcel in) {
        shouldShowPadlock = in.readByte() != 0;
        isParentalGateEnabled = in.readByte() != 0;
        isBumperPageEnabled = in.readByte() != 0;
        shouldShowSmallClick = in.readByte() != 0;
        isBackButtonEnabled = in.readByte() != 0;
        shouldCloseAtEnd = in.readByte() != 0;
        shouldMuteOnStart = in.readByte() != 0;
        int closeState = in.readInt();
        closeButtonDelayTimer = in.readLong();
        closeButtonState = CloseButtonState.Companion.fromInt(closeState, closeButtonDelayTimer);
        shouldShowCloseWarning = in.readByte() != 0;
        orientation = SAOrientation.fromValue(in.readInt());
    }

    public static final Creator<VideoConfig> CREATOR = new Creator<VideoConfig>() {
        @Override
        public VideoConfig createFromParcel(Parcel in) {
            return new VideoConfig(in);
        }

        @Override
        public VideoConfig[] newArray(int size) {
            return new VideoConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (shouldShowPadlock ? 1 : 0));
        parcel.writeByte((byte) (isParentalGateEnabled ? 1 : 0));
        parcel.writeByte((byte) (isBumperPageEnabled ? 1 : 0));
        parcel.writeByte((byte) (shouldShowSmallClick ? 1 : 0));
        parcel.writeByte((byte) (isBackButtonEnabled ? 1 : 0));
        parcel.writeByte((byte) (shouldCloseAtEnd ? 1 : 0));
        parcel.writeByte((byte) (shouldMuteOnStart ? 1 : 0));
        parcel.writeInt(closeButtonState.getValue());
        parcel.writeLong(closeButtonDelayTimer);
        parcel.writeByte((byte) (shouldShowCloseWarning ? 1 : 0));
        parcel.writeInt(orientation.ordinal());
    }
}
