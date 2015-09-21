package tv.superawesome.sdk.events;

/**
 * Created by gabriel.coman on 21/09/15.
 */
public class SAVideoEventManager extends SAEventManager {
    // singleton variable
    private static SAVideoEventManager eventManager = new SAVideoEventManager ();

    private SAVideoEventManager (){
        // do nothing
        super.request.adtype = SAAdType.SAAdTypeVideo;
    }

    // singleton function
    public static SAVideoEventManager getIntance(){
        return eventManager;
    }
}
