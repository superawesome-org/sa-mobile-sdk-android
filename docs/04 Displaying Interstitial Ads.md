Once you've loaded the interstitial Ad, you can display it. In the Awesome Ads SDK, Interstitial Ads are subclasses of the Activity class, and are displayed much the same way an Activity would be.

```
SALoader.loadAd(__INTERSTITIAL_PLACEMENT_ID__, new SALoaderListener() {
	@Override
	public void didLoadAd(SAAd ad) {
     	// start the interstitial activity
    	SAInterstitialActivity.start(MainActivity.this, ad, true, null, null);
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
        System.out.println("Placement: " + placementId + " could not be loaded");
    }
});

```

Calling the SAInterstitialActivity static function `start` will display the interstitial ad.
The `start` function takes five parameters:

 * any valid Activity context
 * an object of type `SAAd`, which usually gets returned by the callback `didLoadAd` function
 * whether to show the Parental Gate or not
 * an instance of the SAAdListener interface; can be null
 * an instance of the SAParentalGateListener interface; can be null

The listeners will be discussed in a future section.
