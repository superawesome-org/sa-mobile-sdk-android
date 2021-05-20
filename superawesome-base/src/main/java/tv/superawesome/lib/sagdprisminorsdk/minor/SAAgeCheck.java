package tv.superawesome.lib.sagdprisminorsdk.minor;

import android.content.Context;

import tv.superawesome.lib.sagdprisminorsdk.minor.network.CommunicationCenter;
import tv.superawesome.lib.sagdprisminorsdk.minor.process.GetIsMinorInterface;
import tv.superawesome.lib.sagdprisminorsdk.minor.process.GetIsMinorProcess;

public class SAAgeCheck {

    //static
    public static final SAAgeCheck sdk = new SAAgeCheck();

    //internal services and processes
    private GetIsMinorProcess isMinorProcess;

    private SAAgeCheck() {
        isMinorProcess = new GetIsMinorProcess();
    }

    public void getIsMinor(Context context, String dateOfBirth, GetIsMinorInterface listener) {
        String bundleId = context.getApplicationContext().getPackageName();
        isMinorProcess.getIsMinor(context, dateOfBirth, bundleId, listener);
    }

    public void shutdown() {
        isMinorProcess = null;
    }

    public String getURL() {
        return CommunicationCenter.baseURL;
    }
}
