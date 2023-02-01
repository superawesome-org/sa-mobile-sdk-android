package tv.superawesome.sdk.publisher;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.Map;

import tv.superawesome.lib.sagdprisminorsdk.minor.SAAgeCheck;
import tv.superawesome.lib.sagdprisminorsdk.minor.process.GetIsMinorInterface;
import tv.superawesome.lib.sanetwork.file.SAFileDownloader;

/**
 * Created by gabriel.coman on 30/04/2018.
 */

public class AwesomeAds {

    private static boolean isInitialised = false;

    public static void init(Application application, boolean loggingEnabled, Map<String, String> options) {
        QueryAdditionalOptions.Companion.setDefault(new QueryAdditionalOptions(options));
        init(application, loggingEnabled);
    }

    public static void init(Application application, boolean loggingEnabled) {
        if (!isInitialised) {
            Log.d("SuperAwesome", "Initialising AwesomeAds!");
            SAFileDownloader.cleanup(application);
            isInitialised = true;
        } else {
            Log.d("SuperAwesome", "Already initialised AwesomeAds!");
        }
    }

    public static void init(Context context, boolean loggingEnabled) {
        if (!isInitialised) {
            Log.d("SuperAwesome", "Initialising AwesomeAds!");
            SAFileDownloader.cleanup(context);
            isInitialised = true;
        } else {
            Log.d("SuperAwesome", "Already initialised AwesomeAds!");
        }
    }

    public static void triggerAgeCheck(Context context, String dateOfBirth, GetIsMinorInterface listener) {
        SAAgeCheck.sdk.getIsMinor(context, dateOfBirth, listener);
    }
}
