package tv.superawesome.superawesomesdk.view;

import android.util.Log;

import org.json.JSONObject;

import tv.superawesome.superawesomesdk.SuperAwesome;


public class BannerView implements AdView {

	private static final String TAG = "SA SDK - Banner";
    private String placementID;
    private boolean testMode = false;


	public BannerView(String placementID) {
        this.placementID = placementID;
        this.loadAd();
	}

    public void loadAd()
    {
        SuperAwesome.getInstance().getAdManager().getAd(this.placementID, this.testMode, this);
    }

    @Override
    public void onAdLoaded(JSONObject response) {
        System.out.println(response);
    }

    @Override
    public void onAdBeginLoad() {
        Log.d(TAG, "Ad loading");
    }

    @Override
    public void onAdError() {
        Log.d(TAG, "Error loading ad");
    }

}