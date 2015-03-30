Declare the InterstitialView in the layout XML:
	
```
<tv.superawesome.mobile.view.InterstitialView
android:id="@+id/interstitial"
android:layout_width="fill_parent"
android:layout_height="fill_parent"
custom:placementID="__PLACEMENT_ID__">
</tv.superawesome.mobile.view.InterstitialView>
```

Do not forget to replace `__PLACEMENT_ID__` with you own.

The 'custom' namespace is defined in XML by the following attribute:

```
xmlns:custom="http://schemas.android.com/apk/res-auto" 
```

To present the interstitial ad you have to call the `present` method:
	
```
InterstitialView iv = (InterstitialView) findViewById(R.id.interstitial);
iv.present();
```