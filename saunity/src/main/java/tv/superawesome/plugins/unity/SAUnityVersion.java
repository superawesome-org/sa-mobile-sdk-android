package tv.superawesome.plugins.unity;


import android.content.Context;

import tv.superawesome.sdk.SuperAwesome;

public class SAUnityVersion {

    public static void SuperAwesomeUnitySetVersion(Context context, String version, String sdk) {

        SuperAwesome.getInstance().overrideVersion(version);
        SuperAwesome.getInstance().overrideSdk(sdk);

    }

}
