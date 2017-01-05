package tv.superawesome.plugins.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class SAAIR implements FREExtension {
    @Override
    public void initialize() {
        Log.d("SuperAwesome", "Initialize SAAIR");
    }

    @Override
    public FREContext createContext(String s) {
        Log.d("SuperAwesome","Create FREContext for SAAIR");
        return new SAAIRExtensionContext();
    }

    @Override
    public void dispose() {
        Log.d("SuperAwesome", "Dispose SAAIR");
    }
}
