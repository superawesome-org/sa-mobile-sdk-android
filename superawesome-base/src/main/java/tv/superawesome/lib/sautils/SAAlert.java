/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sautils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

/**
 * This class abstracts away the creation of a simple alert dialog
 */
public class SAAlert {

    // constants
    public static final int OK_BUTTON = 0;
    public static final int CANCEL_BUTTON = 1;

    // private instance of an alert dialog
    private AlertDialog dialog;

    // private instance of an input box
    private EditText input;

    // singleton instance for the SAAlert class
    private static SAAlert instance = new SAAlert();

    /**
     * Private constructor
     */
    private SAAlert(){
        // do nothing
    }

    /**
     * Get the only existing instance of the SAAlert class
     *
     * @return instance variable
     */
    public static SAAlert getInstance(){
        return instance;
    }

    /**
     * Main public method of the class, that creates and displays a new alert
     *
     * @param c         the current context (activity or fragment)
     * @param title     the alert box title
     * @param message   the alert box message
     * @param okTitle   the text for the "positive dismiss" button
     * @param nokTitle  the text for the "negative dismiss" button
     * @param hasInput  whether the alert should display an input box or not
     * @param inputType the text type for the input box, if present
     * @param listener1 an instance of the SAAlertInterface, used to send messages back to the
     *                  library user
     */
    public void show(Context c, String title, String message, String okTitle, String nokTitle, boolean hasInput, int inputType, final SAAlertInterface listener1) {

        // create a new listener, that is never null
        final SAAlertInterface listener = listener1 != null ? listener1 : new SAAlertInterface() {@Override public void saDidClickOnAlertButton(int button, String message) {}};

        // create a new alert builder
        final AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setCancelable(false);
        alert.setTitle(title);
        alert.setMessage(message);

        // add input, if it's the case
        if (hasInput) {
            input = new EditText(c);
            input.setInputType(inputType);
            alert.setView(input);
        }

        alert.setPositiveButton(okTitle, new DialogInterface.OnClickListener() {
            /**
             * Method that gets called when a user presses the OK button / positive dismiss of an
             * alert dialog
             *
             * @param dialog        current dialog reference
             * @param whichButton   which button was saDidClickOnAlertButton
             */
            public void onClick(DialogInterface dialog, int whichButton) {
                listener.saDidClickOnAlertButton(OK_BUTTON, input != null ? input.getText().toString() : null);
            }
        });

        if (nokTitle != null) {
            alert.setNegativeButton(nokTitle, new DialogInterface.OnClickListener() {
                /**
                 * Method that gets called when the negative button is clicked
                 *
                 * @param dialog current dialog reference
                 * @param which  which button was saDidClickOnAlertButton
                 */
                public void onClick(DialogInterface dialog, int which) {
                    listener.saDidClickOnAlertButton(CANCEL_BUTTON, null);
                }
            });
        }

        // create and show
        dialog = alert.create();
        dialog.show();
    }
}
