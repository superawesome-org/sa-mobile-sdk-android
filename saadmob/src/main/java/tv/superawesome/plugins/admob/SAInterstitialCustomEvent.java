package tv.superawesome.plugins.admob;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;

import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;

public class SAInterstitialCustomEvent implements CustomEventInterstitial {

    private Context context = null;
    private Integer loadedPlacementId = 0;

    @Override
    public void requestInterstitialAd(final Context context, final CustomEventInterstitialListener listener, String s, MediationAdRequest mediationAdRequest, Bundle bundle) {

        // save the context
        this.context = context;

        SAInterstitialAd.setConfigurationStaging();
        SAInterstitialAd.setTestMode(mediationAdRequest.isTesting());
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {

                    case adLoaded: {

                        loadedPlacementId = placementId;

                        if (listener != null) {
                            listener.onAdLoaded();
                        }
                        break;
                    }
                    case adFailedToLoad: {
                        if (listener != null) {
                            listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        }
                        break;
                    }
                    case adAlreadyLoaded:
                        break;
                    case adShown: {
                        if (listener != null) {
                            listener.onAdOpened();
                        }
                        break;
                    }
                    case adFailedToShow:
                        break;
                    case adClicked: {
                        if (listener != null) {
                            listener.onAdClicked();
                        }
                        break;
                    }
                    case adEnded:
                        break;
                    case adClosed: {
                        if (listener != null) {
                            listener.onAdClosed();
                        }
                        break;
                    }
                }
            }
        });


        try {
            Integer placementId = Integer.parseInt(s);
            SAInterstitialAd.load(placementId, context);
        } catch (NumberFormatException e) {
            if (listener != null) {
                listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            }
        }
    }

    @Override
    public void showInterstitial() {
        SAInterstitialAd.play(loadedPlacementId, context);
    }

    @Override
    public void onDestroy() {
        // do nothing
    }

    @Override
    public void onPause() {
        // do nothing
    }

    @Override
    public void onResume() {
        // do nothing
    }
}
