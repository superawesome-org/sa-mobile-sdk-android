package tv.superawesome.lib.sanetwork;

import android.app.Application;

/**
 * Created by gabriel.coman on 28/12/15.
 */
public class SAApplication extends Application {

    private static Application sInstance;

    public SAApplication() {
        sInstance = this;
    }

    public static Application getInstance() {
        return sInstance;
    }
}
