package tv.superawesome.lib.sautils;

import android.content.Context;

/**
 * Created by gabriel.coman on 28/12/15.
 */
public class SAApplication {

    private static Context applicationContext;

    public static void setSAApplicationContext(Context _applicationContext){
        applicationContext = _applicationContext;
    }

    public static Context getSAApplicationContext(){
        return applicationContext;
    }
}
