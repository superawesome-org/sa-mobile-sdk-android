/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sautils;

/**
 * This interface defines methods that correspond to the SAAlert class.
 */
public interface SAAlertInterface {

    /**
     * Method gets called when a user clicks on one of the buttons of an alert dialog.
     * It's used to send back information to the library user
     *
     * @param button    which button was clicked
     * @param message   the message that was recorded in the input box, if it exists
     */
    void saDidClickOnAlertButton(int button, String message);
}
