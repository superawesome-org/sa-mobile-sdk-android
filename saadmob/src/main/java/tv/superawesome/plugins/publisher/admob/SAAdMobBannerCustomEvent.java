package tv.superawesome.plugins.publisher.admob;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.publisher.SABannerAd;
import tv.superawesome.sdk.publisher.SAInterface;

/**
 * This adapter will be retired and instead use `SAAdMobAdapter` for all ad types
 */
public class SAAdMobBannerCustomEvent implements CustomEventBanner {

    private final int ID = SAUtils.randomNumberBetween(1000000, 1500000);
    private boolean layoutChanged = false;
    private boolean adLoaded = false;
    private boolean setupCompleted = false;
    private SABannerAd bannerAd;

    @Override
    public void requestBannerAd(
            @NonNull Context context,
            @NonNull CustomEventBannerListener customEventBannerListener,
            @Nullable String s,
            @NonNull AdSize adSize,
            @NonNull MediationAdRequest mediationAdRequest,
            @Nullable Bundle bundle) {

        bannerAd = new SABannerAd(context);
        bannerAd.setId(ID);

        // Internally, smart banners use constants to represent their ad size, which means a call to
        // AdSize.getHeight could return a negative value. You can accommodate this by using
        // AdSize.getHeightInPixels and AdSize.getWidthInPixels instead, and then adjusting to match
        // the device's display metrics.
        int widthInPixels = adSize.getWidthInPixels(context);
        int heightInPixels = adSize.getHeightInPixels(context);
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int widthInDp = Math.round(widthInPixels / displayMetrics.density);
        int heightInDp = Math.round(heightInPixels / displayMetrics.density);

        bannerAd.setLayoutParams(new ViewGroup.LayoutParams(widthInDp, heightInDp));

        if (bundle != null) {
            bannerAd.setTestMode(bundle.getBoolean(SAAdMobExtras.kKEY_TEST));
            bannerAd.setConfiguration(
                    SAConfiguration.fromOrdinal(bundle.getInt(SAAdMobExtras.kKEY_CONFIGURATION)));
            bannerAd.setColor(bundle.getBoolean(SAAdMobExtras.kKEY_TRANSPARENT));
            bannerAd.setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE));
            bannerAd.setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE));
        }

        bannerAd.setListener(
                (SAInterface)
                        (i, saEvent) -> {
                            switch (saEvent) {
                                case adLoaded:
                                    // send load event
                                    customEventBannerListener.onAdLoaded(bannerAd);

                                    adLoaded = true;

                                    if (layoutChanged && bannerAd != null && !setupCompleted) {
                                        bannerAd.play(context);
                                        setupCompleted = true;
                                    }
                                    break;
                                case adEmpty:
                                case adFailedToLoad:
                                    customEventBannerListener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_NO_FILL, "", ""));
                                    break;
                                case adAlreadyLoaded:
                                case adEnded:
                                    break;
                                case adShown:
                                    customEventBannerListener.onAdOpened();
                                    break;
                                case adFailedToShow:
                                    customEventBannerListener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_INTERNAL_ERROR, "", ""));
                                    break;
                                case adClicked:
                                    customEventBannerListener.onAdClicked();
                                    customEventBannerListener.onAdLeftApplication();
                                    break;
                                case adClosed:
                                    customEventBannerListener.onAdClosed();
                                    break;
                            }
                        });

        try {
            bannerAd.addOnLayoutChangeListener(
                    (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                        layoutChanged = true;
                        if (adLoaded && !setupCompleted) {
                            bannerAd.play(context);
                        }
                    });
            int placementId = s != null ? Integer.parseInt(s) : 0;
            bannerAd.load(placementId);
        } catch (NumberFormatException e) {
            customEventBannerListener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_INVALID_REQUEST, "", ""));
        }
    }

    @Override
    public void onDestroy() {
        if (bannerAd != null) {
            bannerAd.close();
            ViewGroup parent = (ViewGroup) bannerAd.getParent();
            if (parent != null) {
                parent.removeView(bannerAd);
            }
            bannerAd = null;
        }
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
