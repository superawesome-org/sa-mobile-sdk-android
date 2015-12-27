package tv.superawesome.sdk.listeners;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAVideoAdListener {

    void adStarted(int placementId);
    void videoStarted(int placementId);
    void videoReachedFirstQuartile(int placementId);
    void videoReachedMidpoint(int placementId);
    void videoReachedThirdQuartile(int placementId);
    void videoEnded(int placementId);
    void adEnded(int placementId);
    void allAdsEnded(int placementId);
}
