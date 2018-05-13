package tv.superawesome.demoapp;

import android.app.Application;

import tv.superawesome.sdk.publisher.AwesomeAds;

/**
 * Created by gabriel.coman on 30/04/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AwesomeAds.init(this, true);
    }
}
