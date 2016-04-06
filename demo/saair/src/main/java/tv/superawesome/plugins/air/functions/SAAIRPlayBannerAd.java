package tv.superawesome.plugins.air.functions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.parser.SAParser;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 06/04/16.
 */
public class SAAIRPlayBannerAd implements FREFunction {
    @Override
    public FREObject call(final FREContext freContext, FREObject[] freObjects) {
        /** setup vars with default values */
        Log.d("AIREXT", "playBannerAd");

        if (freObjects.length == 8){
            try {
                /** get variables */
                final String name = freObjects[0].getAsString();
                final int placementId = freObjects[1].getAsInt();
                final String adJson = freObjects[2].getAsString();
                final boolean isParentalGateEnabled = freObjects[3].getAsBool();
                final int x = (int)(freObjects[4].getAsDouble());
                final int y = (int)(freObjects[5].getAsDouble());
                final int w = (int)(freObjects[6].getAsDouble());
                final int h = (int)(freObjects[7].getAsDouble());
                final Activity activity = freContext.getActivity();
                final Context context = activity.getApplicationContext();

                Log.d("AIREXT", "Meta: " + name + "/" + placementId + "/" + isParentalGateEnabled);
                Log.d("AIREXT", x + "/" + y + "/" + w + "/" + h);
                Log.d("AIREXT", "adJson: " + adJson);

                try {
                    JSONObject dataJson = new JSONObject(adJson);
                    SAAd ad = SAParser.parseDictionaryIntoAd(dataJson, placementId);

                    if (ad != null) {
                        Log.d("AIREXT", "ad data valid");
                        /** create the video */
                        SABannerAd banner = new SABannerAd(activity);
                        banner.setAd(ad);
                        banner.setIsParentalGateEnabled(isParentalGateEnabled);
                        banner.setAdListener(new SAAdListener() {
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
                        banner.setParentalGateListener(new SAParentalGateListener() {
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

                        ViewGroup current = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

                        /** set banner width & height */
                        SAUtils.SASize screenSize = SAUtils.getRealScreenSize(activity, false);
                        int maxWidthHeight = Math.max(screenSize.width, screenSize.height);
                        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(maxWidthHeight, maxWidthHeight);
                        RelativeLayout screenLayout = new RelativeLayout(context);
                        screenLayout.setLayoutParams(params1);
                        screenLayout.setBackgroundColor(Color.TRANSPARENT);

                        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(w, h);
                        params2.setMargins(x, y, 0, 0);
                        banner.setLayoutParams(params2);

                        current.addView(screenLayout);
                        screenLayout.addView(banner);

                        /** finally play */
                        banner.play();

                    } else {
                        String meta = "{\"name\":\"" + name + "\", \"func\":\"adFailedToShow\"}";
                        freContext.dispatchStatusEventAsync(meta, "");
                    }
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
