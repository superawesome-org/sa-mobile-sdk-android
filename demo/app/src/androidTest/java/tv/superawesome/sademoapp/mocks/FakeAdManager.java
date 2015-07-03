package tv.superawesome.sademoapp.mocks;

import tv.superawesome.superawesomesdk.AdLoader;
import tv.superawesome.superawesomesdk.AdManager;
import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.view.Ad;
import tv.superawesome.superawesomesdk.view.AdLoaderListener;

/**
 * Created by connor.leigh-smith on 02/07/15.
 */
public class FakeAdManager extends AdManager {

    private Ad responseAd;

    public FakeAdManager (String baseUrl)
    {
        super(baseUrl);
    }

    public void setResponse(Ad responseAd) {
        this.responseAd = responseAd;
    }

    public void getAd(String placementID, boolean testMode, AdLoaderListener listener)
    {
       listener.onLoaded(this.responseAd);
    }
}
