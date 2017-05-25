package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.plugins.admob.SAAdMobExtras;
import tv.superawesome.plugins.admob.SAAdMobBannerCustomEvent;
import tv.superawesome.plugins.admob.SAAdMobInterstitialCustomEvent;
import tv.superawesome.plugins.admob.SAAdMobVideoMediationAdapter;
import tv.superawesome.sdk.views.SAOrientation;

// app: ca-app-pub-7706302691807937~5001530003
// banner: ca-app-pub-7706302691807937/1989188001
// interstitial: ca-app-pub-7706302691807937/6478263208
// video: ca-app-pub-7706302691807937/3465921207

public class AdMobActivity extends Activity {

    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob);

        MobileAds.initialize(this, "ca-app-pub-7706302691807937~5001530003");

        Bundle extras1 = SAAdMobExtras.extras()
                    .setTestMode(true)
                    .setConfiguration(SAConfiguration.STAGING)
                    .setParentalGate(false)
                    .setTransparent(true)
                    .build();

        adView = (AdView) findViewById(R.id.adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d("SuperAwesome/AdMob", "Banner ad closed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                Log.d("SuperAwesome/AdMob", "Banner ad failed to load");
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("SuperAwesome/AdMob", "Banner ad left application");
            }

            @Override
            public void onAdOpened() {
                Log.d("SuperAwesome/AdMob", "Banner ad opened");
            }

            @Override
            public void onAdLoaded() {
                Log.d("SuperAwesome/AdMob", "Banner ad loaded");
            }
        });
        adView.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(SAAdMobBannerCustomEvent.class, extras1).build());


        Bundle extras2 = SAAdMobExtras.extras()
                .setTestMode(true)
                .setConfiguration(SAConfiguration.STAGING)
                .setOrientation(SAOrientation.PORTRAIT)
                .setParentalGate(true)
                .build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7706302691807937/6478263208");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d("SuperAwesome/AdMob", "Interstitial ad closed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                Log.d("SuperAwesome/AdMob", "Interstitial ad failed to load");
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("SuperAwesome/AdMob", "Interstitial ad left application");
            }

            @Override
            public void onAdOpened() {
                Log.d("SuperAwesome/AdMob", "Interstitial ad opened");
            }

            @Override
            public void onAdLoaded() {
                Log.d("SuperAwesome/AdMob", "Interstitial ad loaded");
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(SAAdMobInterstitialCustomEvent.class, extras2).build());

        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.d("SuperAwesome/AdMob", "Video Ad Loaded");
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d("SuperAwesome/AdMob", "Video Ad opened");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.d("SuperAwesome/AdMob", "Video Ad Started");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.d("SuperAwesome/AdMob", "Video AD Closed");
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Log.d("SuperAwesome/AdMob", "Video Ad Rewarded");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.d("SuperAwesome/AdMob", "Video Ad Left app");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.d("SuperAwesome/AdMob", "Video Ad Failed to load");
            }
        });

        Bundle extras3 = SAAdMobExtras.extras()
                .setTestMode(false)
                .setConfiguration(SAConfiguration.STAGING)
                .setParentalGate(false)
                .setOrientation(SAOrientation.LANDSCAPE)
                .setSmallClick(true)
                .setCloseAtEnd(true)
                .setCloseButton(true)
                .build();

        mAd.loadAd("ca-app-pub-7706302691807937/3465921207", new AdRequest.Builder().addNetworkExtrasBundle(SAAdMobVideoMediationAdapter.class, extras3).build());
    }

    public void playInterstitial (View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("SuperAwesome/MoPub", "Interstitial not loaded yet");
        }
    }

    public void playVideo (View view) {
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            Log.d("SuperAwesome/MoPub", "Video Ad not loaded yet");
        }
    }
}
