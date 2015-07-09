package tv.superawesome.superawesomesdk;

import android.content.Context;
import android.util.Log;

import java.net.URLClassLoader;

import tv.superawesome.superawesomesdk.view.AdLoaderListener;
import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.InterstitialView;

public class SuperAwesome {
	
	private static final String TAG = "SuperAwesome SDK";
	private static final String VERSION = "2.0";

	private static SuperAwesome instance = null;

	protected static final String baseUrl = "https://beta.ads.superawesome.tv/v2";
	
	public static String getVersion(){
		return VERSION;
	}

//	public static SuperAwesome getInstance() {
//      if(instance == null) {
//         instance = new SuperAwesome();
//      }
//      return instance;
//	}
	
	public SuperAwesome(){
		Log.v(TAG, "SuperAwesome SDK version "+VERSION);
	}

	public static AdManager createAdManager () {
		return new AdManager(baseUrl);
	}

    public static BannerView createBannerView(Context context, String placementID) {
        return new BannerView(context, placementID, createAdManager());
    }

    public static InterstitialView createInterstitialView(Context context, String placementID) {
        return new InterstitialView(context, placementID, createAdManager());
    }

	public static UrlLoader createUrlLoader() {
		return new UrlLoader();
	}

}
