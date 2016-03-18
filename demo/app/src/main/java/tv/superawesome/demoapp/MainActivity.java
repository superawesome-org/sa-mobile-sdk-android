package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import tv.superawesome.dataprovider.TestDataProvider;
import tv.superawesome.lib.sautils.SAAsyncTask;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.models.AdItem;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;
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
        SuperAwesome.getInstance().setConfigurationStaging();
        SuperAwesome.getInstance().disableTestMode();
        SuperAwesome.getInstance().setApplicationContext(getApplicationContext());

        SAAsyncTask task = new SAAsyncTask(SuperAwesome.getInstance().getApplicationContext(), new SAAsyncTask.SAAsyncTaskListener() {

            @Override
            public Object taskToExecute() throws IOException {
                return SAUtils.syncGet("https://ads.staging.superawesome.tv/v2/ad/79?test=false&sdkVersion=android_3.5.4&rnd=1454507&bundle=tv.superawesome.demoapp");
            }

            @Override
            public void onFinish(Object result) {
                Log.d("SuperAwesome", "" + (String)result);
            }

            @Override
            public void onError() {
                Log.d("SuperAwesome", "onError");
            }
        });

        loader = new SALoader();

        /** set text info */
        TextView saSDKLabel = (TextView)findViewById(R.id.sasdk_label);
        saSDKLabel.setText("SA SDK");
        TextView versionLabel = (TextView)findViewById(R.id.version_label);
        versionLabel.setText("(" + SuperAwesome.getInstance().getSDKVersion() + " - " + SAUtils.getVerboseSystemDetails() + " - " + SuperAwesome.getInstance().getDAUID() +  ")");

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
                                vad.setIsParentalGateEnabled(true);
                                vad.setShouldAutomaticallyCloseAtEnd(true);
                                vad.setShouldShowCloseButton(true);
                                vad.setAdListener(adListener);
                                vad.setVideoAdListener(videoAdListener);
                                vad.setShouldLockOrientation(true);
                                vad.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                vad.setParentalGateListener(parentalGateListener);
                                vad.play();
                            }

                            @Override
                            public void didFailToLoadAdForPlacementId(int placementId) {
                                Log.d("SuperAwesome", "Could not load: " + placementId);
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
                                Log.d("SuperAwesome", "Could not load: " + placementId);
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
        Log.d("SuperAwesome", "adWasShown");
    }

    @Override
    public void adFailedToShow(int placementId) {
        Log.d("SuperAwesome", "adFailedToShow");
    }

    @Override
    public void adWasClosed(int placementId) {
        Log.d("SuperAwesome", "adWasClosed");
    }

    @Override
    public void adWasClicked(int placementId) {
        Log.d("SuperAwesome", "adWasClicked");
    }

    @Override
    public void adHasIncorrectPlacement(int placementId) {
        Log.d("SuperAwesome", "adHasIncorrectPlacement");
    }

    @Override
    public void parentalGateWasCanceled(int placementId) {
        Log.d("SuperAwesome", "parentalGateWasCanceled");
    }

    @Override
    public void parentalGateWasFailed(int placementId) {
        Log.d("SuperAwesome", "parentalGateWasFailed");
    }

    @Override
    public void parentalGateWasSucceded(int placementId) {
        Log.d("SuperAwesome", "parentalGateWasSucceded");
    }

    @Override
    public void adStarted(int placementId) {
        Log.d("SuperAwesome", "adStarted");
    }

    @Override
    public void videoStarted(int placementId) {
        Log.d("SuperAwesome", "videoStarted");
    }

    @Override
    public void videoReachedFirstQuartile(int placementId) {
        Log.d("SuperAwesome", "videoReachedFirstQuartile");
    }

    @Override
    public void videoReachedMidpoint(int placementId) {
        Log.d("SuperAwesome", "videoReachedMidpoint");
    }

    @Override
    public void videoReachedThirdQuartile(int placementId) {
        Log.d("SuperAwesome", "videoReachedThirdQuartile");
    }

    @Override
    public void videoEnded(int placementId) {
        Log.d("SuperAwesome", "videoEnded");
    }

    @Override
    public void adEnded(int placementId) {
        Log.d("SuperAwesome", "adEnded");
    }

    @Override
    public void allAdsEnded(int placementId) {
        Log.d("SuperAwesome", "allAdsEnded");
    }
}
