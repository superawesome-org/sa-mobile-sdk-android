package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.lang.ref.WeakReference;

import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.sdk.models.SAAd;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public class SAInterstitialActivity implements SAViewInterface {

    /** private activity object values */
    private Context context;
    private Intent intent;
    private static WeakReference<Activity> mActivityRef;
    private static InterstitialAdDataHolder holder;

    /**********************************************************************************************/
    /** Normal <Init> functions & other aux functions */
    /**********************************************************************************************/

    public SAInterstitialActivity(Context context){
        this.context = context;
        holder = new InterstitialAdDataHolder();
    }

    public void setAdListener(SAAdInterface adListener) {
        holder._refAdListener = adListener;
    }

    public void setParentalGateListener(SAParentalGateInterface parentalGareListener){
        holder._refParentalGateListener = parentalGareListener;
    }

    public void setShouldLockOrientation(boolean shouldLockOrientation) {
        holder._refShouldLockOrientation = shouldLockOrientation;
    }

    public void setLockOrientation(int lockOrientation){
        holder._refLockOrientation = lockOrientation;
    }

    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        holder._refIsParentalGateEnabled = isParentalGateEnabled;
    }

    protected static void updateActivity(Activity activity){
        mActivityRef = new WeakReference<Activity> (activity);
    }

    /**********************************************************************************************/
    /** <SAViewInterface> */
    /**********************************************************************************************/

    @Override
    public void setAd(SAAd ad){
        holder._refAd = ad;
    }

    @Override
    public SAAd getAd() {
        return holder._refAd;
    }

    @Override
    public void play() {
        intent = new Intent(context, SAInterstitialActivityInner.class);
        context.startActivity(intent);
    }

    @Override
    public void close() {
        if (mActivityRef != null) {
            mActivityRef.get().onBackPressed();
        }
    }

    @Override
    public void advanceToClick() {
        /** do nothing */
    }

    @Override
    public void resizeToSize(int width, int height) {
        /** do nothing */
    }

    /** shorthand start method for the lazy */
    public static void start(Context c,
                             SAAd ad,
                             boolean isParentalGateEnabled,
                             SAAdInterface adListener,
                             SAParentalGateInterface parentalGateListener) {

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

    /**********************************************************************************************/
    /** Inner Activity implementation */
    /**********************************************************************************************/

    public static class SAInterstitialActivityInner extends Activity {

        /** private variables */
        private SAAd ad; /** private ad */
        private boolean isParentalGateEnabled = true; /** init with default value */
        private boolean shouldLockOrientation = false;
        private int lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        /** sdk listeners */
        private SAAdInterface adListener;
        private SAParentalGateInterface parentalGateListener;

        /** subviews */
        private SABannerAd interstitialBanner;

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            /** Always call the superclass so it can save the view hierarchy state */
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
            ad = holder._refAd;
            isParentalGateEnabled = holder._refIsParentalGateEnabled;
            adListener = holder._refAdListener;
            parentalGateListener = holder._refParentalGateListener;
            lockOrientation = holder._refLockOrientation;
            shouldLockOrientation = holder._refShouldLockOrientation;

            /** make sure direction is locked */
            if (shouldLockOrientation) {
                setRequestedOrientation(lockOrientation);
            }

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
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            interstitialBanner.resizeToSize(width, height);
        }

        public void closeInterstitial(View v){
            /** close listener */
            if (adListener != null){
                adListener.adWasClosed(ad.placementId);
            }

            /**
             * call super.onBackPressed() to close the activity because it's own onBackPressed()
             * method is overridden to do nothing e.g. so as not to be closed by the user
             */
            super.onBackPressed();

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
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
}

/**
 * The Video activity's ad data holder object
 */
class InterstitialAdDataHolder {
    public SAAd _refAd;
    public boolean _refIsParentalGateEnabled;
    public boolean _refShouldLockOrientation = false;
    public int _refLockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public SAAdInterface _refAdListener;
    public SAParentalGateInterface _refParentalGateListener;

    InterstitialAdDataHolder (){
        // basic constructor
    }
}
