package tv.superawesome.sdk.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.fragments.SAVideoFragment;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.video.SAVideoViewListener;

public class SAVideoActivity extends FragmentActivity {

    private static final String TAG = "SAVideoActivity";
    private String placementId;
    private String testMode;
    private String isParentalGateEnabled;
    private SAVideoFragment videoAd;
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

        Intent intent = this.getIntent();
        this.placementId = intent.getStringExtra("placementId");
        this.testMode = intent.getStringExtra("testMode");
        this.isParentalGateEnabled = intent.getStringExtra("isParentalGateEnabled");

        this.videoAd = (SAVideoFragment)getSupportFragmentManager().findFragmentById(R.id.sa_video_ad);

        this.videoAd.setListener(new SAVideoViewListener() {
            @Override
            public void onAdStart() {
                Log.d(TAG, "onAdStart");
                SAVideoActivity.listener.onAdStart();;
            }

            @Override
            public void onAdPause() {
                Log.d(TAG, "onAdPause");
                SAVideoActivity.listener.onAdPause();
            }

            @Override
            public void onAdResume() {
                Log.d(TAG, "onAdResume");
                SAVideoActivity.listener.onAdResume();
            }

            @Override
            public void onAdFirstQuartile() {
                Log.d(TAG, "onAdFirstQuartile");
                SAVideoActivity.listener.onAdFirstQuartile();
            }

            @Override
            public void onAdMidpoint() {
                Log.d(TAG, "onAdMidpoint");
                SAVideoActivity.listener.onAdMidpoint();
            }

            @Override
            public void onAdThirdQuartile() {
                Log.d(TAG, "onAdThirdQuartile");
                SAVideoActivity.listener.onAdThirdQuartile();
            }

            @Override
            public void onAdComplete() {
                SAVideoActivity.listener.onAdComplete();
                Log.d(TAG, "onAdComplete");
                finish();
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed");
                SAVideoActivity.listener.onAdClosed();
            }

            @Override
            public void onAdSkipped() {
                Log.d(TAG, "onAdSkipped");
                SAVideoActivity.listener.onAdSkipped();
            }

            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {
                Log.d(TAG, "onAdLoaded");
                SAVideoActivity.listener.onAdLoaded(superAwesomeAd);
            }

            @Override
            public void onAdError(String message) {
                Log.d(TAG, "onAdError");
                SAVideoActivity.listener.onAdError(message);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

}
