package tv.superawesome.plugins.publisher.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import tv.superawesome.lib.sabumperpage.SABumperPage;

public class SAAIRBumperPage {

    public static class SuperAwesomeAIRBumperOverrideName implements FREFunction {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            String name = null;

            try {
                name = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            if (name != null) {
                SABumperPage.overrideName(name);
            }

            return null;
        }
    }
}


