package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import tv.superawesome.sdk.publisher.ui.common.BumperPageActivity;

public class SAUnityBumperPage {

    /**
     * Method that sets the version
     *
     * @param context current context
     * @param name    new bumper page name
     */
    public static void overrideName(Context context, String name) {
        BumperPageActivity.Companion.overrideName(name);
    }
}
