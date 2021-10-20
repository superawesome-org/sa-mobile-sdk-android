package tv.superawesome.demoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import tv.superawesome.lib.sabumperpage.SABumperPage;
import tv.superawesome.sdk.publisher.AwesomeAds;
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
    SABumperPage.overrideName("Test app");

    final SABannerAd bannerAd = findViewById(R.id.preview);
    bannerAd.setListener(
        (SAInterface) (placementId, event) -> {
          if(event == SAEvent.adLoaded)
              bannerAd.play(this);
          });

    ListView myList = findViewById(R.id.MyList);
    final List<AdapterItem> data =
        Arrays.asList(
            new HeaderItem("Banners"),
            new PlacementItem("Banner 300x250 (Tablet MPU)", 61296, Type.BANNER),
            new PlacementItem("Banner 300x50 (Mobile Small Leaderboard)", 61294, Type.BANNER),
            new PlacementItem("Banner 320x50 (Mobile Leaderboard)", 61297, Type.BANNER),
            new HeaderItem("Interstitials"),
            new PlacementItem("Insterstitial - Rich Media", 61295, Type.INTERSTITIAL),
            new PlacementItem("Insterstitial - Static", 61298, Type.INTERSTITIAL),
            new HeaderItem("KSF"),
            new PlacementItem("Insterstitial - Static", 61321, Type.INTERSTITIAL),
            new PlacementItem("Insterstitial - Rich Media", 44262, Type.INTERSTITIAL),
            new PlacementItem("Video", 61320, Type.VIDEO));
    ListAdapter<AdapterItem> adapter = new ListAdapter<>(this);
    myList.setAdapter(adapter);
    adapter.updateData(data);
    adapter.reloadList();

    myList.setOnItemClickListener(
        (parent, view, position, id) -> {
          AdapterItem item = data.get(position);
          if (item instanceof PlacementItem) {
            PlacementItem placement = (PlacementItem) item;

            switch (placement.type) {
              case BANNER:
                bannerAd.load(placement.pid);
                break;
              case INTERSTITIAL:
                SAInterstitialAd.load(43694, MainActivity.this);
                break;

              case VIDEO:
                SAVideoAd.load(44262, this);
            }
          }
        });

    SAInterstitialAd.setListener(
        (SAInterface)
            (placementId, event) -> {
              if (event == SAEvent.adLoaded) {
                SAInterstitialAd.play(placementId, MainActivity.this);
              }
            });

    SAVideoAd.setListener(
        (SAInterface)
            (placementId, event) -> {
              if (event == SAEvent.adLoaded) {
                SAVideoAd.play(placementId, MainActivity.this);
              }
            });
  }

  public void gotoAdMob(View view) {
    Intent intent = new Intent(this, AdMobActivity.class);
    this.startActivity(intent);
  }

  public void gotoMoPub(View view) {
    Intent intent = new Intent(this, NewTestActivity.class);
    this.startActivity(intent);
  }

  public void ageCheck(View view) {

    final String dateOfBirth = "2012-02-02";

    AwesomeAds.triggerAgeCheck(
        this,
        dateOfBirth,
        isMinorModel -> {
          String message;
          if (isMinorModel != null) {
            message =
                "Min age for '"
                    + isMinorModel.getCountry()
                    + "' is '"
                    + isMinorModel.getConsentAgeForCountry()
                    + "' "
                    + "\nThe age is '"
                    + isMinorModel.getAge()
                    + "' "
                    + "'.\nIs '"
                    + dateOfBirth
                    + "' a minor? -> '"
                    + isMinorModel.isMinor()
                    + "'";
          } else {
            message = "Oops! Something went wrong. No valid model for 'Age Check'...";
          }

          Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        });
  }
}

enum Type {
  BANNER,
  INTERSTITIAL,
  VIDEO
}

class AdapterItem {
  // do nothing
}

class HeaderItem extends AdapterItem {
  public final String title;

  public HeaderItem(String title) {
    this.title = title;
  }
}

class PlacementItem extends AdapterItem {
  public final String name;
  public final int pid;
  public final Type type;

  public PlacementItem(String name, int pid, Type type) {
    this.name = name;
    this.pid = pid;
    this.type = type;
  }
}

class ListAdapter<T extends AdapterItem> extends ArrayAdapter<T> {

  private List<T> data;

  public ListAdapter(Context context) {
    super(context, 0);
  }

  public void updateData(List<T> newData) {
    data = newData;
  }

  public void reloadList() {
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
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {

    if (convertView == null) {
      convertView =
          LayoutInflater.from(getContext()).inflate(R.layout.row_placement, parent, false);
    }

    TextView title = convertView.findViewById(R.id.RowTitle);

    T item = getItem(position);

    if (item instanceof HeaderItem) {
      HeaderItem header = (HeaderItem) item;
      title.setText(header.title);
      convertView.setBackgroundColor(Color.LTGRAY);
    } else {
      PlacementItem placement = (PlacementItem) item;
      if (placement != null) {
        String titleText = placement.pid + " | " + placement.name;
        title.setText(titleText);
      }
      title.setBackgroundColor(Color.WHITE);
    }

    return convertView;
  }
}
