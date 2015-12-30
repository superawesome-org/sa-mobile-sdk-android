package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public class SAInterstitialActivity extends Activity {

    /** private variables */
    private boolean isParentalGateEnabled = true; /** init with default value */
    private SAAd ad; /** private ad */

    /** sdk listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;

    /** subviews */
    private SABannerAd interstitialBanner;
    private ImageView padlock;

    /**
     * Static start function
     * @param context
     * @param _ad
     * @param _isParentalGateEnabled
     * @param adListener
     * @param parentalGateListener
     */
    public static void start(Context context, SAAd _ad, boolean _isParentalGateEnabled,
                             SAAdListener adListener, SAParentalGateListener parentalGateListener) {

        /** get data to send */
        AdDataHolder.getInstance()._refAd = _ad;
        AdDataHolder.getInstance()._refIsParentalGateEnabled = _isParentalGateEnabled;
        AdDataHolder.getInstance()._refAdListener = adListener;
        AdDataHolder.getInstance()._refParentalGateListener = parentalGateListener;

        /** start a new intent */
        Intent intent = new Intent(context, SAInterstitialActivity.class);
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
        setContentView(R.layout.activity_sa_interstitial);

        /** assign data from AdDataHolder */
        ad = AdDataHolder.getInstance()._refAd;
        isParentalGateEnabled = AdDataHolder.getInstance()._refIsParentalGateEnabled;
        adListener = AdDataHolder.getInstance()._refAdListener;
        parentalGateListener = AdDataHolder.getInstance()._refParentalGateListener;

        /** get the banner */
        interstitialBanner = (SABannerAd) getFragmentManager().findFragmentById(R.id.interstitial_banner);
        interstitialBanner.setAd(ad);
        interstitialBanner.setAdListener(adListener);
        interstitialBanner.setParentalGateListener(parentalGateListener);
        interstitialBanner.setIsParentalGateEnabled(isParentalGateEnabled);
        interstitialBanner.play();

        /** show or hide the padlock */
        padlock = (ImageView) findViewById(R.id.interstitial_padlock_image);
        if (ad.isFallback) {
            padlock.setVisibility(View.GONE);
        } else {
            padlock.setVisibility(View.VISIBLE);
        }
    }

    public void closeInterstitial(View v){
        /** close listener */
        if (adListener != null){
            adListener.adWasClosed(ad.placementId);
        }

        /** call finish on this activity */
        onBackPressed();
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
        ad = null;
        adListener = null;
        parentalGateListener = null;
    }

    /**
     * Private class that hold info to share between activities
     */
    private static class AdDataHolder {
        public SAAd _refAd;
        public boolean _refIsParentalGateEnabled;
        public SAAdListener _refAdListener;
        public SAParentalGateListener _refParentalGateListener;

        /** set and get methods on the Ad Data Holder class */
        private static final AdDataHolder holder = new AdDataHolder();
        public static AdDataHolder getInstance() {return holder;}
    }
}
