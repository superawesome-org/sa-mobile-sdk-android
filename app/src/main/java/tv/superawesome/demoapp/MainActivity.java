package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import tv.superawesome.lib.sabumperpage.SABumperPage;
import tv.superawesome.sdk.publisher.SAAppWall;
import tv.superawesome.sdk.publisher.SABannerAd;
import tv.superawesome.sdk.publisher.SAEvent;
import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.SAInterstitialAd;
import tv.superawesome.sdk.publisher.SAVideoAd;

public class MainActivity extends Activity {

    /** the options list */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SABannerAd myBanner = (SABannerAd) findViewById(R.id.MyBanner);
        myBanner.enableBumperPage();
        myBanner.enableParentalGate();
        myBanner.setConfigurationStaging();
        myBanner.enableTestMode();
        myBanner.disableMoatLimiting();
        myBanner.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {

                Log.d("SADefaults", "BANNER AD: " + placementId + " -> Event : " + event);

                if (event == SAEvent.adLoaded) {
                    myBanner.play(MainActivity.this);
                }
            }
        });

        SAInterstitialAd.setConfigurationProduction();
        SAInterstitialAd.disableParentalGate();
        SAInterstitialAd.disableBumperPage();
        SAInterstitialAd.disableTestMode();
//        SAInterstitialAd.setOrientationPortrait();
        SAInterstitialAd.disableMoatLimiting();
        SAInterstitialAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {

                Log.d("SADefaults", "INTERSTITIAL AD: " + placementId + " -> Event : " + event);

                if (event == SAEvent.adLoaded) {
                    SAInterstitialAd.play(placementId, MainActivity.this);
                }
            }
        });

        SAVideoAd.setConfigurationProduction();
        SAVideoAd.enableParentalGate();
        SAVideoAd.enableBumperPage();
        SAVideoAd.enableTestMode();
        SAVideoAd.disableMoatLimiting();
        SAVideoAd.disableCloseAtEnd();
        SAVideoAd.enableCloseButton();
        SAVideoAd.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {

                Log.d("SADefaults", "VIDEO AD: " + placementId + " -> Event : " + event);

                if (event == SAEvent.adLoaded) {
                    SAVideoAd.play(placementId, MainActivity.this);
                }
            }
        });

        SAAppWall.setConfigurationProduction();
        SAAppWall.setListener(new SAInterface() {
            @Override
            public void onEvent(int placementId, SAEvent event) {

                Log.d("SADefaults", "APP WALL AD: " + placementId + " -> Event : " + event);

                if (event == SAEvent.adLoaded) {
                    SAAppWall.play(placementId, MainActivity.this);
                }
            }
        });

        ListView myList = (ListView) findViewById(R.id.MyList);
        final List<AdapterItem> data = Arrays.asList(
                new HeaderItem("Banners"),
                new PlacementItem("CPM Banner 1 (Image)", 636, Type.BANNER),
                new PlacementItem("CPM Banner 2 (Image)", 635, Type.BANNER),
                new PlacementItem("CPI Banner 1 (Image)", 618, Type.BANNER),
                new PlacementItem("CPM MPU 1 (Tag)", 639, Type.BANNER),
                new HeaderItem("Interstitials"),
                new PlacementItem("CPM Interstitial 1 (Image)", 620, Type.INTERSTITIAL),
                new PlacementItem("CPM Interstitial 2 (Rich Media)", 621, Type.INTERSTITIAL),
                new PlacementItem("CPM Interstitial 3 (Rich Media)", 622, Type.INTERSTITIAL),
                new PlacementItem("CPM Interstitial 4 (Rich Media)", 637, Type.INTERSTITIAL),
                new PlacementItem("CPM Interstitial 5 (Rich Media)", 624, Type.INTERSTITIAL),
                new PlacementItem("CPM Interstitial 6 (Rich Media)", 625, Type.INTERSTITIAL),
                new PlacementItem("CPM Scalable Interstitial 7", 645, Type.INTERSTITIAL),
                new PlacementItem("CPM Scrollable Interstitial 8", 653, Type.INTERSTITIAL),
                new PlacementItem("CPM Interstitial 9 (Tag)", 626, Type.INTERSTITIAL),
                new PlacementItem("Popstitial 700", 700, Type.INTERSTITIAL),
                new PlacementItem("400 Interstitial", 702, Type.INTERSTITIAL),
                new PlacementItem("Rich Media Video", 715, Type.INTERSTITIAL),
                new PlacementItem("34602", 33615, Type.INTERSTITIAL),
                new HeaderItem("Videos"),
                new PlacementItem("CPM Preroll 1 (Video)", 30479, Type.VIDEO),
                new PlacementItem("CPM Preroll 2 (Video)", 629, Type.VIDEO),
                new PlacementItem("CPM Preroll 3 (Video)", 630, Type.VIDEO),
                new PlacementItem("CPM Preroll 4 (Tag)", 631, Type.VIDEO),
                new PlacementItem("CPI Preroll 1 (Video)", 627, Type.VIDEO),
                new PlacementItem("CPI Preroll 1 (Video)", 28000, Type.VIDEO),
                new HeaderItem("App Wall"),
                new PlacementItem("CPI AppWall 1 (Images)", 633, Type.APPWALL)
        );
        ListAdapter<AdapterItem> adapter = new ListAdapter<>(this);
        myList.setAdapter(adapter);
        adapter.updateData(data);
        adapter.reloadList();

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItem item = data.get(position);
                if (item instanceof PlacementItem) {
                    PlacementItem placement = (PlacementItem)item;

                    switch (placement.type) {
                        case BANNER: myBanner.load(placement.pid); break;
                        case INTERSTITIAL: SAInterstitialAd.load(placement.pid, MainActivity.this); break;
                        case VIDEO: SAVideoAd.load(placement.pid, MainActivity.this); break;
                        case APPWALL: SAAppWall.load(placement.pid, MainActivity.this); break;
                    }
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void gotoAdMob(View view) {
        Intent intent = new Intent(this, AdMobActivity.class);
        this.startActivity(intent);
    }

    public void gotoMoPub(View view) {
        Intent intent = new Intent(this, MoPubActivity.class);
        this.startActivity(intent);
    }
}

enum Type {
    BANNER,
    INTERSTITIAL,
    VIDEO,
    APPWALL
}

class AdapterItem {
    // do nothing
}

class HeaderItem extends AdapterItem {
    public String title;
    public HeaderItem (String title) {
        this.title = title;
    }
}

class PlacementItem extends AdapterItem {
    public String name;
    public int pid;
    public Type type;
    public PlacementItem (String name, int pid, Type type) {
        this.name = name;
        this.pid = pid;
        this.type = type;
    }
}

class ListAdapter <T extends AdapterItem> extends ArrayAdapter<T> {

    private List<T> data;

    public ListAdapter(Context context) {
        super(context, 0);
    }

    public void updateData (List<T> newData) {
        data = newData;
    }

    public void reloadList () {
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int i) {
        return getItem(i) instanceof HeaderItem ? 0 : 1;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_placement, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.RowTitle);

        T item = getItem(position);

        if (item instanceof HeaderItem) {
            HeaderItem header = (HeaderItem) item;
            title.setText(header.title);
            convertView.setBackgroundColor(Color.LTGRAY);
        }
        else {
            PlacementItem placement = (PlacementItem) item;
            title.setText(placement.pid + " | " + placement.name);
            title.setBackgroundColor(Color.WHITE);
        }


        return convertView;
    }
}
