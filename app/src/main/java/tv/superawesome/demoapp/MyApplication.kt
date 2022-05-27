package tv.superawesome.demoapp;

import androidx.multidex.MultiDexApplication;

import tv.superawesome.sdk.publisher.AwesomeAds;

/**
 * Created by gabriel.coman on 30/04/2018.
 */

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AwesomeAds.init(this, true);
    }
}
