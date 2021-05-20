/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that extends a FREContext object
 */
public class SAAIRExtensionContext extends FREContext{
    /**
     * Overridden FREContext "getFunctions" method that will return a hash-map containing
     * function names and implementation so that AIR knows what to call
     *
     * @return a hash-map of function names-implementations
     */
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
        functions.put("SuperAwesomeAIRVersionSetVersion", new SAAIRVersion.SuperAwesomeAIRVersionSetVersion());
        functions.put("SuperAwesomeAIRBumperOverrideName", new SAAIRBumperPage.SuperAwesomeAIRBumperOverrideName());
        functions.put("SuperAwesomeAIRAwesomeAdsInit", new SAAIRAwesomeAds.SuperAwesomeAIRAwesomeAdsInit());
        functions.put("SuperAwesomeAIRAwesomeAdsTriggerAgeCheck", new SAAIRAwesomeAds.SuperAwesomeAIRAwesomeAdsTriggerAgeCheck());

        // return the result for the context
        return functions;
    }

    /**
     * Method that disposes of the current FREContext
     */
    @Override
    public void dispose() {
        Log.d("AIREXT", "AIR Extension Context disposed");
    }
}
