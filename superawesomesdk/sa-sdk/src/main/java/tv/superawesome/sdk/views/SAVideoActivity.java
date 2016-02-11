package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.savast.savastmanager.SAVASTManager;
import tv.superawesome.lib.savast.savastmanager.SAVASTManagerListener;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayer;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;

/**
 * Created by gabriel.coman on 24/12/15.
 */
public class SAVideoActivity {

    /** private activity object values */
    private Context context;
    private Intent intent;
    private static WeakReference<Activity> mActivityRef;

    /** base constructor */
    public SAVideoActivity(Context context){
        this.context = context;
    }

    /** setter functions */
    public void setAd(SAAd ad){
        AdDataHolder.getInstance()._refAd = ad;
    }

    public void setAdListener(SAAdListener adListener) {
        AdDataHolder.getInstance()._refAdListener = adListener;
    }

    public void setVideoAdListener(SAVideoAdListener videoAdListener){
        AdDataHolder.getInstance()._refVideoAdListener = videoAdListener;
    }

    public void setParentalGateListener(SAParentalGateListener parentalGateListener){
        AdDataHolder.getInstance()._refParentalGateListener = parentalGateListener;
    }

    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        AdDataHolder.getInstance()._refIsParentalGateEnabled = isParentalGateEnabled;
    }

    public void setShouldShowCloseButton (boolean shouldShowCloseButton){
        AdDataHolder.getInstance()._refShouldShowCloseButton = shouldShowCloseButton;
    }

    public void setShouldAutomaticallyCloseAtEnd (boolean shouldAutomaticallyCloseAtEnd){
        AdDataHolder.getInstance()._refShouldAutomaticallyCloseAtEnd = shouldAutomaticallyCloseAtEnd;
    }

    /** weak ref update function - needed mostly to get the close() function to work */
    protected static void updateActivity(Activity activity){
        mActivityRef = new WeakReference<Activity> (activity);
    }

    /** play function */
    public void play(){
        intent = new Intent(context, SAVideoActivityInner.class);
        context.startActivity(intent);
    }

    /** close function */
    public void close() {
        if (mActivityRef != null) {
            mActivityRef.get().onBackPressed();
        }
    }

    /** shorthand start method for the lazy */
    public static void start(Context c,
                             SAAd ad,
                             boolean isParentalGateEnabled,
                             boolean shouldShowCloseButton,
                             SAAdListener adListener,
                             SAParentalGateListener parentalGateListener,
                             SAVideoAdListener videoAdListener) {

        /** create activity */
        SAVideoActivity activity = new SAVideoActivity(c);

        /** set ad */
        activity.setAd(ad);

        /** set ad parameters */
        activity.setIsParentalGateEnabled(isParentalGateEnabled);
        activity.setShouldShowCloseButton(shouldShowCloseButton);
        activity.setShouldAutomaticallyCloseAtEnd(true);

        /** set listeners */
        activity.setAdListener(adListener);
        activity.setParentalGateListener(parentalGateListener);
        activity.setVideoAdListener(videoAdListener);

        /** start playing */
        activity.play();
    }

    /** inner activity class */
    public static class SAVideoActivityInner extends Activity implements SANavigationInterface {

        /** private variables that control how the ad behaves */
        private SAAd ad;
        private boolean isParentalGateEnabled = true;
        private boolean shouldShowCloseButton = false;
        private boolean shouldAutomaticallyCloseAtEnd = true;

        /** sdk listeners */
        private SAAdListener adListener;
        private SAParentalGateListener parentalGateListener;
        private SAVideoAdListener videoAdListener;

        /** vast stuff */
        private SAVASTManager manager;
        private SAVASTPlayer videoPlayer;

        /** the padlock part */
        private ImageView padlock;
        private Button closeBtn;

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            /** Always call the superclass so it can save the view hierarchy state */
            super.onSaveInstanceState(savedInstanceState);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            /** update parent class weak ref to point to this activity */
            SAVideoActivity.updateActivity(this);

            /** load resources */
            String packageName = SAApplication.getSAApplicationContext().getPackageName();
            int activity_sa_videoId = getResources().getIdentifier("activity_sa_video", "layout", packageName);
            int padlock_imageId = getResources().getIdentifier("padlock_image", "id", packageName);
            int video_playerId = getResources().getIdentifier("video_player", "id", packageName);
            int close_btnId = getResources().getIdentifier("video_close", "id", packageName);

            setContentView(activity_sa_videoId);

            /** assign data from AdDataHolder */
            ad = AdDataHolder.getInstance()._refAd;
            isParentalGateEnabled = AdDataHolder.getInstance()._refIsParentalGateEnabled;
            shouldShowCloseButton = AdDataHolder.getInstance()._refShouldShowCloseButton;
            shouldAutomaticallyCloseAtEnd = AdDataHolder.getInstance()._refShouldAutomaticallyCloseAtEnd;
            adListener = AdDataHolder.getInstance()._refAdListener;
            videoAdListener = AdDataHolder.getInstance()._refVideoAdListener;
            parentalGateListener = AdDataHolder.getInstance()._refParentalGateListener;

            /** get close button */
            closeBtn = (Button) findViewById(close_btnId);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    close();
                }
            });
            /** also check visibility of button */
            if (shouldShowCloseButton){
                closeBtn.setVisibility(View.VISIBLE);
            } else {
                closeBtn.setVisibility(View.GONE);
            }

            /** get padlock */
            padlock = (ImageView) findViewById(padlock_imageId);

            if (ad.isFallback) {
                padlock.setVisibility(View.GONE);
            } else {
                padlock.setVisibility(View.VISIBLE);
            }

            /** get player */
            videoPlayer = (SAVASTPlayer) getFragmentManager().findFragmentById(video_playerId);

            /** create the VAST manager */
            manager = new SAVASTManager(videoPlayer, new SAVASTManagerListener() {
                @Override
                public void didParseVASTAndFindAds() {
                    /** do nothing here */
                }

                @Override
                public void didParseVASTButDidNotFindAnyAds() {
                    if (adListener != null){
                        adListener.adFailedToShow(ad.placementId);
                    }

                    /** close when no ads */
                    close();
                }

                @Override
                public void didNotParseVAST() {
                    if (adListener != null){
                        adListener.adFailedToShow(ad.placementId);
                    }

                    /** close when no ads */
                    close();
                }

                @Override
                public void didStartAd() {
                    /** send thie event just to be sure */
                    SASender.sendEventToURL(ad.creative.viewableImpressionURL);

                    /** call listener */
                    if (adListener != null){
                        adListener.adWasShown(ad.placementId);
                    }
                }

                @Override
                public void didStartCreative() {
                    if (videoAdListener != null){
                        videoAdListener.videoStarted(ad.placementId);
                    }
                }

                @Override
                public void didReachFirstQuartileOfCreative() {
                    if (videoAdListener != null){
                        videoAdListener.videoReachedFirstQuartile(ad.placementId);
                    }
                }

                @Override
                public void didReachMidpointOfCreative() {
                    if (videoAdListener != null){
                        videoAdListener.videoReachedMidpoint(ad.placementId);
                    }
                }

                @Override
                public void didReachThirdQuartileOfCreative() {
                    if (videoAdListener != null){
                        videoAdListener.videoReachedThirdQuartile(ad.placementId);
                    }
                }

                @Override
                public void didEndOfCreative() {
                    if (videoAdListener != null){
                        videoAdListener.videoEnded(ad.placementId);
                    }
                }

                @Override
                public void didHaveErrorForCreative() {
                    /** do nothing here */
                }

                @Override
                public void didEndAd() {
                    if (videoAdListener != null){
                        videoAdListener.adEnded(ad.placementId);
                    }
                }

                @Override
                public void didEndAllAds() {
                    if (videoAdListener != null){
                        videoAdListener.allAdsEnded(ad.placementId);
                    }

                    /** call finish on this activity */
                    if (shouldAutomaticallyCloseAtEnd) {
                        close();
                    }
                }

                @Override
                public void didGoToURL(String url) {
                    /** make these ok */
                    ad.creative.fullClickURL = url;
                    /** do a final checkup */
                    if (!ad.creative.fullClickURL.contains("ads.superawesome.tv/v2/video/click/") &&
                            !ad.creative.fullClickURL.contains("ads.staging.superawesome.tv./v2/video/click/")) {
                        ad.creative.isFullClickURLReliable = true;
                    } else {
                        ad.creative.isFullClickURLReliable = false;
                    }

                    /** open the parental gate */
                    if (isParentalGateEnabled) {
                        /** send event */
                        SASender.sendEventToURL(ad.creative.parentalGateClickURL);
                        /** create pg */
                        SAParentalGate gate = new SAParentalGate(SAVideoActivityInner.this, SAVideoActivityInner.this, ad);
                        gate.show();
                        gate.setListener(parentalGateListener);
                    }
                    /** go directly to the URL */
                    else {
                        advanceToClick();
                    }
                }
            });
            /** send the message to parse the VAST part */
            manager.parseVASTURL(ad.creative.details.vast);
        }


        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            /**
             * do nothing here - just let the UI elements resize correctly as specified by the
             * XML interface files
             */
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
            videoPlayer = null;
            manager = null;
            ad = null;
            adListener = null;
            videoAdListener = null;
        }

        /** public close function */
        public void close(){
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
        public void advanceToClick() {
            SALog.Log("Going to " + ad.creative.fullClickURL);

            /** call listener */
            if (adListener != null) {
                adListener.adWasClicked(ad.placementId);
            }

            /** first send this */
            if (!ad.creative.isFullClickURLReliable) {
                SASender.sendEventToURL(ad.creative.trackingURL);
            }

            /** go-to-url */
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ad.creative.fullClickURL));
            startActivity(browserIntent);
        }
    }

    /**
     * Private class that hold info to share between activities
     */
    private static class AdDataHolder {
        public SAAd _refAd;
        public boolean _refIsParentalGateEnabled = true;
        public boolean _refShouldShowCloseButton = false;
        public boolean _refShouldAutomaticallyCloseAtEnd = true;
        public SAAdListener _refAdListener;
        public SAParentalGateListener _refParentalGateListener;
        public SAVideoAdListener _refVideoAdListener;

        /** set and get methods on the Ad Data Holder class */
        private static final AdDataHolder holder = new AdDataHolder();
        public static AdDataHolder getInstance() {return holder;}
    }
}
