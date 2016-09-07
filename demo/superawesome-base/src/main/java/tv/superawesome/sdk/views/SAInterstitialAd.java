package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.samodelspace.SAAd;

public class SAInterstitialAd extends Activity {

    // subviews
    private SABannerAd interstitialBanner = null;

    // fully private variables
    private static SAAd ad = null;

    // private vars w/ exposed setters & getters (state vars)
    private static SAInterface listener = null;
    private static boolean isParentalGateEnabled = false;
    private static boolean shouldLockOrientation = false;
    private static int lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Activity initialization & instance methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super
        super.onCreate(savedInstanceState);

        // local vars
        boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        boolean shouldLockOrientationL = getShouldLockOrientation();
        int lockOrientationL = getLockOrientation();
        SAInterface listenerL = getListener();
        SAAd adL = getAd();

        // gather resource names
        String packageName = SAApplication.getSAApplicationContext().getPackageName();
        int activity_sa_interstitialId = getResources().getIdentifier("activity_sa_interstitial", "layout", packageName);
        int interstitial_bannerId = getResources().getIdentifier("interstitial_banner", "id", packageName);
        int interstitial_closeId = getResources().getIdentifier("interstitial_close", "id", packageName);

        // finally start displaying
        setContentView(activity_sa_interstitialId);

        // make sure direction is locked
        if (shouldLockOrientationL) {
            setRequestedOrientation(lockOrientationL);
        }

        // set the close btn
        Button closeBtn = (Button) findViewById(interstitial_closeId);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        // set the interstitial
        interstitialBanner = (SABannerAd) findViewById(interstitial_bannerId);
        interstitialBanner.setBackgroundColor(Color.rgb(239, 239, 239));
        interstitialBanner.setAd(adL);
        interstitialBanner.setIsPartOfFullscreen(true);
        interstitialBanner.setListener(listenerL);
        interstitialBanner.setIsParentalGateEnabled(isParentalGateEnabledL);

        // finally play!
        interstitialBanner.play(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        interstitialBanner.resize(width, height);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    private void close() {
        // make sure banner is closed and ad is nulled
        nullAd();
        interstitialBanner.close();

        // get local listener
        SAInterface listenerL = getListener();
        if (listenerL != null) listenerL.SADidCloseAd();

        // close & resume previous activity
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Class public interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void load(final int placementId) {
        SALoader loader = new SALoader();
        loader.loadAd(placementId, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {
                ad = saAd;
                if (ad != null) {
                    if (listener != null) {
                        listener.SADidLoadAd(placementId);
                    }
                } else {
                    if (listener != null) {
                        listener.SADidNotLoadAd(placementId);
                    }
                }
            }
        });
    }

    public static boolean hasAdAvailable() {
        return ad != null;
    }

    public static SAAd getAd() {
        return ad;
    }

    public static void nullAd () {
        ad = null;
    }

    public static void play(Context context) {
        if (ad != null && ad.creative.creativeFormat != SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAInterstitialAd.class);
            context.startActivity(intent);
        } else {
            if (listener != null) {
                listener.SADidNotShowAd();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters and getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListener(SAInterface value) {
        listener = value;
    }

    public static void setIsParentalGateEnabled(boolean value) {
        isParentalGateEnabled = value;
    }

    public static void setShouldLockOrientation(boolean value) {
        shouldLockOrientation = value;
    }

    public static void setLockOrientation(int value) {
        lockOrientation = value;
    }

    public static SAInterface getListener () {
        return listener;
    }

    public static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    public static boolean getShouldLockOrientation () {
        return shouldLockOrientation;
    }

    public static int getLockOrientation () {
        return lockOrientation;
    }
}
