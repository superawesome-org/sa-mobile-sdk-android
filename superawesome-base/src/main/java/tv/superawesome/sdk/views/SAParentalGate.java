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

    // private vars
    private int startNum;
    private int endNum;
    private Context c = null;
    private WeakReference<Object> parentRef = null;
    private SAEvents events = null;
    private int gameWallPos = 0;

    // the alert dialog */
    private AlertDialog dialog;

    // vars to hold who has called this
    private boolean calledByBanner = false;
    private boolean calledByVideo = false;
    private boolean calledByGameWall = false;

    /**
     * Constructor with context, parent and reference ad
     *
     * @param c         the current context (activity or fragment)
     * @param parent    the parent - which might be a banner, video or app wall
     * @param _refAd    a reference to the current ad
     */
    SAParentalGate(Context c, Object parent, SAAd _refAd){
        super();
        this.c = c;
        this.parentRef = new WeakReference<> (parent);
        this.events = new SAEvents (c);
        this.events.setAd(_refAd != null ? _refAd : new SAAd());

        String className = parentRef.get().getClass().getName();
        String videoClassName = SAVideoAd.class.getCanonicalName();
        String bannerClassName = SABannerAd.class.getCanonicalName();
        String gamewallClassName = SAAppWall.class.getCanonicalName();
        calledByVideo = className.contains(videoClassName);
        calledByBanner = className.contains(bannerClassName);
        calledByGameWall = className.contains(gamewallClassName);
    }

    /**
     * Constructor with context, parent, reference ad and position;
     * Used by the App wall type ad
     *
     * @param c         the current context (activity or fragment)
     * @param parent    the parent - which might be a banner, video or app wall
     * @param _refAd    a reference to the current ad
     * @param position  the position in the app wall
     */
    SAParentalGate(Context c, Object parent, SAAd _refAd, int position) {
        this(c, parent, _refAd);
        gameWallPos = position;
    }

    /**
     * Method that shows the parental gate popup and fires the necessary events
     */
    void show() {

        // send Open Event
        events.sendEventsFor("pg_open");

        startNum = SAUtils.randomNumberBetween(RAND_MIN, RAND_MAX);
        endNum = SAUtils.randomNumberBetween(RAND_MIN, RAND_MAX);

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

        // pause video
        if (calledByVideo) {
            ((SAVideoAd) parentRef.get()).pause();
        }

        final AlertDialog.Builder aContinue = alert.setPositiveButton(SA_CHALLANGE_ALERTVIEW_CONTINUEBUTTON_TITLE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (!input.getText().toString().equals("")) {
                    int userValue = Integer.parseInt(input.getText().toString());

                    // dismiss
                    dialog.dismiss();

                    if (userValue == (startNum + endNum)) {

                        // send event
                        events.sendEventsFor("pg_success");

                        // continue w/ click event
                        if (calledByBanner) {
                            ((SABannerAd) parentRef.get()).click();
                        } else if (calledByVideo) {
                            ((SAVideoAd) parentRef.get()).click();
                        } else if (calledByGameWall) {
                            ((SAAppWall) parentRef.get()).click(gameWallPos);
                        }
                    } else {

                        // send event
                        events.sendEventsFor("pg_fail");

                        // go on error way
                        AlertDialog.Builder erroralert = new android.app.AlertDialog.Builder(c);
                        erroralert.setTitle(SA_ERROR_ALERTVIEW_TITLE);
                        erroralert.setMessage(SA_ERROR_ALERTVIEW_MESSAGE);

                        // set button action
                        erroralert.setPositiveButton(SA_ERROR_ALERTVIEW_CANCELBUTTON_TITLE, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                // dismiss this
                                dialog.dismiss();

                                // resume video
                                if (calledByVideo) {
                                    ((SAVideoAd) parentRef.get()).resume();
                                }
                            }
                        });
                        erroralert.show();

                    }
                }
                else {
                    // resume video
                    if (calledByVideo) {
                        ((SAVideoAd) parentRef.get()).resume();
                    }
                }
            }
        });

        alert.setNegativeButton(SA_CHALLANGE_ALERTVIEW_CANCELBUTTON_TITLE, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // send event
                events.sendEventsFor("pg_close");

                // dismiss
                dialog.dismiss();

                // resume video
                if (calledByVideo) {
                    ((SAVideoAd) parentRef.get()).resume();
                }
            }
        });

        dialog = alert.create();
        dialog.show();
    }

    /**
     * Function that closes the parental gate
     */
    public void close() {
        dialog.cancel();
    }
}