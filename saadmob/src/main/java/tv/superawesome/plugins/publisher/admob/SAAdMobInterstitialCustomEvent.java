package tv.superawesome.plugins.publisher.admob;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAInterstitialAd;
import tv.superawesome.sdk.publisher.SAOrientation;

/**
 * This adapter will be retired and instead use `SAAdMobAdapter` for all ad types
 */
public class SAAdMobInterstitialCustomEvent implements CustomEventInterstitial {

    private Context context = null;
    private Integer loadedPlacementId = 0;

    @Override
    public void requestInterstitialAd(@NonNull final Context context, @NonNull final CustomEventInterstitialListener listener, String s, @NonNull MediationAdRequest mediationAdRequest, Bundle bundle) {

        // save the context
        this.context = context;

        // set values
        if (bundle != null) {
            SAInterstitialAd.setConfiguration(SAConfiguration.fromOrdinal(bundle.getInt(SAAdMobExtras.kKEY_CONFIGURATION)));
            SAInterstitialAd.setTestMode(bundle.getBoolean(SAAdMobExtras.kKEY_TEST));
            SAInterstitialAd.setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE));
            SAInterstitialAd.setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE));
            SAInterstitialAd.setBackButton(bundle.getBoolean(SAAdMobExtras.kKEY_BACK_BUTTON));
            SAInterstitialAd.setOrientation(SAOrientation.fromValue(bundle.getInt(SAAdMobExtras.kKEY_ORIENTATION)));
        }

        SAInterstitialAd.setListener((SAInterface) (placementId, event) -> {
            switch (event) {
                case adLoaded:
                    loadedPlacementId = placementId;
                    listener.onAdLoaded();
                    break;

                case adEmpty:
                case adFailedToLoad:
                    listener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_NO_FILL, "", ""));
                    break;

                case adAlreadyLoaded:
                case adEnded:
                    break;
                case adShown:
                    listener.onAdOpened();
                    break;

                case adFailedToShow:
                    listener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_INTERNAL_ERROR, "", ""));

                    break;

                case adClicked:
                    listener.onAdClicked();
                    listener.onAdLeftApplication();
                    break;

                case adClosed:
                    listener.onAdClosed();
                    break;

            }
        });

        try {
            int placementId = Integer.parseInt(s);
            SAInterstitialAd.load(placementId, context);
        } catch (NumberFormatException e) {
            listener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_INVALID_REQUEST, "", ""));
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
