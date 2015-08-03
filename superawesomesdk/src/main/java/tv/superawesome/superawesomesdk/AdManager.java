package tv.superawesome.superawesomesdk;

/**
 * Created by connor.leigh-smith on 23/06/15.
 */
public class AdManager {

    public String baseUrl;

    public AdManager (String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public void getAd(String placementID, boolean testMode, AdLoaderListener listener)
    {
        String url = this.baseUrl + "/ad/" + placementID;
        url = testMode ? url + "?test=true" : url;

        AdLoader adLoader = new AdLoader(listener, SuperAwesome.createUrlLoader(), SuperAwesome.createUrlLoader());
        adLoader.loadAd(url);
    }

}
