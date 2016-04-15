package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class MainActivity extends Activity implements SALoaderListener {

    private SAAd savedAd = null;
    private SABannerAd bannerAd = null;
    private SAVideoAd video = null;

    /** the options list */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(SuperAwesome.getSdkVersion());

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
