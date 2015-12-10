/**
 * @class: SAAux.java
 * @package: tv.superawesome.sdk.aux
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */

/**
 * packaged and imports for this class
 */
package tv.superawesome.sdk.aux;
import android.util.Patterns;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Class that contains a lot of static aux functions
 */
public class SAAux {

    /**
     * @brief Function that returns a random number between two limits
     * @param min - min edge
     * @param max - max edge
     * @return a random integer
     */
    public static int randomNumberBetween(int min, int max){
        Random rand  = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * @brief Cachebuster is just a big random number
     * @return
     */
    public static int getCacheBuster() {
        return SAAux.randomNumberBetween(1000000, 1500000);
    }

    /**
     * @brief return true if json is empty, false otherwise
     * @param dict a json dict
     */
    public static boolean isJSONEmpty(JsonObject dict){
        if (dict == null) return false;
        if (dict.entrySet().isEmpty()) return false;
        if (dict.toString().equals("{}")) return false;
        return true;
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
            queryArray.add(e.getKey() + "=" + e.getValue().toString() + "&") ;
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
        if (url.equals("http://")) return false;
        if (url.equals("https://")) return false;
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
