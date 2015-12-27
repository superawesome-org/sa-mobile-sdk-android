package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.sautils.SALog;
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

    /** ref to current context */
    private static Context refContext;

    /**
     * Function that starts up an activity
     * @param context - the context
     * @param _ad - the add to be passed down to get info from
     * @param _isParentalGateEnabled - whether the parental gate should be on or off
     */
    public static void start(Context context, SAAd _ad, boolean _isParentalGateEnabled,
                             SAAdListener adListener, SAParentalGateListener parentalGateListener, SAVideoAdListener videoAdListener) {
        /** get data to send */
        AdDataHolder.getInstance()._refAd = _ad;
        AdDataHolder.getInstance()._refIsParentalGateEnabled = _isParentalGateEnabled;
        AdDataHolder.getInstance()._refAdListener = adListener;
        AdDataHolder.getInstance()._refParentalGateListener = parentalGateListener;
        AdDataHolder.getInstance()._refVideoAdListener = videoAdListener;

        /** get ref context */
        refContext = context;

        /** start a new intent */
        Intent intent = new Intent(context, SAVideoActivity.class);
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_video);

        /** assign data from AdDataHolder */
        ad = AdDataHolder.getInstance()._refAd;
        isParentalGateEnabled = AdDataHolder.getInstance()._refIsParentalGateEnabled;
        adListener = AdDataHolder.getInstance()._refAdListener;
        parentalGateListener = AdDataHolder.getInstance()._refParentalGateListener;
        videoAdListener = AdDataHolder.getInstance()._refVideoAdListener;

        /** get padlock */
        padlock = (ImageView) findViewById(R.id.padlock_image);

        if (ad.isFallback) {
            padlock.setVisibility(View.GONE);
        }

        /** get player */
        videoPlayer = (SAVASTPlayer) getFragmentManager().findFragmentById(R.id.video_player);

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
            }

            @Override
            public void didGoToURL(String url) {
                // make these ok
                ad.creative.isFullClickURLReliable = true;
                ad.creative.fullClickURL = url;

                /** open the parental gate */
                if (isParentalGateEnabled) {
                    SALog.Log("PG here we go!!!!");
                    SAParentalGate gate = new SAParentalGate(refContext, ad);
                    gate.show();
                    gate.setListener(parentalGateListener);

                }
                /** go directly to the URL */
                else {
                    SASender.sendEventToURL(ad.creative.trackingURL);
                    SALog.Log("Tracking to " + ad.creative.trackingURL);
                    SALog.Log("Going to " + url);

                    // go-to-url
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ad.creative.fullClickURL));
                    startActivity(browserIntent);
                }
            }
        });
        /** send the message to parse the VAST part */
        manager.parseVASTURL(ad.creative.details.vast);
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
