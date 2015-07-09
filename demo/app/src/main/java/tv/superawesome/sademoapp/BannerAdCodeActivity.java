package tv.superawesome.sademoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.model.Ad;
import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.PlacementViewListener;


public class BannerAdCodeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad_code);
        setResult(RESULT_OK);

        BannerView bannerView = SuperAwesome.createBannerView(this, "5687");
        bannerView.enableTestMode();
        bannerView.setListener(new PlacementViewListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("Main APP", "Loaded ad");
            }

            @Override
            public void onAdError(String message) {
                Log.d("Main APP", message);
            }
        });
        bannerView.loadAd();
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        rootView.addView(bannerView);
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
