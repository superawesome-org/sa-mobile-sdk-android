package tv.superawesome.plugins.unity;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAFullscreenVideoAd;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 04/02/16.
 */
public class SAUnityExtensionContext {

    private HashMap<String, Object> adMap = null;

    /** the singleton SuperAwesome instance */
    private static SAUnityExtensionContext instance = new SAUnityExtensionContext();

    /** make the constructor private so that this class cannot be instantiated */
    private SAUnityExtensionContext(){
        adMap = new HashMap<>();
    }

    /** Get the only object available */
    public static SAUnityExtensionContext getInstance(){
        return instance;
    }

    /** setters and getters */
    public void setAdMap(String key, Object obj){
        adMap.put(key, obj);
    }

    public Object getAdMap(String key){
        return adMap.get(key);
    }

    public void removeFromMap(String key) {
        adMap.remove(key);
    }

    public void removeFromMap(final int placementId) {
        String key = null;
        for (Map.Entry<String, Object> entry: adMap.entrySet()) {
            Object o = entry.getValue();
            String k = entry.getKey();
            int placement = -1;
            if (o.getClass().toString().contains(SABannerAd.class.getName())) {
                SABannerAd b = (SABannerAd)o;
                placement = b.getAd().placementId;
            } else if (o.getClass().toString().contains(SAVideoAd.class.getName())) {
                SAVideoAd v = (SAVideoAd)o;
                placement = v.getAd().placementId;
            } else if (o.getClass().toString().contains(SAInterstitialAd.class.getName())) {
                SAInterstitialAd i = (SAInterstitialAd)o;
                placement = i.getAd().placementId;
            } else if (o.getClass().toString().contains(SAFullscreenVideoAd.class.getName())) {
                SAFullscreenVideoAd fv = (SAFullscreenVideoAd)o;
                placement = fv.getAd().placementId;
            }

            if (placement == placementId) {
                key = k;
            }
        }

        /** finally remove */
        if (key != null){
            adMap.remove(key);
            Log.d("SuperAwesome", adMap.entrySet().size() + " Keys remaining in Unity ad dict");
        }
    }
}
