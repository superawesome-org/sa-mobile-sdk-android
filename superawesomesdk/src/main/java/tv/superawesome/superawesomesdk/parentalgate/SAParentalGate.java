package tv.superawesome.superawesomesdk.parentalgate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import java.util.Random;
/**
 * Created by connor.leigh-smith on 28/08/15.
 */
public class SAParentalGate {

    // constants
    private static final int RAND_MIN = 50;
    private static final int RAND_MAX = 99;
    private static final String TAG = "ParentalTag";

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
        alert.setTitle("Parental gate");
        alert.setMessage("Solve the following problem to continue: "+startNum+" + "+endNum+ " = ? ");

        // Set an EditText view to get user input
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        final AlertDialog.Builder aContinue = alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (!input.getText().toString().equals("")) {
                    int userValue = Integer.parseInt(input.getText().toString());

                    if (userValue == (startNum + endNum)) {
                        // go on success way
                        listener.onPressContinueWithSuccess();
                    } else {
                        // go on error way
                        listener.onPressContinueWithError();
                    }
                }


                return;
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                // go on cancel way
                listener.onPressCancel();

                return;
            }
        });
        alert.show();
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    // setter
    public void setListener(SAParentalGateListener list){
        this.listener = list;
    }

    public  SAParentalGateListener getListener(){
        return this.listener;
    }
}
