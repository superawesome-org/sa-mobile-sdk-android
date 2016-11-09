package com.mopub.sa.mobileads;

import android.content.Context;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;


/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeBannerCustomEvent extends CustomEventBanner {

    @Override
    protected void loadBanner(final Context context, final CustomEventBannerListener listener, Map<String, Object> map, Map<String, String> map1) {

        // get map variables
        int placementId = 0;
        boolean isTestEnabled = false;
        boolean isParentalGateEnabled = true;

        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt(map1.get("placementId"));
        }
        if (map1.get("isTestEnabled") != null) {
            isTestEnabled = Boolean.valueOf(map1.get("isTestEnabled"));
        }
        if (map1.get("isParentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("isParentalGateEnabled"));
        }

        // create the banner ad
        final SABannerAd bannerAd = new SABannerAd(context);
        if (isTestEnabled) {
            bannerAd.enableTestMode();
        } else {
            bannerAd.disableTestMode();
        }
        if (isParentalGateEnabled) {
            bannerAd.enableParentalGate();
        } else {
            bannerAd.disableParentalGate();
        }
        bannerAd.setConfigurationProduction();
        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        if (listener != null) {
                            listener.onBannerLoaded(bannerAd);
                        }

                        bannerAd.play(context);
                        break;
                    }
                    case adFailedToLoad:
                        break;
                    case adShown:
                        break;
                    case adFailedToShow: {
                        if (listener != null){
                            listener.onBannerFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                        }
                        break;
                    }
                    case adClicked: {
                        if (listener != null) {
                            listener.onBannerClicked();
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
