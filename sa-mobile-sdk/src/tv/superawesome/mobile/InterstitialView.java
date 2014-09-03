package tv.superawesome.mobile;

import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class InterstitialView extends FrameLayout {
	
	public AdtechInterstitialView adtechView;
	
	private AdtechInterstitialViewCallback atcb = new AdtechInterstitialViewCallback(){
    	@Override
    	public void onAdSuccess() {
    		
    	}
    	
    	@Override
    	public void onAdFailure() {
    		// This method is called when an ad download failed. This could happen because of networking reasons or other
    		//server communication reasons.
    	}
	};

	public InterstitialView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		adtechView = new AdtechInterstitialView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		adtechView.setLayoutParams(params);
		addView(adtechView);
	}
	
	public void present(){
        AdtechAdConfiguration adtechAdConfiguration = new AdtechAdConfiguration("MyApp");
        adtechAdConfiguration.setAlias("706332-320x480-5");
        adtechAdConfiguration.setDomain("a.adtech.de");
        adtechAdConfiguration.setNetworkId(1486);
        adtechAdConfiguration.setSubnetworkId(1);
        adtechView.setAdConfiguration(adtechAdConfiguration);
        adtechView.setViewCallback(atcb);
        
        adtechView.load();
	}

}
