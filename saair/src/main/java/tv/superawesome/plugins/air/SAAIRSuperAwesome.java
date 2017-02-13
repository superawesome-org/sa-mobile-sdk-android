/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import tv.superawesome.lib.sacpi.SACPIInterface;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRSuperAwesome {

    /**
     * Inner static class that implements FREFunction.
     * This class will implement the "call" method from FREFunction and be able to override the
     * current native Android SDK version with the AIR SDK version
     */
    public static class SuperAwesomeAIRSuperAwesomeSetVersion implements FREFunction {
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

            String version = null;
            String sdk = null;

            try {
                version = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                sdk = freObjects[1].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (version != null && sdk != null) {
                SuperAwesome.getInstance().overrideVersion(version);
                SuperAwesome.getInstance().overrideSdk(sdk);
            }

            return null;
        }
    }

}
