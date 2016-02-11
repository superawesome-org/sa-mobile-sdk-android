package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public class SAInterstitialActivity {

    /** private activity object values */
    private Context context;
    private Intent intent;
    private static WeakReference<Activity> mActivityRef;

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

    public void setParentalGateListener(SAParentalGateListener parentalGareListener){
        AdDataHolder.getInstance()._refParentalGateListener = parentalGareListener;
    }

    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        AdDataHolder.getInstance()._refIsParentalGateEnabled = isParentalGateEnabled;
    }

    /** weak ref update function - needed mostly to get the close() function to work */
    protected static void updateActivity(Activity activity){
        mActivityRef = new WeakReference<Activity> (activity);
    }

    /** play function */
    public void play(){
        intent = new Intent(context, SAInterstitialActivityInner.class);
        context.startActivity(intent);
    }

    /** close func */
    public void close() {
        if (mActivityRef != null) {
            mActivityRef.get().onBackPressed();
        }
    }

    /** shorthand start method for the lazy */
    public static void start(Context c,
                             SAAd ad,
                             boolean isParentalGateEnabled,
                             SAAdListener adListener,
                             SAParentalGateListener parentalGateListener) {

        /** create activity */
        SAInterstitialActivity activity = new SAInterstitialActivity(c);

        /** set ad */
        activity.setAd(ad);

        /** set ad parameters */
        activity.setIsParentalGateEnabled(isParentalGateEnabled);

        /** set listeners */
        activity.setAdListener(adListener);
        activity.setParentalGateListener(parentalGateListener);

        /** start playing */
        activity.play();
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

            /** update parent class weak ref to point to this activity */
            SAInterstitialActivity.updateActivity(this);

            /** load resource */
            String packageName = SAApplication.getSAApplicationContext().getPackageName();
            int activity_sa_interstitialId = getResources().getIdentifier("activity_sa_interstitial", "layout", packageName);
            int interstitial_bannerId = getResources().getIdentifier("interstitial_banner", "id", packageName);

            setContentView(activity_sa_interstitialId);

            /** assign data from AdDataHolder */
            ad = AdDataHolder.getInstance()._refAd;
            isParentalGateEnabled = AdDataHolder.getInstance()._refIsParentalGateEnabled;
            adListener = AdDataHolder.getInstance()._refAdListener;
            parentalGateListener = AdDataHolder.getInstance()._refParentalGateListener;

            /** get the banner */
            interstitialBanner = (SABannerAd) findViewById(interstitial_bannerId);
            interstitialBanner.setBackgroundColor(Color.rgb(239, 239, 239));
            interstitialBanner.setAd(ad);
            interstitialBanner.setAdListener(adListener);
            interstitialBanner.setParentalGateListener(parentalGateListener);
            interstitialBanner.setIsParentalGateEnabled(isParentalGateEnabled);
            interstitialBanner.play();
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            interstitialBanner.rearrangeBannerWebView(newConfig.orientation);
        }

        public void closeInterstitial(View v){
            /** close listener */
            if (adListener != null){
                adListener.adWasClosed(ad.placementId);
            }

            /**
             * call super.onBackPressed() to close the activity because it's own onBackPressed()
             * method is overriden to do nothing e.g. so as not to be closed by the user
             */
            super.onBackPressed();
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
        public void onBackPressed() {
            /** do nothing */
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
