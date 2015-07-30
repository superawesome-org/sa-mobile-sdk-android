package tv.superawesome.superawesomesdk.views.video;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import tv.superawesome.superawesomesdk.AdManager;
import tv.superawesome.superawesomesdk.R;
import tv.superawesome.superawesomesdk.models.SAAd;
import tv.superawesome.superawesomesdk.views.SAPlacementListener;
import tv.superawesome.superawesomesdk.views.SAPlacementView;

/**
 * Created by connor.leigh-smith on 09/07/15.
 */
public class SAVideoView extends SAPlacementView {

    // The video player.
    private static VideoPlayer mVideoPlayer;

    private RelativeLayout rootView;

    protected SAVideoViewListener videoListener;
    protected ImageButton playButton;
    /* Updated when the mRaidInterstitial declares the ad is ready, after loading it. */
    private boolean isReady;
    /* Set when the user calls display(); the interstitial is shown when both 'isReady' and 'display' are true. */
    private boolean display;
    private boolean playInstantly;


    protected VideoPlayerController mVideoPlayerController;

    public SAVideoView(Context context, String placementID, AdManager adManager) {
        super(context, placementID, adManager);
    }

    public SAVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void show() {
        this.display = true;
        if (this.isReady) {
            this.addView(rootView);
        }
    }

    public void setPlayInstantly(boolean playInstantly) {
        this.playInstantly = playInstantly;
    }

    public void setVideoListener(SAVideoViewListener vl) {
        this.videoListener = vl;
    }

    @Override
    protected void setView(String content) {
        Log.d(TAG, "Loaded VAST: " + content);

        rootView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.video_ad_view, null);
        mVideoPlayer = (VideoPlayer) rootView.findViewById(R.id.videoPlayer);
        playButton = (ImageButton) rootView.findViewById(R.id.playButton);
        final ProgressBar spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);

        mVideoPlayerController = new VideoPlayerController(context, mVideoPlayer, rootView, content);

        mVideoPlayerController.setListener(new SAVideoViewListener() {
            @Override
            public void onAdStart() {
                Log.d(TAG, "SAVideoView ad start");
                spinner.setVisibility(View.GONE);
                if (listener != null) videoListener.onAdStart();
                padlockImage.bringToFront();
            }

            @Override
            public void onAdPause() {
                if (listener != null) videoListener.onAdPause();
            }

            @Override
            public void onAdResume() {
                if (listener != null) videoListener.onAdResume();
            }

            @Override
            public void onAdFirstQuartile() {
                Log.d(TAG, "SAVideoView ad first quartile");
                if (listener != null) videoListener.onAdFirstQuartile();
            }

            @Override
            public void onAdMidpoint() {
                if (listener != null) videoListener.onAdMidpoint();
            }

            @Override
            public void onAdThirdQuartile() {
                if (listener != null) videoListener.onAdThirdQuartile();
            }

            @Override
            public void onAdComplete() {
                if (listener != null) videoListener.onAdComplete();
            }

            @Override
            public void onAdClosed() {
                if (listener != null) videoListener.onAdClosed();
            }

            @Override
            public void onAdSkipped() {
                if (listener != null) videoListener.onAdSkipped();
            }

            @Override
            public void onAdLoaded(SAAd ad) {
                if (listener != null) videoListener.onAdLoaded(ad);
            }

            @Override
            public void onAdError(String message) {
                if (listener != null) videoListener.onAdError(message);
            }
        });

        if (this.playInstantly) {
            mVideoPlayerController.play();
            spinner.setVisibility(View.VISIBLE);
        } else {
            playButton.setVisibility(View.VISIBLE);
        }

        // When Play is clicked, request ads and hide the button.
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoPlayerController.play();
                view.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
            }
        });

        this.isReady = true;
        if (this.display) {
            this.addView(rootView);
        }

        showPadlock();
    }

    private void showPadlock() {
        Log.d(TAG, "Showing padlock");
//        If ad isn't loaded for some reason, or ad is a fallback ad, do not show the padlock.
        if (this.loadedAd == null || this.loadedAd.isFallback) {
            return;
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = 20 * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        int height = 20 * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        int x = getMeasuredWidth() - width;
        int y = getMeasuredHeight() - height;


//        RelativeLayout.LayoutParams padlockParams = new RelativeLayout.LayoutParams(width, height);

        this.padlockImage = (ImageButton)rootView.findViewById(R.id.sa_padlock);

//        padlockImage.setLayoutParams(padlockParams);
        padlockImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        padlockImage.setPadding(0, 0, 0, 0);
        padlockImage.setVisibility(View.VISIBLE);

        padlockImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPadlockClick(v);
            }
        });
        padlockImage.bringToFront();

        Log.d(TAG, "Showed padlock");
    }


    @Override
    public void paused() {
        if (mVideoPlayerController != null) {
            mVideoPlayerController.pause();
        }
    }

    @Override
    public void resumed() {
        if (mVideoPlayerController != null) {
            mVideoPlayerController.resume();
        }
    }
}
