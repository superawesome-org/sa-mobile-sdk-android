package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */
public class SAInterstitialEventManager extends SAEventManager {
    // singleton variable
    private static SAInterstitialEventManager eventManager = new SAInterstitialEventManager();

    private SAInterstitialEventManager(){
        // do nothing
        super.request.adtype = SAAdType.SAAdTypeInterstitial;
    }

    // singleton function
    public static SAInterstitialEventManager getIntance(){
        return eventManager;
    }
}
