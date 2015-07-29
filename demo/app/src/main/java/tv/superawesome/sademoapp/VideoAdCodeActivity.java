package tv.superawesome.sademoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import tv.superawesome.superawesomesdk.fragments.SAVideoFragment;

public class VideoAdCodeActivity extends ActionBarActivity {

    private static SAVideoFragment videoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_ad_code);
        setResult(RESULT_OK);
//
//        if (videoFragment == null) {
//            videoFragment = SAVideoFragment.newInstance("5740", true, true);
//            videoFragment.setListener(new SAVideoViewListener() {
//                @Override
//                public void onAdStart() {
//
//                }
//
//                @Override
//                public void onAdPause() {
//
//                }
//
//                @Override
//                public void onAdResume() {
//
//                }
//
//                @Override
//                public void onAdFirstQuartile() {
//
//                }
//
//                @Override
//                public void onAdMidpoint() {
//
//                }
//
//                @Override
//                public void onAdThirdQuartile() {
//
//                }
//
//                @Override
//                public void onAdComplete() {
//                    Log.d("Main APP", "onAdComplete");
//                }
//
//                @Override
//                public void onAdClosed() {
//
//                }
//
//                @Override
//                public void onAdSkipped() {
//
//                }
//
//                @Override
//                public void onAdLoaded(SAAd superAwesomeAd) {
//
//                }
//
//                @Override
//                public void onAdError(String message) {
//
//                }
//            });
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.ad_placeholder, videoFragment);
//            fragmentTransaction.commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_ad_code, menu);
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
