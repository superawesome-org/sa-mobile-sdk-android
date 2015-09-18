package tv.superawesome.sdk.padlock;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.RatingBar;
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
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.dialog_padlock, null);

        TextView t = (TextView) v.findViewById(R.id.clickHereTextView);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingsBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
