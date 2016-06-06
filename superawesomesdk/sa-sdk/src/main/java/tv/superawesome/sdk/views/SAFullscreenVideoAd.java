package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.lang.ref.WeakReference;

import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.saadloader.models.SAAd;

/**
 * Created by gabriel.coman on 24/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAFullscreenVideoAd implements SAViewInterface {

    /** private activity object values */
    private Context context;
    private Intent intent;
    private static WeakReference<Activity> mActivityRef;
    private static SAVideoAdDataHolder holder;

    /**********************************************************************************************/
    /** Normal <Init> functions & other aux functions */
    /**********************************************************************************************/

    /** base constructor */
    public SAFullscreenVideoAd(Context context){
        this.context = context;
        holder = new SAVideoAdDataHolder();
    }



    public void setAdListener(SAAdInterface adListener) {
        holder._refAdListener = adListener;
    }

    public void setVideoAdListener(SAVideoAdInterface videoAdListener){
        holder._refVideoAdListener = videoAdListener;
    }

    public void setParentalGateListener(SAParentalGateInterface parentalGateListener){
        holder._refParentalGateListener = parentalGateListener;
    }

    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        holder._refIsParentalGateEnabled = isParentalGateEnabled;
    }

    public void setShouldShowCloseButton (boolean shouldShowCloseButton){
        holder._refShouldShowCloseButton = shouldShowCloseButton;
    }

    public void setShouldAutomaticallyCloseAtEnd (boolean shouldAutomaticallyCloseAtEnd){
        holder._refShouldAutomaticallyCloseAtEnd = shouldAutomaticallyCloseAtEnd;
    }

    public void setShouldLockOrientation(boolean shouldLockOrientation) {
        holder._refShouldLockOrientation = shouldLockOrientation;
    }

    public void setShouldShowSmallClickButton(boolean shouldShowSmallClickButton) {
        holder._refShouldShowSmallClickButton = shouldShowSmallClickButton;
    }

    public void setLockOrientation(int lockOrientation){
        holder._refLockOrientation = lockOrientation;
    }

    /** weak ref update function - needed mostly to get the close() function to work */
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

    /** play function */
    @Override
    public void play(){
        intent = new Intent(context, SAFullscreenVideoAdActivity.class);
        context.startActivity(intent);
    }

    /** close function */
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

    /** inner activity class */
    public static class SAFullscreenVideoAdActivity extends Activity {

        /** private variables that control how the ad behaves */
        private SAAd ad;
        private boolean isParentalGateEnabled = true;
        private boolean shouldShowCloseButton = true;
        private boolean shouldAutomaticallyCloseAtEnd = true;
        private boolean shouldLockOrientation = false;
        private boolean shouldShowSmallClickButton = false;
        private int lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        /** sdk listeners */
        private SAAdInterface adListener;
        private SAParentalGateInterface parentalGateListener;
        private SAVideoAdInterface videoAdListener;

        /** vast stuff */
        private SAVideoAd videoAd;

        /** is OK to close bool */
        private boolean isOKToClose = false;

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
             super.onSaveInstanceState(savedInstanceState);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            /** update parent class weak ref to point to this activity */
            SAFullscreenVideoAd.updateActivity(this);

            /** load resources */
            String packageName = SAApplication.getSAApplicationContext().getPackageName();
            int activity_sa_videoId = getResources().getIdentifier("activity_sa_video", "layout", packageName);
            int video_adId = getResources().getIdentifier("video_ad", "id", packageName);

            setContentView(activity_sa_videoId);

            /** assign data from AdDataHolder */
            ad = holder._refAd;
            isParentalGateEnabled = holder._refIsParentalGateEnabled;
            shouldShowCloseButton = holder._refShouldShowCloseButton;
            shouldAutomaticallyCloseAtEnd = holder._refShouldAutomaticallyCloseAtEnd;
            adListener = holder._refAdListener;
            videoAdListener = holder._refVideoAdListener;
            parentalGateListener = holder._refParentalGateListener;
            shouldLockOrientation = holder._refShouldLockOrientation;
            shouldShowSmallClickButton = holder._refShouldShowSmallClickButton;
            lockOrientation = holder._refLockOrientation;

            /** make sure direction is locked */
            if (shouldLockOrientation) {
                setRequestedOrientation(lockOrientation);
            }

            /** get player */
            videoAd = (SAVideoAd) findViewById(video_adId);

            if (savedInstanceState == null) {
                videoAd.setAd(ad);
                videoAd.setParentalGateListener(parentalGateListener);
                videoAd.setAdListener(adListener);
                videoAd.setVideoAdListener(videoAdListener);
                videoAd.setIsParentalGateEnabled(isParentalGateEnabled);
                videoAd.setShouldShowCloseButton(shouldShowCloseButton);
                videoAd.setShouldShowSmallClickButton(shouldShowSmallClickButton);
                videoAd.setInternalAdListener(new SAAdInterface() {
                    @Override
                    public void adWasShown(int placementId) {}
                    @Override
                    public void adWasClosed(int placementId) {
                        close();
                    }
                    @Override
                    public void adWasClicked(int placementId) {}
                    @Override
                    public void adHasIncorrectPlacement(int placementId) {
                        close();
                    }
                    @Override
                    public void adFailedToShow(int placementId) {
                        close();
                    }
                });
                videoAd.setInternalVideoAdListener(new SAVideoAdInterface() {
                    @Override
                    public void videoStarted(int placementId) {}
                    @Override
                    public void videoReachedFirstQuartile(int placementId) {}
                    @Override
                    public void videoReachedMidpoint(int placementId) {}
                    @Override
                    public void videoReachedThirdQuartile(int placementId) {}
                    @Override
                    public void videoEnded(int placementId) {}
                    @Override
                    public void adEnded(int placementId) {}
                    @Override
                    public void adStarted(int placementId) {
                        isOKToClose = true;
                    }
                    @Override
                    public void allAdsEnded(int placementId) {
                        if (shouldAutomaticallyCloseAtEnd) {
                            close();
                        }
                    }
                });
                videoAd.play();
            }
        }

        @Override
        public void onBackPressed() {

        }

        /** public close function */
        public void closeVideo(View v){
            if (isOKToClose) {
                close();
            }
        }

        public void close() {
            /** close listener */
            if (adListener != null){
                adListener.adWasClosed(ad.placementId);
            }
            videoAd.close();

            /**
             * call super.onBackPressed() to close the activity because it's own onBackPressed()
             * method is overridden to do nothing e.g. so as not to be closed by the user
             */
            super.finish();

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}

/**
 * The Video activity's ad data holder object
 */
class SAVideoAdDataHolder {
    public SAAd _refAd;
    public boolean _refIsParentalGateEnabled = true;
    public boolean _refShouldShowCloseButton = true;
    public boolean _refShouldAutomaticallyCloseAtEnd = true;
    public boolean _refShouldLockOrientation = false;
    public boolean _refShouldShowSmallClickButton = false;
    public int _refLockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public SAAdInterface _refAdListener;
    public SAParentalGateInterface _refParentalGateListener;
    public SAVideoAdInterface _refVideoAdListener;

    SAVideoAdDataHolder(){
        // basic contructor
    }
}