package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.SAVideoActivity;
import tv.superawesome.sdk.views.SAVideoAd;

public class MainActivity extends Activity {

    private SAAd savedAd = null;
    private SAVideoAd videoAd2 = null;

    /** the options list */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
        SuperAwesome.getInstance().enableTestMode();
        SuperAwesome.getInstance().setConfigurationProduction();

        if (savedInstanceState == null) {
            SALoader loader = new SALoader();
            loader.loadAd(28000, new SALoaderListener() {
                @Override
                public void didLoadAd(SAAd ad) {
                    savedAd = ad;
//                    videoAd2 = (SAVideoAd) findViewById(R.id.SAVideoAd2Id);
//                    videoAd2.setAd(savedAd);
//                    Log.d("SuperAwesome", "Loaded first time " + videoAd2.getAd().placementId);
//                    videoAd2.play();

                    SAVideoActivity vad = new SAVideoActivity(MainActivity.this);
                    vad.setAd(ad);
                    vad.play();
                }

                @Override
                public void didFailToLoadAdForPlacementId(int placementId) {

                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("savedAd", savedAd);
        super.onSaveInstanceState(outState);
    }
}
