package tv.superawesome.plugins.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * Created by gabriel.coman on 16/03/16.
 */
public class SAAIRExtension implements FREExtension {
    @Override
    public void initialize() {
        Log.d("SuperAwesome", "Initialize SAAIRExtension");
    }

    @Override
    public FREContext createContext(String s) {
        Log.d("SuperAwesome","Create FREContext for SAAIRExtension");
        return new SAAIRExtensionContext();
    }

    @Override
    public void dispose() {
        Log.d("SuperAwesome", "Dispose SAAIRExtension");
    }
}
