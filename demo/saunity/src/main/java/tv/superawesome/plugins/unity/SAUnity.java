package tv.superawesome.plugins.unity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAGameWall;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 12/09/16.
 */
public class SAUnity {

    private static HashMap<String, SABannerAd> bannerAdHashMap = new HashMap<>();

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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SABannerAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void SuperAwesomeUnitySABannerAdCreate (Context context, final String unityName) {

        SABannerAd bannerAd = new SABannerAd(context);

        bannerAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        sendToUnity(unityName, placementId, "adLoaded");
                        break;
                    }
                    case adFailedToLoad: {
                        sendToUnity(unityName, placementId, "adFailedToLoad");
                        break;
                    }
                    case adShown: {
                        sendToUnity(unityName, placementId, "adShown");
                        break;
                    }
                    case adFailedToShow: {
                        sendToUnity(unityName, placementId, "adFailedToShow");
                        break;
                    }
                    case adClicked: {
                        sendToUnity(unityName, placementId, "adClicked");
                        break;
                    }
                    case adClosed: {
                        sendToUnity(unityName, placementId, "adClosed");
                        break;
                    }
                }
            }
        });

        bannerAdHashMap.put(unityName, bannerAd);
    }

    public static void SuperAwesomeUnitySABannerAdLoad(Context context, String unityName, int placementId, int configuration, boolean test) {

        if (bannerAdHashMap.containsKey(unityName)) {

            SABannerAd bannerAd = bannerAdHashMap.get(unityName);

            if (configuration == 0) {
                bannerAd.setConfigurationProduction();
            } else {
                bannerAd.setConfigurationStaging();
            }

            if (test) {
                bannerAd.enableTestMode();
            } else {
                bannerAd.disableTestMode();
            }

            bannerAd.load(placementId);
        }
    }

    public static boolean SuperAwesomeUnitySABannerAdHasAdAvailable (Context context, String unityName) {

        if (bannerAdHashMap.containsKey(unityName)) {

            SABannerAd bannerAd = bannerAdHashMap.get(unityName);

            return bannerAd.hasAdAvailable();
        }
        return false;
    }

    public static void SuperAwesomeUnitySABannerAdPlay (Context context, String unityName, boolean isParentalGateEnabled, int position, int size, int color) {

        if (bannerAdHashMap.containsKey(unityName)) {

            // get activity
            Activity activity = (Activity) context;

            // get banner ad
            final SABannerAd bannerAd = bannerAdHashMap.get(unityName);

            if (isParentalGateEnabled) {
                bannerAd.enableParentalGate();
            } else {
                bannerAd.disableParentalGate();
            }

            if (color == 0) {
                bannerAd.setColorTransparent();
            } else {
                bannerAd.setColorGray();
            }

            // calc actual banner W & H
            int width, height;

            if (size == 1) {
                width = 300;
                height = 50;
            }
            else if (size == 2) {
                width = 728;
                height = 90;
            }
            else if (size == 3) {
                width = 300;
                height = 250;
            }
            else {
                width = 320;
                height = 50;
            }

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

    public static void SuperAwesomeUnitySABannerAdClose (Context context, String unityName) {

        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.close();
            ((ViewGroup)bannerAd.getParent()).removeView(bannerAd);
            bannerAdHashMap.remove(unityName);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAInterstitialAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void SuperAwesomeUnitySAInterstitialAdCreate (Context context) {

        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        sendToUnity("SAInterstitialAd", placementId, "adLoaded");
                        break;
                    }
                    case adFailedToLoad: {
                        sendToUnity("SAInterstitialAd", placementId, "adFailedToLoad");
                        break;
                    }
                    case adShown: {
                        sendToUnity("SAInterstitialAd", placementId, "adShown");
                        break;
                    }
                    case adFailedToShow: {
                        sendToUnity("SAInterstitialAd", placementId, "adFailedToShow");
                        break;
                    }
                    case adClicked: {
                        sendToUnity("SAInterstitialAd", placementId, "adClicked");
                        break;
                    }
                    case adClosed: {
                        sendToUnity("SAInterstitialAd", placementId, "adClosed");
                        break;
                    }
                }
            }
        });

    }

    public static void SuperAwesomeUnitySAInterstitialAdLoad (Context context, int placementId, int configuration, boolean test) {

        if (test) {
            SAInterstitialAd.enableTestMode();
        } else {
            SAInterstitialAd.disableTestMode();
        }

        if (configuration == 0) {
            SAInterstitialAd.setConfigurationProduction();
        } else {
            SAInterstitialAd.setConfigurationStaging();
        }

        SAInterstitialAd.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable (Context context, int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAInterstitialAdPlay (Context context, int placementId, boolean isParentalGateEnabled, int orientation) {

        if (isParentalGateEnabled) {
            SAInterstitialAd.enableParentalGate();
        } else {
            SAInterstitialAd.disableParentalGate();
        }

        if (orientation == SAOrientation.LANDSCAPE.getValue()) {
            SAInterstitialAd.setOrientationLandscape();
        } else if (orientation == SAOrientation.PORTRAIT.getValue()) {
            SAInterstitialAd.setOrientationPortrait();
        } else {
            SAInterstitialAd.setOrientationAny();
        }

        SAInterstitialAd.play(placementId, context);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAVideoAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void SuperAwesomeUnitySAVideoAdCreate (Context context) {

        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        sendToUnity("SAVideoAd", placementId, "adLoaded");
                        break;
                    }
                    case adFailedToLoad: {
                        sendToUnity("SAVideoAd", placementId, "adFailedToShow");
                        break;
                    }
                    case adShown: {
                        sendToUnity("SAVideoAd", placementId, "adShown");
                        break;
                    }
                    case adFailedToShow: {
                        sendToUnity("SAVideoAd", placementId, "adFailedToShow");
                        break;
                    }
                    case adClicked: {
                        sendToUnity("SAVideoAd", placementId, "adClicked");
                        break;
                    }
                    case adClosed: {
                        sendToUnity("SAVideoAd", placementId, "adClosed");
                        break;
                    }
                }
            }
        });
    }

    public static void SuperAwesomeUnitySAVideoAdLoad(Context context, int placementId, int configuration, boolean test) {

        if (test) {
            SAVideoAd.enableTestMode();
        } else {
            SAVideoAd.disableTestMode();
        }

        if (configuration == 0) {
            SAVideoAd.setConfigurationProduction();
        } else {
            SAVideoAd.setConfigurationStaging();
        }

        SAVideoAd.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable (Context context, int placementId) {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAVideoAdPlay (Context context, int placementId, boolean isParentalGateEnabled, boolean shouldShowCloseButton, boolean shouldShowSmallClickButton, boolean shouldAutomaticallyCloseAtEnd, int orientation) {

        if (isParentalGateEnabled) {
            SAVideoAd.enableParentalGate();
        } else {
            SAVideoAd.disableParentalGate();
        }

        if (shouldAutomaticallyCloseAtEnd) {
            SAVideoAd.enableCloseAtEnd();
        } else {
            SAVideoAd.disableCloseAtEnd();
        }

        if (shouldShowCloseButton) {
            SAVideoAd.enableCloseButton();
        } else {
            SAVideoAd.disableCloseButton();
        }

        if (shouldShowSmallClickButton) {
            SAVideoAd.enableSmallClickButton();
        } else {
            SAVideoAd.disableSmallClickButton();
        }

        if (orientation == SAOrientation.LANDSCAPE.getValue()) {
            SAVideoAd.setOrientationLandscape();
        } else if (orientation == SAOrientation.PORTRAIT.getValue()) {
            SAVideoAd.setOrientationPortrait();
        } else {
            SAVideoAd.setOrientationAny();
        }

        SAVideoAd.play(placementId, context);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAGameWall interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void SuperAwesomeUnitySAGameWallCreate (Context context) {

        SAGameWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: {
                        sendToUnity("SAGameWall", placementId, "adLoaded");
                        break;
                    }
                    case adFailedToLoad: {
                        sendToUnity("SAGameWall", placementId, "adFailedToLoad");
                        break;
                    }
                    case adShown: {
                        sendToUnity("SAGameWall", placementId, "adShown");
                        break;
                    }
                    case adFailedToShow: {
                        sendToUnity("SAGameWall", placementId, "adFailedToShow");
                        break;
                    }
                    case adClicked: {
                        sendToUnity("SAGameWall", placementId, "adClicked");
                        break;
                    }
                    case adClosed: {
                        sendToUnity("SAGameWall", placementId, "adClosed");
                        break;
                    }
                }
            }
        });

    }

    public static void SuperAwesomeUnitySAGameWallLoad (Context context, int placementId, int configuration, boolean test) {

        if (test) {
            SAGameWall.enableTestMode();
        } else {
            SAGameWall.disableTestMode();
        }

        if (configuration == 0) {
            SAGameWall.setConfigurationProduction();
        } else {
            SAGameWall.setConfigurationStaging();
        }

        SAGameWall.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAGameWallHasAdAvailable (Context context, int placementId) {
        return SAGameWall.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAGameWallPlay (Context context, int placementId, boolean isParentalGateEnabled) {

        if (isParentalGateEnabled) {
            SAGameWall.enableParentalGate();
        } else {
            SAGameWall.disableParentalGate();
        }

        SAGameWall.play(placementId, context);
    }
}
