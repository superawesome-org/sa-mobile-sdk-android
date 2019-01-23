/**
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.saparentalgate.SAParentalGate;
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savideoplayer.AwesomeMediaControl;
import tv.superawesome.lib.savideoplayer.AwesomeVideoPlayer;
import tv.superawesome.lib.savideoplayer.MediaControl;
import tv.superawesome.lib.savideoplayer.VideoPlayer;
import tv.superawesome.sdk.publisher.video.SAAdChromeControl;
import tv.superawesome.sdk.publisher.video.VideoUtils;

/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
public class SAVideoActivity extends Activity implements VideoPlayer.Listener {

    // fed-in data
    private SAAd ad = null;
    private Config config = null;
    private SAEvents events = null;
    private SAInterface listenerRef = null;
    // derived objects
    private MediaControl control = new AwesomeMediaControl();
    private SAVideoEvents videoEvents = null;
    private SAVideoClick videoClick = null;

    private RelativeLayout parent = null;
    private SAAdChromeControl chrome;
    private ImageButton closeButton = null;
    private AwesomeVideoPlayer videoPlayer = null;

    /**
     * Overridden "onCreate" method, part of the Activity standard set of methods.
     * Here is the part where the activity / video ad gets configured
     *
     * @param savedInstanceState previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get values from the intent
        Intent myIntent = getIntent();
        ad = myIntent.getParcelableExtra("ad");
        config = myIntent.getParcelableExtra("config");

        // get listener & events from static ad context
        listenerRef = SAVideoAd.getListener();
        events = SAVideoAd.getEvents();

        // setup derived objects
        videoEvents = new SAVideoEvents(events);
        videoClick = new SAVideoClick(ad, config.isParentalGateEnabled, config.isBumperPageEnabled, events);

        // make sure direction is locked
        switch (config.orientation) {
            case ANY: setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); break;
            case PORTRAIT: setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); break;
            case LANDSCAPE: setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); break;
        }

        int size = RelativeLayout.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);

        // create main content for activity
        parent = new RelativeLayout(this);
        parent.setId(SAUtils.randomNumberBetween(1000000, 1500000));
        parent.setLayoutParams(params);
        setContentView(parent);

        chrome = new SAAdChromeControl(this);
        chrome.shouldShowPadlock(config.shouldShowPadlock);
        chrome.setShouldShowSmallClickButton(config.shouldShowSmallClick);
        chrome.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.handleAdClick(view);
                listenerRef.onEvent(ad.placementId, SAEvent.adClicked);
            }
        });
        chrome.padlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.handleSafeAdClick(view);
            }
        });

        videoPlayer = new AwesomeVideoPlayer(this);
        videoPlayer.setLayoutParams(params);
        videoPlayer.setControl(control);
        videoPlayer.setChrome(chrome);
        videoPlayer.setBackgroundColor(Color.BLACK);
        parent.addView(videoPlayer);

        videoPlayer.setListener(this);

        // create the close button
        closeButton = new ImageButton(this);
        closeButton.setImageBitmap(SAImageUtils.createCloseButtonBitmap());
        closeButton.setPadding(0, 0, 0, 0);
        closeButton.setBackgroundColor(Color.TRANSPARENT);
        closeButton.setVisibility(config.shouldShowCloseButton ? View.VISIBLE : View.GONE);
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

        try {
            Uri fileUri = new VideoUtils().getUriFromFile(this, ad.creative.details.media.path);
            control.playAsync(this, fileUri);
        } catch (Exception ignored) {}
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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
       if (config.isBackButtonEnabled) {
            close();
            super.onBackPressed();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VideoPlayer.Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPrepared(VideoPlayer videoPlayer, int time, int duration) {

        videoEvents.prepare(videoPlayer, time, duration);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adShown);
        }
    }

    @Override
    public void onTimeUpdated(VideoPlayer videoPlayer, int time, int duration) {
        videoEvents.time(videoPlayer, time, duration);
    }

    @Override
    public void onComplete(VideoPlayer videoPlayer, int time, int duration) {
        videoEvents.complete(videoPlayer, time, duration);
        closeButton.setVisibility(View.VISIBLE);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adEnded);
        }

        if (config.shouldCloseAtEnd) {
            close();
        }
    }

    @Override
    public void onError(VideoPlayer videoPlayer, Throwable throwable, int time, int duration) {
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
     * Method that closes the interstitial ad
     */
    private void close() {

        // call listener
        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adClosed);
        }

        // close
        SAParentalGate.close();

        // close the video player
        videoPlayer.destroy();

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}

class Config implements Parcelable {

    boolean shouldShowPadlock;
    boolean isParentalGateEnabled;
    boolean isBumperPageEnabled;
    boolean shouldShowSmallClick;
    boolean isBackButtonEnabled;
    boolean shouldCloseAtEnd;
    boolean shouldShowCloseButton;
    SAOrientation orientation;

    Config(boolean shouldShowPadlock,
           boolean isParentalGateEnabled,
           boolean isBumperPageEnabled,
           boolean shouldShowSmallClick,
           boolean isBackButtonEnabled,
           boolean shouldCloseAtEnd,
           boolean shouldShowCloseButton,
           SAOrientation orientation) {
        this.shouldShowPadlock = shouldShowPadlock;
        this.isParentalGateEnabled = isParentalGateEnabled;
        this.isBumperPageEnabled = isBumperPageEnabled;
        this.shouldShowSmallClick = shouldShowSmallClick;
        this.isBackButtonEnabled = isBackButtonEnabled;
        this.shouldCloseAtEnd = shouldCloseAtEnd;
        this.shouldShowCloseButton = shouldShowCloseButton;
        this.orientation = orientation;
    }

    protected Config(Parcel in) {
        shouldShowPadlock = in.readByte() != 0;
        isParentalGateEnabled = in.readByte() != 0;
        isBumperPageEnabled = in.readByte() != 0;
        shouldShowSmallClick = in.readByte() != 0;
        isBackButtonEnabled = in.readByte() != 0;
        shouldCloseAtEnd = in.readByte() != 0;
        shouldShowCloseButton = in.readByte() != 0;
        orientation = SAOrientation.fromValue(in.readInt());
    }

    public static final Creator<Config> CREATOR = new Creator<Config>() {
        @Override
        public Config createFromParcel(Parcel in) {
            return new Config(in);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
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
        parcel.writeByte((byte) (shouldShowCloseButton ? 1 : 0));
        parcel.writeInt(orientation.ordinal());
    }
}
