package tv.superawesome.sdk.views;

import tv.superawesome.lib.samodelspace.SAAd;

/**
 * Defines a common interface that SABannerAd, SAInterstitial and SAFullscreenVideoAd can adhere to
 * to make navigation a little less fragmented in the Android SDK
 */
public interface SAViewInterface {

    // load the ad
    void load(int placementId);

    /** sets the ad for a SABannerAd, SAInterstitialAd, etc type class */
    void setAd(SAAd ad);

    /** return an Ad */
    SAAd getAd();

    /** show the padlock */
    boolean shouldShowPadlock();

    /** play the ad */
    void play();

    /** second pass at trying to go to URL */
    void click();

    /** resize view or view controller */
    void resize(int width, int height);

    /** close the ad */
    void close();

}
