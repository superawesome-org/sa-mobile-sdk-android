package tv.superawesome.plugins.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import tv.superawesome.sdk.SuperAwesome;

public class SAAIRVersion {

    public static class SuperAwesomeAIRSetVersion implements FREFunction {
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
