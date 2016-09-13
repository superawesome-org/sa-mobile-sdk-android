package tv.superawesome.plugins.unity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 12/09/16.
 */
public class SAUnity {

    private static HashMap<String, SABannerAd> bannerAdHashMap = new HashMap<>();

    /**
     * Send payload to Unity
     * @param unityAd the name of the unity ad
     * @param placementId the placement Id
     * @param callback the callback name
     */
    public static void sendToUnity(String unityAd, int placementId, String callback) {

        // don't do anything if class is not available
        if (!SAUtils.isClassAvailable("com.unity3d.player.UnityPlayer")) return;

        // form the payload
        String payload = "{ \"placementId\":\"" + placementId + "\", \"type\":\"sacallback_"+callback+"\"}";

        // try to call the unity player
        try {
            Class<?> unity = Class.forName("com.unity3d.player.UnityPlayer");
            java.lang.reflect.Method method = unity.getMethod("UnitySendMessage", String.class, String.class, String.class);
            method.invoke(unity, unityAd, "nativeCallback", payload);
        } catch (ClassNotFoundException e) {
            // failure
        } catch (NoSuchMethodException e) {
            // failure
        } catch (InvocationTargetException e) {
            // failure
        } catch (IllegalAccessException e) {
            // failure;
        }
    }

    /**
     * Create a new banner, add a listener to it and add it to the hash map
     * @param context current context
     * @param unityName unity name of the object
     */
    public static void SuperAwesomeUnitySABannerAdCreate (Context context,
                                                          final String unityName) {

        SuperAwesome.getInstance().setApplicationContext(context);

        SABannerAd bannerAd = new SABannerAd(context);
        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: sendToUnity(unityName, placementId, "adLoaded"); break;
                    case adFailedToLoad: sendToUnity(unityName, placementId, "adFailedToLoad"); break;
                    case adShown: sendToUnity(unityName, placementId, "adShown"); break;
                    case adFailedToShow: sendToUnity(unityName, placementId, "adFailedToShow"); break;
                    case adClicked: sendToUnity(unityName, placementId, "adClicked"); break;
                    case adClosed: sendToUnity(unityName, placementId, "adClosed"); break;
                }
            }
        });
        bannerAdHashMap.put(unityName, bannerAd);
    }

    /**
     * Load an ad for a banner
     * @param context current context
     * @param unityName unity name of the banner
     * @param placementId placement to load the ad for
     * @param configuration production = 0 / staging = 1
     * @param test true / false
     */
    public static void SuperAwesomeUnitySABannerAdLoad(Context context,
                                                       String unityName,
                                                       int placementId,
                                                       int configuration,
                                                       boolean test) {

        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            switch (configuration) {
                case 0: bannerAd.setConfigurationProduction(); break;
                case 1: bannerAd.setConfigurationStaging(); break;
                default: bannerAd.setConfigurationStaging();
            }
            bannerAd.setTest(test);
            bannerAd.load(placementId);
        } else {
            // handle failure
        }
    }

    /**
     * Check if the banner has an ad available
     * @param context the current context
     * @param unityName the unity name of the banner
     * @return true / false
     */
    public static boolean SuperAwesomeUnitySABannerAdHasAdAvailable (Context context,
                                                                     String unityName) {

        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            return bannerAd.hasAdAvailable();
        }
        return false;
    }

    /**
     * Play a banner ad
     * @param context current context
     * @param unityName unity name of the ad
     * @param position TOP = 0 / BOTTOM = 1
     * @param size BANNER_320_50 = 0 / BANNER_300_50 = 1 / BANNER_728_90 = 2 / BANNER_300_250 = 3
     * @param color TRANSPARENT = 0 / GRAY = 1
     */
    public static void SuperAwesomeUnitySABannerAdPlay (Context context,
                                                        String unityName,
                                                        boolean isParentalGateEnabled,
                                                        int position,
                                                        int size,
                                                        int color) {
        if (bannerAdHashMap.containsKey(unityName)) {
            // get activity
            Activity activity = (Activity) context;

            // get banner ad
            final SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.setIsParentalGateEnabled(isParentalGateEnabled);
            bannerAd.setBackgroundColor(color == 0 ? Color.TRANSPARENT : Color.rgb(191, 191, 191));

            // get actual sizes for the banner
            Display getOrient = ((Activity)context).getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            getOrient.getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            int smallDimension = (width > height ? height : width);
            final int bigDimension = (width < height ? height : width);

            SAUtils.SASize screenSize = SAUtils.getRealScreenSize(activity, false);

            float factor = SAUtils.getScaleFactor(activity);

            /// calc actual banner W & H
            int bwidth = 0, bheight = 0;
            if      (size == 1) { bwidth = 300; bheight = 50;  }
            else if (size == 2) { bwidth = 728; bheight = 90;  }
            else if (size == 3) { bwidth = 300; bheight = 250; }
            else                { bwidth = 320; bheight = 50;  }

            // scale it according to the factor
            int scaledWidth = (int)(factor * bwidth);
            int scaledHeight = (int)(factor * bheight);

            // make sure it's not bigger than the screen
            if (scaledWidth > screenSize.width) {
                scaledHeight = (screenSize.width * scaledHeight) / scaledWidth;
            }

            // but not bigger than 15% of the screen's height
            if (scaledHeight > 0.15 * screenSize.height) {
                scaledHeight = (int)(0.15 * screenSize.height);
            }

            bannerAd.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, scaledHeight));

            bannerAd.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    if (bottom != oldBottom || right != oldRight) {
                        bannerAd.resize(right, bottom);
                    }
                }
            });

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
        } else {
            // handled failure
        }
    }

    /**
     * Remove a banner
     * @param context current context
     * @param unityName unity name of the ad
     */
    public static void SuperAwesomeUnitySABannerAdClose (Context context,
                                                         String unityName) {

        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.close();
            ((ViewGroup)bannerAd.getParent()).removeView(bannerAd);
            bannerAdHashMap.remove(unityName);
        }
    }

    /**
     * @param context the current context
     * Add a listener to an interstitial ad
     */
    public static void SuperAwesomeUnitySAInterstitialAdCreate (Context context) {

        SuperAwesome.getInstance().setApplicationContext(context);

        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: sendToUnity("SAInterstitialAd", placementId, "adLoaded"); break;
                    case adFailedToLoad: sendToUnity("SAInterstitialAd", placementId, "adFailedToLoad"); break;
                    case adShown: sendToUnity("SAInterstitialAd", placementId, "adShown"); break;
                    case adFailedToShow: sendToUnity("SAInterstitialAd", placementId, "adFailedToShow"); break;
                    case adClicked: sendToUnity("SAInterstitialAd", placementId, "adClicked"); break;
                    case adClosed: sendToUnity("SAInterstitialAd", placementId, "adClosed"); break;
                }
            }
        });

    }

    /**
     * Load an interstitial ad
     * @param context the current context
     * @param placementId placement id to load ad for
     * @param configuration production = 0 / staging = 1
     * @param test true / false
     */
    public static void SuperAwesomeUnitySAInterstitialAdLoad (Context context,
                                                              int placementId,
                                                              int configuration,
                                                              boolean test) {
        SAInterstitialAd.setTest(test);
        switch (configuration) {
            case 0: SAInterstitialAd.setConfigurationProduction(); break;
            case 1: SAInterstitialAd.setConfigurationStaging(); break;
            default: SAInterstitialAd.setConfigurationProduction(); break;
        }
        SAInterstitialAd.load(placementId);
    }

    /**
     * Check to see of the interstitial ad has a valid ad loaded
     * @param context the current context
     * @param placementId the placement id to check for availability
     * @return true / false
     */
    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable (Context context,
                                                                           int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
    }

    /**
     * Play an interstitial ad
     * @param context the current context
     * @param placementId the placement id to play the ad for
     * @param isParentalGateEnabled true / false
     * @param shouldLockOrientation true / false
     * @param lockOrientation ANY = 0 / PORTRAIT = 1 / LANDSCAPE = 2
     */
    public static void SuperAwesomeUnitySAInterstitialAdPlay (Context context,
                                                              int placementId,
                                                              boolean isParentalGateEnabled,
                                                              boolean shouldLockOrientation,
                                                              int lockOrientation) {
        SAInterstitialAd.setIsParentalGateEnabled(isParentalGateEnabled);
        SAInterstitialAd.setShouldLockOrientation(shouldLockOrientation);
        int orientation;
        switch (lockOrientation) {
            case 0: orientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED; break;
            case 1: orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; break;
            case 2: orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; break;
            default: orientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED; break;
        }
        SAInterstitialAd.setLockOrientation(orientation);
        SAInterstitialAd.play(placementId, context);
    }

    /**
     * Add a listener to the video ad
     * @param context the current context
     */
    public static void SuperAwesomeUnitySAVideoAdCreate (Context context) {

        SuperAwesome.getInstance().setApplicationContext(context);

        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: sendToUnity("SAVideoAd", placementId, "adLoaded"); break;
                    case adFailedToLoad: sendToUnity("SAVideoAd", placementId, "adFailedToShow"); break;
                    case adShown: sendToUnity("SAVideoAd", placementId, "adShown"); break;
                    case adFailedToShow: sendToUnity("SAVideoAd", placementId, "adFailedToShow"); break;
                    case adClicked: sendToUnity("SAVideoAd", placementId, "adClicked"); break;
                    case adClosed: sendToUnity("SAVideoAd", placementId, "adClosed"); break;
                }
            }
        });
    }

    /**
     * Load a video ad
     * @param context the current context
     * @param placementId the id to load the ad for
     * @param configuration production = 0 / staging = 1
     * @param test true / false
     */
    public static void SuperAwesomeUnitySAVideoAdLoad(Context context,
                                                      int placementId,
                                                      int configuration,
                                                      boolean test) {
        SAVideoAd.setTest(test);
        switch (configuration) {
            case 0: SAVideoAd.setConfigurationProduction(); break;
            case 1: SAVideoAd.setConfigurationStaging(); break;
            default: SAVideoAd.setConfigurationProduction(); break;
        }
        SAVideoAd.load(placementId);
    }

    /**
     * Check if the video ad has an ad present
     * @param context the current context
     * @param placementId the placement id to check for availability
     * @return true / false
     */
    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable (Context context,
                                                                    int placementId) {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    /**
     * Play a video ad
     * @param context the current context
     * @param placementId the placement id to play
     * @param isParentalGateEnabled true / false
     * @param shouldShowCloseButton true /false
     * @param shouldShowSmallClickButton true /false
     * @param shouldAutomaticallyCloseAtEnd true / false
     * @param shouldLockOrientation true / false
     * @param lockOrientation ANY = 0 / PORTRAIT = 1 / LANDSCAPE = 2
     */
    public static void SuperAwesomeUnitySAVideoAdPlay (Context context,
                                                       int placementId,
                                                       boolean isParentalGateEnabled,
                                                       boolean shouldShowCloseButton,
                                                       boolean shouldShowSmallClickButton,
                                                       boolean shouldAutomaticallyCloseAtEnd,
                                                       boolean shouldLockOrientation,
                                                       int lockOrientation) {
        SAVideoAd.setIsParentalGateEnabled(isParentalGateEnabled);
        SAVideoAd.setShouldShowCloseButton(shouldShowCloseButton);
        SAVideoAd.setShouldShowSmallClickButton(shouldShowSmallClickButton);
        SAVideoAd.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setShouldLockOrientation(shouldLockOrientation);
        int orientation;
        switch (lockOrientation) {
            case 0: orientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED; break;
            case 1: orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; break;
            case 2: orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; break;
            default: orientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED; break;
        }
        SAVideoAd.setLockOrientation(orientation);
        SAVideoAd.play(placementId, context);
    }
}
