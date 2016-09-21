package tv.superawesome.plugins.air;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import java.util.HashMap;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 13/09/16.
 */
public class SAAIR {

    private static HashMap<String, SABannerAd> bannerAdHashMap = new HashMap<>();

    private static void sendToAIR(FREContext context, String name, int placementId, String callback) {
        String meta = "{\"name\":\"" + name + "\", \"placementId\":" + placementId + " , \"callback\":\"" + callback + "\"}";
        if (context != null) {
            context.dispatchStatusEventAsync(meta, "");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SABannerAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SuperAwesomeAIRSABannerAdCreate implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 1) {

                try {
                    final String airName = freObjects[0].getAsString();
                    final Context context = freContext.getActivity();

                    SABannerAd bannerAd = new SABannerAd(context);
                    bannerAd.setListener(new SAInterface() {
                        @Override
                        public void onEvent(int placementId, SAEvent event) {
                            switch (event) {
                                case adLoaded: {
                                    sendToAIR(freContext, airName, placementId, "adLoaded");
                                    break;
                                }
                                case adFailedToLoad: {
                                    sendToAIR(freContext, airName, placementId, "adFailedToLoad");
                                    break;
                                }
                                case adShown: {
                                    sendToAIR(freContext, airName, placementId, "adShown");
                                    break;
                                }
                                case adFailedToShow: {
                                    sendToAIR(freContext, airName, placementId, "adFailedToShow");
                                    break;
                                }
                                case adClicked: {
                                    sendToAIR(freContext, airName, placementId, "adClicked");
                                    break;
                                }
                                case adClosed: {
                                    sendToAIR(freContext, airName, placementId, "adClosed");
                                    break;
                                }
                            }
                        }
                    });
                    bannerAdHashMap.put(airName, bannerAd);

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSABannerAdLoad implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 4) {

                try {
                    String airName = freObjects[0].getAsString();
                    int placementId = freObjects[1].getAsInt();
                    int configuration = freObjects[2].getAsInt();
                    boolean test = freObjects[3].getAsBool();

                    if (bannerAdHashMap.containsKey(airName)) {
                        SABannerAd bannerAd = bannerAdHashMap.get(airName);

                        // config
                        if (configuration == 0) {
                            bannerAd.setConfigurationProduction();
                        } else {
                            bannerAd.setConfigurationStaging();
                        }

                        // test
                        if (test) {
                            bannerAd.enableTestMode();
                        } else {
                            bannerAd.disableTestMode();
                        }

                        bannerAd.load(placementId);
                    }

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSABannerAdHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 1) {

                try {
                    String airName = freObjects[0].getAsString();

                    if (bannerAdHashMap.containsKey(airName)) {
                        SABannerAd bannerAd = bannerAdHashMap.get(airName);
                        boolean hasAdAvailable = bannerAd.hasAdAvailable();
                        return FREObject.newObject(hasAdAvailable);
                    } else {
                        return FREObject.newObject(false);
                    }
                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }

            }

            try {
                return FREObject.newObject(false);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSABannerAdPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 5) {

                try {
                    String airName = freObjects[0].getAsString();
                    boolean isParentalGateEnabled = freObjects[1].getAsBool();
                    int position = freObjects[2].getAsInt();
                    int size = freObjects[3].getAsInt();
                    int color = freObjects[4].getAsInt();
                    Activity activity = freContext.getActivity();

                    if (bannerAdHashMap.containsKey(airName)) {

                        // get banner ad
                        final SABannerAd bannerAd = bannerAdHashMap.get(airName);

                        // parental gate
                        if (isParentalGateEnabled) {
                            bannerAd.enableParentalGate();
                        } else {
                            bannerAd.disableParentalGate();
                        }

                        // color
                        if (color == 0) {
                            bannerAd.setColorTransparent();
                        } else {
                            bannerAd.setColorGray();
                        }

                        /// calc actual banner W & H
                        int width, height;
                        if      (size == 1) { width = 300; height = 50;  }
                        else if (size == 2) { width = 728; height = 90;  }
                        else if (size == 3) { width = 300; height = 250; }
                        else                { width = 320; height = 50;  }

                        // get real screen size
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
                        RelativeLayout screenLayout = new RelativeLayout(activity);
                        screenLayout.setBackgroundColor(Color.TRANSPARENT);
                        screenLayout.setGravity(position == 0 ? Gravity.TOP : Gravity.BOTTOM);
                        screenLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                        // form hierarchy
                        ViewGroup current = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
                        screenLayout.addView(bannerAd);
                        current.addView(screenLayout);

                        // finally play banner ad
                        bannerAd.play(activity);
                    }

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSABannerAdClose implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 1) {

                try {
                    String airName = freObjects[0].getAsString();

                    if (bannerAdHashMap.containsKey(airName)) {

                        SABannerAd bannerAd = bannerAdHashMap.get(airName);

                        bannerAd.close();

                        ((ViewGroup)bannerAd.getParent()).removeView(bannerAd);

                        bannerAdHashMap.remove(airName);
                    }

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAInterstitialAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SuperAwesomeAIRSAInterstitialAdCreate implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            SAInterstitialAd.setListener(new SAInterface() {
                @Override
                public void onEvent(int placementId, SAEvent event) {
                    switch (event) {
                        case adLoaded: {
                            sendToAIR(freContext, "SAInterstitialAd", placementId, "adLoaded");
                            break;
                        }
                        case adFailedToLoad: {
                            sendToAIR(freContext, "SAInterstitialAd", placementId, "adFailedToLoad");
                            break;
                        }
                        case adShown: {
                            sendToAIR(freContext, "SAInterstitialAd", placementId, "adShown");
                            break;
                        }
                        case adFailedToShow: {
                            sendToAIR(freContext, "SAInterstitialAd", placementId, "adFailedToShow");
                            break;
                        }
                        case adClicked: {
                            sendToAIR(freContext, "SAInterstitialAd", placementId, "adClicked");
                            break;
                        }
                        case adClosed: {
                            sendToAIR(freContext, "SAInterstitialAd", placementId, "adClosed");
                            break;
                        }
                    }
                }
            });

            return null;
        }
    }

    public static class SuperAwesomeAIRSAInterstitialAdLoad implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 3) {

                try {
                    int placementId = freObjects[0].getAsInt();
                    int configuration = freObjects[1].getAsInt();
                    boolean test = freObjects[2].getAsBool();

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

                    SAInterstitialAd.load(placementId, freContext.getActivity());

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSAInterstitialAdHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 1) {

                try {

                    int placementId = freObjects[0].getAsInt();

                    boolean hasAdAvailable = SAInterstitialAd.hasAdAvailable(placementId);

                    return FREObject.newObject(hasAdAvailable);

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            try {
                return FREObject.newObject(false);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSAInterstitialAdPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            if (freObjects.length == 3) {

                try {
                    int placementId = freObjects[0].getAsInt();
                    boolean isParentalGateEnabled = freObjects[1].getAsBool();
                    int orientation = freObjects[2].getAsInt();
                    Context context = freContext.getActivity();

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

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAVideoAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SuperAwesomeAIRSAVideoAdCreate implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            SAVideoAd.setListener(new SAInterface() {
                @Override
                public void onEvent(int placementId, SAEvent event) {
                    switch (event) {
                        case adLoaded: {
                            sendToAIR(freContext, "SAVideoAd", placementId, "adLoaded");
                            break;
                        }
                        case adFailedToLoad: {
                            sendToAIR(freContext, "SAVideoAd", placementId, "adFailedToLoad");
                            break;
                        }
                        case adShown: {
                            sendToAIR(freContext, "SAVideoAd", placementId, "adShown");
                            break;
                        }
                        case adFailedToShow: {
                            sendToAIR(freContext, "SAVideoAd", placementId, "adFailedToShow");
                            break;
                        }
                        case adClicked: {
                            sendToAIR(freContext, "SAVideoAd", placementId, "adClicked");
                            break;
                        }
                        case adClosed: {
                            sendToAIR(freContext, "SAVideoAd", placementId, "adClosed");
                            break;
                        }
                    }
                }
            });

            return null;
        }
    }

    public static class SuperAwesomeAIRSAVideoAdLoad implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 3) {

                try {
                    int placementId = freObjects[0].getAsInt();
                    int configuration = freObjects[1].getAsInt();
                    boolean test = freObjects[2].getAsBool();

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

                    SAVideoAd.load(placementId, freContext.getActivity());

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSAVideoAdHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 1) {

                try {
                    int placementId = freObjects[0].getAsInt();
                    boolean hasAdAvailable = SAVideoAd.hasAdAvailable(placementId);
                    return FREObject.newObject(hasAdAvailable);
                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            try {
                return FREObject.newObject(false);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSAVideoAdPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            if (freObjects.length == 6) {

                try {
                    int placementId = freObjects[0].getAsInt();
                    boolean isParentalGateEnabled = freObjects[1].getAsBool();
                    boolean shouldShowCloseButton = freObjects[2].getAsBool();
                    boolean shouldShowSmallClickButton = freObjects[3].getAsBool();
                    boolean shouldAutomaticallyCloseAtEnd = freObjects[4].getAsBool();
                    int orientation = freObjects[5].getAsInt();
                    Context context = freContext.getActivity();

                    if (isParentalGateEnabled) {
                        SAVideoAd.enableParentalGate();
                    } else {
                        SAVideoAd.disableParentalGate();
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

                    if (shouldAutomaticallyCloseAtEnd) {
                        SAVideoAd.enableCloseAtEnd();
                    } else {
                        SAVideoAd.disableCloseAtEnd();
                    }

                    if (orientation == SAOrientation.LANDSCAPE.getValue()) {
                        SAVideoAd.setOrientationLandscape();
                    } else if (orientation == SAOrientation.PORTRAIT.getValue()) {
                        SAVideoAd.setOrientationPortrait();
                    } else {
                        SAVideoAd.setOrientationAny();
                    }

                    SAVideoAd.play(placementId, context);

                } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }



}
