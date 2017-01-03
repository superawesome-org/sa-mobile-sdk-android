package tv.superawesome.plugins.unity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

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

    public static void SuperAwesomeUnitySABannerAdLoad(Context context, String unityName, int placementId, int configuration, boolean test) {
        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.setConfiguration(SAConfiguration.fromValue(configuration));
            bannerAd.setTestMode(test);
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

    public static void SuperAwesomeUnitySABannerAdPlay (Context context, String unityName, boolean isParentalGateEnabled, int position, int width, int height, boolean color) {

        if (bannerAdHashMap.containsKey(unityName)) {

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

    public static void SuperAwesomeUnitySABannerAdClose (Context context, String unityName) {
        if (bannerAdHashMap.containsKey(unityName)) {
            SABannerAd bannerAd = bannerAdHashMap.get(unityName);
            bannerAd.close();
            ViewGroup parent = (ViewGroup) bannerAd.getParent();
            if (parent != null) parent.removeView(bannerAd);
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
                    case adLoaded: sendToUnity("SAInterstitialAd", placementId, "adLoaded"); break;
                    case adFailedToLoad: sendToUnity("SAInterstitialAd", placementId, "adFailedToLoad"); break;
                    case adShown: sendToUnity("SAInterstitialAd", placementId, "adShown"); break;
                    case adFailedToShow: sendToUnity("SAInterstitialAd", placementId, "adFailedToShow"); break;
                    case adClicked: sendToUnity("SAInterstitialAd", placementId, "adClicked"); break;
                    case adClosed: sendToUnity("SAInterstitialAd", placementId, "adClosed");break;
                }
            }
        });

    }

    public static void SuperAwesomeUnitySAInterstitialAdLoad (Context context, int placementId, int configuration, boolean test) {
        SAInterstitialAd.setTestMode(test);
        SAInterstitialAd.setConfiguration(SAConfiguration.fromValue(configuration));
        SAInterstitialAd.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAInterstitialAdHasAdAvailable (Context context, int placementId) {
        return SAInterstitialAd.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAInterstitialAdPlay (Context context, int placementId, boolean isParentalGateEnabled, int orientation, boolean isBackButtonEnabled) {
        SAInterstitialAd.setParentalGate(isParentalGateEnabled);
        SAInterstitialAd.setOrientation(SAOrientation.fromValue(orientation));
        SAInterstitialAd.setBackButton(isBackButtonEnabled);
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

    public static void SuperAwesomeUnitySAVideoAdLoad(Context context, int placementId, int configuration, boolean test) {
        SAVideoAd.setTestMode(test);
        SAVideoAd.setConfiguration(SAConfiguration.fromValue(configuration));
        SAVideoAd.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAVideoAdHasAdAvailable (Context context, int placementId) {
        return SAVideoAd.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAVideoAdPlay (Context context, int placementId, boolean isParentalGateEnabled, boolean shouldShowCloseButton, boolean shouldShowSmallClickButton, boolean shouldAutomaticallyCloseAtEnd, int orientation, boolean isBackButtonEnabled) {
        SAVideoAd.setParentalGate(isParentalGateEnabled);
        SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
        SAVideoAd.setCloseButton(shouldShowCloseButton);
        SAVideoAd.setSmallClick(shouldShowSmallClickButton);
        SAVideoAd.setBackButton(isBackButtonEnabled);
        SAVideoAd.setOrientation(SAOrientation.fromValue(orientation));
        SAVideoAd.play(placementId, context);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAAppWall interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void SuperAwesomeUnitySAAppWallCreate (Context context) {
        SAAppWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {
                switch (event) {
                    case adLoaded: sendToUnity("SAAppWall", placementId, "adLoaded"); break;
                    case adFailedToLoad: sendToUnity("SAAppWall", placementId, "adFailedToLoad"); break;
                    case adShown: sendToUnity("SAAppWall", placementId, "adShown"); break;
                    case adFailedToShow: sendToUnity("SAAppWall", placementId, "adFailedToShow"); break;
                    case adClicked: sendToUnity("SAAppWall", placementId, "adClicked"); break;
                    case adClosed: sendToUnity("SAAppWall", placementId, "adClosed"); break;
                }
            }
        });
    }

    public static void SuperAwesomeUnitySAAppWallLoad (Context context, int placementId, int configuration, boolean test) {
        SAAppWall.setTestMode(test);
        SAAppWall.setConfiguration(SAConfiguration.fromValue(configuration));
        SAAppWall.load(placementId, context);
    }

    public static boolean SuperAwesomeUnitySAAppWallHasAdAvailable (Context context, int placementId) {
        return SAAppWall.hasAdAvailable(placementId);
    }

    public static void SuperAwesomeUnitySAAppWallPlay (Context context, int placementId, boolean isParentalGateEnabled, boolean isBackButtonEnabled) {
        SAAppWall.setParentalGate(isParentalGateEnabled);
        SAAppWall.setBackButton(isBackButtonEnabled);
        SAAppWall.play(placementId, context);
    }
}
