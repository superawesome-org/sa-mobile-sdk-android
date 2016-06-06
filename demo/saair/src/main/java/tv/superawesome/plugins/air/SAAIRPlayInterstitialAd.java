package tv.superawesome.plugins.air;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.AccessibleObject;

import tv.superawesome.lib.saadloader.models.SAAd;
import tv.superawesome.sdk.views.SAAdInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAParentalGateInterface;

/**
 * Created by gabriel.coman on 06/04/16.
 */
public class SAAIRPlayInterstitialAd implements FREFunction {
    @Override
    public FREObject call(final FREContext freContext, FREObject[] freObjects) {
        /** setup vars with default values */
        Log.d("AIREXT", "playInterstitialAd");

        if (freObjects.length == 6){
            try {
                /** get variables */
                final String name = freObjects[0].getAsString();
                final int placementId = freObjects[1].getAsInt();
                final String adJson = freObjects[2].getAsString();
                final boolean isParentalGateEnabled = freObjects[3].getAsBool();
                final boolean shouldLockOrientation = freObjects[4].getAsBool();
                final int lockOrientation = freObjects[5].getAsInt();
                final Context context = freContext.getActivity().getApplicationContext();

                try {
                    JSONObject dataJson = new JSONObject(adJson);
                    SAAd ad = new SAAd(dataJson);

                    SAInterstitialAd inter = new SAInterstitialAd(freContext.getActivity());
                    inter.setAd(ad);
                    inter.setIsParentalGateEnabled(isParentalGateEnabled);
                    inter.setShouldLockOrientation(shouldLockOrientation);
                    if (lockOrientation == 1) {
                        inter.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else if (lockOrientation == 2){
                        inter.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                    inter.setAdListener(new SAAdInterface() {
                        @Override
                        public void adWasShown(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adWasShown\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }

                        @Override
                        public void adFailedToShow(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adFailedToShow\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }

                        @Override
                        public void adWasClosed(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adWasClosed\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }

                        @Override
                        public void adWasClicked(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adWasClicked\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }

                        @Override
                        public void adHasIncorrectPlacement(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adHasIncorrectPlacement\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }
                    });
                    inter.setParentalGateListener(new SAParentalGateInterface() {
                        @Override
                        public void parentalGateWasCanceled(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"parentalGateWasCanceled\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }

                        @Override
                        public void parentalGateWasFailed(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"parentalGateWasFailed\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }

                        @Override
                        public void parentalGateWasSucceded(int placementId) {
                            String meta = "{\"name\":\"" + name + "\", \"func\":\"parentalGateWasSucceded\"}";
                            freContext.dispatchStatusEventAsync(meta, "");
                        }
                    });
                    inter.play();
                } catch (JSONException e) {
                    String meta = "{\"name\":\"" + name + "\", \"func\":\"adFailedToShow\"}";
                    freContext.dispatchStatusEventAsync(meta, "");
                }

            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
