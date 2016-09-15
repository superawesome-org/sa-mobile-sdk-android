package com.mopub.sa.mobileads;

import android.content.Context;
import android.content.pm.ActivityInfo;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;

/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeInterstitialCustomEvent extends CustomEventInterstitial {

    // private state vars
    private int placementId = 0;
    private Context context;

    @Override
    protected void loadInterstitial(final Context context, final CustomEventInterstitialListener listener, Map<String, Object> map, Map<String, String> map1) {

        // get the context
        this.context = context;

        // get map variables
        boolean isTestEnabled = false;
        boolean isParentalGateEnabled = true;
        SAOrientation orientation = SAOrientation.ANY;

        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt(map1.get("placementId"));
        }
        if (map1.get("isTestEnabled") != null) {
            isTestEnabled = Boolean.valueOf(map1.get("isTestEnabled"));
        }
        if (map1.get("isParentalGateEnabled") != null){
            isParentalGateEnabled = Boolean.valueOf(map1.get("isParentalGateEnabled"));
        }
        if (map1.get("lockOrientation") != null) {
            if (map1.get("lockOrientation").equals("PORTRAIT")) {
                orientation = SAOrientation.PORTRAIT;
            } else if (map1.get("lockOrientation").equals("LANDSCAPE")) {
                orientation = SAOrientation.LANDSCAPE;
            }
        }
        if (map1.get("orientation") != null) {
            if (map1.get("orientation").equals("PORTRAIT")) {
                orientation = SAOrientation.PORTRAIT;
            } else if (map1.get("orientation").equals("LANDSCAPE")) {
                orientation = SAOrientation.LANDSCAPE;
            }
        }

        // configure & load the interstitial
        SAInterstitialAd.setConfigurationProduction();

        if (isTestEnabled) {
            SAInterstitialAd.enableTestMode();
        } else {
            SAInterstitialAd.disableTestMode();
        }

        if (isParentalGateEnabled) {
            SAInterstitialAd.enableParentalGate();
        } else {
            SAInterstitialAd.disableParentalGate();
        }

        if (orientation == SAOrientation.LANDSCAPE) {
            SAInterstitialAd.setOrientationLandscape();
        } else if (orientation == SAOrientation.PORTRAIT) {
            SAInterstitialAd.setOrientationPortrait();
        } else {
            SAInterstitialAd.setOrientationAny();
        }

        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        if (listener != null) {
                            listener.onInterstitialLoaded();
                        }
                        break;
                    }
                    case adFailedToLoad: {
                        if (listener != null) {
                            listener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                        }
                        break;
                    }
                    case adShown: {
                        if (listener != null) {
                            listener.onInterstitialShown();
                        }
                        break;
                    }
                    case adFailedToShow: {
                        if (listener != null) {
                            listener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                        }
                        break;
                    }
                    case adClicked: {
                        if (listener != null) {
                            listener.onInterstitialClicked();
                        }
                        break;
                    }
                    case adClosed: {
                        if (listener != null) {
                            listener.onInterstitialDismissed();
                        }
                        break;
                    }
                }
            }
        });

        SAInterstitialAd.load(placementId);
    }

    @Override
    protected void showInterstitial() {
        SAInterstitialAd.play(placementId, this.context);
    }

    @Override
    protected void onInvalidate() {
        // do nothing
    }
}
