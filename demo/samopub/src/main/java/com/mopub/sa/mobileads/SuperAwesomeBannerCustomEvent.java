package com.mopub.sa.mobileads;

import android.content.Context;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.views.SABannerAd;


/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeBannerCustomEvent extends CustomEventBanner {

    /** listeners */
    private CustomEventBannerListener evtListener = null;

    /** ref to bannerAd */
    private SABannerAd bannerAd;

    @Override
    protected void loadBanner(final Context context, CustomEventBannerListener customEventBannerListener, Map<String, Object> map, Map<String, String> map1) {

        /** get the listener */
        evtListener = customEventBannerListener;

        /** get map variables */
        int placementId = 0;
        final boolean isTestEnabled;
        final boolean isParentalGateEnabled;

        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt((String)map1.get("placementId").toString());
        }

        if (map1.get("isTestEnabled") != null) {
            isTestEnabled = Boolean.valueOf(map1.get("isTestEnabled"));
        } else {
            isTestEnabled = true;
        }

        if (map1.get("isParentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("isParentalGateEnabled"));
        } else {
            isParentalGateEnabled = false;
        }

        /** before loading */
        SuperAwesome.getInstance().setConfigurationProduction();
        SuperAwesome.getInstance().setTestMode(isTestEnabled);
        SuperAwesome.getInstance().setApplicationContext(context);

        /** start loading */
        SALoader loader = new SALoader();
        loader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd saAd) {

                /** create the new banner ad */
                bannerAd = new SABannerAd(context);
                bannerAd.setAd(saAd);
                bannerAd.setIsParentalGateEnabled(isParentalGateEnabled);
                bannerAd.setAdListener(new SAAdListener() {
                    @Override
                    public void adWasShown(int placementId) {

                    }

                    @Override
                    public void adFailedToShow(int placementId) {
                        if (evtListener != null){
                            evtListener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                        }
                    }

                    @Override
                    public void adWasClosed(int placementId) {

                    }

                    @Override
                    public void adWasClicked(int placementId) {
                        if (evtListener != null) {
                            evtListener.onBannerClicked();
                        }
                    }

                    @Override
                    public void adHasIncorrectPlacement(int placementId) {
                        if (evtListener != null){
                            evtListener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                        }
                    }
                });

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
}
