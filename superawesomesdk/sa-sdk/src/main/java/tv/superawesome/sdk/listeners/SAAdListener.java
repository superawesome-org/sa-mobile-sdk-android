package tv.superawesome.sdk.listeners;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAAdListener {

    void adWasShown(int placementId);
    void adFailedToShow(int placementId);
    void adWasClosed(int placementId);
    void adWasClicked(int placementId);
    void adHasIncorrectPlacement(int placementId);
}
