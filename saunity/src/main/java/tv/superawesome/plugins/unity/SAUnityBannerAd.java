/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.unity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.HashMap;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

/**
 * Class that holds a number of static methods used to communicate with Unity
 */
public class SAUnityBannerAd {

    // hash map containing banne rads
    private static HashMap<String, SABannerAd> bannerAdHashMap = new HashMap<>();

    /**
     * Method that creates a new banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdCreate (Context context, final String unityName) {

        SABannerAd bannerAd = new SABannerAd(context);
        bannerAd.setId(SAUtils.randomNumberBetween(1000000, 1500000));

        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adLoaded.toString()); break;
                    case adFailedToLoad: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToLoad.toString()); break;
                    case adAlreadyLoaded: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adAlreadyLoaded.toString()); break;
                    case adShown: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adShown.toString()); break;
                    case adFailedToShow: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adFailedToShow.toString()); break;
                    case adClicked: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClicked.toString()); break;
                    case adEnded: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adEnded.toString()); break;
                    case adClosed: SAUnityCallback.sendAdCallback(unityName, placementId, SAEvent.adClosed.toString()); break;
                }
            }
        });

        bannerAdHashMap.put(unityName, bannerAd);
    }

    /**
     * Method that loads a new Banner Ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdLoad(Context context, String unityName, int placementId, int configuration, boolean test) {
        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.setConfiguration(SAConfiguration.fromValue(configuration));
            bannerAd.setTestMode(test);
            bannerAd.load(placementId);
        }
    }

    /**
     * Method that checks to see if an ad is available for a banner ad (from Unity)
     */
    public static boolean SuperAwesomeUnitySABannerAdHasAdAvailable (Context context, String unityName) {
        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            return bannerAd.hasAdAvailable();
        }
        return false;
    }

    /**
     * Method that plays a new Banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdPlay (Context context, String unityName, boolean isParentalGateEnabled, int position, int width, int height, boolean color) {

        if (bannerAdHashMap.containsKey(unityName) && !bannerAdHashMap.get(unityName).isClosed()) {

            // get activity
            Activity activity = (Activity) context;

            // get banner ad
            final SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.setParentalGate(isParentalGateEnabled);
            bannerAd.setColor(color);

            // get screen size
            SAUtils.SASize screenSize = SAUtils.getRealScreenSize(activity, false);

            // get scale factor
            float factor = SAUtils.getScaleFactor(activity);

            // scale it according to the factor
            int scaledWidth = (int)(factor * width);
            int scaledHeight = (int)(factor * height);

            // make sure it's not bigger than the screen
            if (scaledWidth > screenSize.width) {
                scaledHeight = (screenSize.width * scaledHeight) / scaledWidth;
            }

            // but not bigger than 15% of the screen's height
            if (scaledHeight > 0.15 * screenSize.height) {
                scaledHeight = (int)(0.15 * screenSize.height);
            }

            bannerAd.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, scaledHeight));

            // create a relative layout as big as the screen
            RelativeLayout screenLayout = new RelativeLayout(context);
            screenLayout.setBackgroundColor(Color.TRANSPARENT);
            screenLayout.setGravity(position == 0 ? Gravity.TOP : Gravity.BOTTOM);
            screenLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // form hierarchy
            ViewGroup current = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            screenLayout.addView(bannerAd);
            current.addView(screenLayout);

            // finally play banner ad
            bannerAd.play(context);
        }
    }

    /**
     * Method that closes a banner ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdClose (Context context, String unityName) {
        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.close();
            ViewGroup parent = (ViewGroup) bannerAd.getParent();
            if (parent != null) parent.removeView(bannerAd);
            // bannerAdHashMap.remove(unityName);
        }
    }
}
