/**
 * @class: SAUtils.java
 * @package: tv.superawesome.sdk.aux
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */

/**
 * packaged and imports for this class
 */
package tv.superawesome.lib.sautils;

import com.google.gson.JsonObject;
import java.util.Random;

/**
 * Class that contains a lot of static aux functions
 */
public class SAUtils {

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
     * @brief return true if json is empty, false otherwise
     * @param dict a json dict
     */
    public static boolean isJSONEmpty(JsonObject dict) {
        if (dict == null) return false;
        if (dict.entrySet().isEmpty()) return false;
        if (dict.toString().equals("{}")) return false;
        return true;
    }
}
