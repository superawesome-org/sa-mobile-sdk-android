package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

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

        // ask users to add two numbers when clicking on an ad
        SAVideoAd.enableParentalGate ();

        // lock orientation to portrait or landscape
        SAVideoAd.setOrientationLandscape ();

        // enable or disable the android back button
        SAVideoAd.enableBackButton ();

        // enable or disable a close button
        SAVideoAd.enableCloseButton ();

        // enable or disable auto-closing at the end
        SAVideoAd.enableCloseAtEnd ();

        SAVideoAd.load (30961, MainActivity.this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadAds (View view) {


    }

    public void playBanner(View v){
        if (bannerAd != null && bannerAd.hasAdAvailable()) {
            bannerAd.play(MainActivity.this);
        }
    }

    public void playInterstitial1(View v){
        if (SAInterstitialAd.hasAdAvailable(33220)) {
            SAInterstitialAd.enableBackButton();
            SAInterstitialAd.play(33220, MainActivity.this);
        }
    }

    public void playInterstitial2(View v){
        if (SAInterstitialAd.hasAdAvailable(418)) {
            SAInterstitialAd.disableBackButton();
            SAInterstitialAd.play(418, MainActivity.this);
        }
    }

    public void playVideo1(View v){
        if (SAVideoAd.hasAdAvailable(30961)) {
            SAVideoAd.play(30961, MainActivity.this);
        }
    }

    public void playVideo2(View v){
        if (SAVideoAd.hasAdAvailable(481)) {
            SAVideoAd.play(481, MainActivity.this);
        }
    }
}
