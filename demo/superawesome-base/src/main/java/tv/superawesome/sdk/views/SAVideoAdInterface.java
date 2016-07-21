package tv.superawesome.sdk.views;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAVideoAdInterface {

    /**
     * Callback when whole ad bundle starts
     * @param placementId
     */
    void adStarted(int placementId);

    /**
     * Callback when one video creative starts
     * @param placementId
     */
    void videoStarted(int placementId);

    /**
     * Callback when one video creative reaches 1/4
     * @param placementId
     */
    void videoReachedFirstQuartile(int placementId);

    /**
     * Callback when one video creative reaches 1/2
     * @param placementId
     */
    void videoReachedMidpoint(int placementId);

    /**
     * Callback when one video creative reaches 3/4
     * @param placementId
     */
    void videoReachedThirdQuartile(int placementId);

    /**
     * Callback when one video creative ends
     * @param placementId
     */
    void videoEnded(int placementId);

    /**
     * Callback when one video ad ends
     * @param placementId
     */
    void adEnded(int placementId);

    /**
     * Callback when all video ads have ended
     * @param placementId
     */
    void allAdsEnded(int placementId);
}
