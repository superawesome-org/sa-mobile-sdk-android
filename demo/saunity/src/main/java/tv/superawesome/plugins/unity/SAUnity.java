package tv.superawesome.plugins.unity;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.*;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Parser.SAParser;
import tv.superawesome.sdk.data.Parser.SAParserListener;
import tv.superawesome.sdk.data.Validator.SAValidator;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAInterstitialActivity;
import tv.superawesome.sdk.views.SAVideoActivity;

import com.unity3d.player.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gabriel.coman on 21/01/16.
 */
public class SAUnity {


    private static void SendUnityMsgPayload(String unityAd, String callback, String payloadName, String payloadData) {
        String payload = "{\"type\":\""+callback+"\",\""+payloadName+"\":" + payloadData + "}";
        UnityPlayer.UnitySendMessage(unityAd, "nativeCallback", payload);
    }

    private static void SendUnityMsg(String unityAd, String callback) {
        SendUnityMsgPayload(unityAd, callback, "na", "\"na\"");
    }

    public static int getRotation(Context context){
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 1;
            case Surface.ROTATION_180:
                return 0;
            default:
                return 1;
        }
    }

    public static void SuperAwesomeUnityLoadAd(final Context context, final String unityName, int placementId, boolean isTestingEnabled) {
        /** setup testing */
        SuperAwesome.getInstance().setTestMode(isTestingEnabled);
        SuperAwesome.getInstance().setApplicationContext(context);
        SuperAwesome.getInstance().setConfigurationStaging();

        /** create the new saloader */
        SALoader loader = new SALoader();
        loader.loadAd(placementId, new SALoaderListener() {
            @Override
            public void didLoadAd(SAAd ad) {
                SendUnityMsgPayload(unityName, "callback_didLoadAd", "adJson", ad.adJson);
            }

            @Override
            public void didFailToLoadAdForPlacementId(int placementId) {
                SendUnityMsgPayload(unityName, "callback_didFailToLoadAd", "", "");
            }
        });
    }

    public static void SuperAwesomeUnitySABannerAd(final Context context, int placementId, String adJson, final String unityName, final int position, final int size, final boolean isParentalGateEnabled) {
        /** form the json object to parse */
        try {
            JSONObject dataJson = new JSONObject(adJson);

            if (dataJson != null) {
                SAParser.parseDictionaryIntoAd(dataJson, placementId, new SAParserListener() {
                    @Override
                    public void parsedAd(SAAd ad) {

                        /** check for validity */
                        boolean isValid = SAValidator.isAdDataValid(ad);

                        if (isValid) {
                            /** get current rotation */
                            final int[] currentRotation = {getRotation(context)};

                            /** context to activity */
                            final Activity activity = (Activity) context;

                            /** get the current view group */
                            ViewGroup current = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

                            /** start the banner ad */
                            final SABannerAd bannerAd = new SABannerAd(context);

                            /** calculate width & height */
                            int width = 0, height = 0;
                            if (size == 1)      { width = 300; height = 50;  }
                            else if (size == 2) { width = 728; height = 90;  }
                            else if (size == 3) { width = 300; height = 250; }
                            else                { width = 320; height = 50;  }

                            /** calc scaling factor */
                            DisplayMetrics metrics = new DisplayMetrics();
                            Display display = activity.getWindowManager().getDefaultDisplay();
                            display.getMetrics(metrics);
                            float factor = (float)metrics.densityDpi / (float)DisplayMetrics.DENSITY_DEFAULT;
                            /** update width & height */
                            final int scaledWidth = (int)(factor * width);
                            final int scaledHeight = (int)(factor * height);

                            /** set banner width & height */
                            int maxWidthHeight = Math.max(metrics.widthPixels, metrics.heightPixels);
                            RelativeLayout.LayoutParams params1 =
                                    new RelativeLayout.LayoutParams(maxWidthHeight, maxWidthHeight);
                            RelativeLayout screenLayout = new RelativeLayout(context);
                            screenLayout.setLayoutParams(params1);
                            screenLayout.setBackgroundColor(Color.TRANSPARENT);

                            final RelativeLayout.LayoutParams params2 =
                                    new RelativeLayout.LayoutParams(scaledWidth, scaledHeight);
                            params2.leftMargin = (metrics.widthPixels - scaledWidth) / 2;
                            params2.topMargin = (position == 0 ? 0 : (metrics.heightPixels - scaledHeight));
                            bannerAd.setLayoutParams(params2);

                            current.addView(screenLayout);
                            screenLayout.addView(bannerAd);

                            /** orientation changed */
                            OrientationEventListener listener = new OrientationEventListener(activity, SensorManager.SENSOR_DELAY_UI) {
                                @Override
                                public void onOrientationChanged(int orientation) {
                                    int newRotation = getRotation(context);

                                    if (newRotation != currentRotation[0]){
                                        currentRotation[0] = newRotation;

                                        DisplayMetrics metrics = new DisplayMetrics();
                                        Display display = activity.getWindowManager().getDefaultDisplay();
                                        display.getMetrics(metrics);

                                        params2.leftMargin = (metrics.widthPixels - scaledWidth) / 2;
                                        params2.topMargin = (position == 0 ? 0 : (metrics.heightPixels - scaledHeight));
                                        bannerAd.setLayoutParams(params2);
                                    }
                                }
                            };
                            listener.enable();

                            /** set the ad */
                            bannerAd.setAd(ad);

                            /** parametrize it */
                            bannerAd.setIsParentalGateEnabled(isParentalGateEnabled);

                            /** set the listener */
                            bannerAd.setAdListener(new SAAdListener() {
                                @Override
                                public void adWasShown(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasShown");
                                }

                                @Override
                                public void adFailedToShow(int placementId) {
                                    SendUnityMsg(unityName, "callback_adFailedToShow");
                                }

                                @Override
                                public void adWasClosed(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasClosed");
                                }

                                @Override
                                public void adWasClicked(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasClicked");
                                }

                                @Override
                                public void adHasIncorrectPlacement(int placementId) {
                                    SendUnityMsg(unityName, "callback_adHasIncorrectPlacement");
                                }
                            });
                            bannerAd.setParentalGateListener(new SAParentalGateListener() {
                                @Override
                                public void parentalGateWasCanceled(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasCanceled");
                                }

                                @Override
                                public void parentalGateWasFailed(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasFailed");
                                }

                                @Override
                                public void parentalGateWasSucceded(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasSucceded");
                                }
                            });

                            /** start playing the banner */
                            bannerAd.play();

                        } else {
                            SendUnityMsg(unityName, "callback_adFailedToShow");
                        }
                    }
                });
            } else {
                SendUnityMsg(unityName, "callback_adFailedToShow");
            }
        } catch (JSONException e) {
            SendUnityMsg(unityName, "callback_adFailedToShow");
        }
    }

    public static void SuperAwesomeUnitySAInterstitialAd(final Context context, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled){
        /** form the json object to parse */
        try {
            JSONObject dataJson = new JSONObject(adJson);

            if (dataJson != null) {
                SAParser.parseDictionaryIntoAd(dataJson, placementId, new SAParserListener() {
                    @Override
                    public void parsedAd(SAAd ad) {

                        /** check for validity */
                        boolean isValid = SAValidator.isAdDataValid(ad);

                        if (isValid) {

                            /** create the interstitial */
                            SAInterstitialActivity interstitial = new SAInterstitialActivity(context);

                            /** set the ad data */
                            interstitial.setAd(ad);

                            /** parametrise the interstitial */
                            interstitial.setIsParentalGateEnabled(isParentalGateEnabled);

                            /** add listeners */
                            interstitial.setAdListener(new SAAdListener() {
                                @Override
                                public void adWasShown(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasShown");
                                }

                                @Override
                                public void adFailedToShow(int placementId) {
                                    SendUnityMsg(unityName, "callback_adFailedToShow");
                                }

                                @Override
                                public void adWasClosed(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasClosed");
                                }

                                @Override
                                public void adWasClicked(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasClicked");
                                }

                                @Override
                                public void adHasIncorrectPlacement(int placementId) {
                                    SendUnityMsg(unityName, "callback_adHasIncorrectPlacement");
                                }
                            });
                            interstitial.setParentalGateListener(new SAParentalGateListener() {
                                @Override
                                public void parentalGateWasCanceled(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasCanceled");
                                }

                                @Override
                                public void parentalGateWasFailed(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasFailed");
                                }

                                @Override
                                public void parentalGateWasSucceded(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasSucceded");
                                }
                            });

                            /** start playing the interstitial */
                            interstitial.play();

                        } else {
                            SendUnityMsg(unityName, "callback_adFailedToShow");
                        }
                    }
                });
            } else {
                SendUnityMsg(unityName, "callback_adFailedToShow");
            }
        } catch (JSONException e) {
            SendUnityMsg(unityName, "callback_adFailedToShow");
        }
    }

    public static void SuperAwesomeUnitySAVideoAd(final Context context, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled, final boolean shouldShowCloseButton, final boolean shouldAutomaticallyCloseAtEnd) {
/** form the json object to parse */
        try {
            JSONObject dataJson = new JSONObject(adJson);

            if (dataJson != null) {
                SAParser.parseDictionaryIntoAd(dataJson, placementId, new SAParserListener() {
                    @Override
                    public void parsedAd(SAAd ad) {

                        /** check for validity */
                        boolean isValid = SAValidator.isAdDataValid(ad);

                        if (isValid) {
                            /** create the video */
                            SAVideoActivity video = new SAVideoActivity(context);

                            /** set the ad */
                            video.setAd(ad);

                            /** parametrise the video */
                            video.setIsParentalGateEnabled(isParentalGateEnabled);
                            video.setShouldShowCloseButton(shouldShowCloseButton);
                            video.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);

                            /** add listeners */
                            video.setAdListener(new SAAdListener() {
                                @Override
                                public void adWasShown(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasShown");
                                }

                                @Override
                                public void adFailedToShow(int placementId) {
                                    SendUnityMsg(unityName, "callback_adFailedToShow");
                                }

                                @Override
                                public void adWasClosed(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasClosed");
                                }

                                @Override
                                public void adWasClicked(int placementId) {
                                    SendUnityMsg(unityName, "callback_adWasClicked");
                                }

                                @Override
                                public void adHasIncorrectPlacement(int placementId) {
                                    SendUnityMsg(unityName, "callback_adHasIncorrectPlacement");
                                }
                            });
                            video.setVideoAdListener(new SAVideoAdListener() {
                                @Override
                                public void adStarted(int placementId) {
                                    SendUnityMsg(unityName, "callback_adStarted");
                                }

                                @Override
                                public void videoStarted(int placementId) {
                                    SendUnityMsg(unityName, "callback_videoStarted");
                                }

                                @Override
                                public void videoReachedFirstQuartile(int placementId) {
                                    SendUnityMsg(unityName, "callback_videoReachedFirstQuartile");
                                }

                                @Override
                                public void videoReachedMidpoint(int placementId) {
                                    SendUnityMsg(unityName, "callback_videoReachedMidpoint");
                                }

                                @Override
                                public void videoReachedThirdQuartile(int placementId) {
                                    SendUnityMsg(unityName, "callback_videoReachedThirdQuartile");
                                }

                                @Override
                                public void videoEnded(int placementId) {
                                    SendUnityMsg(unityName, "callback_videoEnded");
                                }

                                @Override
                                public void adEnded(int placementId) {
                                    SendUnityMsg(unityName, "callback_adEnded");
                                }

                                @Override
                                public void allAdsEnded(int placementId) {
                                    SendUnityMsg(unityName, "callback_allAdsEnded");
                                }
                            });
                            video.setParentalGateListener(new SAParentalGateListener() {
                                @Override
                                public void parentalGateWasCanceled(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasCanceled");
                                }

                                @Override
                                public void parentalGateWasFailed(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasFailed");
                                }

                                @Override
                                public void parentalGateWasSucceded(int placementId) {
                                    SendUnityMsg(unityName, "callback_parentalGateWasSucceded");
                                }
                            });

                            /** finally play the video */
                            video.play();
                        } else {
                            SendUnityMsg(unityName, "callback_adFailedToShow");
                        }
                    }
                });
            } else {
                SendUnityMsg(unityName, "callback_adFailedToShow");
            }
        } catch (JSONException e) {
            SendUnityMsg(unityName, "callback_adFailedToShow");
        }
    }
}
