If you prefer using code in order to add banners to your app, you can use the following code snippet to do so:

```
private BannerView bannerView;

public void createBanner(View view) {
	bannerView = new BannerView(this, null);
	bannerView.setPlacementID("__PLACEMENT_ID__");
	linearlayout.addView(bannerView2);
}
```

Do not forget to replace the `__YOUR_PLACEMENT_ID__` string with your placement ID.