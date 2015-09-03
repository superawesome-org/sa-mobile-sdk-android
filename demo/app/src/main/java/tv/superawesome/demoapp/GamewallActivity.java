package tv.superawesome.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.games.Game;

import tv.superawesome.sdk.AdManager;
import tv.superawesome.sdk.gamewall.SAGamewall;
import tv.superawesome.sdk.models.SAAd;

public class GamewallActivity extends AppCompatActivity {

    private static final String TAG = "GameWall Activity";
    private SAGamewall gamewall;
    private Button gamewallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamewall);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GamewallActivity.this);
        final TextView textAmount = (TextView) findViewById(R.id.text_amount);
        textAmount.setText(String.valueOf(preferences.getInt("currency", 0)));

        SAGamewall.Listener gamewallListener = new SAGamewall.Listener() {
            @Override
            public void onAdError(String message) {
                Log.d(TAG, message);
            }

            @Override
            public void onAdLoaded(SAAd ad) {

            }

            @Override
            public void onGiveReward(int amount) {
                // Retrieve currency so far from preferences and add & save new amount rewarded.
                int currency = preferences.getInt("currency", 0);
                currency += amount;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("currency", currency);
                editor.apply();
                textAmount.setText(String.valueOf(currency));
            }

            @Override
            public void onAvailableChange(boolean available) {
                if (available) {
                    // Show button
                    gamewallButton.setVisibility(View.VISIBLE);
                } else {
                    // Hide button
                    gamewallButton.setVisibility(View.INVISIBLE);
                }
            }
        };

        gamewallButton = (Button)findViewById(R.id.gamewall_button);
        gamewall = new SAGamewall(this, gamewallListener, "5687");
    }

    public void showGamewall(View v) {
        this.gamewall.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gamewall, menu);
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
    protected void onNewIntent(Intent intent) {
        gamewall.checkForClaimData(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamewall.resume();
    }

    @Override
    protected void onPause() {
        gamewall.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        gamewall.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!gamewall.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        gamewall.updateView();
    }

}
