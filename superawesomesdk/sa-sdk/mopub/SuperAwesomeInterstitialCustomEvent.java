package com.mopub.mobileads;

import android.content.Context;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.AdManager;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.SAInterstitialView;
import tv.superawesome.sdk.views.SAPlacementListener;

/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeInterstitialCustomEvent extends CustomEventInterstitial {

    private SAInterstitialView interstitial;
    private AdManager manager;
    private CustomEventInterstitialListener evtListener;
    private Boolean hasShown;

    @Override
    protected void loadInterstitial(Context context, final CustomEventInterstitialListener customEventInterstitialListener, Map<String, Object> map, Map<String, String> map1) {

        hasShown = false;

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

        // save evt listner
        evtListener = customEventInterstitialListener;

        // get ad manager
        manager = SuperAwesome.createAdManager();

        interstitial = new SAInterstitialView(context, placementId, manager);
        interstitial.setTestMode(testMode);
        interstitial.setParentalGateEnabled(isParentalGateEnabled);
        interstitial.loadAd();
        interstitial.setListener(new SAPlacementListener() {
            @Override
            public void onAdLoaded(SAAd saAd) {
                System.out.println("Interstitial ad was loaded OK");

                if (evtListener != null) {
                    evtListener.onInterstitialLoaded();
                }
            }

            @Override
            public void onAdError(String s) {
                System.out.println("Interstitial ad was loaded NOK");

                if (evtListener != null) {
                    evtListener.onInterstitialFailed(MoPubErrorCode.valueOf("SA Ad Error"));
                }
            }
        });
    }

    @Override
    protected void showInterstitial() {

        if (!hasShown) {
            interstitial.show();
            hasShown = true;
        }

        if (evtListener != null) {
            evtListener.onInterstitialShown();
        }
    }

    @Override
    protected void onInvalidate() {
        interstitial = null;
    }
}
