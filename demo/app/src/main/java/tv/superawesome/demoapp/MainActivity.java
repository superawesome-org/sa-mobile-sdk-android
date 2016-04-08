package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAInterstitialActivity;
import tv.superawesome.sdk.views.SAVideoActivity;
import tv.superawesome.sdk.views.SAVideoAd;

public class MainActivity extends Activity implements SALoaderListener {

    private SAAd savedAd = null;
    private SAVideoAd videoAd2 = null;
    private SABannerAd bannerAd = null;

    /** the options list */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SuperAwesome", "MainActivity onCreate");
        SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
        SuperAwesome.getInstance().enableTestMode();
        SuperAwesome.getInstance().setConfigurationProduction();


        if (savedInstanceState == null) {
            SALoader loader = new SALoader();
            loader.loadAd(28000, this);
            loader.loadAd(30741, this);
            SALoader loader1 = new SALoader();
            loader1.loadAd(10305, this);
        }


//        if (savedInstanceState == null) {
//            SALoader loader = new SALoader();
//            loader.loadAd(28000, new SALoaderListener() {
//                @Override
//                public void didLoadAd(SAAd ad) {
//                    videoAd2 = (SAVideoAd) findViewById(R.id.SAVideoAd2Id);
//                    videoAd2.setAd(ad);
//                    videoAd2.play();
//
////                    SAVideoActivity vad = new SAVideoActivity(MainActivity.this);
////                    vad.setAd(ad);
////                    vad.play();
//                }
//
//                @Override
//                public void didFailToLoadAdForPlacementId(int placementId) {
//
//                }
//            });
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("savedAd", savedAd);
        Log.d("SuperAwesome", "onSaveIntanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void didLoadAd(SAAd ad) {
        Log.d("SuperAwesome", ad.placementId + " was loaded");
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
        Log.d("SuperAwesome", placementId + " failed");
    }
}
