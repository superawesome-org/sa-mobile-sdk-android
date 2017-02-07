package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

public class MainActivity extends Activity {

    private SABannerAd bannerAd = null;
    private SABannerAd bannerAd2 = null;

    /** the options list */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SASession session = new SASession(this);
        session.setConfigurationStaging();

//        SuperAwesome.getInstance().handleCPI(this, new SAInstallEventInterface() {
//            @Override
//            public void saDidCountAnInstall(boolean success) {
//
//            }
//        });

        bannerAd = (SABannerAd) findViewById(R.id.mybanner);
        bannerAd.setConfigurationStaging();
        bannerAd.disableTestMode();
        bannerAd.disableParentalGate();
        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                Log.d("SuperAwesome", "Banner " + placementId + " --> " + event);
                if (event == SAEvent.adLoaded) {
                    bannerAd.play(MainActivity.this);
                }
            }
        });

        bannerAd2 = (SABannerAd) findViewById(R.id.mybanner2);
        bannerAd2.setConfigurationStaging();
        bannerAd2.disableParentalGate();
        bannerAd2.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                Log.d("SuperAwesome", "Banner 2 " + placementId + " --> " + event);
                if (event == SAEvent.adLoaded) {
                    bannerAd2.play(MainActivity.this);
                }
            }
        });

        SAInterstitialAd.setConfigurationStaging();
        SAInterstitialAd.disableTestMode();
        SAInterstitialAd.enableParentalGate();
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    SAInterstitialAd.play(placementId, MainActivity.this);
                } else {
                    Log.d("SuperAwesome", "Interstitial " + placementId + " --> " + event);
                }
            }
        });

        SAVideoAd.setConfigurationStaging();
        SAVideoAd.disableTestMode();
        SAVideoAd.enableParentalGate();
        SAVideoAd.disableCloseAtEnd();
        SAVideoAd.setOrientationLandscape();
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    SAVideoAd.play(placementId, MainActivity.this);
                } else {
                    Log.d("SuperAwesome", "Video " + placementId + " --> " + event);
                }
            }
        });

        SAAppWall.setConfigurationStaging();
        SAAppWall.enableParentalGate();
        SAAppWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    SAAppWall.play(placementId, MainActivity.this);
                } else {
                    Log.d("SuperAwesome", "App Wall " + placementId + " --> " + event);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadAds (View view) {
    }

    public void playBanner1(View v){
        bannerAd.enableParentalGate();
        bannerAd.load(599);
    }

    public void playBanner2(View v) {
//        bannerAd2.load(602);
        bannerAd2.enableParentalGate();
        bannerAd2.load(611);
    }

    public void playInterstitial1(View v){
        SAInterstitialAd.load(600, this);
    }

    public void playInterstitial2(View v){
        SAInterstitialAd.load(601, this);
    }

    public void playInterstitial3(View v){
        SAInterstitialAd.load(605, this);
    }

    public void playInterstitial4(View v){
//        SAInterstitialAd.load(606, this);
//        SAInterstitialAd.load(613, this);
        SAInterstitialAd.enableParentalGate();
        SAInterstitialAd.load(614, this);
    }

    public void playVideo1(View v){
        SAVideoAd.load(603, this);
    }

    public void playVideo2(View v){
//        SAVideoAd.load(604, this);
        SAVideoAd.load(612, this);
    }

    public void playAppWall(View v){
        SAAppWall.load(437, this);
    }
}
