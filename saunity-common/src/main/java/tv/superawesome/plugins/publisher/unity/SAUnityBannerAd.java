package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tv.superawesome.plugins.publisher.unity.util.SAJsonUtil;
import tv.superawesome.plugins.publisher.unity.util.SAScreenUtil;
import tv.superawesome.sdk.publisher.models.SAEvent;
import tv.superawesome.sdk.publisher.ui.banner.BannerView;

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
        BannerView bannerAd = new BannerView(context);
        bannerAd.setId(View.generateViewId());

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
    public static void SuperAwesomeUnitySABannerAdLoad(Context context,
                                                       String unityName,
                                                       int placementId,
                                                       int configuration,
                                                       boolean test,
                                                       String encodedOptions) {
        SuperAwesomeUnitySABannerAdLoad(
                context,
                unityName,
                placementId,
                configuration,
                test,
                encodedOptions,
                null
        );

    }
    /**
     * Method that loads a new Banner Ad (from Unity)
     */
    public static void SuperAwesomeUnitySABannerAdLoad(Context context,
                                                       String unityName,
                                                       int placementId,
                                                       int configuration,
                                                       boolean test,
                                                       String openRtbPartnerId,
                                                       String encodedOptions) {
        if (bannerAdHashMap.containsKey(unityName)) {
            BannerView bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.setTestMode(test);

            if (encodedOptions != null && !encodedOptions.isEmpty()) {
                try {
                    bannerAd.load(
                            placementId,
                            openRtbPartnerId,
                            SAJsonUtil.JSONtoMap(new JSONObject(encodedOptions))
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Fallback to loading without options
                    bannerAd.load(placementId, openRtbPartnerId, null);
                }
            } else {
                bannerAd.load(placementId, openRtbPartnerId, null);
            }
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
            BannerView bannerAd = bannerAdHashMap.get(unityName);
            if (bannerAd != null) {
                bannerAd.close();
                bannerAd.setVisibility(View.GONE);
            }
        }
    }
}