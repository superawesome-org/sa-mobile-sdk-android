package tv.superawesome.plugins.publisher.unity;

import android.content.Context;

import tv.superawesome.sdk.publisher.common.ui.common.BumperPage;

public class SAUnityBumperPage {

    /**
     * Method that sets the version
     *
     * @param context   current context
     * @param name      new bumper page name
     */
    public static void SuperAwesomeUnityBumperOverrideName (Context context, String name) {
        BumperPage.overrideName(name);
    }
}
