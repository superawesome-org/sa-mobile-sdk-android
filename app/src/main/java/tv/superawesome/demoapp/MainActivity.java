package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

public class MainActivity extends Activity {

    private SABannerAd bannerAd = null;

    /** the options list */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerAd = (SABannerAd) findViewById(R.id.mybanner);
        bannerAd.setConfigurationStaging();
        bannerAd.disableTestMode();
        bannerAd.disableParentalGate();
        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    Log.d("SuperAwesome", "Ad " + placementId + " loaded");
                } else if (event == SAEvent.adFailedToLoad) {
                    Log.d("SuperAwesome", "Ad " + placementId + " failed to load");
                }
            }
        });

        SAInterstitialAd.setConfigurationStaging();
        SAInterstitialAd.enableBackButton();
        SAInterstitialAd.setOrientationPortrait();
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    Log.d("SuperAwesome", "Ad " + placementId + " loaded");
                } else if (event == SAEvent.adFailedToLoad) {
                    Log.d("SuperAwesome", "Ad " + placementId + " failed to load");
                } else if (event == SAEvent.adShown) {
                    Log.d("SuperAwesome", "Ad " + placementId + " shown");
                } else if (event == SAEvent.adClosed) {
                    Log.d("SuperAwesome", "Ad " + placementId + " closed");
                }
            }
        });

        SAVideoAd.enableBackButton();
        SAVideoAd.disableParentalGate();
        SAVideoAd.disableTestMode();
        SAVideoAd.setOrientationLandscape();
        SAVideoAd.setConfigurationProduction();
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    Log.d("SuperAwesome", "Ad " + placementId + " loaded");
                } else if (event == SAEvent.adFailedToLoad) {
                    Log.d("SuperAwesome", "Ad " + placementId + " failed to load");
                } else if (event == SAEvent.adShown) {
                    Log.d("SuperAwesome", "Ad " + placementId + " shown");
                } else if (event == SAEvent.adClosed) {
                    Log.d("SuperAwesome", "Ad " + placementId + " closed");
                }
            }
        });

        SAAppWall.enableBackButton();
        SAAppWall.setConfigurationStaging();
        SAAppWall.disableTestMode();
        SAAppWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadAds (View view) {
        bannerAd.load(485);
        SAInterstitialAd.load(556, MainActivity.this);
        SAInterstitialAd.load(418, MainActivity.this);
        SAAppWall.load(437, MainActivity.this);
//        SAVideoAd.load(32841, MainActivity.this);
        SAVideoAd.setConfigurationStaging();
        SAVideoAd.load(481, MainActivity.this);
        SAVideoAd.load(544, MainActivity.this);
    }

    public void playBanner(View v){
        if (bannerAd.hasAdAvailable()) {
            bannerAd.play(MainActivity.this);
        }
    }

    public void playInterstitial1(View v){
        if (SAInterstitialAd.hasAdAvailable(556)) {
            SAInterstitialAd.enableBackButton();
            SAInterstitialAd.play(556, MainActivity.this);
        }
    }

    public void playInterstitial2(View v){
        if (SAInterstitialAd.hasAdAvailable(418)) {
            SAInterstitialAd.disableBackButton();
            SAInterstitialAd.play(418, MainActivity.this);
        }
    }

    public void playVideo1(View v){
        if (SAVideoAd.hasAdAvailable(544)) {
            SAVideoAd.play(544, MainActivity.this);
        }
//        if (SAAppWall.hasAdAvailable(437)) {
//            SAAppWall.play(437, MainActivity.this);
//        }
    }

    public void playVideo2(View v){
        if (SAVideoAd.hasAdAvailable(481)) {
            SAVideoAd.play(481, MainActivity.this);
        }
    }
}
