package tv.superawesome.plugins.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

import tv.superawesome.plugins.air.functions.SAAIRLoadAdFunction;
import tv.superawesome.plugins.air.functions.SAAIRPlayFullscreenVideoAd;

/**
 * Created by gabriel.coman on 16/03/16.
 */
public class SAAIRExtensionContext extends FREContext{
    @Override
    public Map<String, FREFunction> getFunctions() {
        Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
        functions.put("loadAd", new SAAIRLoadAdFunction());
        functions.put("playFullscreenVideoAd", new SAAIRPlayFullscreenVideoAd());
        return functions;
    }

    @Override
    public void dispose() {
        Log.d("AIREXT", "AIR Extension Context disposed");
    }
}
