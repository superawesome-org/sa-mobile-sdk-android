package tv.superawesome.sdk.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.samodelspace.SAAd;

public class SAInterstitialAd extends Activity implements SAViewInterface {

    private Context context = null;
    private SALoader loader = null;
    private SAAd ad = null;
    private SAInterface listener = null;
    private SABannerAd interstitialBanner = null;

    // state vars
    private boolean isParentalGateEnabled = false;
    private boolean shouldLockOrientation = false;
    private int     lockOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Activity initialization
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public SAInterstitialAd () {
        initialize();
    }

    public SAInterstitialAd (Context c) {
        context = c;
        initialize();
    }

    private void initialize () {
        loader = new SALoader();
    }

    public void setListener (SAInterface listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    public void setIsParentalGateEnabled (boolean value) {
        isParentalGateEnabled = value;
    }

    public void setShouldLockOrientation (boolean value) {
        shouldLockOrientation = value;
    }

    public void setLockOrientation (int value) {
        lockOrientation = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String adJson = extras.getString("SAInterstitialAd_ad");
            JSONObject jsonObject = SAJsonParser.newObject(adJson);
            this.ad = new SAAd(jsonObject);
            this.isParentalGateEnabled = extras.getBoolean("SAInterstitialAd_isParentalGateEnabled");
            this.shouldLockOrientation = extras.getBoolean("SAInterstitialAd_shouldLockOrientation");
            this.lockOrientation = extras.getByte("SAInterstitialAd_lockOrientation");
        }

        // gather resource names
        String packageName = SAApplication.getSAApplicationContext().getPackageName();
        int activity_sa_interstitialId = getResources().getIdentifier("activity_sa_interstitial", "layout", packageName);
        int interstitial_bannerId = getResources().getIdentifier("interstitial_banner", "id", packageName);
        int interstitial_closeId = getResources().getIdentifier("interstitial_close", "id", packageName);

        // finally start displaying
        setContentView(activity_sa_interstitialId);

        // make sure direction is locked
        if (shouldLockOrientation) {
            setRequestedOrientation(lockOrientation);
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
        interstitialBanner.setIsPartOfFullscreen(true);
        interstitialBanner.adListener = listener;
        interstitialBanner.isParentalGateEnabled = isParentalGateEnabled;

        // finally play!
        interstitialBanner.play();
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        ad = null;
        listener = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SAViewInterface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void load(final int placementId) {
        loader.loadAd(placementId, new SALoaderInterface() {
            @Override
            public void didLoadAd(SAAd saAd) {
                ad = saAd;
                if (ad != null) {
                    if (listener != null) {
                        listener.adWasLoaded(placementId);
                    }
                } else {
                    if (listener != null) {
                        listener.adWasNotLoaded(placementId);
                    }
                }
            }
        });
    }

    @Override
    public void setAd(SAAd ad) {
        this.ad = ad;
    }

    @Override
    public SAAd getAd() {
        return this.ad;
    }

    @Override
    public boolean shouldShowPadlock() {
        return ad.creative.creativeFormat != SACreativeFormat.tag && !ad.isFallback && !(ad.isHouse && !ad.safeAdApproved);
    }

    @Override
    public void play() {
        if (ad != null && ad.creative.creativeFormat != SACreativeFormat.video && context != null) {
            Intent intent = new Intent(context, SAInterstitialAd.class);
            intent.putExtra("SAInterstitialAd_ad", ad.writeToJson().toString());
            intent.putExtra("SAInterstitialAd_isParentalGateEnabled", isParentalGateEnabled);
            intent.putExtra("SAInterstitialAd_shouldLockOrientation", shouldLockOrientation);
            intent.putExtra("SAInterstitialAd_lockOrientation", lockOrientation);
            context.startActivity(intent);
        } else {
            if (listener != null) {
                listener.adFailedToShow(0);
            }
        }
    }

    @Override
    public void click() {
        // do nothing
    }

    @Override
    public void resize(int width, int height) {
        // do nothing
    }

    @Override
    public void close() {
        this.ad = null;
        interstitialBanner.close();
        if (listener != null) listener.adWasClosed(ad.placementId);
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
