/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.cpi.SAInstallEventInterface;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityCPI {

    // CPI name
    private static final String unityName = "SuperAwesomeCPI";

    /**
     * Method that sends a callback to Unity after a
     * CPI operation on production
     */
    public static void SuperAwesomeUnitySuperAwesomeHandleCPI (Context context) {

        SuperAwesome.getInstance().handleCPI(context, new SAInstallEventInterface() {
            @Override
            public void didCountAnInstall(boolean success) {
                SAUnityCallback.sendCPICallback(unityName, success, "HandleCPI");
            }
        });

    }

    /**
     * Method that sends a callback to Unity after a
     * CPI operation on staging
     */
    public static void SuperAwesomeUnitySuperAwesomeHandleStagingCPI (Context context) {

        SuperAwesome.getInstance().handleStagingCPI(context, new SAInstallEventInterface() {
            @Override
            public void didCountAnInstall(boolean success) {
                SAUnityCallback.sendCPICallback(unityName, success, "HandleStagingCPI");
            }
        });

    }

}
