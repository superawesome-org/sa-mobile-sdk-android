package tv.superawesome.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tv.superawesome.dataprovider.TestDataProvider;
import tv.superawesome.lib.sanetwork.SASystem;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.models.AdItem;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.views.SAInterstitialActivity;
import tv.superawesome.sdk.views.SAVideoActivity;

public class MainActivity extends Activity implements
        SAAdListener, SAVideoAdListener, SAParentalGateListener {

    private SAAdListener adListener = this;
    private SAParentalGateListener parentalGateListener = this;
    private SAVideoAdListener videoAdListener = this;

    private SAVideoActivity vad;

    private SALoader loader;

    /** the options list */
    private ListView optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** SA setup */
        SuperAwesome.getInstance().setConfigurationProduction();
        SuperAwesome.getInstance().disableTestMode();
        SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
        loader = new SALoader();

        /** set text info */
        TextView saSDKLabel = (TextView)findViewById(R.id.sasdk_label);
        saSDKLabel.setText("SA SDK");
        TextView versionLabel = (TextView)findViewById(R.id.version_label);
        versionLabel.setText("(" + SuperAwesome.getInstance().getSDKVersion() + " - " + SASystem.getVerboseSystemDetails() + " - " + SuperAwesome.getInstance().getDAUID() +  ")");

        /** setup the list */
        optionsList = (ListView)findViewById(R.id.optionsList);

        /** populate the data */
        final List<AdItem> options = TestDataProvider.createTestData();
        OptionsAdapter adapter = new OptionsAdapter(this, R.layout.listview_cell, options);
        optionsList.setAdapter(adapter);

        /** add listeners */
        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /** get the current selected option and start doing something */
                AdItem option = options.get(position);
                if (option.testEnabled) {
                    SuperAwesome.getInstance().enableTestMode();
                } else {
                    SuperAwesome.getInstance().disableTestMode();
                }

                /** get the type */
                switch (option.type){
                    case fullscreen_video_item:{
                        loader.loadAd(option.placementId, new SALoaderListener() {
                            @Override
                            public void didLoadAd(SAAd ad) {
                                ad.print();
                                vad = new SAVideoActivity(MainActivity.this);
                                vad.setAd(ad);
                                vad.setIsParentalGateEnabled(false);
                                vad.setShouldAutomaticallyCloseAtEnd(true);
                                vad.setShouldShowCloseButton(true);
                                vad.setAdListener(adListener);
                                vad.setVideoAdListener(videoAdListener);
                                vad.setParentalGateListener(parentalGateListener);
                                vad.play();
                            }

                            @Override
                            public void didFailToLoadAdForPlacementId(int placementId) {
                                SALog.Log("Could not load: " + placementId);
                            }
                        });

                        break;
                    }
                    case interstitial_item:{
                        loader.loadAd(option.placementId, new SALoaderListener() {
                            @Override
                            public void didLoadAd(SAAd ad) {
                                SAInterstitialActivity.start(MainActivity.this, ad, true, adListener, parentalGateListener);
                            }

                            @Override
                            public void didFailToLoadAdForPlacementId(int placementId) {
                                SALog.Log("Could not load: " + placementId);
                            }
                        });
                        break;
                    }
                    case banner_item:{
                        BannerActivity.start(MainActivity.this, option.placementId, adListener, parentalGateListener);
                        break;
                    }
                    case video_item: {
                        VideoActivity.start(MainActivity.this, option.placementId, adListener, parentalGateListener, videoAdListener);
                        break;
                    }
                }
            }
        });

    }

    /** <DELEGATES> */

    @Override
    public void adWasShown(int placementId) {
        SALog.Log("adWasShown");
    }

    @Override
    public void adFailedToShow(int placementId) {
        SALog.Log("adFailedToShow");
    }

    @Override
    public void adWasClosed(int placementId) {
        SALog.Log("adWasClosed");
    }

    @Override
    public void adWasClicked(int placementId) {
        SALog.Log("adWasClicked");
    }

    @Override
    public void adHasIncorrectPlacement(int placementId) {
        SALog.Log("adHasIncorrectPlacement");
    }

    @Override
    public void parentalGateWasCanceled(int placementId) {
        SALog.Log("parentalGateWasCanceled");
    }

    @Override
    public void parentalGateWasFailed(int placementId) {
        SALog.Log("parentalGateWasFailed");
    }

    @Override
    public void parentalGateWasSucceded(int placementId) {
        SALog.Log("parentalGateWasSucceded");
    }

    @Override
    public void adStarted(int placementId) {
        SALog.Log("adStarted");
    }

    @Override
    public void videoStarted(int placementId) {
        SALog.Log("videoStarted");
    }

    @Override
    public void videoReachedFirstQuartile(int placementId) {
        SALog.Log("videoReachedFirstQuartile");
    }

    @Override
    public void videoReachedMidpoint(int placementId) {
        SALog.Log("videoReachedMidpoint");
    }

    @Override
    public void videoReachedThirdQuartile(int placementId) {
        SALog.Log("videoReachedThirdQuartile");
    }

    @Override
    public void videoEnded(int placementId) {
        SALog.Log("videoEnded");
    }

    @Override
    public void adEnded(int placementId) {
        SALog.Log("adEnded");
    }

    @Override
    public void allAdsEnded(int placementId) {
        SALog.Log("allAdsEnded");
    }
}
