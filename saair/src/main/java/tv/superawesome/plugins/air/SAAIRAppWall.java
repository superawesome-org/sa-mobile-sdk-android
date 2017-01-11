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

public class SAAIRAppWall {

    private static final String airName = "SAAppWall";

    public static class SuperAwesomeAIRSAAppWallCreate implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            SAAppWall.setListener(new SAInterface() {
                @Override
                public void onEvent(int placementId, SAEvent event) {
                    switch (event) {
                        case adLoaded: SAAIRCallback.sendAdCallback(freContext, airName, placementId, "adLoaded"); break;
                        case adFailedToLoad: SAAIRCallback.sendAdCallback(freContext, airName, placementId, "adFailedToLoad"); break;
                        case adShown: SAAIRCallback.sendAdCallback(freContext, airName, placementId, "adShown"); break;
                        case adFailedToShow: SAAIRCallback.sendAdCallback(freContext, airName, placementId, "adFailedToShow"); break;
                        case adClicked: SAAIRCallback.sendAdCallback(freContext, airName, placementId, "adClicked"); break;
                        case adClosed: SAAIRCallback.sendAdCallback(freContext, airName, placementId, "adClosed"); break;
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
