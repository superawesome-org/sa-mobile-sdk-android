package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sanetwork.SASender;
import tv.superawesome.lib.savast.savastmanager.SAVASTManager;
import tv.superawesome.lib.savast.savastmanager.SAVASTManagerListener;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayer;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Models.SACreativeFormat;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;

/**
 * Created by gabriel.coman on 14/02/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVideoAd extends RelativeLayout implements SAViewProtocol {
    /** Private variables */
    private boolean isParentalGateEnabled = true;
    private SAAd ad; /** private ad */

    /** Subviews & other important stuffs */
    private SAVASTManager manager;
    private SAVASTPlayer videoPlayer;
    private ImageView padlock;

    /** listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;
    private SAVideoAdListener videoAdListener;
    private SAAdListener internalAdListener;
    private SAVideoAdListener internalVideoAdListener;

    /** helper vars */
    private float cWidth = 0;
    private float cHeight = 0;
    private float bigDimension = 0;
    private float smallDimension = 0;
    private boolean layoutOK = false;
    private String destinationURL = null;

    /**********************************************************************************************/
    /** Init methods */
    /**********************************************************************************************/

    public SAVideoAd(Context context) {
        this(context, null, 0);
    }

    public SAVideoAd(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SAVideoAd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /** get ids */
        String packageName = context.getPackageName();
        int view_sa_bannerId = getResources().getIdentifier("view_sa_video", "layout", packageName);
        int video_playerId = getResources().getIdentifier("video_player", "id", packageName);
        int padlockId = getResources().getIdentifier("padlock_image", "id", packageName);

        /** create / assign the subviews */
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(view_sa_bannerId, this);

        videoPlayer = (SAVASTPlayer) ((Activity)context).getFragmentManager().findFragmentById(video_playerId);
        padlock = (ImageView)findViewById(padlockId);

        this.setBackgroundColor(Color.BLACK);
    }

    /**********************************************************************************************/
    /** <SAViewProtocol> */
    /**********************************************************************************************/

    @Override
    public void setAd(SAAd ad) {
        this.ad = ad;
    }

    @Override
    public SAAd getAd() {
        return this.ad;
    }

    @Override
    public void play() {
        /** check to see for the correct placement type */
        if (ad.creative.format != SACreativeFormat.video) {
            if (adListener != null) {
                adListener.adHasIncorrectPlacement(ad.placementId);
            }

            if (internalAdListener != null) {
                internalAdListener.adHasIncorrectPlacement(ad.placementId);
            }

            return;
        }

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

                if (internalAdListener != null) {
                    internalAdListener.adFailedToShow(ad.placementId);
                }
            }

            @Override
            public void didNotParseVAST() {
                if (adListener != null){
                    adListener.adFailedToShow(ad.placementId);
                }

                if (internalAdListener != null) {
                    internalAdListener.adFailedToShow(ad.placementId);
                }
            }

            @Override
            public void didStartAd() {
                /** send thie event just to be sure */
                SASender.sendEventToURL(ad.creative.viewableImpressionURL);

                /** call listener */
                if (adListener != null){
                    adListener.adWasShown(ad.placementId);
                }

                if (internalAdListener != null){
                    internalVideoAdListener.adStarted(ad.placementId);
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

                if (internalVideoAdListener != null){
                    internalVideoAdListener.allAdsEnded(ad.placementId);
                }
            }

            @Override
            public void didGoToURL(String url) {
                tryToGoToURL(url);
            }
        });

        /** send the message to parse the VAST part */
        manager.parseVASTURL(ad.creative.details.vast);

        /** make the padlock visible or not */
        if (ad.isFallback || ad.isHouse) {
            padlock.setVisibility(View.GONE);
        } else {
            padlock.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void close() {
        videoPlayer.close();
        manager = null;
    }

    @Override
    public void tryToGoToURL(String url) {
        /** update the destination URL */
        destinationURL = url;

        /** check for PG */
        if (isParentalGateEnabled) {
            /** send event */
            SASender.sendEventToURL(ad.creative.parentalGateClickURL);
            /** create pg */
            SAParentalGate gate = new SAParentalGate(getContext(), this, ad);
            gate.show();
            gate.setListener(parentalGateListener);
        } else {
            advanceToClick();
        }
    }

    @Override
    public void advanceToClick() {
        /** call listener */
        if (adListener != null) {
            adListener.adWasClicked(ad.placementId);
        }

        if (!destinationURL.contains(SuperAwesome.getInstance().getBaseURL())) {
            SASender.sendEventToURL(ad.creative.trackingURL);
        }

        System.out.println("Going to " + destinationURL);

        /** go-to-url */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destinationURL));
        getContext().startActivity(browserIntent);
    }

    @Override
    public void resizeToSize(int width, int height) {

    }

    /**********************************************************************************************/
    /** setters for listeners */
    /**********************************************************************************************/

    /**
     * This function sets the ad listener
     * @param adListener
     */
    public void setAdListener(SAAdListener adListener) {
        this.adListener = adListener;
    }

    public void setInternalAdListener(SAAdListener adListener) {
        this.internalAdListener = adListener;
    }

    /**
     * This function sets the parental gate listener
     * @param parentalGateListener
     */
    public void setParentalGateListener(SAParentalGateListener parentalGateListener){
        this.parentalGateListener = parentalGateListener;
    }

    /**
     * Set the video ad listener reference
     * @param videoAdListener
     */
    public void setVideoAdListener(SAVideoAdListener videoAdListener){
        this.videoAdListener = videoAdListener;
    }

    public void setInternalVideoAdListener(SAVideoAdListener videoAdListener) {
        this.internalVideoAdListener = videoAdListener;
    }

    /**
     * And this one for the parental gate
     * @param isParentalGateEnabled
     */
    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) {
        this.isParentalGateEnabled = isParentalGateEnabled;
    }
}
