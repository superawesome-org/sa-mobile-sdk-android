package tv.superawesome.sdk.views;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAInterface {

    void adWasLoaded(int placementId);
    void adWasNotLoaded(int placementId);

    /**
     * Callback when ad is first shown
     * @param placementId
     */
    void adWasShown(int placementId);

    /**
     * Callback when an ad failed to show
     * @param placementId
     */
    void adFailedToShow(int placementId);

    /**
     * Callback for interstitial and video ads when closed
     * @param placementId
     */
    void adWasClosed(int placementId);

    /**
     * Callback called when user clicks-through the ad
     * @param placementId
     */
    void adWasClicked(int placementId);

    /**
     * Callback when ad has incorrect placement for its purpose (e.g. video ad with banner type placement)
     * @param placementId
     */
    void adHasIncorrectPlacement(int placementId);
}
