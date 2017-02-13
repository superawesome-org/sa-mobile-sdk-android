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
