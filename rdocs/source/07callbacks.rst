Callbacks
=========

Once an ad starts playing, it will send back callbacks to notify you that it has finished different lifecycle activities.
To respond to them we'll use a similar listener / delegate pattern as with SALoaderListener.

Standard ad callbacks
^^^^^^^^^^^^^^^^^^^^^

To catch standard ad callbacks:

* your Activity must implement the **SAAdListener**:

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAAdListener {

        // class member variables
        private SALoader loader = null;
        private SAAd bannerAdData = null;
        private SABannerAd bannerAd = null;

        // rest of the implementation ...

    }

* the Activity must be set as delegate for your display objects:

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAAdListener {

        // rest of the implementation ...

        public void showBanner(View v){
            if (bannerAdData != null) {
                banner = (SABannerAd) findViewById(R.id.mybanner);
                banner.setAd(bannerAdData);
                banner.setAdListener(this);
                banner.play();
            }
        }
    }

* your Activity must implement the callback methods specified by SAAdListener

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAAdListener {

        // rest of the implementation ...

        @Override
        public void adWasShown(int placementId) {
            // this function is called when the ad
            // is shown on the screen
        }

        @Override
        public void adFailedToShow(int placementId) {
            // this function is called when the ad failed to show
        }

        @Override
        public void adWasClosed(int placementId) {
            // this function is called when an ad is closed;
            // only applies to fullscreen ads
            // like interstitials and fullscreen videos
        }

        @Override
        public void adWasClicked(int placementId) {
            // this function is called when an ad is clicked
        }

        @Override
        public void adHasIncorrectPlacement(int placementId) {
            // only called when setting an SAAd object
            // containing video data for a
            // banner type display object (or similar)
        }
    }

Parental gate callbacks
^^^^^^^^^^^^^^^^^^^^^^^

To catch parental gate callbacks:

* Your Activity must implement the **SAParentalGateListener**:

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAParentalGateListener {

        // class member variables
        private SALoader loader = null;
        private SAAd bannerAdData = null;
        private SABannerAd bannerAd = null;

        // rest of the implementation ...

    }

* the Activity must be set as delegate for your display objects:

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAParentalGateListener {

        // rest of the implementation ...

        public void showBanner(View v){
            if (bannerAdData != null) {
                banner = (SABannerAd) findViewById(R.id.mybanner);
                banner.setAd(bannerAdData);
                banner.setIsParentalGateEnabled(true);
                banner.setParentalGateListener(this);
                banner.play();
            }
        }
    }

* your Activity must implement the callback methods specified by SAAdListener

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAParentalGateListener {

        // rest of the implementation ...

        @Override
        public void parentalGateWasCanceled(int placementId) {
            // this function is called when a
            // parental gate pop-up "cancel" button is pressed
        }

        @Override
        public void parentalGateWasFailed(int placementId) {
            // this function is called when a
            // parental gate pop-up "continue" button is
            // pressed and the parental gate
            // failed (because the numbers weren't OK)
        }

        @Override
        public void parentalGateWasSucceded(int placementId) {
            // this function is called when a
            // parental gate pop-up "continue" button is
            // pressed and the parental gate succeeded
        }
    }

Video callbacks
^^^^^^^^^^^^^^^

To catch video ad callbacks (available only for SAVideoAd and SAVideoActivity objects):

* Your Activity must implement the **SAVideoAdListener**:

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAVideoAdListener {

        // class member variables
        private SALoader loader = null;
        private SAAd videoAdData = null;
        private SAVideoAd videoAd = null;

        // rest of the implementation ...

    }

* the Activity must be set as delegate for your display objects:

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAVideoAdListener {

        // rest of the implementation ...

        public void showVideo(View v){
            if (videoAdData != null) {
                video = (SAVideoAd) findViewById(R.id.myvideo);
                video.setAd(videoAdData);
                video.setVideoAdListener(this);
                video.play();
            }
        }
    }

* your Activity must implement the callback methods specified by SAAdListener

.. code-block:: java

    public class MainActivity
           extends Activity
           implements SALoaderListener,
                      SAVideoAdListener {

        // rest of the implementation ...

        @Override
        public void adStarted(int placementId) {
            // fired when an ad has started
        }

        @Override
        public void videoStarted(int placementId) {
            // fired when a video ad has started
        }

        @Override
        public void videoReachedFirstQuartile(int placementId) {
            // fired when a video ad has reached 1/4 of total duration
        }

        @Override
        public void videoReachedMidpoint(int placementId) {
            // fired when a video ad has reached 1/2 of total duration
        }

        @Override
        public void videoReachedThirdQuartile(int placementId) {
            // fired when a video ad has reached 3/4 of total duration
        }

        @Override
        public void videoEnded(int placementId) {
            // fired when a video ad has ended
        }

        @Override
        public void adEnded(int placementId) {
            // fired when an ad has ended
        }

        @Override
        public void allAdsEnded(int placementId) {
            // fired when all ads have ended
        }
    }
