/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.air;

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

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.publisher.SADefaults;
import tv.superawesome.sdk.publisher.SABannerAd;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRBannerAd {

    // hash map of all the banners that have been created
    private static HashMap<String, SABannerAd> bannerAdHashMap = new HashMap<>();

    /**
     * Class that creates a new Banner Ad (from AIR)
     */
    public static class SuperAwesomeAIRSABannerAdCreate implements FREFunction {
        /**
         * Overridden FREFunction "call" method;
         * This needs to be implemented if this class is to correctly implement the FREFunction
         * interface.
         * This is the way AIR communicates with native Android code.
         *
         * @param freContext    current FREContext
         * @param freObjects    a list of parameters that might have been sent by adobe AIR
         * @return              a FREObject that sends back data to Adobe AIR
         */
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
                final SABannerAd bannerAd = new SABannerAd(context);
                bannerAd.setId(SAUtils.randomNumberBetween(1000000, 1500000));
                bannerAd.setListener((SAInterface) (placementId, event) -> {
                    switch (event) {
                        case adLoaded: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adLoaded.toString()); break;
                        case adEmpty: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adEmpty.toString()); break;
                        case adFailedToLoad: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adFailedToLoad.toString()); break;
                        case adAlreadyLoaded: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adAlreadyLoaded.toString()); break;
                        case adShown: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adShown.toString()); break;
                        case adFailedToShow: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adFailedToShow.toString()); break;
                        case adClicked: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adClicked.toString()); break;
                        case adEnded: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adEnded.toString()); break;
                        case adClosed: SAAIRCallback.sendAdCallback(freContext, airName2, placementId, SAEvent.adClosed.toString()); break;
                    }
                });
                bannerAdHashMap.put(airName2, bannerAd);
            }

            return null;
        }
    }

    /**
     * Class that loads ad data into a Banner Ad (from AIR)
     */
    public static class SuperAwesomeAIRSABannerAdLoad implements FREFunction {
        /**
         * Overridden FREFunction "call" method;
         * This needs to be implemented if this class is to correctly implement the FREFunction
         * interface.
         * This is the way AIR communicates with native Android code.
         *
         * @param freContext    current FREContext
         * @param freObjects    a list of parameters that might have been sent by adobe AIR
         * @return              a FREObject that sends back data to Adobe AIR
         */
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            String airName = null;
            int placementId = SADefaults.defaultPlacementId();
            int configuration = SADefaults.defaultConfiguration().ordinal();
            boolean test = SADefaults.defaultTestMode();

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

    /**
     * Class that checks if an ad is available in a Banner Ad (from AIR)
     */
    public static class SuperAwesomeAIRSABannerAdHasAdAvailable implements FREFunction {
        /**
         * Overridden FREFunction "call" method;
         * This needs to be implemented if this class is to correctly implement the FREFunction
         * interface.
         * This is the way AIR communicates with native Android code.
         *
         * @param freContext    current FREContext
         * @param freObjects    a list of parameters that might have been sent by adobe AIR
         * @return              a FREObject that sends back data to Adobe AIR
         */
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

    /**
     * Class that plays an already loaded ad for a Banner Ad (from AIR)
     */
    public static class SuperAwesomeAIRSABannerAdPlay implements FREFunction {
        /**
         * Overridden FREFunction "call" method;
         * This needs to be implemented if this class is to correctly implement the FREFunction
         * interface.
         * This is the way AIR communicates with native Android code.
         *
         * @param freContext    current FREContext
         * @param freObjects    a list of parameters that might have been sent by adobe AIR
         * @return              a FREObject that sends back data to Adobe AIR
         */
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            // default values
            Activity activity = freContext.getActivity();
            String airName = null;
            int position = 0;
            int width = 320;
            int height = 50;
            boolean isParentalGateEnabled = SADefaults.defaultParentalGate();
            boolean isBumperPageEnabled = SADefaults.defaultBumperPage();
            boolean color = SADefaults.defaultBgColor();

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
                isBumperPageEnabled = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                position = freObjects[3].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                width = freObjects[4].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                height = freObjects[5].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                color = freObjects[6].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (airName != null && bannerAdHashMap.containsKey(airName) && !bannerAdHashMap.get(airName).isClosed()) {

                // get banner ad
                final SABannerAd bannerAd = bannerAdHashMap.get(airName);

                // customize
                bannerAd.setParentalGate(isParentalGateEnabled);
                bannerAd.setBumperPage(isBumperPageEnabled);
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

    /**
     * Class that closes an existing Banner Ad (from AIR)
     */
    public static class SuperAwesomeAIRSABannerAdClose implements FREFunction {
        /**
         * Overridden FREFunction "call" method;
         * This needs to be implemented if this class is to correctly implement the FREFunction
         * interface.
         * This is the way AIR communicates with native Android code.
         *
         * @param freContext    current FREContext
         * @param freObjects    a list of parameters that might have been sent by adobe AIR
         * @return              a FREObject that sends back data to Adobe AIR
         */
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
                // bannerAdHashMap.remove(airName);
            }

            return null;
        }
    }
}
