package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.views.SAVideoActivity;

// import com.unity3d.player.*;

/**
 * Created by gabriel.coman on 13/01/16.
 */
public class SAVideoActivityUnityLinker {

//    public static void start(final Context c, final String adName, String placementId, boolean testMode, final boolean parentalGate) {
//        /** make sure this is valid */
//        if (SAApplication.getSAApplicationContext() == null){
//            SAApplication.setSAApplicationContext(c);
//        }
//
//        /** test only */
//        // SuperAwesome.getInstance().setConfigurationStaging();
//
//        /** get placement as Id */
//        int placement = Integer.parseInt(placementId);
//
//        /** enable or disable test mode before loading */
//        if(testMode){
//            SuperAwesome.getInstance().enableTestMode();
//        } else {
//            SuperAwesome.getInstance().disableTestMode();
//        }
//
//        SALoader.loadAd(placement, new SALoaderListener() {
//            @Override
//            public void didLoadAd(SAAd ad) {
//                /** call to unity */
//                System.out.println("ANDROID - Sending message to videoAdLoaded");
//                UnityPlayer.UnitySendMessage(adName, "videoAdLoaded", "");
//
//                /** create ad */
//                SAVideoActivity fvad = new SAVideoActivity(c);
//                fvad.setAd(ad);
//                fvad.setIsParentalGateEnabled(parentalGate);
//                fvad.setShouldAutomaticallyCloseAtEnd(true);
//                fvad.setShouldShowCloseButton(true);
//                fvad.setAdListener(new SAAdListener() {
//                    @Override
//                    public void adWasShown(int placementId) {
//                        System.out.println("ANDROID - Sending message to videoAdStartedPlaying");
//                        UnityPlayer.UnitySendMessage(adName, "videoAdStartedPlaying", "");
//                    }
//
//                    @Override
//                    public void adFailedToShow(int placementId) {
//                        System.out.println("ANDROID - Sending message to videoAdFailedToPlay");
//                        UnityPlayer.UnitySendMessage(adName, "videoAdFailedToPlay", "");
//                    }
//
//                    @Override
//                    public void adWasClosed(int placementId) {
//                        System.out.println("ANDROID - Sending message to videoAdStoppedPlaying");
//                        UnityPlayer.UnitySendMessage(adName, "videoAdStoppedPlaying", "");
//                    }
//
//                    @Override
//                    public void adWasClicked(int placementId) {
//                        System.out.println("ANDROID - Sending message to videoAdClicked");
//                        UnityPlayer.UnitySendMessage(adName, "videoAdClicked", "");
//                    }
//
//                    @Override
//                    public void adHasIncorrectPlacement(int placementId) {
//                        System.out.println("ANDROID - Sending message to videoAdFailedToPlay");
//                        UnityPlayer.UnitySendMessage(adName, "videoAdFailedToPlay", "");
//                    }
//                });
//
//                /** play the ad */
//                fvad.play();
//            }
//
//            @Override
//            public void didFailToLoadAdForPlacementId(int placementId) {
//                System.out.println("ANDROID - Sending message to videoAdFailedToLoad");
//                UnityPlayer.UnitySendMessage(adName, "videoAdFailedToLoad", "");
//            }
//        });
//    }

}
