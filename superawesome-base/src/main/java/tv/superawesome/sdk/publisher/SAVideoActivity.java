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
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.saparentalgate.SAParentalGate;
import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savideoplayer.AwesomeVideoPlayer;
import tv.superawesome.lib.savideoplayer.VideoPlayer;
import tv.superawesome.sdk.publisher.video.SAChromeControl;
import tv.superawesome.sdk.publisher.video.VideoUtils;

/**
 * Class that abstracts away the process of loading & displaying a video type Ad.
 * A subclass of the Android "Activity" class.
 */
public class SAVideoActivity extends Activity implements VideoPlayer.Listener {

    // the ad
    private SAAd ad = null;

    private RelativeLayout parent = null;
    private SAChromeControl chrome;
    private ImageButton closeButton = null;
    private AwesomeVideoPlayer videoPlayer = null;

    private SAInterface listenerRef = null;

    /**
     * Overridden "onCreate" method, part of the Activity standard set of methods.
     * Here is the part where the activity / video ad gets configured
     *
     * @param savedInstanceState previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listenerRef = SAVideoAd.getListener();

        // local versions of the static vars
        final boolean shouldShowCloseButtonL = SAVideoAd.getShouldShowCloseButton();
        final boolean shouldShowSmallClickButtonL = SAVideoAd.getShouldShowSmallClickButton();
        final SAOrientation orientationL = SAVideoAd.getOrientation();
        String adString = getIntent().getStringExtra("ad");
        ad = new SAAd(SAJsonParser.newObject(adString));

        // make sure direction is locked
        switch (orientationL) {
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

        chrome = new SAChromeControl(this);
        chrome.shouldShowPadlock(ad.isPadlockVisible);
        chrome.setShouldShowSmallClickButton(shouldShowSmallClickButtonL);
        chrome.setClickListener(SAVideoAd.clickEvents);
        chrome.padlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
                startActivity(browserIntent);
            }
        });

        videoPlayer = new AwesomeVideoPlayer(this);
        videoPlayer.setLayoutParams(params);
        videoPlayer.setControl(SAVideoAd.control);
        videoPlayer.setChrome(chrome);
        videoPlayer.setBackgroundColor(Color.BLACK);
        parent.addView(videoPlayer);

        videoPlayer.setListener(this);

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

        try {
            Uri fileUri = new VideoUtils().getUriFromFile(this, ad.creative.details.media.path);
            SAVideoAd.control.playAsync(this, fileUri);
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
        boolean isBackButtonEnabledL = SAVideoAd.getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            close();
            super.onBackPressed();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Custom instance methods
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

        // delete the ad
        SAVideoAd.removeAd(ad.placementId);

        // close the video player
        videoPlayer.close();
        videoPlayer.destroy();

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VideoPlayer.Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPrepared(VideoPlayer videoPlayer, int time, int duration) {

        SAVideoAd.videoEvents.prepare(videoPlayer, time, duration);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adShown);
        }
    }

    @Override
    public void onTimeUpdated(VideoPlayer videoPlayer, int time, int duration) {
        SAVideoAd.videoEvents.time(videoPlayer, time, duration);
    }

    @Override
    public void onComplete(VideoPlayer videoPlayer, int time, int duration) {
        SAVideoAd.videoEvents.complete(videoPlayer, time, duration);
        closeButton.setVisibility(View.VISIBLE);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adEnded);
        }

        if (SAVideoAd.getShouldAutomaticallyCloseAtEnd()) {
            close();
        }
    }

    @Override
    public void onError(VideoPlayer videoPlayer, Throwable throwable, int time, int duration) {
        SAVideoAd.videoEvents.error(videoPlayer, time, duration);

        if (listenerRef != null) {
            listenerRef.onEvent(ad.placementId, SAEvent.adFailedToShow);
        }

        close();
    }

}
