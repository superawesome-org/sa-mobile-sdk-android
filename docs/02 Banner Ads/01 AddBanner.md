Declare the banner fragment in the layout XML:
	
```
<fragment
	android:name="tv.superawesome.sdk.fragments.SABannerFragment"
	android:id="@+id/sa_banner"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:layout_alignParentBottom="true"
	android:layout_centerHorizontal="true"
	custom:placementID="__PLACEMENT_ID__"
	custom:testMode="true" />
```

Do not forget to replace `__PLACEMENT_ID__` with you own.
'testMode' will make your placements show demo ads for as long as the parameter is set to 'true'; this is recommended for while you are testing.

The 'custom' namespace is defined in XML by the following attribute:

```
xmlns:custom="http://schemas.android.com/apk/res-auto" 
```

If you want to access the banner view in the Activity, you can do so this way:
	
```
SABannerFragment banner = (SABannerFragment)getSupportFragmentManager().findFragmentById(R.id.sa_banner);
```

The dimensions of the banner will be the same as your selected size when you created the placement in the Dashboard.