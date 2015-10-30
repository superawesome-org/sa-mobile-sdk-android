package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperAwesome.getInstance().setConfigurationProduction();
        SuperAwesome.getInstance().enableTestMode();

        SALoader.getInstance().loadAd(21022, new SALoaderListener() {
            @Override
            public void didPreloadAd(SAAd ad, int placementId) {
                System.out.println(ad.placementId);
                System.out.println(ad.creative.creativeId);
                System.out.println(ad.creative.clickURL);
                System.out.println(ad.creative.details.video);
                System.out.println(ad.creative.details.vast);
                System.out.println(ad.creative.format);
            }

            @Override
            public void didFailToPreloadAd(int placementId) {
                System.out.println("FAILURE");
            }
        });

//        HashMap<String, Object> m = new HashMap<>();
//        m.put("test", true);
//        SANetwork.sendGET("https://ads.superawesome.tv/v2/ad/24532", m, new SANetListener() {
//            @Override
//            public void success(Object data) {
//                System.out.println("GET " + data);
//
//                JsonParser p = new JsonParser();
//                JsonObject jo = p.parse(data.toString()).getAsJsonObject();
//                System.out.println(jo.get("creative"));
//                System.out.println(jo.get("line_item_id"));
//                System.out.println(jo.get("whazza"));
//
//            }
//
//            @Override
//            public void failure() {
//                System.out.println("failure");
//            }
//        });
//
//        HashMap<String, Object> m2 = new HashMap<>();
//        m2.put("placement", "24532");
//        m2.put("line_item", "26050");
//        m2.put("creative", "23350");
//        m2.put("type", "viewable_impression");
//
//        SANetwork.sendPOST("https://ads.superawesome.tv/v2/event", m2, new SANetListener() {
//            @Override
//            public void success(Object data) {
//                System.out.println("POST: " + data);
//            }
//
//            @Override
//            public void failure() {
//                System.out.println("failure");
//            }
//        });

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
//                        SAVideoActivity.start(MainActivity.this, "21022", "false", "true");
                        break;
                    case 7:
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
