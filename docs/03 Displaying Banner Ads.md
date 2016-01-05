Once you've loaded the Banner ad, you can display it.

To do so, you'll first need to declare a SABannerAd view in your activity or fragment's XML file:

```
<tv.superawesome.sdk.views.SABannerAd
    android:id="@+id/myBannerAd1"
    android:layout_width="match_parent"
    android:layout_height="500px" />

```

Then, in your activity code:

```
SALoader.loadAd(__BANNER_PLACEMENT_ID__, new SALoaderListener() {
   	@Override
    public void didLoadAd(SAAd ad) {
    	// find the Banner
     	SABannerAd myBanner = (SABannerAd) findViewById(R.id.myBannerAd1);

     	// set it's Ad object
        myBanner.setAd(ad);

        // start playing the banner
        myBanner.play();
    }

    @Override
    public void didFailToLoadAdForPlacementId(int placementId) {
        System.out.println("Placement: " + placementId + " could not be loaded");
    }
});

```