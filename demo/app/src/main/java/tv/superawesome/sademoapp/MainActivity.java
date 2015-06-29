package tv.superawesome.sademoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import tv.superawesome.superawesomesdk.SuperAwesome;
import tv.superawesome.superawesomesdk.view.BannerView;
import tv.superawesome.superawesomesdk.view.BannerViewListener;


public class MainActivity extends Activity {

    BannerView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(SuperAwesome.getVersion());

        this.bv = new BannerView(this, "5662");
        this.bv.setListener(new BannerViewListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdError(String message) {

            }
        });
//        this.bv.setLayoutParams(new FrameLayout.LayoutParams(500,500));

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

//    @Override
//    public void mraidNativeFeatureCallTel(String url) {
//
//    }
//
//    @Override
//    public void mraidNativeFeatureCreateCalendarEvent(String eventJSON) {
//
//    }
//
//    @Override
//    public void mraidNativeFeaturePlayVideo(String url) {
//
//    }
//
//    @Override
//    public void mraidNativeFeatureOpenBrowser(String url) {
//
//    }
//
//    @Override
//    public void mraidNativeFeatureStorePicture(String url) {
//
//    }
//
//    @Override
//    public void mraidNativeFeatureSendSms(String url) {
//
//    }
//
//    @Override
//    public void mraidViewLoaded(MRAIDView mraidView) {
//
//    }
//
//    @Override
//    public void mraidViewExpand(MRAIDView mraidView) {
//
//    }
//
//    @Override
//    public void mraidViewClose(MRAIDView mraidView) {
//
//    }
//
//    @Override
//    public boolean mraidViewResize(MRAIDView mraidView, int width, int height, int offsetX, int offsetY) {
//        return false;
//    }
}
