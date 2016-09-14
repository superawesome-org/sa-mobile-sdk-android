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

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.sdk.SuperAwesome;

public class SAInterstitialAd extends Activity {

    // subviews
    private SABannerAd interstitialBanner = null;
    private SAAd ad = null;

    // fully private variables
    private static List<SAAd> ads = new ArrayList<>();

    // private vars w/ exposed setters & getters (state vars)
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };
    private static boolean isParentalGateEnabled = false;
    private static boolean shouldLockOrientation = false;
    private static int lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    private static boolean isTestingEnabled = false;
    private static int configuration = 0;

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
        Bundle bundle = getIntent().getExtras();
        ad = bundle.getParcelable("ad");

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
        interstitialBanner.setAd(ad);
        interstitialBanner.setListener(listenerL);
        if (isParentalGateEnabledL) {
            interstitialBanner.enableParentalGate();
        } else {
            interstitialBanner.disableParentalGate();
        }

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
        // get local listener
        SAInterface listenerL = getListener();
        listenerL.onEvent(ad.placementId, SAEvent.adClosed);

        // make sure banner is closed and ad is nulled
        removeAdFromLoadedAds(ad);
        interstitialBanner.close();

        // close & resume previous activity
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Class public interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void load(final int placementId) {

        // create a current session
        SASession session = new SASession ();
        session.setTest(isTestingEnabled);
        session.setConfiguration(configuration);
        session.setDauId(SuperAwesome.getInstance().getDAUID());
        session.setVersion(SuperAwesome.getInstance().getSDKVersion());

        // create a loader
        SALoader loader = new SALoader();
        loader.loadAd(placementId, session, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {

                // add to the array queue
                if (saAd != null) {
                    ads.add(saAd);
                }

                // call listener
                listener.onEvent(placementId, saAd != null ? SAEvent.adLoaded : SAEvent.adFailedToLoad);
            }
        });
    }

    public static boolean hasAdAvailable(int placementId) {
        Boolean hasAd = false;
        for (SAAd ad : ads) {
            if (ad.placementId == placementId) {
                hasAd = true;
                break;
            }
        }
        return hasAd;
    }

    public static void play(int placementId, Context context) {

        // try to get the ad that fits the placement id
        SAAd adL = null;
        for (SAAd ad : ads) {
            if (ad.placementId == placementId) {
                adL = ad;
            }
        }

        // try to start the activity
        if (adL != null && adL.creative.creativeFormat != SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAInterstitialAd.class);
            intent.putExtra("ad", adL);
            context.startActivity(intent);
        } else {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        }
    }

    private static void removeAdFromLoadedAds (SAAd ad) {
        // have to do this because when I send the ad to the activity, it gets serialized /
        // de-serialized into a new instance
        SAAd toRemove = null;
        for (SAAd ad1 : ads) {
            if (ad1.placementId == ad.placementId) {
                toRemove = ad1;
            }
        }
        if (toRemove != null) {
            ads.remove(toRemove);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters and getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public static void enableParentalGate () {
        isParentalGateEnabled = true;
    }

    public static void disableParentalGate () {
        isParentalGateEnabled = false;
    }

    public static void enableTestMode () {
        isTestingEnabled = true;
    }

    public static void disableTestMode () {
        isTestingEnabled = false;
    }

    public static void setConfigurationProduction () {
        configuration = SASession.CONFIGURATION_PRODUCTION;
    }

    public static void setConfigurationStaging () {
        configuration = SASession.CONFIGURATION_STAGING;
    }

    public static void setOrientationAny () {
        shouldLockOrientation = false;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public static void setOrientationPortrait () {
        shouldLockOrientation = true;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    public static void setOrientationLandscape () {
        shouldLockOrientation = true;
        lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    // private methods to access static variables

    private static SAInterface getListener () {
        return listener;
    }

    private static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    private static boolean getShouldLockOrientation () {
        return shouldLockOrientation;
    }

    private static int getLockOrientation () {
        return lockOrientation;
    }
}
