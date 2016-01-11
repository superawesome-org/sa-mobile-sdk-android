/**
 * @class: SASystem.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.lib.sanetwork;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import tv.superawesome.lib.sautils.SALog;

/**
 * Type of aux functions
 */
public class SASystem {
    /**
     * function that gets the system type - because it's iOS - it will only
     * return "ios"
     * @return constant - ios
     */
    public static SASystemType getSystemType() {
        return SASystemType.android;
    }

    /**
     * function that gets the system size
     * @return where either tablet or mobile
     */
    public static SASystemSize getSystemSize() {

        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double)width/(double)dens;
        double hi = (double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);

        if (screenInches < 6){
            return SASystemSize.mobile;
        } else {
            return SASystemSize.tablet;
        }
    }

    /**
     * just return the verbose system
     * @return a string
     */
    public static String getVerboseSystemDetails() {
        return getSystemType().toString() + "_" + getSystemSize().toString();
    }
}
