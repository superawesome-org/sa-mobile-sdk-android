package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.cpi.SAInstallEventInterface;

public class SAAIRCPI {

    private static final String airName = "SuperAwesomeCPI";

    public static class SuperAwesomeAIRSuperAwesomeHandleCPI implements FREFunction {
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

    public static class SuperAwesomeAIRSuperAwesomeHandleStagingCPI implements FREFunction {

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
