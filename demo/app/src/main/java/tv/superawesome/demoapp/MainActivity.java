package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;

public class MainActivity extends Activity {

    private SAAd savedAd = null;

    /** the options list */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
        SuperAwesome.getInstance().enableTestMode();
        SuperAwesome.getInstance().setConfigurationProduction();

        Log.d("SuperAwesome", savedAd + "");

        SALoader loader = new SALoader();
        loader.loadAd(28000, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd ad) {
                savedAd = ad;
                Log.d("SuperAwesone", "loaded " + savedAd.placementId);
            }

            @Override
            public void didFailToLoadAdForPlacementId(int placementId) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
