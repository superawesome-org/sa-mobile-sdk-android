package tv.superawesome.lib.saparentalgate;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

import java.util.Random;

/**
 * Class that defines a parental gate - basically a pop-up that, when enables, forces users to
 * respond to a mini-math quiz in order to proceed forward
 */
public class SAParentalGate {

    // constants for the rand nr. generator
    private static final int RAND_MIN = 50;
    private static final int RAND_MAX = 99;

    // constants for text based stuff
    private static final String SA_CHALLANGE_ALERTVIEW_TITLE = "Parental Gate";
    private static final String SA_CHALLANGE_ALERTVIEW_MESSAGE = "Please solve the following problem to continue: ";
    private static final String SA_CHALLANGE_ALERTVIEW_CANCELBUTTON_TITLE = "Cancel";
    private static final String SA_CHALLANGE_ALERTVIEW_CONTINUEBUTTON_TITLE = "Continue";

    private static final String SA_ERROR_ALERTVIEW_TITLE = "Oops! That was the wrong answer.";
    private static final String SA_ERROR_ALERTVIEW_MESSAGE = "Please seek guidance from a responsible adult to help you continue.";
    private static final String SA_ERROR_ALERTVIEW_CANCELBUTTON_TITLE = "Ok";

    // the alert dialog
    private static AlertDialog dialog = null;

    // the pg listener
    private static SAParentalGate.Interface listener = new SAParentalGate.Interface() {
        @Override public void parentalGateOpen () {}
        @Override public void parentalGateSuccess() {}
        @Override public void parentalGateFailure() {}
        @Override public void parentalGateCancel() {}
    };

    /**
     * Method that shows the parental gate popup and fires the necessary events
     */
    public static void show(final Context c) {

        listener.parentalGateOpen();

        final int startNum = randomNumberBetween(RAND_MIN, RAND_MAX);
        final int endNum = randomNumberBetween(RAND_MIN, RAND_MAX);

        // we have an alert dialog builder
        final AlertDialog.Builder alert = new AlertDialog.Builder(c);
        // set title and message
        alert.setTitle(SA_CHALLANGE_ALERTVIEW_TITLE);
        alert.setCancelable(false);
        alert.setMessage(SA_CHALLANGE_ALERTVIEW_MESSAGE + startNum + " + " + endNum + " = ? ");

        // Set an EditText view to get user input
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        // create positive dialog
        alert.setPositiveButton(SA_CHALLANGE_ALERTVIEW_CONTINUEBUTTON_TITLE, (dialog, whichButton) -> {

            int userValue;

            // try parsing the result and check for mathematical correctness
            try {
                userValue = Integer.parseInt(input.getText().toString());

                if (userValue == (startNum + endNum)) {

                    listener.parentalGateSuccess();

                } else {

                    // go on error way
                    AlertDialog.Builder erroralert = new AlertDialog.Builder(c);
                    erroralert.setTitle(SA_ERROR_ALERTVIEW_TITLE);
                    erroralert.setMessage(SA_ERROR_ALERTVIEW_MESSAGE);

                    // set button action
                    erroralert.setPositiveButton(SA_ERROR_ALERTVIEW_CANCELBUTTON_TITLE, (dialog1, whichButton1) -> {

                        listener.parentalGateFailure();

                        // dismiss this
                        dialog1.dismiss();

                    });
                    erroralert.show();
                }

            }
            // catch the number format error and calce the parental gate
            catch (Exception e) {

                listener.parentalGateCancel();

            }

            // dismiss
            dialog.dismiss();
        });

        // create negative dialog
        alert.setNegativeButton(SA_CHALLANGE_ALERTVIEW_CANCELBUTTON_TITLE, (dialog, which) -> {

            // dismiss
            dialog.dismiss();

            listener.parentalGateCancel();

        });

        dialog = alert.create();
        dialog.show();
    }

    /**
     * Close method for the dialog
     */
    public static void close () {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /**
     * Set the parental gate listener
     *
     * @param lis the listener instance
     */
    public static void setListener (SAParentalGate.Interface lis) {
        listener = lis != null ? lis : listener;
    }

    private static int randomNumberBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public interface Interface {
        /**
         * Method part of SAParentalGateInterface called when the gate is opened
         */
        void parentalGateOpen ();

        /**
         * Method part of SAParentalGateInterface called when the gate is successful
         */
        void parentalGateSuccess ();

        /**
         * Method part of SAParentalGateInterface called when the gate is failed
         */
        void parentalGateFailure ();

        /**
         * Method part of SAParentalGateInterface called when the gate is closed
         */
        void parentalGateCancel ();
    }
}
