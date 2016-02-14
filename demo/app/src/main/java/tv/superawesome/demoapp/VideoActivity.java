package tv.superawesome.demoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 14/02/16.
 */
public class VideoActivity extends Activity {

    private boolean isParentalGateEnabled = true; /** init with default value */
    private int placementId;
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;
    private SAVideoAdListener videoAdListener;
    private SALoader loader;

    public static void start(Context context, int placementId, SAAdListener adListener, SAParentalGateListener parentalGateListener, SAVideoAdListener videoAdListener) {

        /** get data to send */
        DataHolder.getInstance()._refPlacementId = placementId;
        DataHolder.getInstance()._refAdListener = adListener;
        DataHolder.getInstance()._refParentalGateListener = parentalGateListener;
        DataHolder.getInstance()._refVideoAdListener = videoAdListener;

        /** start a new intent */
        Intent intent = new Intent(context, VideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** call super and layout */
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        /** assign data from AdDataHolder */
        placementId = DataHolder.getInstance()._refPlacementId;
        adListener = DataHolder.getInstance()._refAdListener;
        parentalGateListener = DataHolder.getInstance()._refParentalGateListener;

        SALog.Log("Testing: " + DataHolder.getInstance()._refPlacementId);

        loader = new SALoader();
        loader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd ad) {
//                SABannerAd banner = (SABannerAd)findViewById(R.id.bannerView);
//                banner.setAd(ad);
//                banner.setIsParentalGateEnabled(isParentalGateEnabled);
//                banner.setAdListener(adListener);
//                banner.setParentalGateListener(parentalGateListener);
//                banner.play();

                SAVideoAd video = new SAVideoAd(VideoActivity.this);
                video.setAd(ad);
                video.play();

                android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(640, 480);
                params.leftMargin = 30;
                params.topMargin = 120;
                video.setLayoutParams(params);

                ((ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)).addView(video);

            }

            @Override
            public void didFailToLoadAdForPlacementId(int placementId) {
                SALog.Log("Could not load : " + placementId);

                // call finish on this activity
                onBackPressed();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        adListener = null;
        parentalGateListener = null;
        videoAdListener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Private class that hold info to share between activities
     */
    private static class DataHolder {
        public int _refPlacementId;
        public SAAdListener _refAdListener;
        public SAParentalGateListener _refParentalGateListener;
        public SAVideoAdListener _refVideoAdListener;

        /** set and get methods on the Ad Data Holder class */
        private static final DataHolder holder = new DataHolder();
        public static DataHolder getInstance() {return holder;}
    }
}
