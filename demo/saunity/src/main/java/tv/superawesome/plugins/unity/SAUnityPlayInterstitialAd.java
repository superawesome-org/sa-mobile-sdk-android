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
//import tv.superawesome.sdk.views.SAInterstitialAd;
//import tv.superawesome.sdk.views.SAParentalGateInterface;
//
///**
// * Created by gabriel.coman on 13/05/16.
// */
//public class SAUnityPlayInterstitialAd {
//
//    /**
//     * Play an interstitial ad, using the following parameters
//     * @param context the current context, might be an activity
//     * @param placementId the placement Id, needed for the parseDictionaryIntoAd function
//     * @param adJson the ad Json data used to generate the ad
//     * @param unityName the unique name of the unity object that called this func
//     * @param isParentalGateEnabled whether the parental gate is enabled or not
//     */
//    public static void SuperAwesomeUnitySAInterstitialAd(final Context context, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled, final boolean shouldLockOrientation, final int lockOrientation){
//        System.out.println("SuperAwesomeUnitySAInterstitialAd " + unityName);
//
//        /** form the json object to parse */
//        try {
//            JSONObject dataJson = new JSONObject(adJson);
//            SAAd ad = new SAAd(dataJson);
//
//            /** create the interstitial */
//            SAInterstitialAd interstitial = new SAInterstitialAd(context);
//
//            /** set the ad data */
//            interstitial.setAd(ad);
//
//            /** parametrise the interstitial */
//            interstitial.setIsParentalGateEnabled(isParentalGateEnabled);
//            interstitial.setShouldLockOrientation(shouldLockOrientation);
//            if (lockOrientation == 1){
//                interstitial.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else if (lockOrientation == 2){
//                interstitial.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//
//            /** add listeners */
//            interstitial.setAdListener(new SAAdInterface() {
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
//            interstitial.setParentalGateListener(new SAParentalGateInterface() {
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
//            /** start playing the interstitial */
//            interstitial.play();
//
//            /** add to this map */
//            SAUnityExtensionContext.getInstance().setAdMap(unityName, interstitial);
//        } catch (JSONException e) {
//            SAUnityExtension.SendUnityMsg(unityName, placementId, "callback_adFailedToShow");
//        }
//    }
//
//    /**
//     * Closes an interstitial ad
//     * @param unityName - the unity name of the interstitial ad
//     */
//    public static void SuperAwesomeUnityCloseSAInterstitialAd(final Context context, String unityName) {
//        Object temp = SAUnityExtensionContext.getInstance().getAdMap(unityName);
//
//        System.out.println("SuperAwesomeUnityCloseSAInterstitialAd " + unityName);
//
//        if (temp != null){
//            if (temp.getClass().getName().equals(SAInterstitialAd.class.getName())){
//                SAInterstitialAd iad = (SAInterstitialAd)temp;
//                iad.close();
//            }
//        }
//    }
//}
