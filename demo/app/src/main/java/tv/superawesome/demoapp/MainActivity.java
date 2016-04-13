package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.VideoView;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAInterstitialActivity;
import tv.superawesome.sdk.views.SAVideoAd;

public class MainActivity extends Activity implements SALoaderListener {

    private SAAd savedAd = null;
    private SABannerAd bannerAd = null;
    private SAVideoAd video = null;

    /** the options list */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
        SuperAwesome.getInstance().disableTestMode();
        SuperAwesome.getInstance().setConfigurationProduction();

        if (savedInstanceState == null) {
            SALoader loader = new SALoader();
            loader.loadAd(31093, this);
        }
        else {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("savedAd", savedAd);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void didLoadAd(SAAd ad) {
        savedAd = ad;
        SAInterstitialActivity iad = new SAInterstitialActivity(MainActivity.this);
        iad.setAd(ad);
        iad.play();
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
        Log.d("SuperAwesome", "Failed for " + placementId);
    }
}
