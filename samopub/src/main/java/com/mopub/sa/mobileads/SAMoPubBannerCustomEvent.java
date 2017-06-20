/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package com.mopub.sa.mobileads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

/**
 * Class that extends the MoPub standard CustomEventBanner class in order to communicate with
 * MoPub and load a banner ad
 */
public class SAMoPubBannerCustomEvent extends CustomEventBanner {

    private final int ID = SAUtils.randomNumberBetween(1000000, 1500000);
    private SABannerAd bannerAd;

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

        int placementId;
        try {
            placementId = Integer.parseInt(map1.get(SAMoPub.kPLACEMENT_ID));
        } catch (Exception e) {
            placementId = SuperAwesome.getInstance().defaultPlacementId();
        }

        boolean isTestEnabled;
        try {
            isTestEnabled = Boolean.valueOf(map1.get(SAMoPub.kTEST_ENABLED));
        } catch (Exception e) {
            isTestEnabled = SuperAwesome.getInstance().defaultTestMode();
        }

        boolean isParentalGateEnabled;
        try {
            isParentalGateEnabled = Boolean.valueOf(map1.get(SAMoPub.kPARENTAL_GATE));
        } catch (Exception e) {
            isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
        }

        SAConfiguration configuration = SuperAwesome.getInstance().defaultConfiguration();
        try {
            String config = map1.get(SAMoPub.kCONFIGURATION);
            if (config != null && config.equals("STAGING")) {
                configuration = SAConfiguration.STAGING;
            }
        } catch (Exception e) {
            // do nothing
        }

        // create & customise the banner ad
        bannerAd = new SABannerAd(context);
        bannerAd.setId(ID);
        bannerAd.setTestMode(isTestEnabled);
        bannerAd.setParentalGate(isParentalGateEnabled);
        bannerAd.setConfiguration(configuration);
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
                    case adEmpty:
                    case adFailedToLoad:
                        break;
                    case adShown:
                        break;
                    case adFailedToShow: {
                        if (listener != null) {
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

    /**
     * Overridden "onInvalidate" method of CustomEventBanner that is triggered when ad data
     * is invalidated
     */
    @Override
    protected void onInvalidate() {
        Log.d("SuperAwesome/MoPub", "On invalidate!");
        // do nothing
    }
}
