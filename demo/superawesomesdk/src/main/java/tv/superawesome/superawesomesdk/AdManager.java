package tv.superawesome.superawesomesdk;

import tv.superawesome.superawesomesdk.view.PlacementView;

/**
 * Created by connor.leigh-smith on 23/06/15.
 */
public class AdManager {

    public String baseUrl;

    public AdManager (String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public void getAd(String placementID, boolean testMode, PlacementView delegate)
    {
        String url = this.baseUrl + "/ad/" + placementID;
        url = testMode ? url + "?test=true" : url;

        AdLoader adLoader = new AdLoader(delegate);
        adLoader.execute(url);
    }

}
