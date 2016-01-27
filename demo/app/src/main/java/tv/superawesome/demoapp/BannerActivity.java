package tv.superawesome.demoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.views.SABannerAd;

import static android.widget.AbsoluteLayout.*;

/**
 * Created by gabriel.coman on 07/01/16.
 */
public class BannerActivity extends Activity {

    private boolean isParentalGateEnabled = true; /** init with default value */
    private int placementId;
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;
    private SALoader loader;

    public static void start(Context context, int placementId, SAAdListener adListener, SAParentalGateListener parentalGateListener) {

        /** get data to send */
        DataHolder.getInstance()._refPlacementId = placementId;
        DataHolder.getInstance()._refAdListener = adListener;
        DataHolder.getInstance()._refParentalGateListener = parentalGateListener;

        /** start a new intent */
        Intent intent = new Intent(context, BannerActivity.class);
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

        setContentView(R.layout.activity_banner);

        /** assign data from AdDataHolder */
        placementId = DataHolder.getInstance()._refPlacementId;
        adListener = DataHolder.getInstance()._refAdListener;
        parentalGateListener = DataHolder.getInstance()._refParentalGateListener;

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


                SABannerAd banner = new SABannerAd(BannerActivity.this);
                banner.setAd(ad);
                banner.play();

                android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 300);
                params.leftMargin = 250;
                params.topMargin = 350;
                banner.setLayoutParams(params);

                ((ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)).addView(banner);

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
    }

    /**
     * Private class that hold info to share between activities
     */
    private static class DataHolder {
        public int _refPlacementId;
        public SAAdListener _refAdListener;
        public SAParentalGateListener _refParentalGateListener;

        /** set and get methods on the Ad Data Holder class */
        private static final DataHolder holder = new DataHolder();
        public static DataHolder getInstance() {return holder;}
    }
}
