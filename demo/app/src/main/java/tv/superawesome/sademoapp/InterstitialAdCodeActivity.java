package tv.superawesome.sademoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.model.Ad;
import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.InterstitialView;
import tv.superawesome.superawesomesdk.view.PlacementViewListener;


public class InterstitialAdCodeActivity extends ActionBarActivity {

    InterstitialView interstitialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad_code);
        setResult(RESULT_OK);

        interstitialView = SuperAwesome.createInterstitialView(this, "5692");
        interstitialView.enableTestMode();
        interstitialView.setListener(new PlacementViewListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("Main APP", "Loaded ad");
            }

            @Override
            public void onAdError(String message) {
                Log.d("Main APP", message);
            }
        });
        interstitialView.loadAd();
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        rootView.addView(interstitialView);
    }

    public void showInterstitial(View view) {
        interstitialView.show();
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
