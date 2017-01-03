package com.mopub.sa.mobileads;

import android.content.Context;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

public class SuperAwesomeBannerCustomEvent extends CustomEventBanner {

    private static final String KEY_placementId = "placementId";
    private static final String KEY_isTestEnabled = "isTestEnabled";
    private static final String KEY_isParentalGateEnabled = "isParentalGateEnabled";

    @Override
    protected void loadBanner(final Context context, final CustomEventBannerListener listener, Map<String, Object> map, Map<String, String> map1) {

        // define & init map variables w/ default values
        int placementId = SuperAwesome.getInstance().defaultPlacementId();
        boolean isTestEnabled = SuperAwesome.getInstance().defaultTestMode();
        boolean isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();

        // try and get the ones sent over by the MoPub JSON
        if (map1.containsKey(KEY_placementId)) {
            try {
                placementId = Integer.parseInt(map1.get(KEY_placementId));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_isTestEnabled)) {
            try {
                isTestEnabled = Boolean.valueOf(map1.get(KEY_isTestEnabled));
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_isParentalGateEnabled)) {
            try {
                isParentalGateEnabled = Boolean.valueOf(map1.get(KEY_isParentalGateEnabled));
            } catch (Exception e) {
                // do nothing
            }
        }

        // create & customise the banner ad
        final SABannerAd bannerAd = new SABannerAd(context);
        bannerAd.setTestMode(isTestEnabled);
        bannerAd.setParentalGate(isParentalGateEnabled);
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
        // load the banner ad
        bannerAd.load(placementId);
    }

    @Override
    protected void onInvalidate() {
        // do nothing
    }
}
