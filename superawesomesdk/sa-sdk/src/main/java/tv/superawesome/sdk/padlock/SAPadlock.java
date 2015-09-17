package tv.superawesome.sdk.padlock;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import tv.superawesome.sdk.R;

/**
 * Created by gabriel.coman on 17/09/15.
 */
public class SAPadlock {
    public SAPadlock(){
        // do nothing
    }

    public void createPadlock(Context context) {
        /* What should happen when the padlock is tapped? */
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);

        builder.setView(R.layout.dialog_padlock);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
