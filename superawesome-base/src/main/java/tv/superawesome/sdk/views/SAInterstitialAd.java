package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.samodelspace.SAResponse;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASessionInterface;
import tv.superawesome.sdk.SuperAwesome;

public class SAInterstitialAd extends Activity {

    // subviews
    private SABannerAd interstitialBanner = null;
    private SAAd ad = null;

    // fully private variables
    private static HashMap<Integer, Object> ads = new HashMap<>();

    // private vars w/ exposed setters & getters (state vars)
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };
    private static boolean isParentalGateEnabled = true;
    private static boolean isTestingEnabled = false;
    private static boolean isBackButtonEnabled = false;
    private static SAOrientation orientation = SAOrientation.ANY;
    private static SAConfiguration configuration = SAConfiguration.PRODUCTION;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Activity initialization & instance methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super
        super.onCreate(savedInstanceState);

        // local vars
        boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        SAOrientation orientationL = getOrientation();
        SAInterface listenerL = getListener();
        Bundle bundle = getIntent().getExtras();
        String adStr = bundle.getString("ad");
        ad = new SAAd(SAJsonParser.newObject(adStr));

        // gather resource names
        String packageName = this.getPackageName();
        int activity_sa_interstitialId = getResources().getIdentifier("activity_sa_interstitial", "layout", packageName);
        int interstitial_bannerId = getResources().getIdentifier("interstitial_banner", "id", packageName);
        int interstitial_closeId = getResources().getIdentifier("interstitial_close", "id", packageName);

        // finally start displaying
        setContentView(activity_sa_interstitialId);

        // make sure direction is locked
        switch (orientationL) {
            case ANY:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
            case PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
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
        interstitialBanner.setBackgroundColor(Color.rgb(224, 224, 224));
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
        boolean isBackButtonEnabledL = getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            SAInterface listenerL = getListener();
            listenerL.onEvent(ad.placementId, SAEvent.adClosed);
            super.onBackPressed();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Custom instance methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

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

    public static void load(final int placementId, Context context) {

        if (!ads.containsKey(placementId)) {

            // set a placeholder
            ads.put(placementId, new Object());

            // create the loader
            final SALoader loader = new SALoader(context);

            // create a current session
            final SASession session = new SASession (context);
            session.setTestMode(isTestingEnabled);
            session.setConfiguration(configuration);
            session.setVersion(SuperAwesome.getInstance().getSDKVersion());
            session.prepareSession(new SASessionInterface() {
                @Override
                public void sessionReady() {

                    // after session is prepared, start loading
                    loader.loadAd(placementId, session, new SALoaderInterface() {
                        @Override
                        public void didLoadAd(SAResponse response) {

                            // put the correct value
                            if (response.isValid()) {
                                ads.put(placementId, response.ads.get(0));
                            }
                            // remove existing
                            else {
                                ads.remove(placementId);
                            }

                            // call listener
                            listener.onEvent(placementId, response.isValid () ? SAEvent.adLoaded : SAEvent.adFailedToLoad);
                        }
                    });
                }
            });

        } else {
            listener.onEvent(placementId, SAEvent.adFailedToLoad);
        }
    }

    public static boolean hasAdAvailable(int placementId) {
        Object object = ads.get(placementId);
        return object != null && object instanceof SAAd;
    }

    public static void play(int placementId, Context context) {

        // try to get the ad that fits the placement id
        SAAd adL = (SAAd) ads.get(placementId);

        // try to start the activity
        if (adL != null && adL.creative.creativeFormat != SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAInterstitialAd.class);
            intent.putExtra("ad", adL.writeToJson().toString());
            context.startActivity(intent);
        } else {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        }
    }

    private static void removeAdFromLoadedAds (SAAd ad) {
        ads.remove(ad.placementId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters and getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public static void enableParentalGate () {
        setParentalGate(true);
    }

    public static void disableParentalGate () {
        setParentalGate(false);
    }

    public static void enableTestMode () {
        setTestMode(true);
    }

    public static void disableTestMode () {
        setTestMode(false);
    }

    public static void enableBackButton () {
        setBackButton(true);
    }

    public static void disableBackButton () {
        setBackButton(false);
    }

    public static void setConfigurationProduction () {
        setConfiguration(SAConfiguration.PRODUCTION);
    }

    public static void setConfigurationStaging () {
        setConfiguration(SAConfiguration.STAGING);
    }

    public static void setOrientationAny () {
        setOrientation(SAOrientation.ANY);
    }

    public static void setOrientationPortrait () {
        setOrientation(SAOrientation.PORTRAIT);
    }

    public static void setOrientationLandscape () {
        setOrientation(SAOrientation.LANDSCAPE);
    }

    // private methods to access static variables

    private static SAInterface getListener () {
        return listener;
    }

    private static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    private static SAOrientation getOrientation () {
        return orientation;
    }

    private static boolean getIsBackButtonEnabled () {
        return isBackButtonEnabled;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Generic setters and getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setParentalGate (boolean value) {
        isParentalGateEnabled = value;
    }

    public static void setTestMode (boolean value) {
        isTestingEnabled = value;
    }

    public static void setBackButton (boolean value) {
        isBackButtonEnabled = value;
    }

    public static void setConfiguration (SAConfiguration value) {
        configuration = value;
    }

    public static void setOrientation (SAOrientation value) {
        orientation = value;
    }
}
