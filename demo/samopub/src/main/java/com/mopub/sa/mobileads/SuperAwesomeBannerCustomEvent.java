package com.mopub.sa.mobileads;

import android.content.Context;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
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

        /** create the banner ad */
        bannerAd = new SABannerAd(context);
        bannerAd.setTest(isTestEnabled);
        bannerAd.setConfigurationProduction();
        bannerAd.setIsParentalGateEnabled(isParentalGateEnabled);
        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        if (evtListener != null) {
                            evtListener.onBannerLoaded(bannerAd);
                        }

                        bannerAd.play(context);
                        break;
                    }
                    case adFailedToLoad:
                        break;
                    case adShown:
                        break;
                    case adFailedToShow: {
                        if (evtListener != null){
                            evtListener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                        }
                        break;
                    }
                    case adClicked: {
                        if (evtListener != null) {
                            evtListener.onBannerClicked();
                        }
                        break;
                    }
                    case adClosed:
                        break;
                }
            }
        });
        bannerAd.load(placementId);
    }

    @Override
    protected void onInvalidate() {
        // do nothing
    }
}
