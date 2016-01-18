Once you've loaded the interstitial Ad, you can display it. 

There are two ways to display an Interstitial Ad:

First one is similar to the Banner Ad; You declare a interstitial object and set different parameters on it before calling the `play()` function to actually display it.

```
SALoader.loadAd(_PLACEMENT_ID_, new SALoaderListener() {
    @Override
    public void didLoadAd(SAAd saAd) {
    	SAInterstitialActivity iad = new SAInterstitialActivity(MainActivity.this);
        iad.setAd(saAd);
        iad.setIsParentalGateEnabled(true);
        iad.setAdListener(new SAAdListener() {
        	// implement ad listener functions
        });
        iad.setParentalGateListener(new SAParentalGateListener() {
        	// implement parental gate listener functions
        });
        
        // play
        iad.play();
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementID) {
    	// failure to load due to various reasons
    }
});

```

The second one is a static shorthand method on the SAInterstitialActivity class:

```
SALoader.loadAd(__INTERSTITIAL_PLACEMENT_ID__, new SALoaderListener() {
	@Override
	public void didLoadAd(SAAd ad) {
     	// start the interstitial activity
    	SAInterstitialActivity.start(MainActivity.this, ad, true, null, null);
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementID) {
        // dom error happened
    }
});

```

The `start` function takes five parameters:

 * any valid Activity context
 * an object of type `SAAd`, which usually gets returned by the callback `didLoadAd` function
 * whether to show the Parental Gate or not
 * an instance of the SAAdListener interface; can be null
 * an instance of the SAParentalGateListener interface; can be null

The listeners will be discussed in a future section.
