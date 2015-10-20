package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.activities.SAGamewallActivity;
import tv.superawesome.sdk.activities.SAVideoActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(SuperAwesome.getVersion());

        String[] ads = {
                "Banner ad - code",
                "Banner ad - XML",
                "Interstitial - code",
                "Interstitial - XML",
                "Video ad - code",
                "Video ad - XML",
                "Video ad - fullscreen activity",
                "Gamewall",
                "All ads in one activity"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, ads);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, BannerAdCodeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, BannerAdXmlActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, InterstitialAdCodeActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, InterstitialAdXmlActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, VideoAdCodeActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, VideoAdXmlActivity.class));
                        break;
                    case 6:
                        SAVideoActivity.start(MainActivity.this, "21022", "false");
//                        SAVideoActivity
                        break;
                    case 7:
//                        startActivity(new Intent(MainActivity.this, SAGamewallActivity.class));
                        startActivity(new Intent(MainActivity.this, GamewallActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, AllAdsActivity.class));
                        break;
                }

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            };
        });
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
