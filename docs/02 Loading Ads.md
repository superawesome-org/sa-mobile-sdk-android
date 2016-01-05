In AwesomeAds displaying Ads is always a two-step process: First load the ad, then render it. 
In this chapter we're going to discuss the first step, how to load an Ad.

All ads are loaded exactly the same (whether they are banner, interstitial or video type ads): by calling the `SALoader.loadAd()` function.

The syntax for such a call is as follows:

```
SALoader.loadAd(__PLACEMENT_ID__, new SALoaderListener() {
   	@Override
    public void didLoadAd(SAAd ad) {
    	// print the ad details
     	ad.print();
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
    	System.out.println("Placement: " + placementId + " could not be loaded");
    }
});

```

The loadAd() function has two parameters:
 * the __PLACEMENT_ID__, which is the integer ID of your placement. You can use one of the test ones or your own, once you set it up on the Dashboard.
 * a new instance of the SALoaderListener Java interface, which must always implement two functions: `didLoadAd()` and `didFailToLoadAdForPlacementId()`.

The `didLoadAd(SAAd ad)` function acts as a callback letting you know that an ad has been successfully loaded. You can then use the `ad` object passed as parameter to print ad details, pass it further down to be rendered, etc.
Displaying ads should be done only from this function, since it's the safest point to assume an ad has been loaded.

The `didFailToLoadAdForPlacementId(int placementId)` function acts as a callback letting you know that an ad has failed to load. 
The `placementId` parameter lets you know what placement has failed to deliver an ad. Please note that failure can mean anything from there's no ad available on the server to there's no internet connection.
