package com.mopub.mobileads;

import android.content.Context;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.views.SABannerAd;


/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeBannerCustomEvent extends CustomEventBanner implements SAAdListener {

    /** listeners */
    private SAAdListener adListener = this;
    private SAParentalGateListener parentalGateListener = this;
    private CustomEventBannerListener evtListener = null;

    /** ref to bannerAd */
    private SABannerAd bannerAd;

    @Override
    protected void loadBanner(final Context context, CustomEventBannerListener customEventBannerListener, Map<String, Object> map, Map<String, String> map1) {

        /** get the listener */
        evtListener = customEventBannerListener;

        /** get map variables */
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

        /** start loading */
        SALoader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd saAd) {

                /** create the new banner ad */
                bannerAd = new SABannerAd(context);
                bannerAd.setAd(saAd);
                bannerAd.setIsParentalGateEnabled(isParentalGateEnabled);
                bannerAd.setAdListener(adListener);
                bannerAd.setParentalGateListener(parentalGateListener);

                /** call play */
                bannerAd.play();

                /** call evt listener */
                if (evtListener != null) {
                    evtListener.onBannerLoaded(bannerAd);
                }
            }

            @Override
            public void didFailToLoadAdForPlacementId(int i) {
                /** call to event listener */
                if (evtListener != null) {
                    evtListener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                }
            }
        });
    }

    @Override
    protected void onInvalidate() {
        /** n/a */
    }

    @Override
    public void adWasShown(int i) {

    }

    @Override
    public void adFailedToShow(int i) {
        if (evtListener != null){
            evtListener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
        }
    }

    @Override
    public void adWasClosed(int i) {

    }

    @Override
    public void adWasClicked(int i) {
        if (evtListener != null) {
            evtListener.onBannerClicked();
        }
    }

    @Override
    public void adHasIncorrectPlacement(int i) {
        if (evtListener != null) {
            evtListener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
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
