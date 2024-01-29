package tv.superawesome.sdk.publisher;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.Map;

import tv.superawesome.lib.featureflags.FeatureFlags;
import tv.superawesome.lib.featureflags.FeatureFlagsManager;
import tv.superawesome.lib.sagdprisminorsdk.minor.SAAgeCheck;
import tv.superawesome.lib.sagdprisminorsdk.minor.process.GetIsMinorInterface;
import tv.superawesome.lib.sanetwork.file.SAFileDownloader;

/**
 * Created by gabriel.coman on 30/04/2018.
 */

public class AwesomeAds {

    private static boolean isInitialised = false;

    private static final FeatureFlagsManager featureFlagsManager = new FeatureFlagsManager();

    public static void init(Application application, boolean loggingEnabled, Map<String, Object> options) {
        QueryAdditionalOptions.Companion.setInstance(new QueryAdditionalOptions(options));
        init(application, loggingEnabled);
    }

    public static void init(Application application, boolean loggingEnabled) {
        if (!isInitialised) {
            Log.d("SuperAwesome", "Initialising AwesomeAds!");
            SAFileDownloader.cleanup(application);
            featureFlagsManager.fetchFeatureFlags();
            isInitialised = true;
        } else {
            Log.d("SuperAwesome", "Already initialised AwesomeAds!");
        }
    }

    public static void init(Context context, boolean loggingEnabled, Map<String, Object> options) {
        QueryAdditionalOptions.Companion.setInstance(new QueryAdditionalOptions(options));
        init(context, loggingEnabled);
    }

    public static void init(Context context, boolean loggingEnabled) {
        if (!isInitialised) {
            Log.d("SuperAwesome", "Initialising AwesomeAds!");
            SAFileDownloader.cleanup(context);
            featureFlagsManager.fetchFeatureFlags();
            isInitialised = true;
        } else {
            Log.d("SuperAwesome", "Already initialised AwesomeAds!");
        }
    }

    public static void triggerAgeCheck(Context context, String dateOfBirth, GetIsMinorInterface listener) {
        SAAgeCheck.sdk.getIsMinor(context, dateOfBirth, listener);
    }

    public static FeatureFlags getFeatureFlags() {
        try {
            return featureFlagsManager.getFeatureFlags();
        } catch (NullPointerException e) {
            Log.w("SuperAwesome", "Feature Flags not loaded, returning default values");
            return new FeatureFlags();
        }
    }
}
