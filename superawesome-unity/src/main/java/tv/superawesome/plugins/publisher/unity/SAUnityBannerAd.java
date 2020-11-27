/**
 * @Copyright: SADefaults Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;

import tv.superawesome.sdk.publisher.common.components.NumberGenerator;
import tv.superawesome.sdk.publisher.common.models.SAEvent;
import tv.superawesome.sdk.publisher.ui.banner.BannerView;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityBannerAd {

    // hash map containing banner ads
    private static HashMap<String, BannerView> bannerAdHashMap = new HashMap<>();

    /**
     * Method that creates a new banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdCreate(Context context, final String unityName) {

        // create the banner
        BannerView bannerAd = new BannerView(context);
        bannerAd.setId(new NumberGenerator().nextIntForCache());

        bannerAd.setListener((placementId, event) -> {
            switch (event) {
                case adLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adLoaded.toString());
                    break;
                case adEmpty:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEmpty.toString());
                    break;
                case adFailedToLoad:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToLoad.toString());
                    break;
                case adAlreadyLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adAlreadyLoaded.toString());
                    break;
                case adShown:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adShown.toString());
                    break;
                case adFailedToShow:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToShow.toString());
                    break;
                case adClicked:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClicked.toString());
                    break;
                case adEnded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEnded.toString());
                    break;
                case adClosed:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClosed.toString());
                    break;
            }
        });

        bannerAdHashMap.put(unityName, bannerAd);
    }

    /**
     * Method that loads a new Banner Ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdLoad(Context context, String unityName, int placementId, int configuration, boolean test) {
        if (bannerAdHashMap.containsKey(unityName)) {
            BannerView bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.setTestMode(test);
            bannerAd.load(placementId);
        }
    }

    /**
     * Method that checks to see if an ad is available for a banner ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySABannerAdHasAdAvailable(Context context, String unityName) {
        if (bannerAdHashMap.containsKey(unityName)) {
            BannerView bannerAd = bannerAdHashMap.get(unityName);
            return bannerAd != null && bannerAd.hasAdAvailable();
        }
        return false;
    }

    public static class SASize {

        // member vars
        public int width = 0;
        public int height = 0;

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
     * Function that returns the current screen size
     *
     * @param activity the activity to pass along as context
     * @return a SASize object with width & height members
     */
    public static SASize getRealScreenSize(Activity activity, boolean rotate) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();

        View decorView = activity.getWindow().getDecorView();

        if (!rotate) {
            return new SASize(decorView.getWidth(), decorView.getHeight());
        } else {
            return new SASize(decorView.getHeight(), decorView.getWidth());
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

    /**
     * Method that plays a new Banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdPlay(Context context, String unityName, boolean isParentalGateEnabled, boolean isBumperPageEnabled, int position, int width, int height, boolean color) {

        if (bannerAdHashMap.containsKey(unityName) && !bannerAdHashMap.get(unityName).isClosed()) {

            // get activity
            Activity activity = (Activity) context;

            // get banner ad
            final BannerView bannerAd = bannerAdHashMap.get(unityName);
            if (bannerAd == null) {
                Log.e("SuperAwesome", "Banner ad is null. Could not start banner.");
                return;
            }
            bannerAd.setParentalGate(isParentalGateEnabled);
            bannerAd.setBumperPage(isBumperPageEnabled);
            bannerAd.setColor(color);

            // get screen size
            SASize screenSize = getRealScreenSize(activity, false);

            // get scale factor
            float factor = getScaleFactor(activity);

            // scale it according to the factor
            int scaledWidth = (int) (factor * width);
            int scaledHeight = (int) (factor * height);

            // make sure it's not bigger than the screen
            if (scaledWidth > screenSize.width) {
                scaledHeight = (screenSize.width * scaledHeight) / scaledWidth;
            }

            // but not bigger than 15% of the screen's height
            if (scaledHeight > 0.15 * screenSize.height) {
                scaledHeight = (int) (0.15 * screenSize.height);
            }

            FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(screenSize.width, scaledHeight);
            layout.gravity = position == 0 ? Gravity.TOP : Gravity.BOTTOM;

            try {
                activity.addContentView(bannerAd, layout);
                bannerAd.play();
            } catch (Exception e) {
                Log.e("SuperAwesome", "Failed to add banner to Unity activity! " + e.getMessage());
            }
        }
    }

    /**
     * Method that closes a banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdClose(Context context, String unityName) {
        if (bannerAdHashMap.containsKey(unityName)) {
            // close the banner
            BannerView bannerAd = bannerAdHashMap.get(unityName);
            if (bannerAd != null) {
                bannerAd.close();
                bannerAd.setVisibility(View.GONE);
            }
        }
    }
}