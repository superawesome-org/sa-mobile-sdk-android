package tv.superawesome.mobile;

import java.util.Observable;
import java.util.Observer;

import tv.superawesome.mobile.ParentalGate.ParentalGateViewCallback;

import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialView;
import com.adtech.mobilesdk.publisher.view.AdtechInterstitialViewCallback;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class InterstitialView extends DisplayAdView implements Observer{
	
	public AdtechInterstitialView adtechView;
	
	public InterstitialView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		adtechView = new AdtechInterstitialView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		adtechView.setLayoutParams(params);
		addView(adtechView);
		
		//Set observer
		SuperAwesome.getInstance().addObserver(this);
		SuperAwesome.getInstance().setContext(context);
	}
	
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
	
	public void present(){
		AdtechAdConfiguration conf = getConfiguration();
		if(conf != null){
			adtechView.setAdConfiguration(conf);
			adtechView.setViewCallback(atcb);
    	adtechView.load();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
