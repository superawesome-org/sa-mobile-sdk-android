/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sacpi.SACPI;
import tv.superawesome.lib.sacpi.SACPIInterface;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityCPI {

    // CPI name
    private static final String unityName = "SACPI";

    /**
     * Method that sends a callback to Unity after a
     * CPI operation on production
     *
     * @param context current context (activity or fragment)
     */
    public static void SuperAwesomeUnitySACPIHandleCPI (Context context) {

        SACPI.getInstance().sendInstallEvent(context, new SACPIInterface() {
            @Override
            public void saDidCountAnInstall(boolean success) {
                SAUnityCallback.sendCPICallback(unityName, success, "HandleCPI");
            }
        });

    }
}
