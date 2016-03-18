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

import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.savast.SAVASTManager;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.models.SACreativeFormat;
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
    private SAVideoPlayer videoPlayer;
    private ImageView padlock;
    private SAParentalGate gate;

    /** listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;
    private SAVideoAdListener videoAdListener;
    private SAAdListener internalAdListener;
    private SAVideoAdListener internalVideoAdListener;

    /** helper vars */
    private String destinationURL = null;
    private List<String> clickTracking = null;

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

        videoPlayer = (SAVideoPlayer) ((Activity)context).getFragmentManager().findFragmentById(video_playerId);
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
        manager = new SAVASTManager(videoPlayer, new SAVASTManager.SAVASTManagerListener() {
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
                /** send this event just to be sure */
                if (ad != null && ad.creative != null ) {
                    /** send viewable impression url */
                    SAEvents.sendEventToURL(ad.creative.viewableImpressionURL);

                    /** send impression URL, if exits, to 3rd party only */
                    if (ad.creative.impressionURL != null && !ad.creative.impressionURL.contains(SuperAwesome.getInstance().getBaseURL())) {
                        SAEvents.sendEventToURL(ad.creative.impressionURL);
                    }

                    /** send moat */
                    SAEvents.sendVideoMoatEvent((Activity)getContext(), videoPlayer.getVideoPlayer(), videoPlayer.getMediaPlayer(), ad);
                }

                /** repair tags as iframes ... */

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
                /** close the gate */
                if (gate != null) {
                    gate.close();
                }

                if (videoAdListener != null){
                    videoAdListener.allAdsEnded(ad.placementId);
                }

                if (internalVideoAdListener != null){
                    internalVideoAdListener.allAdsEnded(ad.placementId);
                }

                /** send the end event */
                SAEvents.sendVideoMoatComplete(ad);
            }

            @Override
            public void didGoToURL(String url, List<String> _clickTracking) {
                /** update the destination URL */
                destinationURL = url;
                clickTracking = _clickTracking;

                /** check for PG */
                if (isParentalGateEnabled) {
                    /** send event */
                    SAEvents.sendEventToURL(ad.creative.parentalGateClickURL);
                    /** create pg - make sure to access SAVideo.this, not this */
                    gate = new SAParentalGate(getContext(), SAVideoAd.this, ad);
                    gate.show();
                    gate.setListener(parentalGateListener);
                } else {
                    advanceToClick();
                }
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
    public void advanceToClick() {
        /** call listener */
        if (adListener != null) {
            adListener.adWasClicked(ad.placementId);
        }

        /** send click tracking events */
        for (Iterator<String> i = clickTracking.iterator(); i.hasNext(); ){
            String clickTracking = i.next();
            SAEvents.sendEventToURL(clickTracking);
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
