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
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.sdk.publisher.SADefaults;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAOrientation;
import tv.superawesome.sdk.publisher.SAVideoAd;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRVideoAd {

    private static final String airName = "SAVideoActivity";

    /**
     * Class that creates a new Video Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAVideoAdCreate implements FREFunction {
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

            SAVideoAd.setListener((SAInterface) (placementId, event) -> {
                switch (event) {
                    case adLoaded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adLoaded.toString()); break;
                    case adEmpty: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adEmpty.toString()); break;
                    case adFailedToLoad: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adFailedToLoad.toString()); break;
                    case adAlreadyLoaded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adAlreadyLoaded.toString()); break;
                    case adShown: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adShown.toString()); break;
                    case adFailedToShow: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adFailedToShow.toString()); break;
                    case adClicked: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adClicked.toString()); break;
                    case adEnded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adEnded.toString()); break;
                    case adClosed: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adClosed.toString()); break;
                }
            });

            return null;
        }
    }

    /**
     * Class that loads ad data into a Video Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAVideoAdLoad implements FREFunction {
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
            int playback = SADefaults.defaultPlaybackMode().ordinal();

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

            try {
                playback = freObjects[3].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // config & load
            SAVideoAd.setConfiguration(SAConfiguration.fromValue(configuration));
            SAVideoAd.setTestMode(test);
            SAVideoAd.setPlaybackMode(SARTBStartDelay.fromValue(playback));
            SAVideoAd.load(placementId, context);

            return null;
        }
    }

    /**
     * Class that checks if an ad is available in a Video Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAVideoAdHasAdAvailable implements FREFunction {
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

            boolean hasAdAvailable = SAVideoAd.hasAdAvailable(placementId);

            try {
                return FREObject.newObject(hasAdAvailable);
            } catch (FREWrongThreadException e) {
                return null;
            }
        }
    }

    /**
     * Class that plays an already loaded ad for a Video Ad (from AIR)
     */
    public static class SuperAwesomeAIRSAVideoAdPlay implements FREFunction {
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
            boolean shouldShowCloseButton = SADefaults.defaultCloseButton();
            boolean shouldShowSmallClickButton = SADefaults.defaultSmallClick();
            boolean shouldAutomaticallyCloseAtEnd = SADefaults.defaultCloseAtEnd();
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
                shouldShowCloseButton = freObjects[3].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                shouldShowSmallClickButton = freObjects[4].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                shouldAutomaticallyCloseAtEnd = freObjects[5].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                orientation = freObjects[6].getAsInt();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                isBackButtonEnabled = freObjects[7].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            // configure & play
            SAVideoAd.setParentalGate(isParentalGateEnabled);
            SAVideoAd.setBumperPage(isBumperPageEnabled);
            SAVideoAd.setCloseButton(shouldShowCloseButton);
            SAVideoAd.setSmallClick(shouldShowSmallClickButton);
            SAVideoAd.setCloseAtEnd(shouldAutomaticallyCloseAtEnd);
            SAVideoAd.setOrientation(SAOrientation.fromValue(orientation));
            SAVideoAd.setBackButton(isBackButtonEnabled);
            SAVideoAd.play(placementId, context);

            return null;
        }
    }

}
