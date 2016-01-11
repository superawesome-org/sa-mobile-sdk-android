package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public class SAInterstitialActivity {

    /** private activity object values */
    private Context context;
    private Intent intent;

    /** base constructor */
    public SAInterstitialActivity(Context context){
        this.context = context;
    }

    /** setter functions */
    public void setAd(SAAd ad){
        AdDataHolder.getInstance()._refAd = ad;
    }

    public void setAdListener(SAAdListener adListener) {
        AdDataHolder.getInstance()._refAdListener = adListener;
    }

    public void setParentalGateListener (SAParentalGateListener parentalGateListener) {
        AdDataHolder.getInstance()._refParentalGateListener = parentalGateListener;
    }

    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        AdDataHolder.getInstance()._refIsParentalGateEnabled = isParentalGateEnabled;
    }

    /** play function */
    public void play(){
        intent = new Intent(context, SAInterstitialActivityInner.class);
        context.startActivity(intent);
    }

    public static class SAInterstitialActivityInner extends Activity {

        /** private variables */
        private SAAd ad; /** private ad */
        private boolean isParentalGateEnabled = true; /** init with default value */

        /** sdk listeners */
        private SAAdListener adListener;
        private SAParentalGateListener parentalGateListener;

        /** subviews */
        private SABannerAd interstitialBanner;
        private ImageView padlock;

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

            /** load resource */
            String packageName = SAApplication.getSAApplicationContext().getPackageName();
            int activity_sa_interstitialId = getResources().getIdentifier("activity_sa_interstitial", "layout", packageName);
            int interstitial_bannerId = getResources().getIdentifier("interstitial_banner", "id", packageName);
            int interstitial_padlock_imageId = getResources().getIdentifier("interstitial_padlock_image", "id", packageName);

            setContentView(activity_sa_interstitialId);

            /** assign data from AdDataHolder */
            ad = AdDataHolder.getInstance()._refAd;
            isParentalGateEnabled = AdDataHolder.getInstance()._refIsParentalGateEnabled;
            adListener = AdDataHolder.getInstance()._refAdListener;
            parentalGateListener = AdDataHolder.getInstance()._refParentalGateListener;

            /** show or hide the padlock */
            padlock = (ImageView) findViewById(interstitial_padlock_imageId);
            if (ad.isFallback) {
                padlock.setVisibility(View.GONE);
            } else {
                padlock.setVisibility(View.VISIBLE);
            }
            /** for the interstitial make the fallback always true so that it does not show up twice */
            ad.isFallback = true;
            
            /** get the banner */
            interstitialBanner = (SABannerAd) findViewById(interstitial_bannerId);
            interstitialBanner.setAd(ad);
            interstitialBanner.setAdListener(adListener);
            interstitialBanner.setParentalGateListener(parentalGateListener);
            interstitialBanner.setIsParentalGateEnabled(isParentalGateEnabled);
            interstitialBanner.play();
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
