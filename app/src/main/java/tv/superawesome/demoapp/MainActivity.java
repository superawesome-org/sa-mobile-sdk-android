package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAUtils;
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

//        SACPI sacpi = new SACPI();
//        sacpi.sendInstallEvent(this, session, new SAInstallEventInterface() {
//            @Override
//            public void saDidCountAnInstall(boolean success) {
//                Log.d("SuperAwesome", "Install evt with " + success);
//            }
//        });

        bannerAd = (SABannerAd) findViewById(R.id.mybanner);

//        RelativeLayout refBanner = (RelativeLayout) findViewById(R.id.RelBanner);
//        bannerAd = new SABannerAd(this);
//        if (savedInstanceState != null) {
//            int bannerId = savedInstanceState.getInt("banner1_Id");
//            bannerAd.setId(bannerId);
//        } else {
//            bannerAd.setId(SAUtils.randomNumberBetween(10000, 15000));
//        }
//        bannerAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
//        refBanner.addView(bannerAd);

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
        SAInterstitialAd.disableParentalGate();
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
        SAVideoAd.disableParentalGate();
        SAVideoAd.disableCloseAtEnd();
        SAVideoAd.setOrientationLandscape();
//        SAVideoAd.enableCloseButton();
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
//        outState.putInt("banner1_Id", bannerAd.getId());
        super.onSaveInstanceState(outState);
    }

    public void loadAds (View view) {
    }

    public void playBanner1(View v){
        bannerAd.load(599);
    }

    public void playBanner2(View v) {
        bannerAd2.load(602);
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
        SAInterstitialAd.load(606, this);
    }

    public void playVideo1(View v){
        SAVideoAd.load(603, this);
    }

    public void playVideo2(View v){
        SAVideoAd.load(604, this);
    }

    public void playAppWall(View v){
        SAAppWall.load(437, this);
    }
}
