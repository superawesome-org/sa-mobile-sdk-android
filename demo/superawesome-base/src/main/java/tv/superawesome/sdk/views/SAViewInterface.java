package tv.superawesome.sdk.views;

import tv.superawesome.lib.samodelspace.SAAd;

/**
 * Defines a common interface that SABannerAd, SAInterstitial and SAFullscreenVideoAd can adhere to
 * to make navigation a little less fragmented in the Android SDK
 */
public interface SAViewInterface {
    /** sets the ad for a SABannerAd, SAInterstitialAd, etc type class */
    void setAd(SAAd ad);

    /** return an Ad */
    SAAd getAd();

    /** play the ad */
    void play();

    /** show the padlock */
    boolean shouldShowPadlock();

    /** close the ad */
    void close();

    /** second pass at trying to go to URL */
    void advanceToClick();

    /** resize view or view controller */
    void resizeToSize(int width, int height);
}
