package tv.superawesome.plugins.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

import tv.superawesome.plugins.air.functions.SAAIRLoadAdFunction;
import tv.superawesome.plugins.air.functions.SAAIRPlayBannerAd;
import tv.superawesome.plugins.air.functions.SAAIRPlayFullscreenVideoAd;
import tv.superawesome.plugins.air.functions.SAAIRPlayInterstitialAd;
import tv.superawesome.plugins.air.functions.SAAIRPlayVideoAd;

/**
 * Created by gabriel.coman on 16/03/16.
 */
public class SAAIRExtensionContext extends FREContext{
    @Override
    public Map<String, FREFunction> getFunctions() {
        Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
        functions.put("loadAd", new SAAIRLoadAdFunction());
        functions.put("playFullscreenVideoAd", new SAAIRPlayFullscreenVideoAd());
        functions.put("playInterstitialAd", new SAAIRPlayInterstitialAd());
        functions.put("playVideoAd", new SAAIRPlayVideoAd());
        functions.put("playBannerAd", new SAAIRPlayBannerAd());
        return functions;
    }

    @Override
    public void dispose() {
        Log.d("AIREXT", "AIR Extension Context disposed");
    }
}
