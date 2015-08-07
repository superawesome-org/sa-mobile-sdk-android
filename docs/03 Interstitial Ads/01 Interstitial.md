Declare the InterstitialView in the layout XML:
	
```
<fragment
    android:name="tv.superawesome.superawesomesdk.fragments.SAInterstitialFragment"
    android:id="@+id/sa_interstitial"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    custom:placementID="__PLACEMENT_ID__"
    custom:testMode="true"
    custom:showInstantly="true" />
```

Do not forget to replace `__PLACEMENT_ID__` with you own.
'testMode' will make your placements show demo ads for as long as the parameter is set to 'true'; this is recommended for while you are testing.
If 'showInstantly' is set, the ad will open as soon as it has been loaded. Otherwise you can call the interstitial's 'show' method whener you want it to be shown.

The 'custom' namespace is defined in XML by the following attribute:

```
xmlns:custom="http://schemas.android.com/apk/res-auto" 
```

To present the interstitial ad you have to call the `present` method:
	
```
SAInterstitialFragment interstitial = (SAInterstitialFragment)getSupportFragmentManager().findFragmentById(R.id.sa_interstitial);
```

The dimensions of the interstitial will be the same as your selected size when you created the placement in the Dashboard.
