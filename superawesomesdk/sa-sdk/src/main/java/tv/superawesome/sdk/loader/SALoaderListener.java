package tv.superawesome.sdk.loader;

import tv.superawesome.sdk.models.SAAd;

/**
 * Loader interface
 */
public interface SALoaderListener {

    /**
     * After SALoader pre-loads an Ad, this is the function that should be called
     * @param ad - sends back the Ad object that was loaded
     */
    void didLoadAd(SAAd ad);

    /**
     * After SALoader fails to pre-loads an Ad, this is the function that should be called
     * @param placementId - sends back the Ad's placement Id
     */
    void didFailToLoadAdForPlacementId(int placementId);

}