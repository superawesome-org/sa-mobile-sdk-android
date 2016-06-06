//package tv.superawesome.plugins.air;
//
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.util.Log;
//
//import com.adobe.fre.FREContext;
//import com.adobe.fre.FREFunction;
//import com.adobe.fre.FREInvalidObjectException;
//import com.adobe.fre.FREObject;
//import com.adobe.fre.FRETypeMismatchException;
//import com.adobe.fre.FREWrongThreadException;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import tv.superawesome.sdk.models.SAAd;
//import tv.superawesome.sdk.parser.SAParser;
//import tv.superawesome.sdk.views.SAAdInterface;
//import tv.superawesome.sdk.views.SAParentalGateInterface;
//import tv.superawesome.sdk.views.SAFullscreenVideoAd;
//import tv.superawesome.sdk.views.SAVideoAdInterface;
//
///**
// * Created by gabriel.coman on 17/03/16.
// */
//public class SAAIRPlayFullscreenVideoAd implements FREFunction {
//    @Override
//    public FREObject call(final FREContext freContext, FREObject[] freObjects) {
//        /** setup vars with default values */
//        Log.d("AIREXT", "playFullscreenVideoAd");
//
//        if (freObjects.length == 9){
//            try {
//                /** get variables */
//                final String name = freObjects[0].getAsString();
//                final int placementId = freObjects[1].getAsInt();
//                final String adJson = freObjects[2].getAsString();
//                final boolean isParentalGateEnabled = freObjects[3].getAsBool();
//                final boolean shouldShowCloseButton = freObjects[4].getAsBool();
//                final boolean shouldAutomaticallyCloseAtEnd = freObjects[5].getAsBool();
//                final boolean shouldShowSmallClickButton = freObjects[6].getAsBool();
//                final boolean shouldLockOrientation = freObjects[7].getAsBool();
//                final int lockOrientation = freObjects[8].getAsInt();
//
//                final Context context = freContext.getActivity().getApplicationContext();
//
//                Log.d("AIREXT", "Meta: " + name + "/" + placementId + "/" + isParentalGateEnabled + "/" + shouldShowCloseButton + "/" + shouldAutomaticallyCloseAtEnd);
//                Log.d("AIREXT", "adJson: " + adJson);
//
//                try {
//                    JSONObject dataJson = new JSONObject(adJson);
//                    SAAd ad = new SAAd(dataJson);
//
//                    SAFullscreenVideoAd video = new SAFullscreenVideoAd(freContext.getActivity());
//                    video.setAd(ad);
//                    video.setIsParentalGateEnabled(isParentalGateEnabled);
//                    video.setShouldShowCloseButton(shouldShowCloseButton);
//                    video.setShouldAutomaticallyCloseAtEnd(shouldAutomaticallyCloseAtEnd);
//                    video.setShouldShowSmallClickButton(shouldShowSmallClickButton);
//                    video.setShouldLockOrientation(shouldLockOrientation);
//                    if (lockOrientation == 1){
//                        video.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    } else if (lockOrientation == 2){
//                        video.setLockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    }
//                    video.setAdListener(new SAAdInterface() {
//                        @Override
//                        public void adWasShown(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adWasShown\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void adFailedToShow(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adFailedToShow\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void adWasClosed(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adWasClosed\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void adWasClicked(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adWasClicked\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void adHasIncorrectPlacement(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adHasIncorrectPlacement\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//                    });
//                    video.setVideoAdListener(new SAVideoAdInterface() {
//                        @Override
//                        public void adStarted(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adStarted\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void videoStarted(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"videoStarted\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void videoReachedFirstQuartile(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"videoReachedFirstQuartile\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void videoReachedMidpoint(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"videoReachedMidpoint\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void videoReachedThirdQuartile(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"videoReachedThirdQuartile\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void videoEnded(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"videoEnded\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void adEnded(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"adEnded\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void allAdsEnded(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"allAdsEnded\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//                    });
//                    video.setParentalGateListener(new SAParentalGateInterface() {
//                        @Override
//                        public void parentalGateWasCanceled(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"parentalGateWasCanceled\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void parentalGateWasFailed(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"parentalGateWasFailed\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//
//                        @Override
//                        public void parentalGateWasSucceded(int placementId) {
//                            String meta = "{\"name\":\"" + name + "\", \"func\":\"parentalGateWasSucceded\"}";
//                            freContext.dispatchStatusEventAsync(meta, "");
//                        }
//                    });
//                    video.play();
//                } catch (JSONException e) {
//                    String meta = "{\"name\":\"" + name + "\", \"func\":\"adFailedToShow\"}";
//                    freContext.dispatchStatusEventAsync(meta, "");
//                }
//
//            } catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException  e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//}
