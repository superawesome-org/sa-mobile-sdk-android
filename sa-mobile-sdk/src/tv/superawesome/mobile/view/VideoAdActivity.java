package tv.superawesome.mobile.view;

import android.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class VideoAdActivity extends Activity{
	
	protected static final String TAG = "SuperAwesome SDK - VideoViewActivity";
	private VideoView videoView;
	
	@Override
  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
      // remove title
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
      addVideoView();
 	}
	
	private void addVideoView(){
		videoView = new VideoView(this, null);
    videoView.setBackgroundColor(Color.BLACK);
		 videoView.setListener(new VideoViewListener() {
		   @Override
		   public void onPlaybackCompleted() {
		  	 Log.v(TAG, "Video ad playback has completed");
		  	 finish();
		   }
		
		   @Override
		   public void onLoaded() {
		  	 Log.v(TAG, "Video ad has loaded");
		     videoView.play();
		   }
		   
		   @Override
		   public void onAdError(){
		  	 Log.v(TAG, "Video ad error");
		  	 finish();
		   }
		 });
		 
		 setContentView(videoView);
	}
	
	@Override
	public void onBackPressed() {
		
	}
}
