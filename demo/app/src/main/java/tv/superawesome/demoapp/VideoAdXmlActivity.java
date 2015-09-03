package tv.superawesome.demoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import tv.superawesome.sdk.fragments.SAVideoFragment;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.views.video.SAVideoViewListener;

public class VideoAdXmlActivity extends ActionBarActivity {

    private static final String TAG = "Video XML Activity";
    private SAVideoFragment videoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_ad_xml);
        setResult(RESULT_OK);

        this.videoAd = (SAVideoFragment)getSupportFragmentManager().findFragmentById(R.id.sa_video_ad);
        this.videoAd.setListener(new SAVideoViewListener() {
            @Override
            public void onAdStart() {
                Log.d(TAG, "onAdStart");
            }

            @Override
            public void onAdPause() {
                Log.d(TAG, "onAdPause");
            }

            @Override
            public void onAdResume() {
                Log.d(TAG, "onAdResume");
            }

            @Override
            public void onAdFirstQuartile() {
                Log.d(TAG, "onAdFirstQuartile");
            }

            @Override
            public void onAdMidpoint() {
                Log.d(TAG, "onAdMidpoint");
            }

            @Override
            public void onAdThirdQuartile() {
                Log.d(TAG, "onAdThirdQuartile");
            }

            @Override
            public void onAdComplete() {
                Log.d(TAG, "onAdComplete");
                Toast toastMessage = Toast.makeText(getApplicationContext(), "Video Ad Completed", Toast.LENGTH_SHORT);
                toastMessage.show();
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed");
            }

            @Override
            public void onAdSkipped() {
                Log.d(TAG, "onAdSkipped");
            }

            @Override
            public void onAdLoaded(SAAd superAwesomeAd) {
                Log.d(TAG, "onAdLoaded");
            }

            @Override
            public void onAdError(String message) {
                Log.d(TAG, "onAdError");
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
