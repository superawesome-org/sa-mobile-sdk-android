package tv.superawesome.plugins.air.functions;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.loader.SALoader;
import tv.superawesome.sdk.loader.SALoaderListener;
import tv.superawesome.sdk.models.SAAd;

/**
 * Created by gabriel.coman on 16/03/16.
 */
public class SAAIRLoadAdFunction implements FREFunction {
    @Override
    public FREObject call(final FREContext freContext, FREObject[] freObjects) {
        /** setup vars with default values */
        Log.d("AIREXT", "loadAd");

        /** get parameters */
        if (freObjects.length == 4) {
            try {
                final String name = freObjects[0].getAsString();
                final int placementId = freObjects[1].getAsInt();
                final boolean isTestingEnabled = freObjects[2].getAsBool();
                final int configuration = freObjects[3].getAsInt();

                /** setup environment */
                SuperAwesome.getInstance().setApplicationContext(freContext.getActivity().getApplicationContext());
                SuperAwesome.getInstance().setTestMode(isTestingEnabled);
                switch (configuration) {
                    case 0: SuperAwesome.getInstance().setConfigurationStaging(); break;
                    case 1: SuperAwesome.getInstance().setConfigurationDevelopment(); break;
                    case 2: default: SuperAwesome.getInstance().setConfigurationProduction(); break;
                }

                Log.d("AIREXT", "Got loadAd params: " + name + ": " + placementId + " test: " + SuperAwesome.getInstance().isTestingEnabled() + " config: " + SuperAwesome.getInstance().getConfiguration());

                SALoader loader = new SALoader();
                loader.loadAd(placementId, new SALoaderListener() {
                    @Override
                    public void didLoadAd(SAAd ad) {
                        Log.d("AIREXT", "Did load ad " + ad.placementId);

                        /** dispatch a loaded ad event */
                        String meta = "{\"placementId\":" + placementId + ", \"name\":\"" + name + "\", \"func\":\"didLoadAd\"}";
                        freContext.dispatchStatusEventAsync(meta, ad.adJson);
                    }

                    @Override
                    public void didFailToLoadAdForPlacementId(int placementId) {
                        Log.d("AIREXT", "Did fail to load Ad " + placementId);

                        /** dispatch a ad not loaded event */
                        String meta = "{\"placementId\":" + placementId + ", \"name\":\"" + name + "\", \"func\":\"didFailToLoadAdForPlacementId\"}";
                        freContext.dispatchStatusEventAsync(meta, "");
                    }
                });

            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
