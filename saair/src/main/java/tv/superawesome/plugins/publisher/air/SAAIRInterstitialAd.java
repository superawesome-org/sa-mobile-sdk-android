/**
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.air;

import android.content.Context;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.sdk.publisher.SADefaults;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAInterstitialAd;
import tv.superawesome.sdk.publisher.SAOrientation;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRInterstitialAd {

    private static final String airName = "SAInterstitialAd";

    /**
     * Class that creates a new Interstitial Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAInterstitialAdCreate implements FREFunction {
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

            SAInterstitialAd.setListener(new SAInterface() {
                @Override
                public void onEvent(int placementId, SAEvent event) {
                    switch (event) {
                        case adLoaded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adLoaded.toString()); break;
                        case adEmpty: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adEmpty.toString()); break;
                        case adFailedToLoad: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adFailedToLoad.toString()); break;
                        case adAlreadyLoaded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adAlreadyLoaded.toString()); break;
                        case adShown: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adShown.toString()); break;
                        case adFailedToShow: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adFailedToShow.toString()); break;
                        case adClicked: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adClicked.toString()); break;
                        case adEnded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adEnded.toString()); break;
                        case adClosed: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adClosed.toString());break;
                    }
                }
            });

            return null;
        }
    }

    /**
     * Class that loads ad data into an Interstitial Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAInterstitialAdLoad implements FREFunction {
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

            Context context = freContext.getActivity();
            int placementId = SADefaults.defaultPlacementId();
            int configuration = SADefaults.defaultConfiguration().ordinal();
            boolean test = SADefaults.defaultTestMode();

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

    /**
     * Class that checks if an ad is available in an Interstitial Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAInterstitialAdHasAdAvailable implements FREFunction {
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

            int placementId = SADefaults.defaultPlacementId();

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

    /**
     * Class that plays an already loaded ad for an Interstitial Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAInterstitialAdPlay implements FREFunction {
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

            Context context = freContext.getActivity();
            int placementId = SADefaults.defaultPlacementId();
            boolean isParentalGateEnabled = SADefaults.defaultParentalGate();
            boolean isBumperPageEnabled = SADefaults.defaultBumperPage();
            int orientation = SADefaults.defaultOrientation().ordinal();
            boolean isBackButtonEnabled = SADefaults.defaultBackButton();

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
                isBumperPageEnabled = freObjects[2].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                orientation = freObjects[3].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isBackButtonEnabled = freObjects[4].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // config & play
            SAInterstitialAd.setParentalGate(isParentalGateEnabled);
            SAInterstitialAd.setBumperPage(isBumperPageEnabled);
            SAInterstitialAd.setOrientation(SAOrientation.fromValue(orientation));
            SAInterstitialAd.setBackButton(isBackButtonEnabled);
            SAInterstitialAd.play(placementId, context);

            return null;
        }
    }
}
