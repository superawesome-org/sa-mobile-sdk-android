package tv.superawesome.plugins.publisher.unity.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

public class SAScreenUtil {
    /**
     * Function that returns the current screen size
     *
     * @param activity the activity to pass along as context
     * @return a SASize object with width & height members
     */
    public static SASize getRealScreenSize(Activity activity, boolean rotate) {
        View decorView = activity.getWindow().getDecorView();
        if (!rotate) {
            return new SASize(decorView.getWidth(), decorView.getHeight());
        } else {
            return new SASize(decorView.getHeight(), decorView.getWidth());
        }
    }

    /**
     * Internal SAUtils class defining a SASize object (containing width & height)
     */
    public static class SASize {

        // member vars
        public final int width;
        public final int height;

        /**
         * SASize basic constructor
         *
         * @param w new width
         * @param h new height
         */
        SASize(int w, int h) {
            width = w;
            height = h;
        }
    }

    /**
     * Get the current scale factor
     *
     * @param activity the activity to pass along as context
     * @return a float meaning the scale
     */
    public static float getScaleFactor(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return (float) metrics.densityDpi / (float) DisplayMetrics.DENSITY_DEFAULT;
    }
}
