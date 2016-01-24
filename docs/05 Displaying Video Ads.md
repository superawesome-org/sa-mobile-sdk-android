Once you've loaded the video Ad, you can display it. 

As with the interstitial ad, there are a two ways to display a video ad:

First one is similar to the Banner Ad and Interstitial ad; You declare a video ad object and set different parameters on it before calling the `play()` function to actually display it.

```
SALoader loader = new SALoader();
loader.loadAd(_PLACEMENT_ID_, new SALoaderListener() {
    @Override
    public void didLoadAd(SAAd saAd) {
    	SAVideoActivity vad = new SAVideoActivity(MainActivity.this);
        vad.setAd(saAd);
        vad.setIsParentalGateEnabled(true);
        vad.setShouldShowCloseButton(true);
        vad.setShouldAutomaticallyCloseAtEnd(false);
        vad.setAdListener(new SAAdListener() {
        	// ad listener implementation            
        });
        vad.setParentalGateListener(new SAParentalGateListener() {
        	// parental gate listener implementation
        });
        vad.setVideoAdListener(new SAVideoAdListener() {
        	// video ad listener
        });
        
        // finally play
        vad.play();
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementID) {
    	// some error happened
    }
});

```

There's also a shorthand way of calling a static member function of SAVideoActivity called `start()`.

```
SALoader loader = new SALoader();
loader.loadAd(__VIDEO_PLACEMENT_ID__, new SALoaderListener() {
   	@Override
    public void didLoadAd(SAAd ad) {
     	// start the video activity
    	SAVideoActivity.start(MainActivity.this, ad, true, false, null, null, null);
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementID) {
        // some error happened
    }
});

```

The `start()` function takes seven parameters:

 * any valid Activity context
 * an object of type `SAAd`, which usually gets returned by the callback `didLoadAd` function
 * whether to show the Parental Gate or not
 * whether to show the close button
 * an instance of the SAAdListener interface; can be null
 * an instance of the SAParentalGateListener interface; can be null
 * an instance of the SAVideoAdListener interface; can be null

The listeners will be discussed in a future section.

