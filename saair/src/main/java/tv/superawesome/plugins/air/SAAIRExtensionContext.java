package tv.superawesome.plugins.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

public class SAAIRExtensionContext extends FREContext{
    @Override
    public Map<String, FREFunction> getFunctions() {

        // create a new map of FREFunction objects
        Map<String, FREFunction> functions = new HashMap<String, FREFunction>();

        // add all functions
        functions.put("SuperAwesomeAIRSAInterstitialAdCreate", new SAAIRInterstitialAd.SuperAwesomeAIRSAInterstitialAdCreate());
        functions.put("SuperAwesomeAIRSAInterstitialAdLoad", new SAAIRInterstitialAd.SuperAwesomeAIRSAInterstitialAdLoad());
        functions.put("SuperAwesomeAIRSAInterstitialAdPlay", new SAAIRInterstitialAd.SuperAwesomeAIRSAInterstitialAdPlay());
        functions.put("SuperAwesomeAIRSAInterstitialAdHasAdAvailable", new SAAIRInterstitialAd.SuperAwesomeAIRSAInterstitialAdHasAdAvailable());
        functions.put("SuperAwesomeAIRSAVideoAdCreate", new SAAIRVideoAd.SuperAwesomeAIRSAVideoAdCreate());
        functions.put("SuperAwesomeAIRSAVideoAdLoad", new SAAIRVideoAd.SuperAwesomeAIRSAVideoAdLoad());
        functions.put("SuperAwesomeAIRSAVideoAdPlay", new SAAIRVideoAd.SuperAwesomeAIRSAVideoAdPlay());
        functions.put("SuperAwesomeAIRSAVideoAdHasAdAvailable", new SAAIRVideoAd.SuperAwesomeAIRSAVideoAdHasAdAvailable());
        functions.put("SuperAwesomeAIRSABannerAdCreate", new SAAIRBannerAd.SuperAwesomeAIRSABannerAdCreate());
        functions.put("SuperAwesomeAIRSABannerAdLoad", new SAAIRBannerAd.SuperAwesomeAIRSABannerAdLoad());
        functions.put("SuperAwesomeAIRSABannerAdHasAdAvailable", new SAAIRBannerAd.SuperAwesomeAIRSABannerAdHasAdAvailable());
        functions.put("SuperAwesomeAIRSABannerAdPlay", new SAAIRBannerAd.SuperAwesomeAIRSABannerAdPlay());
        functions.put("SuperAwesomeAIRSABannerAdClose", new SAAIRBannerAd.SuperAwesomeAIRSABannerAdClose());
        functions.put("SuperAwesomeAIRSAAppWallCreate", new SAAIRAppWall.SuperAwesomeAIRSAAppWallCreate());
        functions.put("SuperAwesomeAIRSAAppWallLoad", new SAAIRAppWall.SuperAwesomeAIRSAAppWallLoad());
        functions.put("SuperAwesomeAIRSAAppWallPlay", new SAAIRAppWall.SuperAwesomeAIRSAAppWallPlay());
        functions.put("SuperAwesomeAIRSAAppWallHasAdAvailable", new SAAIRAppWall.SuperAwesomeAIRSAAppWallHasAdAvailable());
        functions.put("SuperAwesomeAIRSuperAwesomeHandleCPI", new SAAIRCPI.SuperAwesomeAIRSuperAwesomeHandleCPI());
        functions.put("SuperAwesomeAIRGetVersion", new SAAIRVersion.SuperAwesomeAIRSetVersion());

        // return the result for the context
        return functions;
    }

    @Override
    public void dispose() {
        Log.d("AIREXT", "AIR Extension Context disposed");
    }
}
