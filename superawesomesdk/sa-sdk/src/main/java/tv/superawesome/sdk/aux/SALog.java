/**
 * @class: SALog.java
 * @package: tv.superawesome.sdk.aux
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 09/12/15
 *
 */
package tv.superawesome.sdk.aux;

/**
 * Imports needed by the class
 */
import android.util.Log;

/**
 * Class that is used to standardise some Log messages
 */
public class SALog {

    /**
     * constant tags
     */
    private static final String INFO_TAG = "[AA :: INFO]";
    private static final String ERR_TAG = "[AA :: ERROR]";

    /**
     * @brief: just a thin wrapper around Log.d
     * @param message - the message to be printed out
     */
    public static void Log(String message){
        Log.d(INFO_TAG, message);
    }

    /**
     * @brief just a thin wrapper around Log.e
     * @param message - the message to be printed out
     */
    public static void Err(String message) {
        Log.e(ERR_TAG, message);
    }
}
