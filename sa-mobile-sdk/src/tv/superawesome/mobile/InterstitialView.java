package tv.superawesome.mobile;

import tv.superawesome.mobile.ParentalGate.ParentalGateViewCallback;

import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
