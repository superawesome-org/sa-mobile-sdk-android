//package tv.superawesome.plugins.unity;
//
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import tv.superawesome.lib.samodelspace.SAAd;
//import tv.superawesome.sdk.views.SAAdInterface;
//import tv.superawesome.sdk.views.SAFullscreenVideoAd;
//import tv.superawesome.sdk.views.SAParentalGateInterface;
//import tv.superawesome.sdk.views.SAVideoAdInterface;
//
///**
// * Created by gabriel.coman on 13/05/16.
// */
//public class SAUnityPlayFullscreenVideoAd {
//
//    /**
//     * Play a fullscreen video ad
//     * @param context the context, might be an activity
//     * @param placementId the placement id, needed for parseDictionaryIntoAd
//     * @param adJson the ad Json used to render this ad
//     * @param unityName the unique name of the unity object that sent this request
//     * @param isParentalGateEnabled whether the parental gate should be enabled or not
//     * @param shouldShowCloseButton whether to show the close button or not
//     * @param shouldAutomaticallyCloseAtEnd whether the ad should automatically close at the end of it's runtime
//     */
//    public static void SuperAwesomeUnitySAVideoAd(final Context context, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled, final boolean shouldShowCloseButton, final boolean shouldAutomaticallyCloseAtEnd, final boolean shouldShowSmallClickButton, final boolean shouldLockOrientation, final int lockOrientation) {
//
//        System.out.println("SuperAwesomeUnitySAVideoAd " + unityName);
//
//        try {
//            JSONObject dataJson = new JSONObject(adJson);
//            SAAd ad = new SAAd(dataJson);
//
//            /** create the video */
//            SAFullscreenVideoAd video = new SAFullscreenVideoAd(context);
//
//            /** set the ad */
//            video.setAd(ad);
//
//            /** parametrise the video */
//            video.setIsParentalGateEnabled(isParentalGateEnabled);
//            video.setShouldShowCloseButton(shouldShowCloseButton);
//            video.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);
//            video.setShouldShowSmallClickButton(shouldShowSmallClickButton);
//            video.setShouldLockOrientation(shouldLockOrientation);
//            if (lockOrientation == 1){
//                video.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else if (lockOrientation == 2){
//                video.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//
//            /** add listeners */
//            video.setAdListener(new SAAdInterface() {
//                @Override
//                public void adWasShown(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adWasShown");
//                }
//
//                @Override
//                public void adFailedToShow(int placementId) {
//                    SAUnityExtensionContext.getInstance().removeFromMap(placementId);
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
//                }
//
//                @Override
//                public void adWasClosed(int placementId) {
//                    SAUnityExtensionContext.getInstance().removeFromMap(placementId);
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
//            video.setVideoAdListener(new SAVideoAdInterface() {
//                @Override
//                public void adStarted(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adStarted");
//                }
//
//                @Override
//                public void videoStarted(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_videoStarted");
//                }
//
//                @Override
//                public void videoReachedFirstQuartile(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_videoReachedFirstQuartile");
//                }
//
//                @Override
//                public void videoReachedMidpoint(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_videoReachedMidpoint");
//                }
//
//                @Override
//                public void videoReachedThirdQuartile(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_videoReachedThirdQuartile");
//                }
//
//                @Override
//                public void videoEnded(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_videoEnded");
//                }
//
//                @Override
//                public void adEnded(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adEnded");
//                }
//
//                @Override
//                public void allAdsEnded(int placementId) {
//                    SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_allAdsEnded");
//                }
//            });
//            video.setParentalGateListener(new SAParentalGateInterface() {
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
//            /** finally play the video */
//            video.play();
//
//            /** add to this map */
//            SAUnityExtensionContext.getInstance().setAdMap(unityName, video);
//        } catch (JSONException e) {
//            SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
//        }
//    }
//
//    /**
//     * Closes a video ad
//     * @param unityName - the unity name of the video ad
//     */
//    public static void SuperAwesomeUnityCloseSAFullscreenVideoAd(final Context context, String unityName) {
//        Object temp = SAUnityExtensionContext.getInstance().getAdMap(unityName);
//
//        System.out.println("SuperAwesomeUnityCloseSAFullscreenVideoAd " + unityName);
//
//        if (temp != null){
//            if (temp.getClass().getName().equals(SAFullscreenVideoAd.class.getName())){
//                SAFullscreenVideoAd vad = (SAFullscreenVideoAd)temp;
//                vad.close();
//            }
//        }
//    }
//}
