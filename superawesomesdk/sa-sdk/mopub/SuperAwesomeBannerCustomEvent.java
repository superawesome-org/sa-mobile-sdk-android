package com.mopub.mobileads;

import android.content.Context;
import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;
import tv.superawesome.sdk.AdManager;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.SABannerView;
import tv.superawesome.sdk.views.SAPlacementListener;

/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeBannerCustomEvent extends CustomEventBanner{

    // private vars
    private SABannerView banner;
    private AdManager manager;

    @Override
    protected void loadBanner(Context context, final CustomEventBannerListener customEventBannerListener, Map<String, Object> map, Map<String, String> map1) {

        // get ad manager
        manager = SuperAwesome.createAdManager();

        // get map variables
        String placementId = "";
        Boolean testMode = true;
        Boolean isParentalGateEnabled = true;

        if (map1.get("placementId") != null ){
            placementId = (String)map1.get("placementId").toString();
        }
        if (map1.get("testMode") != null) {
            testMode = Boolean.valueOf(map1.get("testMode"));
        }
        if (map1.get("parentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("parentalGateEnabled"));
        }

        banner = new SABannerView(context, placementId, manager);
        banner.setTestMode(testMode);
        banner.setParentalGateEnabled(isParentalGateEnabled);
        banner.loadAd();
        banner.setListener(new SAPlacementListener() {
            @Override
            public void onAdLoaded(SAAd saAd) {
                System.out.println("Ad fas fetched OK");

                // call to event listener
                if (customEventBannerListener != null){
                    customEventBannerListener.onBannerLoaded(banner);
                }
            }

            @Override
            public void onAdError(String s) {
                System.out.println("Ad fas fetched NOK");

                // call to event listener
                if (customEventBannerListener != null) {
                    customEventBannerListener.onBannerFailed(MoPubErrorCode.valueOf("SA Ad Error"));
                }
            }
        });
    }

    @Override
    protected void onInvalidate() {

    }
}
