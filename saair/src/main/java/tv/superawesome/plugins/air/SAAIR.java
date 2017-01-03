package tv.superawesome.plugins.air;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

public class SAAIR {

    private static HashMap<String, SABannerAd> bannerAdHashMap = new HashMap<>();

    private static void sendToAIR(FREContext context, String name, int placementId, String callback) {
        String meta = "{\"name\":\"" + name + "\", \"placementId\":" + placementId + " , \"callback\":\"" + callback + "\"}";
        if (context != null) {
            context.dispatchStatusEventAsync(meta, "");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Stub for CPI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SuperAwesomeAIRSuperAwesomeHandleCPI implements FREFunction {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SABannerAd interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SuperAwesomeAIRSABannerAdCreate implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            Context context = freContext.getActivity();
            String airName = null;

            try {
                airName = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            final String airName2 = airName;

            if (airName2 != null) {
                SABannerAd bannerAd = new SABannerAd(context);
                bannerAd.setListener(new SAInterface() {
                    @Override
                    public void onEvent(int placementId, SAEvent event) {
                        switch (event) {
                            case adLoaded: {
                                sendToAIR(freContext, airName2, placementId, "adLoaded");
                                break;
                            }
                            case adFailedToLoad: {
                                sendToAIR(freContext, airName2, placementId, "adFailedToLoad");
                                break;
                            }
                            case adShown: {
                                sendToAIR(freContext, airName2, placementId, "adShown");
                                break;
                            }
                            case adFailedToShow: {
                                sendToAIR(freContext, airName2, placementId, "adFailedToShow");
                                break;
                            }
                            case adClicked: {
                                sendToAIR(freContext, airName2, placementId, "adClicked");
                                break;
                            }
                            case adClosed: {
                                sendToAIR(freContext, airName2, placementId, "adClosed");
                                break;
                            }
                        }
                    }
                });
                bannerAdHashMap.put(airName2, bannerAd);
            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSABannerAdLoad implements FREFunction {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            String airName = null;
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            int configuration = SuperAwesome.getInstance().defaultConfiguration().ordinal();
            boolean test = SuperAwesome.getInstance().defaultTestMode();

            try {
                airName = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                placementId = freObjects[1].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                configuration = freObjects[2].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                test = freObjects[3].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (airName != null && bannerAdHashMap.containsKey(airName)) {
                SABannerAd bannerAd = bannerAdHashMap.get(airName);
                bannerAd.setConfiguration(SAConfiguration.fromValue(configuration));
                bannerAd.setTestMode(test);
                bannerAd.load(placementId);
            }

            return null;
        }
    }

    public static class SuperAwesomeAIRSABannerAdHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            String airName = null;
            boolean hasAdAvailable = false;

            try {
                airName = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (airName != null && bannerAdHashMap.containsKey(airName)) {
                SABannerAd bannerAd = bannerAdHashMap.get(airName);
                hasAdAvailable = bannerAd.hasAdAvailable();
            }

            try {
                return FREObject.newObject(hasAdAvailable);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSABannerAdPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            // default values
            Activity activity = freContext.getActivity();
            String airName = null;
            int position = 0;
            int width = 320;
            int height = 50;
            boolean isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
            boolean color = SuperAwesome.getInstance().defaultBgColor();

            try {
                airName = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isParentalGateEnabled = freObjects[1].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                position = freObjects[2].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                width = freObjects[3].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                height = freObjects[4].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                color = freObjects[5].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (airName != null && bannerAdHashMap.containsKey(airName)) {

                // get banner ad
                final SABannerAd bannerAd = bannerAdHashMap.get(airName);

                // customize
                bannerAd.setParentalGate(isParentalGateEnabled);
                bannerAd.setColor(color);

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

            return null;
        }
    }

    public static class SuperAwesomeAIRSABannerAdClose implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            String airName = null;

            try {
                airName = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (airName != null && bannerAdHashMap.containsKey(airName)) {
                SABannerAd bannerAd = bannerAdHashMap.get(airName);
                bannerAd.close();
                ((ViewGroup)bannerAd.getParent()).removeView(bannerAd);
                bannerAdHashMap.remove(airName);
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

            Context context = freContext.getActivity();
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            int configuration = SuperAwesome.getInstance().defaultConfiguration().ordinal();
            boolean test = SuperAwesome.getInstance().defaultTestMode();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }
            try {
                configuration = freObjects[1].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                test = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // configure & load
            SAInterstitialAd.setTestMode(test);
            SAInterstitialAd.setConfiguration(SAConfiguration.fromValue(configuration));
            SAInterstitialAd.load(placementId, context);

            return null;
        }
    }

    public static class SuperAwesomeAIRSAInterstitialAdHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            int placementId = SuperAwesome.getInstance().defaultPlacementId();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            boolean hasAdAvailable = SAInterstitialAd.hasAdAvailable(placementId);

            try {
                return FREObject.newObject(hasAdAvailable);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSAInterstitialAdPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            Context context = freContext.getActivity();
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            boolean isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
            int orientation = SuperAwesome.getInstance().defaultOrientation().ordinal();
            boolean isBackButtonEnabled = SuperAwesome.getInstance().defaultBackButton();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isParentalGateEnabled = freObjects[1].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                orientation = freObjects[2].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isBackButtonEnabled = freObjects[3].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // config & play
            SAInterstitialAd.setParentalGate(isParentalGateEnabled);
            SAInterstitialAd.setOrientation(SAOrientation.fromValue(orientation));
            SAInterstitialAd.setBackButton(isBackButtonEnabled);
            SAInterstitialAd.play(placementId, context);

            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AIR to SAVideoAd interface
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

            Context context = freContext.getActivity();
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            int configuration = SuperAwesome.getInstance().defaultConfiguration().ordinal();
            boolean test = SuperAwesome.getInstance().defaultTestMode();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                configuration = freObjects[1].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                test = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // configuraton & load
            SAVideoAd.setConfiguration(SAConfiguration.fromValue(configuration));
            SAVideoAd.setTestMode(test);
            SAVideoAd.load(placementId, context);

            return null;
        }
    }

    public static class SuperAwesomeAIRSAVideoAdHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            int placementId = SuperAwesome.getInstance().defaultPlacementId();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            boolean hasAdAvailable = SAVideoAd.hasAdAvailable(placementId);

            try {
                return FREObject.newObject(hasAdAvailable);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSAVideoAdPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            Context context = freContext.getActivity();
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            boolean isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
            boolean shouldShowCloseButton = SuperAwesome.getInstance().defaultCloseButton();
            boolean shouldShowSmallClickButton = SuperAwesome.getInstance().defaultSmallClick();
            boolean shouldAutomaticallyCloseAtEnd = SuperAwesome.getInstance().defaultCloseAtEnd();
            int orientation = SuperAwesome.getInstance().defaultOrientation().ordinal();
            boolean isBackButtonEnabled = SuperAwesome.getInstance().defaultBackButton();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isParentalGateEnabled = freObjects[1].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                shouldShowCloseButton = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                shouldShowSmallClickButton = freObjects[3].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                shouldAutomaticallyCloseAtEnd = freObjects[4].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                orientation = freObjects[5].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isBackButtonEnabled = freObjects[6].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // configure & play
            SAVideoAd.setParentalGate(isParentalGateEnabled);
            SAVideoAd.setCloseButton(shouldShowCloseButton);
            SAVideoAd.setSmallClick(shouldShowSmallClickButton);
            SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
            SAVideoAd.setOrientation(SAOrientation.fromValue(orientation));
            SAVideoAd.setBackButton(isBackButtonEnabled);
            SAVideoAd.play(placementId, context);

            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Unity to SAAppWall interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SuperAwesomeAIRSAAppWallCreate implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            SAAppWall.setListener(new SAInterface() {
                @Override
                public void onEvent(int placementId, SAEvent event) {
                    switch (event) {
                        case adLoaded: {
                            sendToAIR(freContext, "SAAppWall", placementId, "adLoaded");
                            break;
                        }
                        case adFailedToLoad: {
                            sendToAIR(freContext, "SAAppWall", placementId, "adFailedToLoad");
                            break;
                        }
                        case adShown: {
                            sendToAIR(freContext, "SAAppWall", placementId, "adShown");
                            break;
                        }
                        case adFailedToShow: {
                            sendToAIR(freContext, "SAAppWall", placementId, "adFailedToShow");
                            break;
                        }
                        case adClicked: {
                            sendToAIR(freContext, "SAAppWall", placementId, "adClicked");
                            break;
                        }
                        case adClosed: {
                            sendToAIR(freContext, "SAAppWall", placementId, "adClosed");
                            break;
                        }
                    }
                }
            });

            return null;
        }
    }

    public static class SuperAwesomeAIRSAAppWallLoad implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            Context context = freContext.getActivity();
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            int configuration = SuperAwesome.getInstance().defaultConfiguration().ordinal();
            boolean test = SuperAwesome.getInstance().defaultTestMode();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                configuration = freObjects[1].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                test = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // configure & load
            SAAppWall.setConfiguration(SAConfiguration.fromValue(configuration));
            SAAppWall.setTestMode(test);
            SAAppWall.load(placementId, context);

            return null;
        }
    }

    public static class SuperAwesomeAIRSAAppWallHasAdAvailable implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            int placementId = SuperAwesome.getInstance().defaultPlacementId();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            boolean hasAdAvailable = SAAppWall.hasAdAvailable(placementId);

            try {
                return FREObject.newObject(hasAdAvailable);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    public static class SuperAwesomeAIRSAAppWallPlay implements FREFunction {

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            Context context = freContext.getActivity();
            int placementId = SuperAwesome.getInstance().defaultPlacementId();
            boolean isParentalGateEnabled = SuperAwesome.getInstance().defaultParentalGate();
            boolean isBackButtonEnabled = SuperAwesome.getInstance().defaultBackButton();

            try {
                placementId = freObjects[0].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isParentalGateEnabled = freObjects[1].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isBackButtonEnabled = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // configure & play
            SAAppWall.setParentalGate(isParentalGateEnabled);
            SAAppWall.setBackButton(isBackButtonEnabled);
            SAAppWall.play(placementId, context);

            return null;
        }
    }

}
