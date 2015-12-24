package tv.superawesome.lib.sanetwork;

/**
 * Imports for this class
 */
import android.util.Patterns;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAURLUtils {
    /**
     * @brief Cachebuster is just a big random number
     * @return
     */
    public static int getCacheBuster() {
        int min = 1000000;
        int max = 1500000;
        Random rand  = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * @brief Function that forms a query string from a dict
     * @param dict
     * @return
     */
    public static String formGetQueryFromDict(JsonObject dict) {
        String queryString = "";

        ArrayList<String> queryArray = new ArrayList<>();
        for (Map.Entry<String, JsonElement> e : dict.entrySet()) {
            queryArray.add(e.getKey() + "=" + e.getValue().toString().replace("\"","") + "&") ;
        }

        for (String queryObj : queryArray) {
            queryString += queryObj;
        }

        if (queryString.length() > 1) {
            return queryString.substring(0, queryString.length() - 1);
        } else {
            return queryString;
        }
    }

    /**
     * @brief checks if an URL is valid
     * @param url - the url in question
     * @return true if valid, false otherwise
     */
    public static boolean isValidURL(String url){
        if (url == null) return false;
        if (url.equals("http://")) return false;
        if (url.equals("https://")) return false;
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
