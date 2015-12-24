package tv.superawesome.demoapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tv.superawesome.lib.sanetwork.SASystem;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.savast.savastmanager.SAVASTManager;
import tv.superawesome.lib.savast.savastparser.SAVASTParser;
import tv.superawesome.lib.savast.savastparser.SAVASTParserListener;
import tv.superawesome.lib.savast.savastparser.models.SAVASTAd;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayer;
import tv.superawesome.lib.savast.savastplayer.SAVASTPlayerListener;
import tv.superawesome.lib.savast.saxml.SAXML;
import tv.superawesome.sdk.SuperAwesome;
import tv.superawesome.sdk.data.Loader.SALoader;
import tv.superawesome.sdk.data.Loader.SALoaderListener;
import tv.superawesome.sdk.data.Models.SAAd;

public class MainActivity extends Activity {

    private static final int CONTENT_VIEW_ID = 10101010;
    private SAVASTPlayer newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (savedInstanceState == null) {
            newFragment = new SAVASTPlayer();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(CONTENT_VIEW_ID, newFragment).commit();
            getFragmentManager().executePendingTransactions();
        }

//        SuperAwesome.getInstance().setConfigurationProduction();
//        SuperAwesome.getInstance().enableTestMode();
//        SALog.Log(SuperAwesome.getInstance().getSDKVersion());
//        SALog.Log(SASystem.getVerboseSystemDetails());

//        SALoader.loadAd(21022, new SALoaderListener() {
//            @Override
//            public void didLoadAd(SAAd ad) {
//                System.out.println(ad.placementId);
//                System.out.println(ad.creative.creativeId);
//                System.out.println(ad.creative.clickURL);
//                System.out.println(ad.creative.details.video);
//                System.out.println(ad.creative.details.vast);
//                System.out.println(ad.creative.format);
//            }
//
//            @Override
//            public void didFailToLoadAdForPlacementId(int placementId) {
//                System.out.println("FAILURE " + placementId);
//            }
//        });

        String inlineAdURL = "https://ads.superawesome.tv/v2/video/vast/30244/30369/30222/?sdkVersion=ios_3.3.1&rnd=430832557";
        String wrapperAdURL = "https://ads.superawesome.tv/v2/video/vast/30245/30370/30223/?sdkVersion=ios_3.3.1&rnd=489478975";


        SAVASTManager manager = new SAVASTManager(newFragment, null);
        manager.parseVASTURL(inlineAdURL);

//        SAVASTParser parser1 = new SAVASTParser();
//        parser1.execute(wrapperAdURL, new SAVASTParserListener() {
//            @Override
//            public void didParseVASTAndHasResponse(List<SAVASTAd> ads) {
//                SALog.Log("End of parsing: Ads[" + ads.size() + "]");
//                for (Iterator<SAVASTAd> i = ads.iterator(); i.hasNext(); ){
//                    SAVASTAd ad = i.next();
//                    ad.print();
//                }
//            }
//
//            @Override
//            public void didNotFindAnyValidAds() {
//
//            }
//
//            @Override
//            public void didFindInvalidVASTResponse() {
//
//            }
//        });
//
//        SAVASTParser parser2 = new SAVASTParser();
//        parser2.execute(inlineAdURL, new SAVASTParserListener() {
//            @Override
//            public void didParseVASTAndHasResponse(List<SAVASTAd> ads) {
//                SALog.Log("End of parsing: Ads[" + ads.size() + "]");
//                for (Iterator<SAVASTAd> i = ads.iterator(); i.hasNext(); ){
//                    SAVASTAd ad = i.next();
//                    ad.print();
//                }
//            }
//
//            @Override
//            public void didNotFindAnyValidAds() {
//
//            }
//
//            @Override
//            public void didFindInvalidVASTResponse() {
//
//            }
//        });

//        String[] ads = {
//                "Banner ad - code",
//                "Banner ad - XML",
//                "Interstitial - code",
//                "Interstitial - XML",
//                "Video ad - code",
//                "Video ad - XML",
//                "Video ad - fullscreen activity",
//                "Gamewall",
//                "All ads in one activity"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, ads);
//
//        ListView list = (ListView) findViewById(R.id.list);
//        list.setAdapter(adapter);
//
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                switch (position) {
//                    case 0:
//                        startActivity(new Intent(MainActivity.this, BannerAdCodeActivity.class));
//                        break;
//                    case 1:
//                        startActivity(new Intent(MainActivity.this, BannerAdXmlActivity.class));
//                        break;
//                    case 2:
//                        startActivity(new Intent(MainActivity.this, InterstitialAdCodeActivity.class));
//                        break;
//                    case 3:
//                        startActivity(new Intent(MainActivity.this, InterstitialAdXmlActivity.class));
//                        break;
//                    case 4:
//                        startActivity(new Intent(MainActivity.this, VideoAdCodeActivity.class));
//                        break;
//                    case 5:
//                        startActivity(new Intent(MainActivity.this, VideoAdXmlActivity.class));
//                        break;
//                    case 6:
////                        SAVideoActivity.start(MainActivity.this, "21022", "false", "true");
//                        break;
//                    case 7:
//                        startActivity(new Intent(MainActivity.this, GamewallActivity.class));
//                        break;
//                    case 8:
//                        startActivity(new Intent(MainActivity.this, AllAdsActivity.class));
//                        break;
//                }
//
//            }
//
//            @SuppressWarnings("unused")
//            public void onClick(View v) {
//            };
//        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
