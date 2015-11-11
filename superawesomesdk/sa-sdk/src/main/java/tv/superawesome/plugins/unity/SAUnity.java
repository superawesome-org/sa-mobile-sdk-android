package tv.superawesome.plugins.unity;

import android.content.Context;

import tv.superawesome.sdk.*;
import tv.superawesome.sdk.activities.SAVideoActivity;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.video.SAVideoViewListener;

import com.unity3d.player.*;

/**
 * Created by gabriel.coman on 11/11/15.
 */
public class SAUnity {

    public static void playVideo(Context c, final String adName, String placementId, boolean testMode, boolean parentalGate) {
        SAVideoActivity.start(c, placementId, Boolean.toString(testMode), Boolean.toString(parentalGate), new SAVideoViewListener() {
            @Override
            public void onAdStart() {
                System.out.println("ANDROID - Sending message to videoAdStartedPlaying");
                UnityPlayer.UnitySendMessage(adName, "videoAdStartedPlaying", "");
            }

            @Override
            public void onAdPause() {

            }

            @Override
            public void onAdResume() {

            }

            @Override
            public void onAdFirstQuartile() {

            }

            @Override
            public void onAdMidpoint() {

            }

            @Override
            public void onAdThirdQuartile() {

            }

            @Override
            public void onAdComplete() {
                System.out.println("ANDROID - Sending message to videoAdStoppedPlaying");
                UnityPlayer.UnitySendMessage(adName, "videoAdStoppedPlaying", "");
            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdSkipped() {

            }

            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {
                System.out.println("ANDROID - Sending message to videoAdLoaded");
                UnityPlayer.UnitySendMessage(adName, "videoAdLoaded", "");
            }

            @Override
            public void onAdError(String message) {
                System.out.println("ANDROID - Sending message to videoAdFailedToLoad");
                UnityPlayer.UnitySendMessage(adName, "videoAdFailedToLoad", "");
            }
        });
    }
}
