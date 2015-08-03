If you want to be notified of various events in the banner lifecycle, you can set its listener to an object instance that implements the BannerViewListener interface.

The following methods are available in the interface:

| Method      | Description                                                                                                                                  |
|-------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| onAdLeave() | This method is called when the current application is left because the user clicked a banner which will be opened in a the external browser. |
| onAdError() | This method is called when an ad download failed. This could happen because of networking reasons or other server communication reasons.     |

Example implementation:

```
bannerView.setListener(new BannerViewListener() {
	@Override
	public void onAdLeave() {
		Log.d(TAG, "Banner clicked");
	}
	
	@Override
	public void onAdError() {
		Log.d(TAG, "Banner error");
	}
});
```