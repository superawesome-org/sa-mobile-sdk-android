package tv.superawesome.demoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tv.superawesome.sdk.fragments.SABannerFragment;
import tv.superawesome.sdk.views.SAPlacementListener;
import tv.superawesome.sdk.models.SAAd;

public class BannerAdCodeActivity extends ActionBarActivity {

    private SABannerFragment bannerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad_code);
        setResult(RESULT_OK);


        findViewById(R.id.stub_import).setVisibility(View.VISIBLE);
        bannerFragment = (SABannerFragment)getSupportFragmentManager().findFragmentById(R.id.sa_banner);
        bannerFragment.setListener(new SAPlacementListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_banner_ad_code, menu);
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
