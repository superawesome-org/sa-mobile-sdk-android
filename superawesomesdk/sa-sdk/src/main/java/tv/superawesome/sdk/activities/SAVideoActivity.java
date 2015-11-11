package tv.superawesome.sdk.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.fragments.SAVideoFragment;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.video.SAVideoView;
import tv.superawesome.sdk.views.video.SAVideoViewListener;

public class SAVideoActivity extends FragmentActivity {

    private static final String TAG = "SAVideoActivity";
    private String placementId;
    private String testMode;
    private String isParentalGateEnabled;
    private SAVideoFragment videoAd;
    private Button skipButton;
    private static SAVideoViewListener listener;

    public static void start(Context context, String placementId, String testMode, String isParentalGateEnabled, SAVideoViewListener listener) {
        Intent intent = new Intent(context, SAVideoActivity.class);
        intent.putExtra("placementId", placementId);
        intent.putExtra("testMode", testMode);
        intent.putExtra("isParentalGateEnabled", isParentalGateEnabled);
        SAVideoActivity.listener = listener;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_video);

        // close button
        this.skipButton = (Button)this.findViewById(R.id.sa_close_fscreen_video);
        this.skipButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = this.getIntent();
        this.placementId = intent.getStringExtra("placementId");
        this.testMode = intent.getStringExtra("testMode");
        this.isParentalGateEnabled = intent.getStringExtra("isParentalGateEnabled");

        this.videoAd = (SAVideoFragment)getSupportFragmentManager().findFragmentById(R.id.sa_video_ad);

        this.videoAd.setListener(new SAVideoViewListener() {

            @Override
            public void onAdClick() {
                Log.d(TAG, "onAdClick");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdClick();
            }

            @Override
            public void onAdStart() {
                Log.d(TAG, "onAdStart");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdStart();;
            }

            @Override
            public void onAdPause() {
                Log.d(TAG, "onAdPause");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdPause();
            }

            @Override
            public void onAdResume() {
                Log.d(TAG, "onAdResume");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdResume();
            }

            @Override
            public void onAdFirstQuartile() {
                Log.d(TAG, "onAdFirstQuartile");
                skipButton.setVisibility(View.VISIBLE);
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdFirstQuartile();
            }

            @Override
            public void onAdMidpoint() {
                Log.d(TAG, "onAdMidpoint");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdMidpoint();
            }

            @Override
            public void onAdThirdQuartile() {
                Log.d(TAG, "onAdThirdQuartile");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdThirdQuartile();
            }

            @Override
            public void onAdComplete() {
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdComplete();
                Log.d(TAG, "onAdComplete");
                finish();
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdClosed();
            }

            @Override
            public void onAdSkipped() {
                Log.d(TAG, "onAdSkipped");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdSkipped();
            }

            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {
                Log.d(TAG, "onAdLoaded");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdLoaded(superAwesomeAd);
            }

            @Override
            public void onAdError(String message) {
                Log.d(TAG, "onAdError");
                if (SAVideoActivity.listener != null)
                    SAVideoActivity.listener.onAdError(message);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

}
