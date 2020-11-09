package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import tv.superawesome.demoapp.adapter.AdapterItem;
import tv.superawesome.demoapp.adapter.CustomListAdapter;
import tv.superawesome.demoapp.adapter.HeaderItem;
import tv.superawesome.demoapp.adapter.PlacementItem;
import tv.superawesome.demoapp.adapter.Type;
import tv.superawesome.lib.sabumperpage.SABumperPage;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.sdk.publisher.AwesomeAds;
import tv.superawesome.sdk.publisher.SABannerAd;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAInterstitialAd;
import tv.superawesome.sdk.publisher.SAVideoAd;

public class MainActivity extends Activity {

    /**
     * the options list
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SABumperPage.overrideName("Test app");

        final SABannerAd myBanner = findViewById(R.id.MyBanner);
        //myBanner.setConfigurationStaging();
        myBanner.enableParentalGate();
        myBanner.enableBumperPage();
        myBanner.disableTestMode();
        myBanner.disableMoatLimiting();
        myBanner.setListener((SAInterface) (placementId, event) -> {
            Log.d("SADefaults", "BANNER AD: " + placementId + " -> Event : " + event);

            if (event == SAEvent.adLoaded) {
                myBanner.play(MainActivity.this);
            }
        });

//        SAInterstitialAd.setConfigurationStaging();
        SAInterstitialAd.enableParentalGate();
        SAInterstitialAd.disableTestMode();
        SAInterstitialAd.enableBumperPage();
        SAInterstitialAd.enableBackButton();
        SAInterstitialAd.disableTestMode();
        SAInterstitialAd.disableMoatLimiting();
        SAInterstitialAd.setListener((SAInterface) (placementId, event) -> {
            Log.d("SADefaults", "INTERSTITIAL AD: " + placementId + " -> Event : " + event);

            if (event == SAEvent.adLoaded) {
                SAInterstitialAd.play(placementId, MainActivity.this);
            }
        });

        SAVideoAd.setConfigurationProduction();
        SAVideoAd.disableTestMode();
        SAVideoAd.enableParentalGate();
        SAVideoAd.enableBumperPage();
        SAVideoAd.disableMoatLimiting();
        SAVideoAd.enableCloseAtEnd();
        SAVideoAd.enableCloseButton();
        SAVideoAd.setPlaybackMode(SARTBStartDelay.POST_ROLL);
        SAVideoAd.enableBackButton();
        SAVideoAd.setListener((SAInterface) (placementId, event) -> {
            Log.d("SADefaults", "VIDEO AD: " + placementId + " -> Event : " + event);

            if (event == SAEvent.adLoaded) {
                SAVideoAd.play(placementId, MainActivity.this);
                SAVideoAd.play(placementId, MainActivity.this);
            }
        });

        ListView myList = findViewById(R.id.MyList);
        final List<AdapterItem> data = Arrays.asList(
                new HeaderItem("Banners"),
                new PlacementItem("Banner image", 44258, Type.BANNER),
                new HeaderItem("Interstitials"),
                new PlacementItem("Rich Media Interstitial", 44259, Type.INTERSTITIAL),
                new PlacementItem("3rd party Tag", 5393, Type.INTERSTITIAL),
                new PlacementItem("KSF Tag", 5387, Type.INTERSTITIAL),
                new HeaderItem("Videos"),
                new PlacementItem("Video", 44262, Type.VIDEO)
        );
        CustomListAdapter<AdapterItem> adapter = new CustomListAdapter<>(this);
        myList.setAdapter(adapter);
        adapter.updateData(data);
        adapter.reloadList();

        myList.setOnItemClickListener((parent, view, position, id) -> {
            AdapterItem item = data.get(position);
            if (item instanceof PlacementItem) {
                PlacementItem placement = (PlacementItem) item;

                switch (placement.getType()) {
                    case BANNER:
                        myBanner.load(placement.getPid());
                        break;
                    case INTERSTITIAL:
                        SAInterstitialAd.load(placement.getPid(), MainActivity.this);
                        break;
                    case VIDEO: {
                        if (SAVideoAd.hasAdAvailable(placement.getPid())) {
                            Log.e("AwesomeAds", "PLAYING VIDEO");
                            SAVideoAd.play(placement.getPid(), MainActivity.this);
                        } else {
                            Log.e("AwesomeAds", "LOADING VIDEO");
                            SAVideoAd.load(placement.getPid(), MainActivity.this);
                        }
                        break;
                    }
                }
            }
        });

    }

    public void gotoAdMob(View view) {
        Intent intent = new Intent(this, AdMobActivity.class);
        this.startActivity(intent);
    }

    public void gotoMoPub(View view) {
        Intent intent = new Intent(this, MoPubActivity.class);
        this.startActivity(intent);
    }

    public void ageCheck(View view) {

        final String dateOfBirth = "2012-02-02";

        AwesomeAds.triggerAgeCheck(this, dateOfBirth, isMinorModel -> {

            String message;
            if (isMinorModel != null) {
                message = "Min age for '" + isMinorModel.getCountry() + "' is '"
                        + isMinorModel.getConsentAgeForCountry() + "' "
                        + "\nThe age is '" + isMinorModel.getAge() + "' "
                        + "'.\nIs '" + dateOfBirth + "' a minor? -> '" + isMinorModel.isMinor() + "'";
            } else {
                message = "Oops! Something went wrong. No valid model for 'Age Check'...";
            }

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        });
    }
}