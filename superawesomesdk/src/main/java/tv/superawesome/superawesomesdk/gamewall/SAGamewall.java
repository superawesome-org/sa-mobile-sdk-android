package tv.superawesome.superawesomesdk.gamewall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Log;

import com.bee7.gamewall.GameWallImpl;
import com.bee7.gamewall.interfaces.Bee7GameWallManager;
import com.bee7.sdk.common.Reward;
import com.bee7.sdk.publisher.appoffer.AppOffer;

import java.util.List;

import tv.superawesome.superawesomesdk.AdLoaderListener;
import tv.superawesome.superawesomesdk.AdManager;
import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.models.SAAd;
import tv.superawesome.superawesomesdk.models.SAGamewallAd;
import tv.superawesome.superawesomesdk.views.SAPlacementView;

/**
 * Created by connor.leigh-smith on 02/09/15.
 */
public class SAGamewall {

    private static final String TAG = "SAGamewall";
    private GameWallImpl gameWall;
    private String placementID;
    private AdManager adManager;
    private SAGamewall.Listener listener;
    private SAGamewallAd loadedAd;
    private Bee7GameWallManager gamewallManager;
    private Activity activity;

    public SAGamewall(Activity a, SAGamewall.Listener l, String placementID) {
        this.activity = a;
        this.listener = l;
        this.placementID = placementID;
        this.adManager = SuperAwesome.createAdManager();

        this.gamewallManager = new Bee7GameWallManager() {
            @Override
            public void onGiveReward(Reward reward) {
                Log.d(TAG, "onGiveReward: " + reward);
                gameWall.showReward(reward, activity);
                if (listener != null) listener.onGiveReward(reward.getVirtualCurrencyAmount());
            }

            @Override
            public void onAvailableChange(boolean available) {
                Log.d(TAG, "onAvailableChange: " + available);
                if (listener != null) listener.onAvailableChange(available);
            }

            @Override
            public void onVisibleChange(boolean visible, boolean isGameWall) {
                Log.d(TAG, "onVisibleChange: " + visible);
            }

            @Override
            public boolean onGameWallWillClose() {
                Log.d(TAG, "onGameWallWillClose");
                return true;
            }

            @Override
            public void onReportingId(String reportingId, long reportingIdTs) {

            }
        };
        this.loadAd();
    }

    public interface Listener {
        void onAdError(String message);
        void onAdLoaded(SAAd ad);
        void onGiveReward(int amount);
        void onAvailableChange(boolean available);
    }

    private boolean checkAppPermissions() {
        int res = activity.checkCallingOrSelfPermission("android.permission.INTERNET");
        if (res != PackageManager.PERMISSION_GRANTED) {
            String message = "Error: Your app does not have a required permission: INTERNET. Not requesting ad.";
            if (this.listener != null) this.listener.onAdError(message);
            Log.d(TAG, message);
            return false;
        }
        res = activity.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE");
        if (res != PackageManager.PERMISSION_GRANTED) {
            String message = "Error: Your app does not have a required permission: ACCESS_NETWORK_STATE. Not requesting ad.";
            if (this.listener != null) this.listener.onAdError(message);
            Log.d(TAG, message);
            return false;
        }
        return true;
    }

    public void loadAd() {
        if (!this.checkAppPermissions()) {
            return;
        }
        try {
            this.adManager.getAd(this.placementID, false, new AdLoaderListener() {
                @Override
                public void onBeginLoad(String url) {

                }

                @Override
                public void onError(String message) {
                    Log.d(TAG, "Error:" + message);
                    if (listener != null) listener.onAdError(message);
                }

                @Override
                public void onLoaded(SAAd superAwesomeAd) {
                    if (listener != null) listener.onAdLoaded(superAwesomeAd);
                    if (superAwesomeAd.format != SAAd.Format.GAMEWALL) {
                        if (listener != null) listener.onAdError("Wrong ad format; must be a gamewall placement!");
                        return;
                    }
                    loadedAd = (SAGamewallAd)superAwesomeAd;
                    Log.d(TAG, "SAAd loaded");
                    try {
                        if (!superAwesomeAd.error) {
                            gameWall = new GameWallImpl(activity, gamewallManager, loadedAd.apiKey);
                        } else {
                            if (listener != null) listener.onAdError(superAwesomeAd.error_message);
                            Log.d(TAG, "Error: " + superAwesomeAd.error_message);
                        }
                    } catch (Exception e) {
                        if (listener != null) listener.onAdError(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            if (listener != null) listener.onAdError(e.getMessage());
        }
    }

    public void show() {
        if (this.gameWall != null) this.gameWall.show(activity);
    }

    public void checkForClaimData(Intent intent) {
        if (this.gameWall != null) this.gameWall.checkForClaimData(intent);
    }

    public void resume() {
        if (this.gameWall != null) this.gameWall.resume();
    }

    public void pause() {
        if (this.gameWall != null) this.gameWall.pause();
    }

    public void destroy() {
        if (this.gameWall != null) this.gameWall.destroy();
    }

    public boolean onBackPressed() {
        return this.gameWall != null && this.gameWall.onBackPressed();
    }

    public void updateView() {
        if (this.gameWall != null) this.gameWall.updateView();
    }
}
