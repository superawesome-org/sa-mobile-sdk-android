package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;


public class MoPubActivity extends Activity {

    private static final String kBANNER_ID = "ea2f54bc51824a72b50ff8052bae9ba7";
    private static final String kINTER_ID  = "1b95482a3bf240f683e308465c3fdab5";
    private static final String kVIDEO_ID  = "a93cda105db0490094e336303c09b4de";

    private MoPubView banner;
    private MoPubInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mopub);

//        banner = (MoPubView) findViewById(R.id.adview);
//        banner.setAdUnitId(kBANNER_ID);
//        banner.setBannerAdListener(new MoPubView.BannerAdListener() {
//            @Override
//            public void onBannerLoaded(MoPubView banner) {
//                Log.d("SADefaults/MoPub", "Banner ad loaded");
//            }
//
//            @Override
//            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
//                Log.d("SADefaults/MoPub", "Banner ad failed");
//            }
//
//            @Override
//            public void onBannerClicked(MoPubView banner) {
//                Log.d("SADefaults/MoPub", "Banner ad clicked");
//            }
//
//            @Override
//            public void onBannerExpanded(MoPubView banner) {
//                Log.d("SADefaults/MoPub", "Banner ad expanded");
//            }
//
//            @Override
//            public void onBannerCollapsed(MoPubView banner) {
//                Log.d("SADefaults/MoPub", "Banner ad collapsed");
//            }
//        });
//        banner.loadAd();

        interstitial = new MoPubInterstitial(this, kINTER_ID);
        interstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                Log.d("SADefaults/MoPub", "Interstitial loaded");
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                Log.d("SADefaults/MoPub", "Interstitial failed");
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {
                Log.d("SADefaults/MoPub", "Interstitial shown");
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {
                Log.d("SADefaults/MoPub", "Interstitial clicked");
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                Log.d("SADefaults/MoPub", "Interstitial dimissed");
            }
        });
        interstitial.load();


//        MoPubRewardedVideos.initializeRewardedVideo(this);
//        MoPubRewardedVideos.setRewardedVideoListener(new MoPubRewardedVideoListener() {
//            @Override
//            public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
//                Log.d("SADefaults/MoPub", "Video loaded");
//            }
//
//            @Override
//            public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
//                Log.d("SADefaults/MoPub", "Video failure");
//            }
//
//            @Override
//            public void onRewardedVideoStarted(@NonNull String adUnitId) {
//                Log.d("SADefaults/MoPub", "Video started");
//            }
//
//            @Override
//            public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
//                Log.d("SADefaults/MoPub", "Video errpr");
//            }
//
//            @Override
//            public void onRewardedVideoClicked(@NonNull String adUnitId) {
//                Log.d("SADefaults/MoPub", "Video clicked");
//            }
//
//            @Override
//            public void onRewardedVideoClosed(@NonNull String adUnitId) {
//                Log.d("SADefaults/MoPub", "Video closed");
//            }
//
//            @Override
//            public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
//                Log.d("SADefaults/MoPub", "Video completed");
//            }
//        });
//        MoPubRewardedVideos.loadRewardedVideo(kVIDEO_ID);

//        SAVideoActivity.enableTestMode();
//        SAVideoActivity.setConfigurationProduction();
//        SAVideoActivity.setListener(new SAInterface() {
//            @Override
//            public void onEvent(int placementId, SAEvent event) {
//                Log.d("AwesomeAds", "Ext callback " + placementId + " | " + event);
//            }
//        });
//        SAVideoActivity.load(28000, this);

    }

    public void playInterstitial (View view) {
        if (interstitial.isReady()) {
            interstitial.show();
        } else {
            Log.d("SADefaults/MoPub", "Interstitial not ready yet");
        }
    }

    public void playVideo (View view) {
        if (MoPubRewardedVideos.hasRewardedVideo(kVIDEO_ID)) {
            MoPubRewardedVideos.showRewardedVideo(kVIDEO_ID);
        } else {
            Log.d("SADefaults/MoPub", "Video not ready yet");
        }
//        if (SAVideoActivity.hasAdAvailable(28000)) {
//            SAVideoActivity.play(28000, this);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroy();
        interstitial.destroy();
    }
}
