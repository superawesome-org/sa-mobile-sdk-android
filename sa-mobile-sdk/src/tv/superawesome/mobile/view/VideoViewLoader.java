package tv.superawesome.mobile.view;

import java.util.Observable;
import java.util.Observer;

import tv.superawesome.mobile.SuperAwesome;
import tv.superawesome.mobile.model.Preroll;
import android.content.Context;
import android.util.Log;

import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdsLoader.AdsLoadedListener;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.samples.demoapp.player.DemoPlayer;

public class VideoViewLoader  implements AdErrorListener, AdsLoadedListener, Observer, AdEventListener{
	
	private static final String TAG = "SuperAwesome SDK - Video Loader";
	
	protected VideoViewLoaderListener listener;
	
	protected ImaSdkFactory sdkFactory;
	protected AdsLoader adsLoader;
	protected ImaSdkSettings sdkSettings;
	protected DemoPlayer videoPlayer;
	protected AdsManager adsManager;
	

	public VideoViewLoader(Context context){
		Log.v(TAG, "VideoViewLoader created");
		
		sdkFactory = ImaSdkFactory.getInstance();
		adsLoader = sdkFactory.createAdsLoader(context, getImaSdkSettings());
		adsLoader.addAdErrorListener(this);
		adsLoader.addAdsLoadedListener(this);
		
		videoPlayer = new DemoPlayer(context);
		
		SuperAwesome.getInstance().addObserver(this);
		SuperAwesome.getInstance().setContext(context);
		
		if(!SuperAwesome.getInstance().getIsLoadingConfiguration()){
			requestAd();
		}
	}
	
	public VideoViewLoaderListener getListener() {
		return listener;
	}

	public void setListener(VideoViewLoaderListener listener) {
		this.listener = listener;
	}
	
	public DemoPlayer getVideoPlayer(){
		return videoPlayer;
	}

	public AdsManager getAdsManager() {
		return adsManager;
	}

	protected ImaSdkSettings getImaSdkSettings() {
    if (sdkSettings == null) {
      sdkSettings = sdkFactory.createImaSdkSettings();
    }
    return sdkSettings;
	}
	
	protected void requestAd() {
		AdDisplayContainer container = sdkFactory.createAdDisplayContainer();
		container.setPlayer(videoPlayer);
	  container.setAdContainer(videoPlayer.getUiContainer());
		
		Log.v(TAG, "Requesting video ad");
	  AdsRequest request = sdkFactory.createAdsRequest();
	  
	  Preroll preroll = SuperAwesome.getInstance().getPreroll();
	  if(preroll == null){
	  	Log.v(TAG, "Using default VAST tag");
		  request.setAdTagUrl("http://u-ads.adap.tv/a/h/0A9CJKYei5h0XHk2xFl36YvivtZ9NIb4jSZkKFRs7AQ=?cb=%7Bcachebreaker%7D&pageUrl=http%3A%2F%2Fadap.tv&eov=eov");
	  }else{
		  request.setAdTagUrl(preroll.vast);
	  }
	  request.setAdDisplayContainer(container);
	  adsLoader.requestAds(request);
	  Log.v(TAG, "Ad requested");
	}
	
	@Override
	public void onAdsManagerLoaded(AdsManagerLoadedEvent event) {
		Log.v(TAG, "Ads manager loaded "+event.toString());
		adsManager = event.getAdsManager();
	  adsManager.addAdErrorListener(this);
	  adsManager.addAdEventListener(this);
	  adsManager.init();
	}

	@Override
	public void onAdError(AdErrorEvent event) {
		Log.v(TAG, "Video Ad Error "+event.getError().getMessage());
		if(listener != null){
			listener.onAdError();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		requestAd();
	}

	@Override
	public void onAdEvent(AdEvent event) {
		switch (event.getType()) {
    	case LOADED:
    		Log.v(TAG, "Received LOADED event");
    		if(listener != null){
    			listener.onLoaded();
    		}
    		break;
    	default:
    		break;
		}
	}
	
}
