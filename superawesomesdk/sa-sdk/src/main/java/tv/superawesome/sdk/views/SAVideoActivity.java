package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savast.savastmanager.SAVASTManager;
import tv.superawesome.lib.savast.savastmanager.SAVASTManagerListener;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayer;
import tv.superawesome.sdk.R;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;

/**
 * Created by gabriel.coman on 24/12/15.
 */
public class SAVideoActivity extends Activity{

    /** private variables */
    private boolean isParentalGateEnabled = true; /** init with default value */
    private SAAd ad; /** private ad */

    /** sdk listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;
    private SAVideoAdListener videoAdListener;

    /** vast stuff */
    private SAVASTManager manager;
    private SAVASTPlayer videoPlayer;

    /** the padlock part */
    private ImageView padlock;

    /**
     * Function that starts up an activity
     * @param context - the context
     * @param _ad - the add to be passed down to get info from
     * @param _isParentalGateEnabled - whether the parental gate should be on or off
     * @param adListener
     * @param parentalGateListener
     * @param videoAdListener
     */
    public static void start(Context context, SAAd _ad, boolean _isParentalGateEnabled,
                             SAAdListener adListener, SAParentalGateListener parentalGateListener, SAVideoAdListener videoAdListener) {
        /** get data to send */
        AdDataHolder.getInstance()._refAd = _ad;
        AdDataHolder.getInstance()._refIsParentalGateEnabled = _isParentalGateEnabled;
        AdDataHolder.getInstance()._refAdListener = adListener;
        AdDataHolder.getInstance()._refParentalGateListener = parentalGateListener;
        AdDataHolder.getInstance()._refVideoAdListener = videoAdListener;

        /** start a new intent */
        Intent intent = new Intent(context, SAVideoActivity.class);
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
        super.onCreate(savedInstanceState);

        /** load resources */
        String packageName = SAApplication.getSAApplicationContext().getPackageName();
        int activity_sa_videoId = SAUtils.getResourceIdByName(packageName, "layout", "activity_sa_video");
        int padlock_imageId = SAUtils.getResourceIdByName(packageName, "id", "padlock_image");
        int video_playerId = SAUtils.getResourceIdByName(packageName, "id", "video_player");

        setContentView(activity_sa_videoId);

        /** assign data from AdDataHolder */
        ad = AdDataHolder.getInstance()._refAd;
        isParentalGateEnabled = AdDataHolder.getInstance()._refIsParentalGateEnabled;
        adListener = AdDataHolder.getInstance()._refAdListener;
        parentalGateListener = AdDataHolder.getInstance()._refParentalGateListener;
        videoAdListener = AdDataHolder.getInstance()._refVideoAdListener;

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
                // do nothing here
            }

            @Override
            public void didParseVASTButDidNotFindAnyAds() {
                if (adListener != null){
                    adListener.adFailedToShow(ad.placementId);
                }
            }

            @Override
            public void didNotParseVAST() {
                if (adListener != null){
                    adListener.adFailedToShow(ad.placementId);
                }
            }

            @Override
            public void didStartAd() {
                // send thie event just to be sure
                SASender.sendEventToURL(ad.creative.viewableImpressionURL);

                // call listener
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
                // do nothing here
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

                // call finish on this activity
                onBackPressed();
            }

            @Override
            public void didGoToURL(String url) {
                // make these ok
                ad.creative.fullClickURL = url;
                // do a final checkup
                if (!ad.creative.fullClickURL.contains("ads.superawesome.tv/v2/video/click/")) {
                    ad.creative.isFullClickURLReliable = true;
                } else {
                    ad.creative.isFullClickURLReliable = false;
                }

                /** open the parental gate */
                if (isParentalGateEnabled) {
                    SAParentalGate gate = new SAParentalGate(SAVideoActivity.this, ad);
                    gate.show();
                    gate.setListener(parentalGateListener);
                }
                /** go directly to the URL */
                else {
                    SALog.Log("Going to " + ad.creative.fullClickURL);

                    // first send this
                    if (!ad.creative.isFullClickURLReliable) {
                        SASender.sendEventToURL(ad.creative.trackingURL);
                    }

                    // go-to-url
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ad.creative.fullClickURL));
                    startActivity(browserIntent);
                }
            }
        });
        /** send the message to parse the VAST part */
        manager.parseVASTURL(ad.creative.details.vast);
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
        videoPlayer = null;
        manager = null;
        ad = null;
        adListener = null;
        videoAdListener = null;
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
        public SAVideoAdListener _refVideoAdListener;

        /** set and get methods on the Ad Data Holder class */
        private static final AdDataHolder holder = new AdDataHolder();
        public static AdDataHolder getInstance() {return holder;}
    }

}
