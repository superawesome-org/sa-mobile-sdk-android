package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import tv.superawesome.sdk.publisher.common.ui.common.BumperPageActivity;

public class SAUnityBumperPage {

    /**
     * Method that sets the bumper page name
     *
     * @param context current context
     * @param name    new bumper page name
     */
    public static void SuperAwesomeUnityBumperOverrideName(Context context, String name) {
        BumperPageActivity.Companion.overrideName(name);
    }
}
