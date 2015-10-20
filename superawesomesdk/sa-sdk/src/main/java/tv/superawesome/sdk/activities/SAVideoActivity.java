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
    private SAVideoFragment videoAd;

    public static void start(Context context, String placementId, String testMode) {
        Intent intent = new Intent(context, SAVideoActivity.class);
        intent.putExtra("placementId", placementId);
        Log.d("LIVIU - AB", testMode);
        intent.putExtra("testMode", testMode);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_video);

        Intent intent = this.getIntent();
        this.placementId = intent.getStringExtra("placementId");
        this.testMode = intent.getStringExtra("testMode");
        Log.d("LIVIU - B", this.testMode);

        this.videoAd = (SAVideoFragment)getSupportFragmentManager().findFragmentById(R.id.sa_video_ad);
//        this.videoAd.testMode = false;
        this.videoAd.setListener(new SAVideoViewListener() {
            @Override
            public void onAdStart() {
                Log.d(TAG, "onAdStart");
            }

            @Override
            public void onAdPause() {
                Log.d(TAG, "onAdPause");
            }

            @Override
            public void onAdResume() {
                Log.d(TAG, "onAdResume");
            }

            @Override
            public void onAdFirstQuartile() {
                Log.d(TAG, "onAdFirstQuartile");
            }

            @Override
            public void onAdMidpoint() {
                Log.d(TAG, "onAdMidpoint");
            }

            @Override
            public void onAdThirdQuartile() {
                Log.d(TAG, "onAdThirdQuartile");
            }

            @Override
            public void onAdComplete() {
                Log.d(TAG, "onAdComplete");
                finish();
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed");
            }

            @Override
            public void onAdSkipped() {
                Log.d(TAG, "onAdSkipped");
            }

            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {
                Log.d(TAG, "onAdLoaded");
            }

            @Override
            public void onAdError(String message) {
                Log.d(TAG, "onAdError");
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

}
