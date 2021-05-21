/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.plugins.publisher.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * Class that implements the FREExtension interface and is able to communicate with Adobe AIR
 */
public class SAAIR implements FREExtension {
    /**
     * Overridden "initialize" method of FREExtension that fires up AIR
     *
     */
    @Override
    public void initialize() {
        Log.d("SADefaults", "Initialize SAAIR");
    }

    /**
     * Overridden "createContext" method of FREExtension that starts up an AIR
     * extension context
     *
     * @param s     the context string
     * @return      a new FREContext object
     */
    @Override
    public FREContext createContext(String s) {
        Log.d("SADefaults","Create FREContext for SAAIR");
        return new SAAIRExtensionContext();
    }

    /**
     * Overridden "dispose" method that destroys an already existing FREExstension
     *
     */
    @Override
    public void dispose() {
        Log.d("SADefaults", "Dispose SAAIR");
    }
}
