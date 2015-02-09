package tv.superawesome.mobile;

import java.util.Observable;
import java.util.Observer;

import tv.superawesome.mobile.ParentalGate.ParentalGateViewCallback;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.adtech.mobilesdk.publisher.configuration.AdtechAdConfiguration;
import com.adtech.mobilesdk.publisher.view.AdtechBannerView;
import com.adtech.mobilesdk.publisher.view.AdtechBannerViewCallback;


public class BannerView extends DisplayAdView implements Observer {
	
	private static final String TAG = "SuperAwesome SDK - Banner";
	public AdtechBannerView adtechView;
	
	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//Add adtech view
		adtechView = new AdtechBannerView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		adtechView.setLayoutParams(params);
		addView(adtechView);
		
		//Set observer
		SuperAwesome.getInstance().addObserver(this);
		SuperAwesome.getInstance().setContext(context);
	}
	
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

	public void load(){
		AdtechAdConfiguration conf = getConfiguration();
		if(conf != null){
			adtechView.setAdConfiguration(conf);
			adtechView.setViewCallback(adTechViewCallback);
			adtechView.load();
		}
	}
	
	public void stop(){
		adtechView.stop();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Log.v("SuperAwesome SDK", "observed");
		load();
	}
}