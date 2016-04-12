Display Ads
===========

In the next sections we'll see how to display banners, inline video ads, interstitials and fullscreen video ads.

We'll suppose we have the same setup as the previous section, but we'll also add
four SuperAwesome display objects that we'll want to show at the press of a button
in our app.

.. code-block:: java

    import tv.superawesome.*;

    public class MainActivity extends Activity
                              implements SALoaderListener {

        // private SALoader class member
        private SALoader loader = null;

        // declare a SAAd object to save data in
        private SAAd bannerAdData = null;
        private SAAd interstitialAdData = null;
        private SAAd videoAdData = null;

        // display objects
        private SABannerAd banner = null;
        private SAVideoAd video = null;
        private SAVideoActivity fvideo = null;
        private SAInterstitialActivity interstitial = null;

        // rest of the implementation
    }

Banner ads
^^^^^^^^^^

To add a **SABannerAd** object to your activity, first add it in the associated xml file (in this case **/layouts/activity_main.xml**):

.. code-block:: xml

    <tv.superawesome.sdk.views.SABannerAd
        android:id="@+id/mybanner"
        android:layout_width="match_parent"
        android:layout_height="100dp"/>


Then in your activity, let's also assume we have a button linked to the **showBanner** function:

.. code-block:: java

    public void showBanner(View v){
        if (bannerAdData != null) {
            banner = (SABannerAd) findViewById(R.id.mybanner);
            banner.setAd(bannerAdData);
            banner.play();
        }
    }

Notice that SABannerAd is a subclass of **RelativeLayout** so it can be added on the screen exactly like a relative layout would
(either as shown, in an activity's xml file, or programmatically).
Also note that you'll have to manually handle re-initializing the banner ad on orientation changes (for now).

Video ads
^^^^^^^^^

To add a **SAVideoAd** object to your activity, first add it in the associated xml file (in this case **/layouts/activity_main.xml**):

.. code-block:: xml

    <tv.superawesome.sdk.views.SAVideoAd
        android:id="@+id/myvideo"
        android:layout_width="300dp"
        android:layout_height="200dp" />

Then in your activity, let's also assume we have a button linked to the **showInLineVideo** function:

.. code-block:: java

    public void showInLineVideo(View v){
        if (videoAdData != null) {
            video = (SAVideoAd) findViewById(R.id.myvideo);
            video.setAd(videoAdData);
            video.play();
        }
    }

Notice that SAVideoAd is a subclass of **FrameLayout** so it can be added on the screen exactly like a frame layout would
(either as shown, in an activity's xml file, or programmatically).
Also note that internally the SAVideoAd employs a Fragment to maintain state and continue playing when the screen changes orientation.
When the activity gets interrupted the SAVideoAd automatically pauses as well.

Interstitial ads
^^^^^^^^^^^^^^^^

Interstitial ads are started the following way:

.. code-block:: java

    public void showInterstitial(View v) {
        if (interstitialAdData != null){
            interstitial = new SAInterstitialActivity(MainActivity.this);
            interstitial.setAd(interstitialAdData);
            interstitial.play();
        }
    }

This will launch a new activity presenting the ad. All orientation is handled by the SDK.

Fullscreen video ads
^^^^^^^^^^^^^^^^^^^^

Fullscreen video ads are started the following way:

.. code-block:: java

    public void showVideo(View v) {
        if (videoAdData != null) {
            fvideo = new SAVideoActivity(MainActivity.this);
            fvideo.setAd(videoAdData);
            fvideo.setShouldAutomaticallyCloseAtEnd(true);
            fvideo.setShouldShowCloseButton(true);
            fvideo.setShouldLockOrientation(true);
            fvideo.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            fvideo.play();
        }
    }

Please note the additional parameters that can be set.
