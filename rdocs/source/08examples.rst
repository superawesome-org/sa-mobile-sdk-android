Full Examples
=============

Simple Example
^^^^^^^^^^^^^^

The first example shows how you can add a banner ad in your app with just a
few lines of code.

**AndroidManifest.xml**

.. code-block:: xml

    <uses-permission android:name="android.permission.INTERNET"/>

**activity_main.xml**

.. code-block:: xml

    <tv.superawesome.sdk.views.SABannerAd
        android:id="@+id/mybanner"
        android:layout_width="match_parent"
        android:layout_height="100dp"/>


**MainActivity.java**

.. code-block:: java

    // other imports ...

    import tv.superawesome.sdk.SuperAwesome;
    import tv.superawesome.sdk.loader.SALoader;
    import tv.superawesome.sdk.loader.SALoaderListener;
    import tv.superawesome.sdk.models.SAAd;
    import tv.superawesome.sdk.views.SABannerAd;

    public class MainActivity
           extends Activity
           implements SALoaderListener {

        private SALoader loader = null;
        private SAAd bannerAdData = null;
        private SABannerAd banner = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // set SuperAwesome setup
            SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
            SuperAwesome.getInstance().enableTestMode();
            SuperAwesome.getInstance().setConfigurationProduction();

            //
            // load ad data from the network just once
            // when the activity is first created
            if (savedInstanceState == null) {
                loader = new SALoader();
                loader.loadAd(30741, this);
            }
            //
            // then reuse the same data from the
            // saved bundle
            //
            // also at this point we'll need to
            // re-create the banner
            else {
                bannerAdData = (SAAd)savedInstanceState.get("bannerAdData");
                restoreBanner();
            }
        }

        @Override
        public void didLoadAd(SAAd ad) {
            // ad data is loaded and can be saved
            // in a member variable
            bannerAdData = ad;

            // start creating the banner
            createBanner();
        }

        @Override
        public void didFailToLoadAdForPlacementId(int placementId) {
            // if this function gets called
            // there was no ad data to be shown
        }

        // function that creates a banner ad
        private void createBanner() {
            if (bannerAdData != null) {
                banner = (SABannerAd) findViewById(R.id.mybanner);
                banner.setAd(bannerAdData);
                banner.play();
            }
        }

        // same as create banner - but this signals
        // this should get called
        // when trying to restore from
        // orientation change
        private void restoreBanner() {
            createBanner();
        }
    }

Complex Example
^^^^^^^^^^^^^^^

This example shows how you can add different types of ads and make them respond to
multiple callbacks.

**activity_main.xml**

.. code-block:: xml

    <Button
        android:id="@+id/button3"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:text="Load Interstitial"
        android:onClick="playInterstitial"/>
    <Button
        android:id="@+id/button4"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:text="Load Fullscreen Video"
        android:onClick="playVideo"/>


**MainActivity.java**

.. code-block:: java

    // other imports ...

    import tv.superawesome.sdk.SuperAwesome;
    import tv.superawesome.sdk.listeners.SAAdListener;
    import tv.superawesome.sdk.listeners.SAParentalGateListener;
    import tv.superawesome.sdk.listeners.SAVideoAdListener;
    import tv.superawesome.sdk.loader.SALoader;
    import tv.superawesome.sdk.loader.SALoaderListener;
    import tv.superawesome.sdk.models.SAAd;
    import tv.superawesome.sdk.views.SAInterstitialActivity;
    import tv.superawesome.sdk.views.SAVideoActivity;

    public class MainActivity
            extends Activity
            implements SALoaderListener,
                        SAAdListener,
                        SAParentalGateListener,
                        SAVideoAdListener {

        // private SALoader class member
        private SALoader loader = null;

        // declare SAAd objects to save data in
        private SAAd interstitialAdData = null;
        private SAAd videoAdData = null;

        // the two ads to be displayed
        private SAInterstitialActivity interstitial = null;
        private SAVideoActivity fvideo = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // setup SuperAwesome test environment
            SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
            SuperAwesome.getInstance().enableTestMode();
            SuperAwesome.getInstance().setConfigurationProduction();

            // when the activity first starts
            // load two ads in parallel,
            // from the network
            if (savedInstanceState == null) {
                loader = new SALoader();
                loader.loadAd(30473, this);
                loader.loadAd(30479, this);
            }
            // restore ad data when
            // savedInstanceState is not null
            else {
                interstitialAdData = (SAAd)savedInstanceState.get("interstitialAdData");
                videoAdData = (SAAd)savedInstanceState.get("videoAdData");
            }
        }

        @Override
        public void didLoadAd(SAAd ad) {
            // save interstitial data
            if (ad.placementId == 30473) {
                interstitialAdData = ad;
            }
            // or save video adta
            else if (ad.placementId == 30479) {
                videoAdData = ad;
            }
        }

        @Override
        public void didFailToLoadAdForPlacementId(int placementId) {

        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putParcelable("interstitialAdData", interstitialAdData);
            outState.putParcelable("videoAdData", videoAdData);
            super.onSaveInstanceState(outState);
        }

        public void playInterstitial(View v){
            if (interstitialAdData != null) {
                interstitial = new SAInterstitialActivity(MainActivity.this);
                interstitial.setAd(interstitialAdData);
                interstitial.setIsParentalGateEnabled(true);
                interstitial.setParentalGateListener(this);
                interstitial.setAdListener(this);
                interstitial.play();
            }
        }

        public void playVideo(View v){
            if (videoAdData != null) {
                fvideo = new SAVideoActivity(MainActivity.this);
                fvideo.setAd(videoAdData);
                fvideo.setShouldAutomaticallyCloseAtEnd(true);
                fvideo.setShouldShowCloseButton(true);
                fvideo.setShouldLockOrientation(true);
                fvideo.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fvideo.setVideoAdListener(this);
                fvideo.setIsParentalGateEnabled(false);
                fvideo.play();
            }
        }

        //
        // SAAdListener implementation

        @Override
        public void adWasShown(int placementId) {
            Lod.d("SuperAwesome", "Ad " + placementId + " has shown!");
        }

        @Override
        public void adFailedToShow(int placementId) {}
        @Override
        public void adWasClosed(int placementId) {}
        @Override
        public void adWasClicked(int placementId) {}
        @Override
        public void adHasIncorrectPlacement(int placementId) {}

        //
        // SAParentalGateListener implementation

        @Override
        public void parentalGateWasCanceled(int placementId) {}
        @Override
        public void parentalGateWasFailed(int placementId) {}
        @Override
        public void parentalGateWasSucceded(int placementId) {}

        //
        // SAVideoAdListener implementation


        @Override
        public void adStarted(int placementId) {}
        @Override
        public void videoStarted(int placementId) {}
        @Override
        public void videoReachedFirstQuartile(int placementId) {}

        @Override
        public void videoReachedMidpoint(int placementId) {
            Lod.d("SuperAwesome", "Video reached Halfpoint");
        }

        @Override
        public void videoReachedThirdQuartile(int placementId) {}
        @Override
        public void videoEnded(int placementId) {}
        @Override
        public void adEnded(int placementId) {}

        @Override
        public void allAdsEnded(int placementId) {
            Lod.d("SuperAwesome", "All ads in video have ended");
        }
    }
