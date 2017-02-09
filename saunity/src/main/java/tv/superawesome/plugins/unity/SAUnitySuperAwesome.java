/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sacpi.SACPIInterface;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnitySuperAwesome {

    // CPI name
    private static final String unityName = "SAUnitySuperAwesome";

    /**
     * Method that sends a callback to Unity after a
     * CPI operation on production
     *
     * @param context current context (activity or fragment)
     */
    public static void SuperAwesomeUnitySuperAwesomeHandleCPI (Context context) {

        SuperAwesome.getInstance().handleCPI(context, new SACPIInterface() {
            @Override
            public void saDidCountAnInstall(boolean success) {
                SAUnityCallback.sendCPICallback(unityName, success, "HandleCPI");
            }
        });

    }

    /**
     * Method that sets the version
     *
     * @param context current context
     * @param version current version string
     * @param sdk     current sdk string
     */
    public static void SuperAwesomeUnitySuperAwesomeSetVersion (Context context, String version, String sdk) {

        SuperAwesome.getInstance().overrideVersion(version);
        SuperAwesome.getInstance().overrideSdk(sdk);

    }
}
