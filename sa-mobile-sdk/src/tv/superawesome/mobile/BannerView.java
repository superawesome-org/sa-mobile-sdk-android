package tv.superawesome.mobile;

import java.util.Observable;
import java.util.Observer;

import tv.superawesome.mobile.ParentalGate.ParentalGateViewCallback;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechBannerView;
import com.adtech.mobilesdk.publisher.view.AdtechBannerViewCallback;


public class BannerView extends FrameLayout implements Observer {
	
	public AdtechBannerView adtechView;
	
	private AdtechBannerViewCallback adTechViewCallback = new AdtechBannerViewCallback() {
		@Override
		public boolean shouldInterceptLandingPageOpening(final String url, NonModalLandingPageHandlerCallback callback) {
			if(!SuperAwesome.getInstance().getUseParentalGate()) return false;
			
			ParentalGate gate = new ParentalGate(getContext());
			ParentalGateViewCallback cb = new ParentalGateViewCallback(){
				public void onCorrectAnswer(){
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					getContext().startActivity(browserIntent);
				}
			};
			gate.setViewCallback(cb);
			return true;
		};
	};
	
	public enum BannerSize {
	    SMALL, MEDIUM, LARGE
	}
	
	private BannerSize size;
	
	
	private AdtechAdConfiguration getDefaultConfiguration(){
		if(size == BannerSize.SMALL){
			AdtechAdConfiguration config = new AdtechAdConfiguration("MyApp");
			config.setAlias("706332-300x50-5");
			config.setDomain("a.adtech.de");
			config.setNetworkId(1486);
			config.setSubnetworkId(1);
			return config;
		}else if(size == BannerSize.MEDIUM){
			AdtechAdConfiguration config = new AdtechAdConfiguration("MyApp");
			config.setAlias("706332-320x50-5");
			config.setDomain("a.adtech.de");
			config.setNetworkId(1486);
			config.setSubnetworkId(1);
			return config;
		}else if(size == BannerSize.LARGE){
			AdtechAdConfiguration config = new AdtechAdConfiguration("MyApp");
			config.setAlias("706332-728x90-5");
			config.setDomain("a.adtech.de");
			config.setNetworkId(1486);
			config.setSubnetworkId(1);
			return config;
		}
		return null;
	}
	
	private AdtechAdConfiguration getConfiguration(){
		int width = 0, height = 0;
		if(size == BannerSize.SMALL){
			width = 300;
			height = 50;
		}else if(size == BannerSize.MEDIUM){
			width = 320;
			height = 50;
		}else if(size == BannerSize.LARGE){
			width = 728;
			height = 90;
		}
		Placement placement = SuperAwesome.getInstance().getPlacement(width, height);
		if(placement == null){
			return getDefaultConfiguration();
		}
		AdtechAdConfiguration config = new AdtechAdConfiguration("MyApp");
		config.setAlias(placement.alias);
		config.setDomain("a.adtech.de");
		config.setNetworkId(placement.networkId);
		config.setSubnetworkId(placement.subNetworkId);
		return config;
	}
	
	private BannerSize getBannerSize(int width, int height){
		if(height>=90 && width>=728){
	        return BannerSize.LARGE;
	    }else if(height>=50 && width>=320){
	        return BannerSize.MEDIUM;
	    }else if(height>=50 && width>=300){
	        return BannerSize.SMALL;
	    }
		return BannerSize.SMALL;
	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		adtechView = new AdtechBannerView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		adtechView.setLayoutParams(params);
		addView(adtechView);
		
		SuperAwesome.getInstance().addObserver(this);
		SuperAwesome.getInstance().setContext(context);
	}
	
	public void load(){
		Log.v("SuperAwesome SDK", "BannerView - Load");
		AdtechAdConfiguration adtechAdConfiguration = getConfiguration();
		adtechView.setAdConfiguration(adtechAdConfiguration);
		adtechView.setViewCallback(adTechViewCallback);
		adtechView.load();
	}
	
	public void stop(){
		adtechView.stop();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		Log.v("SuperAwesome SDK", "BannerView w=" + w + " h="+h);
		size = getBannerSize(w/3, h/3);
		
		//Load banner if configuration has been loaded
		if(!SuperAwesome.getInstance().getIsLoadingConfiguration()){
			load();
		}
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Log.v("SuperAwesome SDK", "observed");
		load();
	}
}