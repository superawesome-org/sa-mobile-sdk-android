package com.mopub.sa.mobileads;

import android.content.Context;
import android.content.pm.ActivityInfo;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;

/**
 * Created by gabriel.coman on 27/10/15.
 */
public class SuperAwesomeInterstitialCustomEvent extends CustomEventInterstitial {

    // custom event listener
    private CustomEventInterstitialListener evtListener;

    // context
    private Context context;

    @Override
    protected void loadInterstitial(final Context context, final CustomEventInterstitialListener customEventInterstitialListener, Map<String, Object> map, Map<String, String> map1) {

        /** get map variables */
        int placementId = 0;
        final boolean isTestEnabled;
        final boolean isParentalGateEnabled;
        boolean shouldLockOrientation = false;
        int lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        if (map1.get("placementId") != null ){
            placementId = Integer.parseInt(map1.get("placementId"));
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

        if (map1.get("shouldLockOrientation") != null) {
            shouldLockOrientation = Boolean.valueOf(map1.get("shouldLockOrientation"));
            if (map1.get("lockDirection") != null) {
                if (map1.get("lockOrientation").equals("PORTRAIT")) {
                    lockOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                } else if (map1.get("lockDirection").equals("LANDSCAPE")){
                    lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                }
            }
        }

        // get the context
        this.context = context;

        // before loading
        SuperAwesome.getInstance().setConfigurationProduction();
        SuperAwesome.getInstance().setTestMode(isTestEnabled);
        SuperAwesome.getInstance().setApplicationContext(this.context);

        // save evt listener reference
        evtListener = customEventInterstitialListener;

        SAInterstitialAd.setShouldLockOrientation(shouldLockOrientation);
        SAInterstitialAd.setLockOrientation(lockOrientation);
        SAInterstitialAd.setIsParentalGateEnabled(isParentalGateEnabled);
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void SADidLoadAd(int placementId) {
                if (evtListener != null) {
                    evtListener.onInterstitialLoaded();
                }
            }

            @Override
            public void SADidNotLoadAd(int placementId) {
                if (evtListener != null) {
                    evtListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                }
            }

            @Override
            public void SADidShowAd() {
                if (evtListener != null) {
                    evtListener.onInterstitialShown();
                }
            }

            @Override
            public void SADidNotShowAd() {
                if (evtListener != null) {
                    evtListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                }
            }

            @Override
            public void SADidCloseAd() {
                if (evtListener != null) {
                    evtListener.onInterstitialDismissed();
                }
            }

            @Override
            public void SADidClickAd() {
                if (evtListener != null) {
                    evtListener.onInterstitialClicked();
                }
            }
        });
        SAInterstitialAd.load(placementId);
    }

    @Override
    protected void showInterstitial() {
        // play the ad
        SAInterstitialAd.play(this.context);
    }

    @Override
    protected void onInvalidate() {
        /** do nothing */
    }
}
