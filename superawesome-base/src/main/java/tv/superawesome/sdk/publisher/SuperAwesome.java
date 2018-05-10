package tv.superawesome.sdk.publisher;

import android.app.Application;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sanetwork.file.SAFileDownloader;

/**
 * Created by gabriel.coman on 30/04/2018.
 */

public class SuperAwesome {

    public static void init (Application application, boolean loggingEnabled) {
        SAEvents.initMoat(application, loggingEnabled);
        SAFileDownloader.cleanup(application);
    }
}
