/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sautils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Class that abstracts away a progress dialog
 */
public class SALoadScreen {

    // private instance of the progress dialog
    private ProgressDialog progress;

    // instance var for singleton
    private static SALoadScreen instance = new SALoadScreen();

    /**
     * Private constructor
     */
    private SALoadScreen() {
        // do nothing
    }

    /**
     * Getter for the current instance
     *
     * @return the current instance
     */
    public static SALoadScreen getInstance(){
        return instance;
    }

    /**
     * Public method that creates and shows a progress dialog
     *
     * @param c the current context (activity or fragment)
     */
    public void show(Context c) {
        progress = new ProgressDialog(c);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();
    }

    /**
     * Public method that dismisses a progress dialog
     */
    public void hide() {
        progress.dismiss();
    }
}
