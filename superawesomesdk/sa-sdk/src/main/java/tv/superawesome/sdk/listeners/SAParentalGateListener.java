package tv.superawesome.sdk.listeners;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAParentalGateListener {

    void parentalGateWasCanceled(int placementId);
    void parentalGateWasFailed(int placementId);
    void parentalGateWasSucceded(int placementId);
}
