/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.air;

import android.content.Context;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRAppWall {

    // constant air name to send callbacks to
    private static final String airName = "SAAppWall";

    /**
     * Class that creates a new App Wall (from AIR)
     */
    public static class SuperAwesomeAIRSAAppWallCreate implements FREFunction {
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

            SAAppWall.setListener(new SAInterface() {
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
                        case adClosed: SAAIRCallback.sendAdCallback(freContext, airName, placementId, SAEvent.adClosed.toString()); break;
                    }
                }
            });

            return null;
        }
    }

    /**
     * Class that loads ad data into an App Wall (from AIR)
     */
    public static class SuperAwesomeAIRSAAppWallLoad implements FREFunction {
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

    /**
     * Class that checks if an ad is available in an App Wall (from AIR)
     */
    public static class SuperAwesomeAIRSAAppWallHasAdAvailable implements FREFunction {
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

    /**
     * Class that plays an already loaded ad for an App Wall (from AIR)
     */
    public static class SuperAwesomeAIRSAAppWallPlay implements FREFunction {
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
