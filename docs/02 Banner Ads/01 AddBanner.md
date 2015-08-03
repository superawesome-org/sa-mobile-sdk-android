Declare the BannerView in the layout XML:
	
```
<tv.superawesome.mobile.view.BannerView
android:id="@+id/banner"
android:layout_width="320dip"
android:layout_height="50dip"
android:layout_alignParentBottom="true"
android:layout_centerHorizontal="true"
custom:placementID="__PLACEMENT_ID__">
</tv.superawesome.mobile.view.BannerView>
```

Do not forget to replace `__PLACEMENT_ID__` with you own.

The 'custom' namespace is defined in XML by the following attribute:

```
xmlns:custom="http://schemas.android.com/apk/res-auto" 
```

If you want to access the banner view in the Activity, you can do so this way:
	
```
BannerView bannerView = (BannerView) findViewById(R.id.banner);
```

The following banner sizes are supported:
 - 320x50
 - 300x50
 - 300x250
 - 728x90 (tablet)