package tv.superawesome.sademoapp;


import java.io.Serializable;

import tv.superawesome.mobile.view.BannerView;
import tv.superawesome.mobile.view.BannerViewListener;
import tv.superawesome.mobile.view.InterstitialView;
import tv.superawesome.mobile.view.InterstitialViewListener;
import tv.superawesome.mobile.view.VideoAdActivity;
import tv.superawesome.mobile.view.VideoView;
import tv.superawesome.mobile.view.VideoViewListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	private static final String TAG = "SuperAwesome Demo App";
	
	//Resources
	private RelativeLayout layout;
	private LinearLayout linlayout;
	private BannerView bannerView;
	private InterstitialView interstitialView;
	
	//Programmatical
	private BannerView bannerView2;
	private InterstitialView interstitialView2;
	private VideoView videoView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layout = (RelativeLayout) findViewById(R.id.layout);
		linlayout = (LinearLayout) findViewById(R.id.linearlayout);
		bannerView = (BannerView) findViewById(R.id.banner_container);
		interstitialView = (InterstitialView) findViewById(R.id.interstitial_container);
	}
	
	public void presentInterstitial(View view) {
		 interstitialView.present();
	}

	
	
	public void createBanner(View view) {
		bannerView2 = new BannerView(this, null);
		bannerView2.setListener(new BannerViewListener() {
			@Override
			public void onAdLeave() {
				Log.d(TAG, "Banner clicked");
			}
			
			@Override
			public void onAdError() {
				Log.d(TAG, "Banner error");
			}
		});
		bannerView2.setPlacementID("5513682");
		linlayout.addView(bannerView2);
	}
	
	public void createInterstitial(View view) {
		interstitialView2 = new InterstitialView(this, null);
		interstitialView2.setPlacementID("5247931");
		interstitialView2.setListener(new InterstitialViewListener() {
			@Override
			public void onLoaded() {
				Log.d(TAG, "Interstitial loaded");
			}
			
			@Override
			public void onAdLeave() {
				Log.d(TAG, "Interstitial ad clicked");
			}
			
			@Override
			public void onAdError() {
				Log.d(TAG, "Interstitial error");
			}
			
			@Override
			public void onAdDismiss() {
				Log.d(TAG, "Interstitial dismissed");
			}
		});
		layout.addView(interstitialView2, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		interstitialView2.present();
	}

	 public void playVideoAd(View view) {
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
	 
	 public void playFullscreenVideoAd(View view){
		 Intent intent = new Intent(this, VideoAdActivity.class);
//		 Bundle params = new Bundle();
//		 params.putBoolean("disable_loading_dialog", true);
//		 intent.putExtras(params);
		 startActivity(intent);
	 }
}
