//package tv.superawesome.plugins.unity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import tv.superawesome.lib.sautils.SAUtils;
//import tv.superawesome.lib.samodelspace.SAAd;
//import tv.superawesome.sdk.views.SAAdInterface;
//import tv.superawesome.sdk.views.SABannerAd;
//import tv.superawesome.sdk.views.SAParentalGateInterface;
//
///**
// * Created by gabriel.coman on 13/05/16.
// */
//public class SAUnityPlayBannerAd {
//
//    private static int getBannerHeight(int screenWidth, int screenHeight, float factor, int bannerSize) {
//        /// calc actual banner W & H
//        int width = 0, height = 0;
//        if      (bannerSize == 1) { width = 300; height = 50;  }
//        else if (bannerSize == 2) { width = 728; height = 90;  }
//        else if (bannerSize == 3) { width = 300; height = 250; }
//        else                      { width = 320; height = 50;  }
//
//        // scale it according to the factor
//        int scaledWidth = (int)(factor * width);
//        int scaledHeight = (int)(factor * height);
//
//        // make sure it's not bigger than the screen
//        if (scaledWidth > screenWidth) {
//            scaledHeight = (screenWidth * scaledHeight) / scaledWidth;
//        }
//
//        // but not bigger than 15% of the screen's height
//        if (scaledHeight > 0.15 * screenHeight) {
//            scaledHeight = (int)(0.15 * screenHeight);
//        }
//
//        // scale it
//        return scaledHeight;
//    }
//
//    /**
//     * Creates a banner ad, based on the following parameters
//     * @param context the current context (activity, etc)
//     * @param placementId the placement Id is needed by the parseDictionaryIntoAd function
//     * @param adJson the actual ad json data
//     * @param unityName the unique name of the unity object that sent this requrst
//     * @param position the position: 0 = top, 1 = bottom
//     * @param size the size of the banner: 0 = 320x50, 1 = 300x50, 2 = 728x90, 3 = 300x250
//     * @param isParentalGateEnabled whether the parental gate is enabled or not
//     */
//    public static void SuperAwesomeUnitySABannerAd(final Context context, int placementId, String adJson, final String unityName, final int position, final int size, final int color, final boolean isParentalGateEnabled) {
//        System.out.println("SuperAwesomeUnitySABannerAd " + unityName);
//
//        /** form the json object to parse */
//        try {
//            JSONObject dataJson = new JSONObject(adJson);
//            SAAd ad = new SAAd(dataJson);
//
//            /** context to activity */
//            final Activity activity = (Activity) context;
//
//            /** get the current view group */
//            ViewGroup current = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//
//            /** start the banner ad */
//            final SABannerAd bannerAd = new SABannerAd(context);
//
//            RelativeLayout screenLayout = new RelativeLayout(context);
//            screenLayout.setBackgroundColor(Color.TRANSPARENT);
//            screenLayout.setGravity(position == 0 ? Gravity.TOP : Gravity.BOTTOM);
//            screenLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//            Display getOrient = ((Activity)context).getWindowManager().getDefaultDisplay();
//            DisplayMetrics metrics = new DisplayMetrics();
//            getOrient.getMetrics(metrics);
//            int width = metrics.widthPixels;
//            int height = metrics.heightPixels;
//            int smallDimension = (width > height ? height : width);
//            final int bigDimension = (width < height ? height : width);
//
//            SAUtils.SASize screenSize = SAUtils.getRealScreenSize(activity, false);
//
//            float factor = SAUtils.getScaleFactor(activity);
//            int scaledBannerHeight = getBannerHeight(screenSize.width, screenSize.height, factor, size);
//
//            bannerAd.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, scaledBannerHeight));
//            bannerAd.setAd(ad);
//            if (color == 0) {
//                bannerAd.setBackgroundColor(Color.TRANSPARENT);
//            } else {
//                bannerAd.setBackgroundColor(Color.rgb(191, 191, 191));
//            }
//            bannerAd.play();
//            bannerAd.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                @Override
//                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//                    if (bottom != oldBottom || right != oldRight) {
//                        bannerAd.resizeToSize(right, bottom);
//                    }
//                }
//            });
//
//            /** parametrize it */
//            bannerAd.setIsParentalGateEnabled(isParentalGateEnabled);
//
//            /** set the listener */
//            bannerAd.setAdListener(new SAAdInterface() {
//                @Override
//                public void adWasShown(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasShown");
//                }
//
//                @Override
//                public void adFailedToShow(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
//                }
//
//                @Override
//                public void adWasClosed(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasClosed");
//                }
//
//                @Override
//                public void adWasClicked(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasClicked");
//                }
//
//                @Override
//                public void adHasIncorrectPlacement(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adHasIncorrectPlacement");
//                }
//            });
//            bannerAd.setParentalGateListener(new SAParentalGateInterface() {
//                @Override
//                public void parentalGateWasCanceled(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_parentalGateWasCanceled");
//                }
//
//                @Override
//                public void parentalGateWasFailed(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_parentalGateWasFailed");
//                }
//
//                @Override
//                public void parentalGateWasSucceded(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_parentalGateWasSucceded");
//                }
//            });
//
//            screenLayout.addView(bannerAd);
//            current.addView(screenLayout);
//
//            /** add to this map */
//            tv.superawesome.plugins.unity.SAUnityExtensionContext.getInstance().setAdMap(unityName, screenLayout);
//        } catch (JSONException e) {
//            tv.superawesome.plugins.unity.SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
//        }
//    }
//
//    /**
//     * Removes a banner ad
//     * @param unityName - the unity name of the banner ad
//     */
//    public static void SuperAwesomeUnityRemoveSABannerAd(final Context context, String unityName) {
//        Object temp = SAUnityExtensionContext.getInstance().getAdMap(unityName);
//
//        System.out.println("SuperAwesomeUnityRemoveSABannerAd " + unityName);
//
//        if (temp != null){
//            if (temp.getClass().getName().equals(RelativeLayout.class.getName())){
//                RelativeLayout bad = (RelativeLayout)temp;
//                ((ViewGroup)bad.getParent()).removeView(bad);
//                SAUnityExtensionContext.getInstance().removeFromMap(unityName);
//            }
//        }
//    }
//}