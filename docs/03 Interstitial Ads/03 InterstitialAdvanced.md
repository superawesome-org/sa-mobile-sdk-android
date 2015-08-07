If you want to be notified of various events in the banner lifecycle, you can set its listener to an object instance that implements the SAPlacementListener interface.

The following methods are available in the interface:

| Method      | Description                                                                                                                                  |
|-------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| onAdLoaded(ad) | This method is called when the ad is loaded and is ready to display. |
| onAdError() | This method is called when an ad download failed or could not be shown for some reason. This is likely to happen because of networking reasons or other server communication reasons.     |

Example implementation:

```
interstitial.setListener(new SAPlacementListener() {
    @Override
    public void onAdLoaded(SAAd superAwesomeAd) {
    }
    @Override
    public void onAdError(String message) {
    }
});
```

More events will be available soon.