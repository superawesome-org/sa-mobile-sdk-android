package com.mopub.sa.mobileads;

import android.content.Context;
import android.content.pm.ActivityInfo;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;

public class SuperAwesomeInterstitialCustomEvent extends CustomEventInterstitial {

    private static final String KEY_placementId = "placementId";
    private static final String KEY_isTestEnabled = "isTestEnabled";
    private static final String KEY_isParentalGateEnabled = "isParentalGateEnabled";
    private static final String KEY_lockOrientation = "lockOrientation";
    private static final String KEY_orientation = "orientation";

    // private state vars
    private int placementId = 0;
    private Context context;

    @Override
    protected void loadInterstitial(final Context context, final CustomEventInterstitialListener listener, Map<String, Object> map, Map<String, String> map1) {

        // get the context
        this.context = context;

        // get map variables
        placementId = SuperAwesome.getInstance().defaultPlacementId();
        boolean isTestEnabled = SuperAwesome.getInstance().defaultTestMode();
        boolean isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
        SAOrientation orientation = SuperAwesome.getInstance().defaultOrientation();

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
        if (map1.containsKey(KEY_lockOrientation)) {
            try {
                String stringOrientation = map1.get(KEY_lockOrientation);
                if (stringOrientation != null && stringOrientation.equals("PORTRAIT")) {
                    orientation = SAOrientation.PORTRAIT;
                } else if (stringOrientation != null && stringOrientation.equals("LANDSCAPE")){
                    orientation = SAOrientation.LANDSCAPE;
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        if (map1.containsKey(KEY_orientation)) {
            try {
                String stringOrientation = map1.get(KEY_orientation);
                if (stringOrientation != null && stringOrientation.equals("PORTRAIT")) {
                    orientation = SAOrientation.PORTRAIT;
                } else if (stringOrientation != null && stringOrientation.equals("LANDSCAPE")){
                    orientation = SAOrientation.LANDSCAPE;
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        // configure the interstitial
        SAInterstitialAd.setConfigurationProduction();
        SAInterstitialAd.setTestMode(isTestEnabled);
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setOrientation(orientation);
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
        // load the interstitial ad
        SAInterstitialAd.load(placementId, context);
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
