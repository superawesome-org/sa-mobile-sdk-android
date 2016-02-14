package tv.superawesome.plugins.unity;

/** import android / misc stuff */
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import org.json.JSONException;
import org.json.JSONObject;

/** import other SuperAwesome stuff */
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.*;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Parser.SAParser;
import tv.superawesome.sdk.data.Validator.SAValidator;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.listeners.SAVideoAdListener;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAInterstitialActivity;
import tv.superawesome.sdk.views.SAVideoActivity;

/** import unity3d plugin classes */
import com.unity3d.player.*;

/**
 * Created by gabriel.coman on 21/01/16.
 */
public class SAUnity {

    /**
     * Function that sends a message back to Unity
     * @param unityAd the unique unity Ad name that send a request to SAUnity.java plugin in the first place
     * @param callback the actual callback that's supposed to get called on the Unity side
     * @param payloadName used only when sending Ad json data back to Unity really
     * @param payloadData the payload contents, again, used when sending Ad json data back to Unity
     */
    private static void SendUnityMsgPayload(String unityAd, String callback, String payloadName, String payloadData) {
        String payload = "{\"type\":\""+callback+"\",\""+payloadName+"\":" + payloadData + "}";
        UnityPlayer.UnitySendMessage(unityAd, "nativeCallback", payload);
    }

    /**
     * Simplified helper function of SendUnityMsgPayload() that just sends out a callback to Unity
     * @param unityAd the unique unity object that generated the request in the first place
     * @param callback the callback to be sent
     */
    private static void SendUnityMsg(String unityAd, String callback) {
        SendUnityMsgPayload(unityAd, callback, "na", "\"na\"");
    }

    /**
     * Aux function to get the correct rotation on Android
     * @param context the current context
     * @return 0 = portrait, 1 = landscape
     */
    public static int getRotation(Context context){
        Display getOrient = ((Activity)context).getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    /**
     * Function that returns the current screen size
     * @param activity - the activity to pass along as context
     * @return a SASize object with width & height members
     */
    public static SASize getRealScreenSize(Activity activity, boolean rotate) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();

        View decorView = activity.getWindow().getDecorView();
//        int uiOptions = decorView.getSystemUiVisibility();
//        SALog.Log("uiOptions " + uiOptions);
//        SALog.Log("decorView ==> W: " + decorView.getWidth() + " H: " + decorView.getHeight());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            display.getRealMetrics(metrics);
//        } else {
//            display.getMetrics(metrics);
//        }

        if (!rotate){
            return new SASize(decorView.getWidth(), decorView.getHeight());
        } else {
            return new SASize(decorView.getHeight(), decorView.getWidth());
        }
//        return new SASize(metrics.widthPixels, metrics.heightPixels);
//        return new SASize(decorView.getWidth(), decorView.getHeight());
    }

    /**
     * Get the current scale factor
     * @param activity - the activity to pass along as context
     * @return a float meaning the scale
     */
    public static float getScaleFactor(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return  (float) metrics.densityDpi / (float) DisplayMetrics.DENSITY_DEFAULT;
    }

    public static RelativeLayout.LayoutParams getBannerLayoutParams(float factor, SASize screenSize, int bannerSize, int bannerPosition){
        /** calc actual banner W & H */
        int width = 0, height = 0;
        if      (bannerSize == 1) { width = 300; height = 50;  }
        else if (bannerSize == 2) { width = 728; height = 90;  }
        else if (bannerSize == 3) { width = 300; height = 250; }
        else                      { width = 320; height = 50;  }

        /** scale it according to the factor */
        int scaledWidth = (int)(factor * width);
        int scaledHeight = (int)(factor * height);

        /** make sure it's not bigger than the screen */
        if (scaledWidth > screenSize.width) {
            scaledHeight = (screenSize.width * scaledHeight) / scaledWidth;
            scaledWidth = screenSize.width;
        }

        SALog.Log("Orientation Width: " + screenSize.width + " H: " + screenSize.height);

        /** create the layout params */
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(scaledWidth, scaledHeight);
        params2.leftMargin = (screenSize.width - scaledWidth) / 2;
        params2.topMargin = (bannerPosition == 0 ? 0 : (screenSize.height - scaledHeight));

        return params2;
    }

    /**
     * Loads an ad using the native SDK and returns the Ad's JSON (to act as "ad data" in Unity)
     * @param context the current context
     * @param unityName the unity unique object that sent this
     * @param placementId the placement id to load the ad for
     * @param isTestingEnabled whether testing is enabled or not
     */
    public static void SuperAwesomeUnityLoadAd(final Context context, final String unityName, int placementId, boolean isTestingEnabled, int configuration) {
        System.out.println("SuperAwesomeUnityLoadAd " + unityName);

        /** setup testing */
        SuperAwesome.getInstance().setTestMode(isTestingEnabled);
        SuperAwesome.getInstance().setApplicationContext(context);
        if (configuration == SuperAwesome.SAConfiguration.STAGING.ordinal()) {
            SuperAwesome.getInstance().setConfigurationStaging();
        } else  if (configuration == SuperAwesome.SAConfiguration.DEVELOPMENT.ordinal()){
            SuperAwesome.getInstance().setConfigurationDevelopment();
        } else  {
            SuperAwesome.getInstance().setConfigurationProduction();
        }

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

    /**
     * Creates a banner ad, based on the following parameters
     * @param context the current context (activity, etc)
     * @param placementId the placement Id is needed by the parseDictionaryIntoAd function
     * @param adJson the actual ad json data
     * @param unityName the unique name of the unity object that sent this requrst
     * @param position the position: 0 = top, 1 = bottom
     * @param size the size of the banner: 0 = 320x50, 1 = 300x50, 2 = 728x90, 3 = 300x250
     * @param isParentalGateEnabled whether the parental gate is enabled or not
     */
    public static void SuperAwesomeUnitySABannerAd(final Context context, int placementId, String adJson, final String unityName, final int position, final int size, final int color, final boolean isParentalGateEnabled) {
        System.out.println("SuperAwesomeUnitySABannerAd " + unityName);

        /** form the json object to parse */
        try {
            JSONObject dataJson = new JSONObject(adJson);

            if (dataJson != null) {

                /** parse Ad */
                SAAd ad = SAParser.parseDictionaryIntoAd(dataJson, placementId);

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

                    /** get factor & screen size */
                    SASize screenSize = getRealScreenSize(activity, false);
                    final float factor = getScaleFactor(activity);

                    /** set banner width & height */
                    int maxWidthHeight = Math.max(screenSize.width, screenSize.height);
                    RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(maxWidthHeight, maxWidthHeight);
                    RelativeLayout screenLayout = new RelativeLayout(context);
                    screenLayout.setLayoutParams(params1);
                    screenLayout.setBackgroundColor(Color.TRANSPARENT);

                    /** update width & height */
                    RelativeLayout.LayoutParams params2 = getBannerLayoutParams(factor, screenSize, size, position);
                    bannerAd.setLayoutParams(params2);
                    if (color == 0) {
                        bannerAd.setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        bannerAd.setBackgroundColor(Color.rgb(191, 191, 191));
                    }

                    current.addView(screenLayout);
                    screenLayout.addView(bannerAd);

                    /** orientation changed */
                    OrientationEventListener listener = new OrientationEventListener(activity, SensorManager.SENSOR_DELAY_UI) {
                        @Override
                        public void onOrientationChanged(int orientation) {
                            int newRotation = getRotation(context);

                            if (newRotation != currentRotation[0]){
                                currentRotation[0] = newRotation;

                                /** calc scaling factor */
                                SASize screenSize = getRealScreenSize(activity, true);

                                RelativeLayout.LayoutParams params2 = getBannerLayoutParams(factor, screenSize, size, position);
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

                    /** add to this map */
                    SAUnityManager.getInstance().setAdMap(unityName, screenLayout);

                } else {
                    SendUnityMsg(unityName, "callback_adFailedToShow");
                }
            } else {
                SendUnityMsg(unityName, "callback_adFailedToShow");
            }
        } catch (JSONException e) {
            SendUnityMsg(unityName, "callback_adFailedToShow");
        }
    }

    /**
     * Removes a banner ad
     * @param unityName - the unity name of the banner ad
     */
    public static void SuperAwesomeUnityRemoveSABannerAd(final Context context, String unityName) {
        Object temp = SAUnityManager.getInstance().getAdMap(unityName);

        System.out.println("SuperAwesomeUnityRemoveSABannerAd " + unityName);

        if (temp != null){
            if (temp.getClass().getName().equals(RelativeLayout.class.getName())){
                RelativeLayout bad = (RelativeLayout)temp;
                ((ViewGroup)bad.getParent()).removeView(bad);
            }
        }
    }

    /**
     * Play an interstitial ad, using the following parameters
     * @param context the current context, might be an activity
     * @param placementId the placement Id, needed for the parseDictionaryIntoAd function
     * @param adJson the ad Json data used to generate the ad
     * @param unityName the unique name of the unity object that called this func
     * @param isParentalGateEnabled whether the parental gate is enabled or not
     */
    public static void SuperAwesomeUnitySAInterstitialAd(final Context context, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled){
        System.out.println("SuperAwesomeUnitySAInterstitialAd " + unityName);

        /** form the json object to parse */
        try {
            JSONObject dataJson = new JSONObject(adJson);

            if (dataJson != null) {

                SAAd ad = SAParser.parseDictionaryIntoAd(dataJson, placementId);

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

                    /** add to this map */
                    SAUnityManager.getInstance().setAdMap(unityName, interstitial);

                } else {
                    SendUnityMsg(unityName, "callback_adFailedToShow");
                }

            } else {
                SendUnityMsg(unityName, "callback_adFailedToShow");
            }
        } catch (JSONException e) {
            SendUnityMsg(unityName, "callback_adFailedToShow");
        }
    }

    /**
     * Closes an interstitial ad
     * @param unityName - the unity name of the interstitial ad
     */
    public static void SuperAwesomeUnityCloseSAInterstitialAd(final Context context, String unityName) {
        Object temp = SAUnityManager.getInstance().getAdMap(unityName);

        System.out.println("SuperAwesomeUnityCloseSAInterstitialAd " + unityName);

        if (temp != null){
            if (temp.getClass().getName().equals(SAInterstitialActivity.class.getName())){
                SAInterstitialActivity iad = (SAInterstitialActivity)temp;
                iad.close();
            }
        }
    }

    /**
     * Play a fullscreen video ad
     * @param context the context, might be an activity
     * @param placementId the placement id, needed for parseDictionaryIntoAd
     * @param adJson the ad Json used to render this ad
     * @param unityName the unique name of the unity object that sent this request
     * @param isParentalGateEnabled whether the parental gate should be enabled or not
     * @param shouldShowCloseButton whether to show the close button or not
     * @param shouldAutomaticallyCloseAtEnd whether the ad should automatically close at the end of it's runtime
     */
    public static void SuperAwesomeUnitySAVideoAd(final Context context, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled, final boolean shouldShowCloseButton, final boolean shouldAutomaticallyCloseAtEnd) {

        System.out.println("SuperAwesomeUnitySAVideoAd " + unityName);

        try {
            JSONObject dataJson = new JSONObject(adJson);

            if (dataJson != null) {

                SAAd ad = SAParser.parseDictionaryIntoAd(dataJson, placementId);

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

                    /** add to this map */
                    SAUnityManager.getInstance().setAdMap(unityName, video);

                } else {
                    SendUnityMsg(unityName, "callback_adFailedToShow");
                }

            } else {
                SendUnityMsg(unityName, "callback_adFailedToShow");
            }
        } catch (JSONException e) {
            SendUnityMsg(unityName, "callback_adFailedToShow");
        }
    }

    /**
     * Closes a video ad
     * @param unityName - the unity name of the video ad
     */
    public static void SuperAwesomeUnityCloseSAFullscreenVideoAd(final Context context, String unityName) {
        Object temp = SAUnityManager.getInstance().getAdMap(unityName);

        System.out.println("SuperAwesomeUnityCloseSAFullscreenVideoAd " + unityName);

        if (temp != null){
            if (temp.getClass().getName().equals(SAVideoActivity.class.getName())){
                SAVideoActivity vad = (SAVideoActivity)temp;
                vad.close();
            }
        }
    }

    /**
     * Private SASize object
     */
    private static class SASize {
        public int width = 0;
        public int height = 0;

        SASize (int w, int h){
            width = w;
            height = h;
        }
    }
}
