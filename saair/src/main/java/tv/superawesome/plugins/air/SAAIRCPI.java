/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.cpi.SAInstallEventInterface;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRCPI {

    // air CPI name
    private static final String airName = "SuperAwesomeCPI";

    /**
     * Inner class that implements a method to send back a callback to Adobe AIR after a
     * CPI operation on production
     */
    public static class SuperAwesomeAIRSuperAwesomeHandleCPI implements FREFunction {
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

            SuperAwesome.getInstance().handleCPI(freContext.getActivity(), new SAInstallEventInterface() {
                @Override
                public void didCountAnInstall(boolean success) {

                    SAAIRCallback.sendCPICallback(freContext, airName, success, "HandleCPI");

                }
            });

            return null;
        }
    }

    /**
     * Inner class that implements a method to send back a callback to Adobe AIR after a
     * CPI operation on staging
     */
    public static class SuperAwesomeAIRSuperAwesomeHandleStagingCPI implements FREFunction {
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

            SuperAwesome.getInstance().handleStagingCPI(freContext.getActivity(), new SAInstallEventInterface() {
                @Override
                public void didCountAnInstall(boolean success) {

                    SAAIRCallback.sendCPICallback(freContext, airName, success, "HandleStagingCPI");

                }
            });

            return null;
        }
    }

}
