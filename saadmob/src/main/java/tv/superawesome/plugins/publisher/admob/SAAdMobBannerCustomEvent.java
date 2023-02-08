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

import tv.superawesome.sdk.publisher.common.components.NumberGenerator;
import tv.superawesome.sdk.publisher.common.models.SAEvent;
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView;

/**
 * This adapter will be retired and instead use `SAAdMobAdapter` for all ad types
 */
@Deprecated
public class SAAdMobBannerCustomEvent implements CustomEventBanner {

    private boolean layoutChanged = false;
    private boolean adLoaded = false;
    private boolean setupCompleted = false;
    private BannerView bannerAd;

    @Override
    public void requestBannerAd(
            @NonNull Context context,
            @NonNull CustomEventBannerListener customEventBannerListener,
            @Nullable String s,
            @NonNull AdSize adSize,
            @NonNull MediationAdRequest mediationAdRequest,
            @Nullable Bundle bundle) {
        NumberGenerator numberGenerator = new NumberGenerator();
        bannerAd = new BannerView(context);
        bannerAd.setId(numberGenerator.nextIntForCache());

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
            bannerAd.setColor(bundle.getBoolean(SAAdMobExtras.kKEY_TRANSPARENT));
            bannerAd.setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE));
            bannerAd.setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE));
        }

        bannerAd.setListener(
                (int i, SAEvent saEvent) -> {
                    switch (saEvent) {
                        case AdLoaded:
                            // send load event
                            customEventBannerListener.onAdLoaded(bannerAd);

                            adLoaded = true;

                            if (layoutChanged && bannerAd != null && !setupCompleted) {
                                bannerAd.play();
                                setupCompleted = true;
                            }
                            break;
                        case AdEmpty:
                        case AdFailedToLoad:
                            customEventBannerListener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_NO_FILL, "", ""));
                            break;
                        case AdAlreadyLoaded:
                        case AdEnded:
                            break;
                        case AdShown:
                            customEventBannerListener.onAdOpened();
                            break;
                        case AdFailedToShow:
                            customEventBannerListener.onAdFailedToLoad(new AdError(AdRequest.ERROR_CODE_INTERNAL_ERROR, "", ""));
                            break;
                        case AdClicked:
                            customEventBannerListener.onAdClicked();
                            customEventBannerListener.onAdLeftApplication();
                            break;
                        case AdClosed:
                            customEventBannerListener.onAdClosed();
                            break;
                    }
                });

        try {
            bannerAd.addOnLayoutChangeListener(
                    (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                        layoutChanged = true;
                        if (adLoaded && !setupCompleted) {
                            bannerAd.play();
                        }
                    });
            int placementId = s != null ? Integer.parseInt(s) : 0;
            bannerAd.load(placementId, null);
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
