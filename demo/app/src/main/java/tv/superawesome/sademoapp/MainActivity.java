package tv.superawesome.sademoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.view.Ad;
import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.BannerViewListener;


public class MainActivity extends Activity {

    BannerView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(SuperAwesome.getVersion());

//        this.bv = SuperAwesome.createBannerView(this, "5662");
//        this.bv = SuperAwesome.createBannerView(this, "5222");
        this.bv = SuperAwesome.createBannerView(this, "5687");
        this.bv.enableTestMode();
        this.bv.setListener(new BannerViewListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("Main APP", "Loaded ad");
            }

            @Override
            public void onAdError(String message) {
                Log.d("Main APP", message);
            }
        });
        this.bv.loadAd();
        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.relative_layout);
        rootView.addView(this.bv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
