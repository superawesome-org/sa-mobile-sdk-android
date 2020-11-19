package tv.superawesome.plugins.publisher.admob;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;

import tv.superawesome.sdk.publisher.common.components.NumberGenerator;
import tv.superawesome.sdk.publisher.ui.banner.BannerView;

public class SAAdMobBannerCustomEvent implements CustomEventBanner {

    private final int ID = new NumberGenerator().nextIntForCache();
    private boolean layouted = false;
    private boolean loaded = false;
    private boolean setup = false;
    private BannerView bannerAd;

    @Override
    public void requestBannerAd(final Context context, final CustomEventBannerListener listener, String s, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle) {

        bannerAd = new BannerView(context);
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
            bannerAd.setColor(bundle.getBoolean(SAAdMobExtras.kKEY_TRANSPARENT));
            bannerAd.setParentalGate(bundle.getBoolean(SAAdMobExtras.kKEY_PARENTAL_GATE));
            bannerAd.setBumperPage(bundle.getBoolean(SAAdMobExtras.kKEY_BUMPER_PAGE));
        }

        bannerAd.setListener((i, saEvent) -> {
            switch (saEvent) {
                case adLoaded: {
                    // send load event
                    if (listener != null) {
                        listener.onAdLoaded(bannerAd);
                    }

                    loaded = true;

                    if (layouted && bannerAd != null && !setup) {
                        bannerAd.play();
                        setup = true;
                    }
                    break;
                }
                case adEmpty:
                case adFailedToLoad: {
                    if (listener != null) {
                        listener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
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
                case adFailedToShow: {
                    if (listener != null) {
                        listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                    }
                    break;
                }
                case adClicked: {
                    if (listener != null) {
                        listener.onAdClicked();
                        listener.onAdLeftApplication();
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
        });

        try {
            bannerAd.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                layouted = true;
                if (loaded && !setup) {
                    bannerAd.play();
                }
            });
            int placementId = Integer.parseInt(s);
            bannerAd.load(placementId);
        } catch (NumberFormatException e) {
            if (listener != null) {
                listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            }
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
