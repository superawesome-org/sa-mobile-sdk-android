package tv.superawesome.plugins.unity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.sdk.views.SAAdInterface;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAParentalGateInterface;

/**
 * Created by gabriel.coman on 13/05/16.
 */
public class SAUnityPlayBannerAd {

    /**
     * Aux function to get the correct rotation on Android
     * @param context the current context
     * @return 0 = portrait, 1 = landscape
     */
    private static int getOrientation(Context context){
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
     * Function that gets a banner's layout params
     * @param factor a calculated factor
     * @param screenSize the screen size
     * @param bannerSize the banner size
     * @param bannerPosition the banner position
     * @return a new set of layout params
     */
    private static RelativeLayout.LayoutParams getBannerLayoutParams(float factor, SAUtils.SASize screenSize, int bannerSize, int bannerPosition){
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

        /** create the layout params */
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(scaledWidth, scaledHeight);
        params2.leftMargin = (screenSize.width - scaledWidth) / 2;
        params2.topMargin = (bannerPosition == 0 ? 0 : (screenSize.height - scaledHeight));

        return params2;
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
            SAAd ad = new SAAd(dataJson);

            /** get current rotation */
            final int[] currentRotation = {getOrientation(context)};

            /** context to activity */
            final Activity activity = (Activity) context;

            /** get the current view group */
            ViewGroup current = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            /** start the banner ad */
            final SABannerAd bannerAd = new SABannerAd(context);

            /** get factor & screen size */
            SAUtils.SASize screenSize = SAUtils.getRealScreenSize(activity, false);
            final float factor = SAUtils.getScaleFactor(activity);

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
                    int newRotation = getOrientation(context);

                    if (newRotation != currentRotation[0]){
                        currentRotation[0] = newRotation;

                        /** calc scaling factor */
                        SAUtils.SASize screenSize = SAUtils.getRealScreenSize(activity, true);

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
            bannerAd.setAdListener(new SAAdInterface() {
                @Override
                public void adWasShown(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasShown");
                }

                @Override
                public void adFailedToShow(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
                }

                @Override
                public void adWasClosed(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasClosed");
                }

                @Override
                public void adWasClicked(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasClicked");
                }

                @Override
                public void adHasIncorrectPlacement(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adHasIncorrectPlacement");
                }
            });
            bannerAd.setParentalGateListener(new SAParentalGateInterface() {
                @Override
                public void parentalGateWasCanceled(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_parentalGateWasCanceled");
                }

                @Override
                public void parentalGateWasFailed(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_parentalGateWasFailed");
                }

                @Override
                public void parentalGateWasSucceded(int placementId) {
                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_parentalGateWasSucceded");
                }
            });

            /** start playing the banner */
            bannerAd.play();

            /** add to this map */
            SAUnityExtensionContext.getInstance().setAdMap(unityName, screenLayout);
        } catch (JSONException e) {
            SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
        }
    }

    /**
     * Removes a banner ad
     * @param unityName - the unity name of the banner ad
     */
    public static void SuperAwesomeUnityRemoveSABannerAd(final Context context, String unityName) {
        Object temp = SAUnityExtensionContext.getInstance().getAdMap(unityName);

        System.out.println("SuperAwesomeUnityRemoveSABannerAd " + unityName);

        if (temp != null){
            if (temp.getClass().getName().equals(RelativeLayout.class.getName())){
                RelativeLayout bad = (RelativeLayout)temp;
                ((ViewGroup)bad.getParent()).removeView(bad);
                SAUnityExtensionContext.getInstance().removeFromMap(unityName);
            }
        }
    }
}
