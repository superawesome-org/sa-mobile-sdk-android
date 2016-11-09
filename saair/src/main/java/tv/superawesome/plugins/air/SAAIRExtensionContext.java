package tv.superawesome.plugins.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gabriel.coman on 16/03/16.
 */
public class SAAIRExtensionContext extends FREContext{
    @Override
    public Map<String, FREFunction> getFunctions() {

        // create a new map of FREFunction objects
        Map<String, FREFunction> functions = new HashMap<String, FREFunction>();

        // add all functions
        functions.put("SuperAwesomeAIRSAInterstitialAdCreate", new SAAIR.SuperAwesomeAIRSAInterstitialAdCreate());
        functions.put("SuperAwesomeAIRSAInterstitialAdLoad", new SAAIR.SuperAwesomeAIRSAInterstitialAdLoad());
        functions.put("SuperAwesomeAIRSAInterstitialAdPlay", new SAAIR.SuperAwesomeAIRSAInterstitialAdPlay());
        functions.put("SuperAwesomeAIRSAInterstitialAdHasAdAvailable", new SAAIR.SuperAwesomeAIRSAInterstitialAdHasAdAvailable());
        functions.put("SuperAwesomeAIRSAVideoAdCreate", new SAAIR.SuperAwesomeAIRSAVideoAdCreate());
        functions.put("SuperAwesomeAIRSAVideoAdLoad", new SAAIR.SuperAwesomeAIRSAVideoAdLoad());
        functions.put("SuperAwesomeAIRSAVideoAdPlay", new SAAIR.SuperAwesomeAIRSAVideoAdPlay());
        functions.put("SuperAwesomeAIRSAVideoAdHasAdAvailable", new SAAIR.SuperAwesomeAIRSAVideoAdHasAdAvailable());
        functions.put("SuperAwesomeAIRSABannerAdCreate", new SAAIR.SuperAwesomeAIRSABannerAdCreate());
        functions.put("SuperAwesomeAIRSABannerAdLoad", new SAAIR.SuperAwesomeAIRSABannerAdLoad());
        functions.put("SuperAwesomeAIRSABannerAdHasAdAvailable", new SAAIR.SuperAwesomeAIRSABannerAdHasAdAvailable());
        functions.put("SuperAwesomeAIRSABannerAdPlay", new SAAIR.SuperAwesomeAIRSABannerAdPlay());
        functions.put("SuperAwesomeAIRSABannerAdClose", new SAAIR.SuperAwesomeAIRSABannerAdClose());
        functions.put("SuperAwesomeAIRSAGameWallCreate", new SAAIR.SuperAwesomeAIRSAGameWallCreate());
        functions.put("SuperAwesomeAIRSAGameWallLoad", new SAAIR.SuperAwesomeAIRSAGameWallLoad());
        functions.put("SuperAwesomeAIRSAGameWallPlay", new SAAIR.SuperAwesomeAIRSAGameWallPlay());
        functions.put("SuperAwesomeAIRSAGameWallHasAdAvailable", new SAAIR.SuperAwesomeAIRSAGameWallHasAdAvailable());

        // return the result for the context
        return functions;
    }

    @Override
    public void dispose() {
        Log.d("AIREXT", "AIR Extension Context disposed");
    }
}
