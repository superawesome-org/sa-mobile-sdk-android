/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import tv.superawesome.lib.sacpi.SACPI;
import tv.superawesome.lib.sacpi.SACPIInterface;

/**
 * Class that holds a number of static methods used to communicate with Adobe AIR
 */
public class SAAIRCPI {

    // air CPI name
    private static final String airName = "SACPI";

    /**
     * Inner class that implements a method to send back a callback to Adobe AIR after a
     * CPI operation on production
     */
    public static class SuperAwesomeAIRSACPIHandleCPI implements FREFunction {
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

            SACPI.getInstance().sendInstallEvent(freContext.getActivity(), new SACPIInterface() {
                @Override
                public void saDidCountAnInstall(boolean success) {
                    SAAIRCallback.sendCPICallback(freContext, airName, success, "HandleCPI");
                }
            });

            return null;
        }
    }
}
