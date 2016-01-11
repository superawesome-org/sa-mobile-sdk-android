package com.mopub.mobileads;

import android.content.Context;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.views.SAInterstitialActivity;

/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeInterstitialCustomEvent extends CustomEventInterstitial implements SAAdListener {

    private SAAdListener adListener = this;
    private SAParentalGateListener parentalGateListener = this;
    private CustomEventInterstitialListener evtListener;

    @Override
    protected void loadInterstitial(final Context context, final CustomEventInterstitialListener customEventInterstitialListener, Map<String, Object> map, Map<String, String> map1) {

        // get map variables
        int placementId = 0;
        final boolean testMode;
        final boolean isParentalGateEnabled;

        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt((String)map1.get("placementId").toString());
        }

        if (map1.get("testMode") != null) {
            testMode = Boolean.valueOf(map1.get("testMode"));
        } else {
            testMode = true;
        }

        if (map1.get("parentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("parentalGateEnabled"));
        } else {
            isParentalGateEnabled = false;
        }

        /** before loading */
        SuperAwesome.getInstance().setConfigurationProduction();
        if (testMode) {
            SuperAwesome.getInstance().enableTestMode();
        } else {
            SuperAwesome.getInstance().disableTestMode();
        }

        // save evt listener
        evtListener = customEventInterstitialListener;

        SALoader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd saAd) {
                SAInterstitialActivity.start(context, saAd, isParentalGateEnabled, adListener, parentalGateListener);
                if (evtListener != null) {
                    evtListener.onInterstitialLoaded();
                }
            }

            @Override
            public void didFailToLoadAdForPlacementId(int i) {
                if (evtListener != null) {
                    evtListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                }
            }
        });
    }

    @Override
    protected void showInterstitial() {

        if (evtListener != null) {
            evtListener.onInterstitialShown();
        }
    }

    @Override
    protected void onInvalidate() {
        // do nothing
    }

    @Override
    public void adWasShown(int i) {
        if (evtListener != null) {
            evtListener.onInterstitialShown();
        }
    }

    @Override
    public void adFailedToShow(int i) {
        if (evtListener != null){
            evtListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
        }
    }

    @Override
    public void adWasClosed(int i) {
        if (evtListener != null){
            evtListener.onInterstitialDismissed();
        }
    }

    @Override
    public void adWasClicked(int i) {
        if (evtListener != null) {
            evtListener.onInterstitialClicked();
        }
    }

    @Override
    public void adHasIncorrectPlacement(int i) {
        if (evtListener != null){
            evtListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
        }
    }

    @Override
    public void parentalGateWasCanceled(int i) {

    }

    @Override
    public void parentalGateWasFailed(int i) {

    }

    @Override
    public void parentalGateWasSucceded(int i) {

    }
}
