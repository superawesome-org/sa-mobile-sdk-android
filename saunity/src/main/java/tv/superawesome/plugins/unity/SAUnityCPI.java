package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.cpi.SAInstallEventInterface;

public class SAUnityCPI {

    private static final String unityName = "SuperAwesomeCPI";

    public static void SuperAwesomeUnitySuperAwesomeHandleCPI (Context context) {

        SuperAwesome.getInstance().handleCPI(context, new SAInstallEventInterface() {
            @Override
            public void didCountAnInstall(boolean success) {
                SAUnityCallback.sendCPICallback(unityName, success, "HandleCPI");
            }
        });

    }

    public static void SuperAwesomeUnitySuperAwesomeHandleStagingCPI (Context context) {

        SuperAwesome.getInstance().handleStagingCPI(context, new SAInstallEventInterface() {
            @Override
            public void didCountAnInstall(boolean success) {
                SAUnityCallback.sendCPICallback(unityName, success, "HandleStagingCPI");
            }
        });

    }

}
