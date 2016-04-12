Loading ads
===========

After you've created your Apps and Placements in the dashboard and successfully integrated the SDK in your project,
the next logical step is to actually start showing ads.

For this purpose, the SDK employs a two-step process:
First, you'll need to load ad data for each placement you'll want to display.
Then, once that data is successfully loaded, you can finally show the ad.
The two steps are independent of each other so you can easily pre-load ads for later use, saving performance.

In the code snippet below we'll start by loading data for the test placement **30471**.
A good place to do this is in an Activity's **onCreate** function,
where we'll create a **SALoader** object to help us.

SALoader is a SDK class whose sole role is to load, parse, process and validate ad data.
You'll usually need just one instance per Activity.

.. code-block:: java

    import tv.superawesome.*;

    public class MainActivity extends Activity {

        // private SALoader class member
        private SALoader loader = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // setup SuperAwesome
            Context c = getApplicationContext();
            SuperAwesome.getInstance().setApplicationContext(c);
            SuperAwesome.getInstance().enableTestMode();

            // load ads only when the
            // activity starts for the first time
            if (savedInstanceState == null) {

                // initialize the loader object
                loader = new SALoader();
                // call the Load ad function
                loader.loadAd(30471, null);
            }
        }
    }

The **loadAd(30471, null)** function loads data asynchronously, so as not to block the main UI thread.
When it's done, it calls two important callback methods, **didLoadAd(SAAd loadedAd)** and **didFailToLoadAdForPlacementId(int placementId)**,
to notify you of either success or failure.
In order to use these callbacks:

* your MainActivity class must implement the **SALoaderListener**:

.. code-block:: java

    public class MainActivity extends Activity
                              implements SALoaderListener

* the MainActivity must be set as delegate for the SALoader object created earlier (replace **null** with **this** in *SALoader.loadAd(30471, null)*):

.. code-block:: java

    public class MainActivity extends Activity
                              implements SALoaderListener {

        // private SALoader class member
        private SALoader loader = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // setup SuperAwesome
            Context c = getApplicationContext();
            SuperAwesome.getInstance().setApplicationContext(c);
            SuperAwesome.getInstance().enableTestMode();


            // load ads only when the
            // activity starts for the first time
            if (savedInstanceState == null) {

                // initialize the loader object
                loader = new SALoader();
                // call the Load ad function
                // notice the second function parameter is not "null",
                // but "this"!
                loader.loadAd(30471, this);
            }
        }
    }

* finally, your MainActivity must also implement the two callback methods mentioned above:

.. code-block:: java

    public class MainActivity extends Activity
                              implements SALoaderListener {

        // rest of the implementation ...

        @Override
        public void didLoadAd(SAAd ad) {
            // at this moment ad data is ready
            ad.print();
        }

        @Override
        public void didFailToLoadAdForPlacementId(int i) {
            // at this moment no ad could be found
        }
    }

You'll notice that didLoadAd: has a callback parameter of type **SAAd**. The SAAd class contains all the information needed to
actually display an ad, such as format (image, video), dimensions, click URL, video information, creative details, etc.
You can find out all details by calling the **print** function, as shown in the example.

Saving an Ad for later use
^^^^^^^^^^^^^^^^^^^^^^^^^^

To save ads for later use, you can save it in a class member variable:

.. code-block:: java

    public class MainActivity extends Activity
                              implements SALoaderListener {

        // member variable that will retain the
        // saved ad, once that's loaded
        private SAAd bannerAdData = null;

        // rest of the implementation ...

        @Override
        public void didLoadAd(SAAd ad) {
            // at this moment ad data is ready and
            // we can save the data!
            bannerAdData = ad;
        }

        // rest of the implementation ...
    }

Saving ads with orientation change
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Android destroys and recreates an activity each time orientation changes, that's why it's important to also save ad data
when this happens.
The **SAAd** class implements the **Parceable** protocol, which allows ad data to be saved between orientation, like so:

.. code-block:: java

    public class MainActivity extends Activity
                              implements SALoaderListener {

        // private SALoader class member
        private SALoader loader = null;

        // member variable that will retain the
        // saved ad, once that's loaded
        private SAAd bannerAdData = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // setup SuperAwesome
            Context c = getApplicationContext();
            SuperAwesome.getInstance().setApplicationContext(c);
            SuperAwesome.getInstance().enableTestMode();

            // load ads only when the
            // activity starts for the first time
            if (savedInstanceState == null) {

                // initialize the loader object
                loader = new SALoader();
                // call the Load ad function
                loader.loadAd(30471, this);
            }
            //
            // if savedInstanceState already has
            // some data, don't perform
            // the load again, but just get it
            // from the bundle
            else {
                bannerAdData = (SAAd) savedInstanceState.get("bannerAdData");
            }
        }

        @Override
        public void didLoadAd(SAAd ad) {
            // at this moment ad data is ready and
            // we can save the data!
            bannerAdData = ad;
        }

        @Override
        public void didFailToLoadAdForPlacementId(int i) {
            // at this moment no ad could be found
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            // before the activity is destroyed,
            // Android can save additional
            // information in a bundle!
            outState.putParcelable("bannerAdData", bannerAdData);
            super.onSaveInstanceState(outState);
        }
    }

Saving multiple Ads for later use
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Finally, if you want to load multiple ads and save them for later use, you can do as such:

.. code-block:: java

    public class MainActivity extends Activity
                              implements SALoaderListener {

        // private SALoader class member
        private SALoader loader = null;

        // member variables that will retain the
        // saved ads, once they're loaded
        private SAAd bannerAdData = null;
        private SAAd interstitialAdData = null;
        private SAAd videoAdData = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // setup SuperAwesome
            Context c = getApplicationContext();
            SuperAwesome.getInstance().setApplicationContext(c);
            SuperAwesome.getInstance().enableTestMode();

            // load ads only when the
            // activity starts for the first time
            if (savedInstanceState == null) {

                // initialize the loader object
                loader = new SALoader();

                // call the Load ad function successively
                // to load three ads in parallel
                loader.loadAd(30471, this);
                loader.loadAd(30473, this);
                loader.loadAd(30479, this);
            }
            //
            // if savedInstanceState already has
            // some data, don't perform
            // the load again, but just get it
            // from the bundle
            else {
                bannerAdData = (SAAd) savedInstanceState.get("bannerAdData");
                interstitialAdData = (SAAd) savedInstanceState.get("interstitialAdData");
                videoAdData = (SAAd) savedInstanceState.get("videoAdData");
            }
        }

        @Override
        public void didLoadAd(SAAd ad) {
            if (ad.placementId == 30471) {
                bannerAdData = ad;
            }
            else if (ad.placementId == 30473) {
                interstitialAdData = ad;
            }
            else if (ad.placementId == 30479) {
                videoAdData = ad;
            }
        }

        @Override
        public void didFailToLoadAdForPlacementId(int i) {
            // at this moment no ad could be found
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            // before the activity is destroyed,
            // Android can save additional
            // information in a bundle!
            outState.putParcelable("bannerAdData", bannerAdData);
            outState.putParcelable("interstitialAdData", interstitialAdData);
            outState.putParcelable("videoAdData", videoAdData);
            super.onSaveInstanceState(outState);
        }
    }
