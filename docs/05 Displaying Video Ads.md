Once you've loaded the video Ad, you can display it. In the Android SDK Video Ads are fullscreen, and are displayed in much the same way as an Activity would be.

```
SALoader.loadAd(__VIDEO_PLACEMENT_ID__, new SALoaderListener() {
   	@Override
    public void didLoadAd(SAAd ad) {
     	// start the video activity
    	SAVideoActivity.start(MainActivity.this, ad, true, null, null, null);
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
        System.out.println("Placement: " + placementId + " could not be loaded");
    }
});

```

Calling the SAVideoActivity static function `start` will display the video ad.
The `start` function takes six parameters:

 * any valid Activity context
 * an object of type `SAAd`, which usually gets returned by the callback `didLoadAd` function
 * whether to show the Parental Gate or not
 * an instance of the SAAdListener interface; can be null
 * an instance of the SAParentalGateListener interface; can be null
 * an instance of the SAVideoAdListener interface; can be null

The listeners will be discussed in a future section.

