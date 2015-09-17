package tv.superawesome.sdk.parentalgate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
//import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Random;

/**
 * Created by connor.leigh-smith on 28/08/15.
 *
 * The SAParentalGate class. It's main goal is to show an AlertDialog
 * that challenges the user to respond to a simple math riddle
 *
 */
public class SAParentalGate {

    // constants for the rand nr. generator
    private static final int RAND_MIN = 50;
    private static final int RAND_MAX = 99;

    // JAVA constants for text based stuff
    private static final String SA_CHALLANGE_ALERTVIEW_TITLE = "Parental Gate";
    private static final String SA_CHALLANGE_ALERTVIEW_MESSAGE = "Please solve the following problem to continue: ";
    private static final String SA_CHALLANGE_ALERTVIEW_CANCELBUTTON_TITLE = "Cancel";
    private static final String SA_CHALLANGE_ALERTVIEW_CONTINUEBUTTON_TITLE = "Continue";

    private static final String SA_ERROR_ALERTVIEW_TITLE = "Sorry, that was the wrong answer";
    private static final String SA_ERROR_ALERTVIEW_MESSAGE = "Please talk to somebody more responsable so that he may guide you";
    private static final String SA_ERROR_ALERTVIEW_CANCELBUTTON_TITLE = "Ok";

    // variables private
    private int startNum;
    private int endNum;
    private Context c;
    private SAParentalGateListener listener;

    public SAParentalGate(Context c){
    super();
        // do nothing constructor
        this.c = c;
    }

    // show function
    public void show() {
        startNum = randInt(RAND_MIN, RAND_MAX);
        endNum = randInt(RAND_MIN, RAND_MAX);

        /* we have an alert dialog builder */
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        // set title and message
        alert.setTitle(SA_CHALLANGE_ALERTVIEW_TITLE);
        alert.setMessage(SA_CHALLANGE_ALERTVIEW_MESSAGE + startNum + " + " + endNum + " = ? ");

        // Set an EditText view to get user input
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

//        input.requestFocus();
//        InputMethodManager imm = (InputMethodManager) c.getSystemService(c.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);

        final AlertDialog.Builder aContinue = alert.setPositiveButton(SA_CHALLANGE_ALERTVIEW_CONTINUEBUTTON_TITLE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (!input.getText().toString().equals("")) {
                    int userValue = Integer.parseInt(input.getText().toString());

                    if (userValue == (startNum + endNum)) {
                        // go on success way
                        if (listener != null) {
                            listener.onPressContinueWithSuccess();
                        }
                    } else {

                        // go on error way
                        AlertDialog.Builder alert = new android.app.AlertDialog.Builder(c);
                        alert.setTitle(SA_ERROR_ALERTVIEW_TITLE);
                        alert.setMessage(SA_ERROR_ALERTVIEW_MESSAGE);

                        // set button action
                        alert.setPositiveButton(SA_ERROR_ALERTVIEW_CANCELBUTTON_TITLE, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do nothing
                                if (listener != null) {
                                    listener.onPressContinueWithError();
                                }
                                return;
                            }
                        });
                        alert.show();

                    }
                }

                return;
            }
        });

        alert.setNegativeButton(SA_CHALLANGE_ALERTVIEW_CANCELBUTTON_TITLE, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // go on cancel way
                if (listener != null) {
                    listener.onPressCancel();
                }
                return;
            }
        });

        // finally show the alert
        alert.show();
    }

    // aux function for random number generation
    private static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    // setters and getters (it's 2015, why is not this automated by now?)
    public void setListener(SAParentalGateListener list){
        this.listener = list;
    }

    public  SAParentalGateListener getListener(){
        return this.listener;
    }
}
