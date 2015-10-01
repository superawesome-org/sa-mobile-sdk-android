package tv.superawesome.sdk;

import android.util.Log;

public class SuperAwesome {

	private static final String TAG = "SuperAwesome SDK";
	private static final String VERSION = "2.0";

	protected static final String baseUrl = "https://ads.superawesome.tv/v2";

	public static String getVersion(){
		return VERSION;
	}

	public SuperAwesome(){
		Log.v(TAG, "SuperAwesome SDK version " + VERSION);
	}

	public static AdManager createAdManager () {
		return new AdManager(baseUrl);
	}

	public static UrlLoader createUrlLoader() {
		return new UrlLoader();
	}

	public static String getBaseUrl() { return baseUrl; }
}
