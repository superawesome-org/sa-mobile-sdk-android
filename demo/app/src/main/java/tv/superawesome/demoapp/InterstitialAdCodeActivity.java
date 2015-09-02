package tv.superawesome.demoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tv.superawesome.superawesomesdk.fragments.SAInterstitialFragment;
import tv.superawesome.superawesomesdk.models.SAAd;
import tv.superawesome.superawesomesdk.views.SAPlacementListener;


public class InterstitialAdCodeActivity extends ActionBarActivity {

    private static SAInterstitialFragment interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad_code);
        setResult(RESULT_OK);

        findViewById(R.id.stub_import).setVisibility(View.VISIBLE);
        interstitial = (SAInterstitialFragment)getSupportFragmentManager().findFragmentById(R.id.sa_interstitial);
        interstitial.setListener(new SAPlacementListener() {
            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {
                Log.d("Main APP", "TEST LOADED AD");
            }

            @Override
            public void onAdError(String message) {
                Log.d("Main APP", message);
            }
        });
    }

    public void showInterstitial(View view) {
        interstitial.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interstitial_ad_code, menu);
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
}
