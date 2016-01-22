package tv.superawesome.plugins.unity;


import android.content.Context;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.*;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Parser.SAParser;
import tv.superawesome.sdk.data.Parser.SAParserListener;
import tv.superawesome.sdk.data.Validator.SAValidator;
import tv.superawesome.sdk.listeners.SAAdListener;
import tv.superawesome.sdk.listeners.SAParentalGateListener;
import tv.superawesome.sdk.views.SAInterstitialActivity;

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
        SendUnityMsgPayload(unityAd, callback, "", "");
    }

    public static void SuperAwesomeUnityLoadAd(final Context c, final String unityName, int placementId, boolean isTestingEnabled) {
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

    public static void SuperAwesomeUnitySABannerAd(final Context c, int placementId, String adJson, String unityName, int position, int size, boolean isParentalGateEnabled) {

    }

    public static void SuperAwesomeUnitySAInterstitialAd(final Context c, int placementId, String adJson, final String unityName, final boolean isParentalGateEnabled){
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

                            SAInterstitialActivity.start(c, ad, isParentalGateEnabled, new SAAdListener() {
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
                            }, new SAParentalGateListener() {
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

    public static void SuperAwesomeUnitySAVideoAd(final Context c, int placementId, String adJson, String unityName, boolean isParentalGateEnabled, boolean shouldShowCloseButton, boolean shouldAutomaticallyCloseAtEnd) {

    }
}
