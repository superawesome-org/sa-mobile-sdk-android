package tv.superawesome.sdk.padlock;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import tv.superawesome.sdk.R;

/**
 * Created by gabriel.coman on 17/09/15.
 */
public class SAPadlock{

    static private final String url = "http://www.superawesome.tv/en/";

    public SAPadlock(){
        // do nothing
    }

    public void createPadlock(final Context context) {
        /* What should happen when the padlock is tapped? */
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);

        // create the view
        View v = View.inflate(context, R.layout.dialog_padlock, null);

        // get the text view and add listeners
        TextView t = (TextView)v.findViewById(R.id.clickHereTextView);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        // set view and other builder stuff
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        // create the dialog and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
