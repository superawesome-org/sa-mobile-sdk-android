If you want to be notified of various events in the interstitial lifecycle, you can set its listener to an object instance that implements the InterstitialViewListener interface.

The following methods are available in the interface:

| Method        | Description                                                                                                                                  |
|---------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| onLoaded()    | This method is called when an ad was downloaded successfully.                                                                                |
| onAdLeave()   | This method is called when the current application is left because the user clicked a banner which will be opened in a the external browser. |
| onAdDismiss() | This method is called when the expires and the interstitial view is dismissed.                                                               |
| onAdError()   | This method is called when an ad download failed. This could happen because of networking reasons or other server communication reasons.     |

Example implementation:

```
interstitialView.setListener(new InterstitialViewListener() {
	@Override
	public void onLoaded() {
		Log.d(TAG, "Interstitial loaded");
	}
	
	@Override
	public void onAdLeave() {
		Log.d(TAG, "Interstitial ad clicked");
	}
	
	@Override
	public void onAdError() {
		Log.d(TAG, "Interstitial error");
	}
	
	@Override
	public void onAdDismiss() {
		Log.d(TAG, "Interstitial dismissed");
	}
});
```