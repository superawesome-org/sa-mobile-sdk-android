/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.mopub;

import android.content.Context;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.publisher.SADefaults;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAInterstitialAd;
import tv.superawesome.sdk.publisher.SAOrientation;

/**
 * Class that extends the MoPub standard CustomEventInterstitial class in order to communicate with
 * MoPub and load an interstitial ad
 */
public class SAMoPubInterstitialCustomEvent extends CustomEventInterstitial {

    // private state vars
    private int placementId = 0;
    private Context context;

    /**
     * Overridden "loadInterstitial" method of CustomEventInterstitial that is
     * triggered when ad data needs to be loaded by MoPub.
     * This will trigger the SADefaults SDK procedures to create & load an ad
     *
     * @param context   current context (activity & fragment)
     * @param listener  an instance of a MoPub listener
     * @param map       values passed down from MoPub
     * @param map1      values passed down from MoPub
     */
    @Override
    protected void loadInterstitial(final Context context, final CustomEventInterstitialListener listener, Map<String, Object> map, Map<String, String> map1) {

        // get the context
        this.context = context;

        try {
            placementId = Integer.parseInt(map1.get(SAMoPub.kPLACEMENT_ID));
        } catch (Exception e) {
            placementId = SADefaults.defaultPlacementId();
        }

        boolean isTestEnabled;
        try {
            isTestEnabled = Boolean.valueOf(map1.get(SAMoPub.kTEST_ENABLED));
        } catch (Exception e) {
            isTestEnabled = SADefaults.defaultTestMode();
        }

        boolean isParentalGateEnabled;
        try {
            isParentalGateEnabled = Boolean.valueOf(map1.get(SAMoPub.kPARENTAL_GATE));
        } catch (Exception e) {
            isParentalGateEnabled = SADefaults.defaultParentalGate();
        }

        SAConfiguration configuration = SADefaults.defaultConfiguration();
        try {
            String config = map1.get(SAMoPub.kCONFIGURATION);
            if (config != null && config.equals("STAGING")) {
                configuration = SAConfiguration.STAGING;
            }
        } catch (Exception e) {
            // do nothing
        }

        SAOrientation orientation = SADefaults.defaultOrientation();
        try {
            String orient = map1.get(SAMoPub.kORIENTATION);
            if (orient != null && orient.equals("PORTRAIT")) {
                orientation = SAOrientation.PORTRAIT;
            }
            if (orient != null && orient.equals("LANDSCAPE")) {
                orientation = SAOrientation.LANDSCAPE;
            }
        } catch (Exception e) {
            // do nothing
        }

        // configure the interstitial
        SAInterstitialAd.setConfiguration(configuration);
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
                    case adEmpty:
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

    /**
     * Overridden "showInterstitial" method of CustomEventInterstitial that actually
     * displays an interstitial ad, after it has been loaded
     */
    @Override
    protected void showInterstitial() {
        SAInterstitialAd.play(placementId, this.context);
    }

    /**
     * Overridden "onInvalidate" method of CustomEventInterstitial that is triggered when ad data
     * is invalidated
     */
    @Override
    protected void onInvalidate() {
        // do nothing
    }
}
