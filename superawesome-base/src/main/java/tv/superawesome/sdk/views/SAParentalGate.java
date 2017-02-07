/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.samodelspace.SAAd;


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
    private AlertDialog dialog = null;

    // the pg listener
    private SAParentalGateInterface listener = null;

    // private vars
    private Context c = null;
    private int position = 0;
    private String destination = null;

    SAParentalGate (Context context, int position, String destination) {
        this.c = context;
        this.position = position;
        this.destination = destination;
        listener = new SAParentalGateInterface() {
            @Override public void parentalGateOpen (int position) {}
            @Override public void parentalGateSuccess(int position, String destination) {}
            @Override public void parentalGateFailure(int position) {}
            @Override public void parentalGateCancel(int position) {}
        };
    }

    /**
     * Method that shows the parental gate popup and fires the necessary events
     */
    void show() {

        listener.parentalGateOpen(position);

        final int startNum = SAUtils.randomNumberBetween(RAND_MIN, RAND_MAX);
        final int endNum = SAUtils.randomNumberBetween(RAND_MIN, RAND_MAX);

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
        alert.setPositiveButton(SA_CHALLANGE_ALERTVIEW_CONTINUEBUTTON_TITLE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                int userValue = -1;

                // try parsing the result and check for mathematical corectness
                try {
                    userValue = Integer.parseInt(input.getText().toString());

                    if (userValue == (startNum + endNum)) {

                        listener.parentalGateSuccess(position, destination);

                    } else {

                        // go on error way
                        AlertDialog.Builder erroralert = new android.app.AlertDialog.Builder(c);
                        erroralert.setTitle(SA_ERROR_ALERTVIEW_TITLE);
                        erroralert.setMessage(SA_ERROR_ALERTVIEW_MESSAGE);

                        // set button action
                        erroralert.setPositiveButton(SA_ERROR_ALERTVIEW_CANCELBUTTON_TITLE, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                listener.parentalGateFailure(position);

                                // dismiss this
                                dialog.dismiss();

                            }
                        });
                        erroralert.show();
                    }

                }
                // catch the number format error and calce the parental gate
                catch (Exception e) {

                    listener.parentalGateCancel(position);

                }

                // dismiss
                dialog.dismiss();
            }
        });

        // create negative dialog
        alert.setNegativeButton(SA_CHALLANGE_ALERTVIEW_CANCELBUTTON_TITLE, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // dismiss
                dialog.dismiss();

                listener.parentalGateCancel(position);

            }
        });

        dialog = alert.create();
        dialog.show();
    }

    /**
     * Close method for the dialog
     */
    public void close () {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /**
     * Set the parental gate listener
     *
     * @param listener the listener instance
     */
    public void setListener (SAParentalGateInterface listener) {
        this.listener = listener != null ? listener : this.listener;
    }
}