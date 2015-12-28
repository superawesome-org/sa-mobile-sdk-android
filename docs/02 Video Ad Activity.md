To load a video Ad into your Activity or Fragment, and then display it, add the following code:

```
SALoader.loadAd(__PLACEMENT_ID__, new SALoaderListener() {
   	@Override
    public void didLoadAd(SAAd ad) {
     	// start the video activity
    	SAVideoActivity.start(
    		MainActivity.this, 		// pass activity context
    		ad, 					// pass the loaded ad to the SAVideoActivity
    		true, 					// true - enable Parental Gate; false - disable Parental Gate
    		adListener, 			// the video activity responds to three listeners, which can also be null
    		parentalGateListener, 
    		videoAdListener);
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
    		System.out.println("Placement: " + placementId + " could not be loaded");
        }
    });

```

To take it step by step: 

```
SALoader.loadAd(__PLACEMENT_ID__, new SALoaderListener() {
	// implementation here
}

```

This function loads async an Ad and expects two parameters: 
 * __PLACEMENT_ID__, which can be 5740 (test one) or whatever you have defined in the Dashboard
 * an instance of the SALoaderListener interface, that defines two callbacks to let you know when an Ad has finished loading or failed to load: `didLoadAd` and `didFailToLoadAdForPlacementId`.

Once you've loaded the Ad, using the SAVideoActivity static function `start` you can display the video ad.
The `start` function takes six parameters:

 * any valid Activity context
 * an object of type `SAAd`, which usually gets returned by the callback `didLoadAd` function
 * whether to show the Parental Gate or not
 * an instance of the SAAdListener interface; can be null
 * an instance of the SAParentalGateListener interface; can be null
 * an instance of the SAVideoAdListener interface; can be null

 The three listeners will be discussed in the next section.

