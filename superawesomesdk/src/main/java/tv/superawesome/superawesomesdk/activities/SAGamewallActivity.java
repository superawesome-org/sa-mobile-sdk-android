package tv.superawesome.superawesomesdk.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bee7.gamewall.GameWallImpl;
import com.bee7.gamewall.interfaces.Bee7GameWallManager;
import com.bee7.sdk.common.Reward;

import tv.superawesome.superawesomesdk.R;

public class SAGamewallActivity extends AppCompatActivity implements Bee7GameWallManager {

    private static final String TAG = "SA SDK - Gamewall";
    private GameWallImpl mGameWall;
    private boolean showRewardNotifications = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_gamewall);

        // Initialise the Bee7 GameWallImpl instance
        mGameWall = new GameWallImpl(this, this, "45A865F2-9D6D-11E4-89D3-123B93F75CBA");
        mGameWall.checkForClaimData(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sagamewall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onGiveReward(Reward reward) {
        Log.d(TAG, "Received reward: " + reward.toString());
        if (showRewardNotifications) {
            mGameWall.showReward(reward, this);
        }
        // Process received reward
        // userBalance += reward.getVirtualCurrencyAmount();
    }

    @Override
    public void onAvailableChange(boolean available) {
        if (available) {
            mGameWall.show(this);
        } else {
            // No offers available
        }
    }

    @Override
    public void onVisibleChange(boolean visible, boolean isGameWall) {
        Log.d(TAG, "onVisibleChange: " + visible);
    }


    @Override
    public boolean onGameWallWillClose() {
        finish();
        return true;
    }

    @Override
    public void onReportingId(String reportingId, long reportingIdTs) {

    }

    protected void onNewIntent(Intent intent) {
        if (mGameWall != null) {
        /*
        * Provide intent data to Bee7 GameWallImpl in order to claim data
        */
            mGameWall.checkForClaimData(intent);
        }
    }
    protected void onResume() {
        super.onResume();
        if (mGameWall != null) {
        /*
        * Call resume for Bee7 GameWallImpl
        */
            mGameWall.resume();
        }
    }

    protected void onPause() {
        if (mGameWall != null) {
        /*
        * Call pause for Bee7 GameWallImpl
        */
            mGameWall.pause();
        }
        super.onPause();
    }

    protected void onDestroy() {
        if (mGameWall != null) {
            mGameWall.destroy();
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        /*
        * Allow Bee7 GameWallImpl to handle back press
        */
        if (mGameWall != null && !mGameWall.onBackPressed()) {
            Log.d(TAG, "Back pressed");
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        /*
        * Allow Bee7 GameWallImpl to handle orientation change.
        * If app doesn't support orientation changes, remove this method.
        */
        if (mGameWall != null) {
            mGameWall.updateView();
        }
    }


}
