package tv.superawesome.sdk.listeners;

/**
 * Created by gabriel.coman on 11/01/16.
 */
public interface SAParentalGateListener {
    void parentalGateWasCanceled(int placementId);
    void parentalGateWasFailed(int placementId);
    void parentalGateWasSucceded(int placementId);
}
