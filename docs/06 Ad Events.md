Banner Ads, Video Ads and Interstitial can be helped by up to three listeners in order to notify you of ad lifecycle events.
In order to use them, your activity must implement one or all of the Listeners:

```
public class MyActivity extends Activity implements SAAdListener, SAParentalGateListener, SAVideoAdListener {

	// private listener objects - assign the current Activity instance to them, since it promises to
    // implement their Interfaces
	private SAAdListener adListener = this;
	private SAParentalGateListener parentalGateListener = this;
	private SAVideoAdListener videoAdListener = this;

	// rest of the activity implementation
	// ....
}


```

Once `MyActivity` declares that it implements the Listener interfaces, it must also implement all functions from
`SAAdListener`:

```
public void adWasShown(int placementId);
public void adFailedToShow(int placementId);
public void adWasClosed(int placementId);
public void adWasClicked(int placementId);
public void adHasIncorrectPlacement(int placementId);

```

And for `SAParentalGateListener`:

```
public void parentalGateWasCanceled(int placementId);
public void parentalGateWasFailed(int placementId);
public void parentalGateWasSucceded(int placementId);

```

And for `SAVideoAdListener`:

```
public void adStarted(int placementId);
public void videoStarted(int placementId);
public void videoReachedFirstQuartile(int placementId);
public void videoReachedMidpoint(int placementId);
public void videoReachedThirdQuartile(int placementId);
public void videoEnded(int placementId);
public void adEnded(int placementId);
public void allAdsEnded(int placementId);

```

To assign a listener to a Banner ad, you'll need to:

```
// standard banner creation from XML
SABannerAd myBanner = (SABannerAd) findViewById(R.id.myBannerAd1);

// also assign the listeners
myBanner.setAdListener(adListener);
myBanner.setParentalGateListener(parentalGateListener);

// set the ad object and play the Ad
myBanner.setAd(ad);
myBanner.play();

```

To assign listeners to an Interstitial ad, you'll need to change its `start()` function call to:

```
SAInterstitialActivity.start(MainActivity.this, ad, true, adListener, parentalGateListener);

```

To assign listeners to a Video ad, you'll need to change its `start()` function call to:

```
SAVideoActivity.start(MainActivity.this, ad, true, adListener, parentalGateListener, videoAdListener);

```