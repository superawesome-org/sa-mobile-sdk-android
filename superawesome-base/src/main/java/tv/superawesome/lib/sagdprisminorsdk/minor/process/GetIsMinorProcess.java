package tv.superawesome.sagdprisminorsdk.minor.process;

import android.content.Context;

import tv.superawesome.sagdprisminorsdk.minor.models.GetIsMinorModel;
import tv.superawesome.sagdprisminorsdk.minor.requests.GetIsMinorRequest;

public class GetIsMinorProcess {

    private GetIsMinorRequest isMinor;

    public GetIsMinorProcess() {

        isMinor = new GetIsMinorRequest();
    }

    public void getIsMinor(final Context context, String dateOfBirth, String bundleId, final GetIsMinorInterface listener) {

        isMinor.execute(context, dateOfBirth, bundleId, new GetIsMinorInterface() {
            @Override
            public void getIsMinorData(GetIsMinorModel isMinorModel) {

                //return the model (may be null)
                listener.getIsMinorData(isMinorModel);
            }
        });

    }
}
