The video ads require Google Play Services. You can find more information about setting up Google Play Services [here](http://developer.android.com/google/play-services/setup.html).

In order to use video ads in your app, first you have to add a preroll placement on the dashboard site. Then, add a SAVideoFragment instance to your view.

Declare the banner fragment in the layout XML:
	
```
<fragment android:name="tv.superawesome.superawesomesdk.fragments.SAVideoFragment"
            android:id="@+id/sa_video_ad"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            custom:placementID="__PLACEMENT_ID__"
            custom:testMode="true"
            custom:playInstantly="true" />
```

Again, do not forget to replace `__PLACEMENT_ID__` with you own.

Once the ad has been loaded it will show the fragment with the video. If 'playInstantly' is true, it will play automatically; otherwise the user will see a play button which will start the video ad when pressed. By implementing the VideoViewListener interface your app can react to events that are emitted by the SAVideoFragment instance:

```
this.videoAd = (SAVideoFragment)getSupportFragmentManager().findFragmentById(R.id.sa_video_ad);
this.videoAd.setListener(new SAVideoViewListener() {
    @Override
    public void onAdStart() {
    }

    @Override
    public void onAdPause() {
    }

    @Override
    public void onAdResume() {
    }

    @Override
    public void onAdFirstQuartile() {
    }

    @Override
    public void onAdMidpoint() {
    }

    @Override
    public void onAdThirdQuartile() {
    }

    @Override
    public void onAdComplete() {
    }

    @Override
    public void onAdClosed() {
    }

    @Override
    public void onAdSkipped() {
    }

    @Override
    public void onAdLoaded(SAAd superAwesomeAd) {
    }

    @Override
    public void onAdError(String message) {
    }
});
```