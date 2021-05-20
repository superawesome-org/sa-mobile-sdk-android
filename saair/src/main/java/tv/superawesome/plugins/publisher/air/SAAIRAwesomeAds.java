package tv.superawesome.plugins.publisher.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import org.json.JSONObject;

import tv.superawesome.lib.sagdprisminorsdk.minor.models.GetIsMinorModel;
import tv.superawesome.lib.sagdprisminorsdk.minor.process.GetIsMinorInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.sdk.publisher.AwesomeAds;

/**
 * Created by gabriel.coman on 13/05/2018.
 */

public class SAAIRAwesomeAds {

    private static final String airName = "AwesomeAds";


    /**
     * Inner static class that implements FREFunction.
     * This class will implement the "call" method from FREFunction and be able to override the
     * current native Android SDK version with the AIR SDK version
     */
    public static class SuperAwesomeAIRAwesomeAdsInit implements FREFunction {
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

            boolean loggingEnabled = false;

            try {
                loggingEnabled = freObjects[0].getAsBool();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                AwesomeAds.init(freContext.getActivity().getApplication(), loggingEnabled);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class SuperAwesomeAIRAwesomeAdsTriggerAgeCheck implements FREFunction {

        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            String dateOfBirth = null;

            try {
                dateOfBirth = freObjects[0].getAsString();
            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }

            try {
                AwesomeAds.triggerAgeCheck(freContext.getActivity(), dateOfBirth, getIsMinorModel -> {

                    GetIsMinorModel model = getIsMinorModel != null ? getIsMinorModel : new GetIsMinorModel();

                    JSONObject payload = SAJsonParser.newObject(
                            "name", airName,
                            "isMinor", model.isMinor(),
                            "age", model.getAge(),
                            "consentAgeForCountry", model.getConsentAgeForCountry(),
                            "country", model.getCountry()
                    );

                    SAAIRCallback.sendToAIR(freContext, payload);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
