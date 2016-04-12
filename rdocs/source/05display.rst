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
        android:id="@+id/bannerid"
        android:layout_width="match_parent"
        android:layout_height="100dp"/>

Then in your activity:

 
