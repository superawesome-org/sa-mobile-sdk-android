package tv.superawesome.lib.sagdprisminorsdk.minor.process;

import android.content.Context;

import tv.superawesome.lib.sagdprisminorsdk.minor.requests.GetIsMinorRequest;

public class GetIsMinorProcess {

    private final GetIsMinorRequest isMinor;

    public GetIsMinorProcess() {

        isMinor = new GetIsMinorRequest();
    }

    public void getIsMinor(final Context context, String dateOfBirth, String bundleId, final GetIsMinorInterface listener) {

        //return the model (may be null)
        isMinor.execute(context, dateOfBirth, bundleId, (GetIsMinorInterface) listener::getIsMinorData);

    }
}
