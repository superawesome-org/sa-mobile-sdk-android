package tv.superawesome.sdk.parentalgate;

/**
 * Created by connor.leigh-smith on 28/08/15.
 */
public interface SAParentalGateListener {

    /*
    the three functions to implement for everyone
    who wants to implement SAParentalGateListener
    */
    public void onPressCancel();
    public void onPressContinueWithError();
    public void onPressContinueWithSuccess();

}
