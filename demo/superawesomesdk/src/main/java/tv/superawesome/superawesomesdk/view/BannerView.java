package tv.superawesome.superawesomesdk.view;

import org.json.JSONObject;

import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.view.AdView;


public class BannerView implements AdView {

	private static final String TAG = "SuperAwesome SDK - Banner";
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

}