package tv.superawesome.mobile;

import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechBannerView;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;


public class BannerView extends FrameLayout {
	
	public AdtechBannerView adtechView;
	
	public enum BannerSize {
	    SMALL, MEDIUM, LARGE
	}
	
	private AdtechAdConfiguration getDefaultConfiguration(BannerSize size){
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
	}
	
	public void load(){
		Log.v("BannerView", "BannerView - Load");
		adtechView.load();
	}
	
	public void stop(){
		adtechView.stop();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		Log.v("BannerView", "BannerView w=" + w + " h="+h);
		
		BannerSize size = getBannerSize(w/3, h/3);
		AdtechAdConfiguration adtechAdConfiguration = getDefaultConfiguration(size);
		adtechView.setAdConfiguration(adtechAdConfiguration);
		load();
		
		super.onSizeChanged(w, h, oldw, oldh);
	}
}