package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import tv.superawesome.lib.samodelspace.SAVASTAdType;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.cpi.SACPI;
import tv.superawesome.sdk.cpi.SAInstallEvent;
import tv.superawesome.sdk.cpi.SAInstallEventInterface;
import tv.superawesome.sdk.cpi.SASourceBundleInspector;
import tv.superawesome.sdk.cpi.SASourceBundleInspectorInterface;
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SASession session = new SASession(this);
        session.setConfigurationStaging();

        SuperAwesome.getInstance().handleStagingCPI(this, new SAInstallEventInterface() {
            @Override
            public void didCountAnInstall(boolean success) {
                Log.d("SuperAwesome-CPI-Stag", "Did count install: " + success);
            }
        });

        bannerAd = (SABannerAd) findViewById(R.id.mybanner);
        bannerAd.setConfigurationStaging();
        bannerAd.disableTestMode();
        bannerAd.disableParentalGate();
        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded){
                    Log.d("SuperAwesome", "Ad " + placementId + " Loaded OK");
                } else if (event == SAEvent.adFailedToLoad) {
                    Log.d("SuperAwesome", "Ad " + placementId + " Failed to load");
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
                    Log.d("SuperAwesome", "Ad " + placementId + " Loaded OK");
                } else if (event == SAEvent.adFailedToLoad) {
                    Log.d("SuperAwesome", "Ad " + placementId + " Failed to load");
                }
            }
        });

        SAVideoAd.setConfigurationStaging();
        SAVideoAd.disableTestMode();
        SAVideoAd.disableParentalGate();
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                if (event == SAEvent.adLoaded) {
                    Log.d("SuperAwesome", "Ad " + placementId + " Loaded OK");
                } else if (event == SAEvent.adFailedToLoad) {
                    Log.d("SuperAwesome", "Ad " + placementId + " Failed to load");
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadAds (View view) {
        bannerAd.load(584);
        SAInterstitialAd.load(585, this);
        SAVideoAd.load(586, this);
    }

    public void playBanner(View v){
        if (bannerAd != null && bannerAd.hasAdAvailable()) {
            bannerAd.play(MainActivity.this);
        }
    }

    public void playInterstitial1(View v){
        if (SAInterstitialAd.hasAdAvailable(585)) {
            SAInterstitialAd.play(585, MainActivity.this);
        }
    }

    public void playInterstitial2(View v){
        //
    }

    public void playVideo1(View v){
        if (SAVideoAd.hasAdAvailable(586)) {
            SAVideoAd.play(586, MainActivity.this);
        }
    }

    public void playVideo2(View v){
        //
    }
}
