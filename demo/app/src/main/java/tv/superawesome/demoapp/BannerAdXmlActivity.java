package tv.superawesome.demoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import tv.superawesome.superawesomesdk.fragments.SABannerFragment;
import tv.superawesome.superawesomesdk.models.SAAd;
import tv.superawesome.superawesomesdk.views.SAPlacementListener;


public class BannerAdXmlActivity extends ActionBarActivity {

    private SABannerFragment banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad_xml);
        setResult(RESULT_OK);

        this.banner = (SABannerFragment)getSupportFragmentManager().findFragmentById(R.id.sa_banner);
        banner.setListener(new SAPlacementListener() {
            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {

            }

            @Override
            public void onAdError(String message) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_banner_ad_xml, menu);
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
