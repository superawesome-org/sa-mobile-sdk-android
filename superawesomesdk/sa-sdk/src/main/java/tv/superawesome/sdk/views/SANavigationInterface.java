package tv.superawesome.sdk.views;

/**
 * Defines a common interface that SABannerAd, SAInterstitial and SAVideoActivity can adhere to
 * to make navigation a little less fragmented in the Android SDK
 */
public interface SANavigationInterface {
    /** this one function all three classes must implement */
    void advanceToClick();
}
