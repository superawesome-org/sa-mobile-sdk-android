/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package com.mopub.sa.mobileads;

import android.content.Context;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

/**
 * Class that extends the MoPub standard CustomEventBanner class in order to communicate with
 * MoPub and load a banner ad
 */
public class SuperAwesomeBannerCustomEvent extends CustomEventBanner {

    // private constants
    private static final String KEY_placementId = "placementId";
    private static final String KEY_isTestEnabled = "isTestEnabled";
    private static final String KEY_isParentalGateEnabled = "isParentalGateEnabled";

    /**
     * Overridden "loadBanner" method of CustomEventBanner that is triggered when ad data
     * needs to be loaded by MoPub. This will trigger the SuperAwesome SDK procedures to
     * create & load an ad
     *
     * @param context   current context (activity & fragment)
     * @param listener  an instance of a MoPub listener
     * @param map       values passed down from MoPub
     * @param map1      values passed down from MoPub
     */
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

    /**
     * Overridden "onInvalidate" method of CustomEventBanner that is triggered when ad data
     * is invalidated
     */
    @Override
    protected void onInvalidate() {
        // do nothing
    }
}
