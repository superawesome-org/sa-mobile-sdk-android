package tv.superawesome.sdk.views;

import tv.superawesome.sdk.data.Models.SAAd;

/**
 * Defines a common interface that SABannerAd, SAInterstitial and SAVideoActivity can adhere to
 * to make navigation a little less fragmented in the Android SDK
 */
public interface SAViewProtocol {
    /** this one function all three classes must implement */
    void setAd(SAAd ad);
    SAAd getAd();
    void play();
    void close();
    void tryToGoToURL(String url);
    void advanceToClick();
    void resizeToOrientation(int orientation);

}
