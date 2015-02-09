package tv.superawesome.sademoapp;


import tv.superawesome.mobile.view.BannerView;
import tv.superawesome.mobile.view.InterstitialView;
import tv.superawesome.mobile.view.VideoView;
import tv.superawesome.mobile.view.VideoViewListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	private static final String TAG = "SuperAwesome Demo App";
	private BannerView bannerView;
	private InterstitialView interstitialView;
	private VideoView videoView;
	private RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bannerView = (BannerView) findViewById(R.id.banner_container);
		
		interstitialView = (InterstitialView) findViewById(R.id.interstitial_container);
	}
	
	 public void presentInterstitial(View view) {
		 interstitialView.present();
	 }
	 
	 public void playVideoAd(View view){
		 layout = (RelativeLayout) findViewById(R.id.layout);
		 videoView = new VideoView(this, null);
		 layout.addView(videoView);
		 videoView.setListener(new VideoViewListener() {
		     @Override
		     public void onPlaybackCompleted() {
		    	 Log.v(TAG, "Video ad playback has completed");
		    	 layout.removeView(videoView);
		     }

		     @Override
		     public void onLoaded() {
		    	 Log.v(TAG, "Video ad has loaded");
		       videoView.play();
		     }
		     
		     @Override
		     public void onAdError(){
		    	 Log.v(TAG, "Video ad error");
		    	 
		    	 layout.removeView(videoView);
		     }
		 });
	 }
}
