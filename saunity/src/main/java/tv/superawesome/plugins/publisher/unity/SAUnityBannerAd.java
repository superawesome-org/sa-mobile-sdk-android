/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;

import tv.superawesome.plugins.publisher.unity.util.SAScreenUtil;
import tv.superawesome.sdk.publisher.common.components.NumberGenerator;
import tv.superawesome.sdk.publisher.common.models.SAEvent;
import tv.superawesome.sdk.publisher.common.ui.banner.BannerView;


/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityBannerAd {

    // hash map containing banner ads
    private static final HashMap<String, BannerView> bannerAdHashMap = new HashMap<>();

    /**
     * Method that creates a new banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdCreate(Context context, final String unityName) {
        // create the banner
        BannerView bannerAd = new BannerView(context);
        NumberGenerator numberGenerator = new NumberGenerator();
        bannerAd.setId(numberGenerator.nextIntForCache());

        bannerAd.setListener((placementId, event) -> {
            switch (event) {
                case AdLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdLoaded.getValue());
                    break;
                case AdEmpty:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdEmpty.getValue());
                    break;
                case AdFailedToLoad:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdFailedToLoad.getValue());
                    break;
                case AdAlreadyLoaded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdAlreadyLoaded.getValue());
                    break;
                case AdShown:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdShown.getValue());
                    break;
                case AdFailedToShow:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdFailedToShow.getValue());
                    break;
                case AdClicked:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdClicked.getValue());
                    break;
                case AdEnded:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdEnded.getValue());
                    break;
                case AdClosed:
                    SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.AdClosed.getValue());
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
            bannerAd.load(placementId, null);
        }
    }

    /**
     * Method that checks to see if an ad is available for a banner ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySABannerAdHasAdAvailable(Context context, String unityName) {
        if (bannerAdHashMap.containsKey(unityName)) {
            BannerView bannerAd = bannerAdHashMap.get(unityName);
            return bannerAd.hasAdAvailable();
        }
        return false;
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
            bannerAd.setParentalGate(isParentalGateEnabled);
            bannerAd.setBumperPage(isBumperPageEnabled);
            bannerAd.setColor(color);

            // get screen size
            SAScreenUtil.SASize screenSize = SAScreenUtil.getRealScreenSize(activity, false);

            // get scale factor
            float factor = SAScreenUtil.getScaleFactor(activity);

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