package tv.superawesome.sdk.parentalgate;

/**
 * Created by connor.leigh-smith on 28/08/15.
 *
 * This is the SAParentalGateListener public interface. It define three base functions for
 * everyone who wants to implement all the full functionality of SAParentalGate
 *
 * @method onPressCancel() - if defined should show what happens when a user cancels the challenge
 * submitted by the SDK. Obviously, this means that he also will not continue with the add
 *
 * @method onPressContinueWithError() - when the user presses the continue button but the number
 * he imputed is wrong. Usually a warning will appear.
 *
 * @method onPressContinueWithSuccess() - when the user presses the continue button and the number
 * he imputed is correct. Usually he should be taken to the ad.
 *
 */
public interface SAParentalGateListener {

    /*
     * the three functions to define / implement for everyone
     * who wants to implement SAParentalGateListener
    */
    public void onPressCancel();
    public void onPressContinueWithError();
    public void onPressContinueWithSuccess();

}
