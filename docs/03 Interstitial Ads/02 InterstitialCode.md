If you prefer using code in order to add banners to your app, you can use the following code snippet to do so:

```
private InterstitialView interstitialView;

public void createInterstitial(View view) {
	interstitialView = new InterstitialView(this, null);
	interstitialView.setPlacementID("__PLACEMENT_ID__");
	layout.addView(interstitialView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	interstitialView.present();
}
```

Do not forget to replace the `__YOUR_PLACEMENT_ID__` string with your placement ID.