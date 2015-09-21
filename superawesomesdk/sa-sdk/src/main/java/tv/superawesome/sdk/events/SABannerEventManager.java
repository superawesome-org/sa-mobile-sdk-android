package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */
public class SABannerEventManager extends SAEventManager {
    // singleton variable
    private static SABannerEventManager eventManager = new SABannerEventManager();

    private SABannerEventManager(){
        // do nothing
        super.request.adtype = SAAdType.SAAdTypeBanner;
    }

    // singleton function
    public static SABannerEventManager getIntance(){
        return eventManager;
    }
}
