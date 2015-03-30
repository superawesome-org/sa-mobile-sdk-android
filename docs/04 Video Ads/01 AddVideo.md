The VideoAd class requires Google Play Services. You can find more information about setting up Google Play Services [here](http://developer.android.com/google/play-services/setup.html).

In order to use video ads in your app, first you have to add a preroll placement on the dashboard site. Then, add a VideoView instance to your view.

Once the ad has been loaded you can start the playback by calling the `play` method on the VideoView. By implementing the VideoViewListener interface your app can react to events that are emitted by the VideoView instance.

```
private VideoView videoView;

public void playVideoAd(View view) {
	videoView = new VideoView(this, null);
	content.addView(videoView);
	videoView.setListener(new VideoViewListener() {
		@Override
		public void onPlaybackCompleted() {
			Log.v(TAG, "Video ad playback has completed");
		}
		
		@Override
		public void onLoaded() {
			videoView.play();
		}

		@Override
	     public void onAdError(){
	    	 Log.v(TAG, "Video ad error");
	    	 layout.removeView(videoView);
	     }
	});
}
```