package tv.superawesome.plugins.unity;

import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;

public class SAUnityCallback {

    public static void sendToUnity(String unityAd, int placementId, String callback) {

        // don't do anything if class is not available
        if (!SAUtils.isClassAvailable("com.unity3d.player.UnityPlayer")) return;

        // form the payload
        JSONObject data = SAJsonParser.newObject(new Object[] {
                "placementId", "" + placementId + "",
                "type", "sacallback_" + callback
        });

        String payload = data.toString();

        // try to call the unity player
        try {
            Class<?> unity = Class.forName("com.unity3d.player.UnityPlayer");
            java.lang.reflect.Method method = unity.getMethod("UnitySendMessage", String.class, String.class, String.class);
            method.invoke(unity, unityAd, "nativeCallback", payload);
        } catch (ClassNotFoundException e) {
            Log.d("UNITY CALLBACK-Ex", e.toString());
        } catch (NoSuchMethodException e) {
            Log.d("UNITY CALLBACK-Ex", e.toString());
        } catch (InvocationTargetException e) {
            Log.d("UNITY CALLBACK-Ex", e.toString());
        } catch (IllegalAccessException e) {
            Log.d("UNITY CALLBACK-Ex", e.toString());
        }
    }

}
