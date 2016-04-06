package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.savast.SAVASTManager;
import tv.superawesome.lib.savideoplayer.SAVideoPlayer;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.models.SACreativeFormat;

/**
 * Created by gabriel.coman on 05/04/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVideoAd extends FrameLayout implements SAViewProtocol, SAVASTManager.SAVASTManagerListener {

    /** the Ad */
    private boolean isParentalGateEnabled = true;
    private boolean shouldShowCloseButton = false;
    private SAAd ad;

    /** display stuff */
    private SAVASTManager manager;
    private SAVideoPlayer videoPlayer;
    private SAParentalGate gate;

    /** listeners */
    private SAAdListener adListener;
    private SAParentalGateListener parentalGateListener;
    private SAVideoAdListener videoAdListener;
    private SAAdListener internalAdListener;
    private SAVideoAdListener internalVideoAdListener;

    /** helper vars */
    private String destinationURL = null;
    private List<String> clickTracking = new ArrayList<>();

    public SAVideoAd(Context context) { this(context, null, 0); }
    public SAVideoAd(Context context, AttributeSet attrs) { this(context, attrs, 0); }
    public SAVideoAd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        String packageName = context.getPackageName();
        int view_sa_videoadId = getResources().getIdentifier("view_sa_video", "layout", packageName);
        int video_playerId = getResources().getIdentifier("sa_videoplayer_id", "id", packageName);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(view_sa_videoadId, this);
        videoPlayer = (SAVideoPlayer)((Activity) context).getFragmentManager().findFragmentById(video_playerId);
    }

    /**
     * *********************************************************************************************
     * <SAViewProtocol> Implementation
     * *********************************************************************************************
     */

    @Override
    public void setAd(SAAd ad) {
        this.ad = ad;
    }

    @Override
    public SAAd getAd() {
        return ad;
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

        videoPlayer.shouldShowPadlock = !(ad.isFallback || ad.isHouse);
        videoPlayer.shouldShowCloseButton = shouldShowCloseButton;
        manager = new SAVASTManager(videoPlayer, this);
        manager.parseVASTURL(ad.creative.details.vast);
    }

    @Override
    public void close() {
        videoPlayer.close();
    }

    @Override
    public void advanceToClick() {
        /** call listener */
        if (adListener != null) {
            adListener.adWasClicked(ad.placementId);
        }

        /** send click tracking events */
        for (String click : clickTracking) {
            SAEvents.sendEventToURL(click);
        }

        System.out.println("Going to " + destinationURL);

        /** go-to-url */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destinationURL));
        getContext().startActivity(browserIntent);
    }

    @Override
    public void resizeToSize(int width, int height) {

    }

    /**
     * These functions sets the ad listener
     */
    public void setAdListener(SAAdListener adListener) {
        this.adListener = adListener;
    }
    public void setInternalAdListener(SAAdListener adListener) { this.internalAdListener = adListener; }
    public void setParentalGateListener(SAParentalGateListener parentalGateListener){ this.parentalGateListener = parentalGateListener; }
    public void setVideoAdListener(SAVideoAdListener videoAdListener){ this.videoAdListener = videoAdListener; }
    public void setInternalVideoAdListener(SAVideoAdListener videoAdListener) { this.internalVideoAdListener = videoAdListener; }
    public void setIsParentalGateEnabled (boolean isParentalGateEnabled) { this.isParentalGateEnabled = isParentalGateEnabled; }
    public void setShouldShowCloseButton(boolean shouldShowCloseButton) { this.shouldShowCloseButton = shouldShowCloseButton; }

    /**
     * *********************************************************************************************
     * <SAVASTManager.SAVASTManagerListener> Implementation
     * *********************************************************************************************
     */

    @Override
    public void didNotFindAds() {
        if (adListener != null) {
            adListener.adFailedToShow(ad.placementId);
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
        }

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

    @Override
    public void didClickOnClose() {
        if (internalAdListener != null) {
            internalAdListener.adWasClosed(ad.placementId);
        }
    }
}
