package tv.superawesome.plugins.publisher.unity;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import tv.superawesome.sagdprisminorsdk.minor.models.GetIsMinorModel;
import tv.superawesome.sagdprisminorsdk.minor.process.GetIsMinorInterface;
import tv.superawesome.sdk.publisher.SuperAwesome;

/**
 * Created by gabriel.coman on 13/05/2018.
 */

public class SAUnitySuperAwesome {

    public static void SuperAwesomeUnityInit (Activity activity, boolean loggingEnabled) {
        SuperAwesome.init(activity.getApplication(), loggingEnabled);
    }

    public static void SuperAwesomeUnityTriggerAgeCheck (Context context, String dateOfBirth) {
        SuperAwesome.triggerAgeCheck(context, dateOfBirth, new GetIsMinorInterface() {
            @Override
            public void getIsMinorData(GetIsMinorModel getIsMinorModel) {

                try {
                    JSONObject jsonObject = getIsMinorModel.writeToJson();
                    SAUnityCallback.sendToUnity("SuperAwesome", jsonObject);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });
    }
}
