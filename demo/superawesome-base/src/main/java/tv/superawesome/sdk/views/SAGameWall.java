package tv.superawesome.sdk.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.saadloader.SALoaderInterface;
import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACampaignType;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.samodelspace.SAResponse;
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sasession.SASessionInterface;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;

/**
 * Created by gabriel.coman on 22/09/16.
 */
public class SAGameWall extends Activity {

    // private instance vars
    private SAResponse response = null;

    private SAParentalGate gate;

    private List<SAEvents> events = new ArrayList<>();

    // static private vars
    private static HashMap<Integer, Object> responses = new HashMap<>();
    private static SAInterface listener = new SAInterface() { @Override public void onEvent(int placementId, SAEvent event) {} };
    private static boolean isParentalGateEnabled = true;
    private static boolean isTestingEnabled = false;
    private static boolean isBackButtonEnabled = false;
    private static SAConfiguration configuration = SAConfiguration.PRODUCTION;

    // private vars
    private static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get local vars
        final SAInterface listenerL = getListener();
        final boolean isParentalGateEnabledL = getIsParentalGateEnabled();
        Bundle bundle = getIntent().getExtras();
        String responseStr = bundle.getString("response");
        response = new SAResponse(SAJsonParser.newObject(responseStr));

        // create the events array
        for (SAAd ad : response.ads) {
            SAEvents event = new SAEvents(this);
            event.setAd(ad);
            events.add(event);
        }

        // get resources (dynamically)
        String packageName = this.getPackageName();
        int activity_sa_gamewallId = getResources().getIdentifier("activity_sa_gamewall", "layout", packageName);
        int close_btnId = getResources().getIdentifier("gamewall_close", "id", packageName);
        int GameWallGridId = getResources().getIdentifier("GameWallGrid", "id", packageName);
        int padlockId = getResources().getIdentifier("padlock_button", "id", packageName);

        // finally start displaying
        setContentView(activity_sa_gamewallId);

        // get the padlock
        Button padlock = (Button) findViewById(padlockId);
        padlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ads.superawesome.tv/v2/safead"));
                SAGameWall.this.startActivity(browserIntent);
            }
        });

        // close btn
        Button closeBtn = (Button) findViewById(close_btnId);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        // create adapter
        GameWallAdapter adapter = new GameWallAdapter(this, response.ads);

        // set the game grid
        GridView gameGrid = (GridView) findViewById(GameWallGridId);
        gameGrid.setAdapter(adapter);
        gameGrid.setNumColumns(response.ads.size() <= 3 ? 1 : 3);
        gameGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // check for PG
                SAAd ad = response.ads.get(position);
                if (isParentalGateEnabledL) {
                    gate = new SAParentalGate(SAGameWall.this, SAGameWall.this, ad, position);
                    gate.show();
                } else {
                    click(position);
                }
            }
        });

        // send events
        for (SAEvents event : events) {
            event.sendEventsFor("impression");
            event.sendEventsFor("sa_impr");
            event.sendViewableImpressionForDisplay(gameGrid);
        }

        // send event
        listenerL.onEvent(response.placementId, SAEvent.adShown);
      }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        boolean isBackButtonEnabledL = getIsBackButtonEnabled();
        if (isBackButtonEnabledL) {
            SAInterface listenerL = getListener();
            listenerL.onEvent(response.placementId, SAEvent.adClosed);
            super.onBackPressed();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Custom instance methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void click(int position) {
        // get ad
        SAAd ad = response.ads.get(position);
        // get events
        SAEvents event = events.get(position);

        // get local
        SAInterface listenerL = getListener();
        SAConfiguration configurationL = getConfiguration();

        // callback
        listenerL.onEvent(response.placementId, SAEvent.adClicked);

        if (ad.saCampaignType == SACampaignType.CPI) {

            // send events
            event.sendEventsFor("install");
            event.sendEventsFor("sa_tracking");

            // get click URL
            String clickUrl = ad.creative.clickUrl;
            clickUrl += "&referrer=";
            JSONObject referrerData = SAJsonParser.newObject(new Object[]{
                    "utm_source", configurationL.ordinal(),
                    "utm_campaign", ad.campaignId,
                    "utm_term", ad.lineItemId,
                    "utm_content", ad.creative.id,
                    "utm_medium", ad.placementId
            });
            String referrerQuery = SAUtils.formGetQueryFromDict(referrerData);
            referrerQuery = referrerQuery.replace("&", "%26");
            referrerQuery = referrerQuery.replace("=", "%3D");
            clickUrl += referrerQuery;

            Log.d("SuperAwesome", "Going to " + clickUrl);

            // open URL
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickUrl));
            startActivity(browserIntent);
        }
    }

    private void close() {

        // get local
        SAInterface listenerL = getListener();

        // call listener
        listenerL.onEvent(response.placementId, SAEvent.adClosed);

        // delete the ad
        removeAdFromLoadedAds(response);

        // close
        this.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public class interface
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void load(final int placementId, Context context) {

        if (!responses.containsKey(placementId)) {

            // set a placeholder
            responses.put(placementId, new Object());

            // create a loader
            final SALoader loader = new SALoader(context);

            // create a current session
            final SASession session = new SASession (context);
            session.setTestMode(isTestingEnabled);
            session.setConfiguration(configuration);
            session.setVersion(SuperAwesome.getInstance().getSDKVersion());
            session.prepareSession(new SASessionInterface() {
                @Override
                public void sessionReady() {

                    // after session is OK - start loding
                    loader.loadAd(placementId, session, new SALoaderInterface() {
                        @Override
                        public void didLoadAd(SAResponse response) {

                            // put the correct value
                            if (response.isValid()) {
                                responses.put(placementId, response);
                            }
                            // remove existing
                            else {
                                responses.remove(placementId);
                            }

                            // call listener
                            listener.onEvent(placementId, response.isValid () ? SAEvent.adLoaded : SAEvent.adFailedToLoad);
                        }
                    });
                }
            });

        } else {
            listener.onEvent(placementId, SAEvent.adFailedToLoad);
        }

    }

    public static boolean hasAdAvailable (int placementId) {
        Object object = responses.get(placementId);
        return object != null && object instanceof SAResponse;
    }

    public static void play(int placementId, Context c) {

        // capture context
        context = c;

        // try to get the ad that fits the placement id
        SAResponse responseL = (SAResponse) responses.get(placementId);

        // try to start the activity
        if (responseL != null && responseL.format == SACreativeFormat.gamewall && context != null) {
            Intent intent = new Intent(context, SAGameWall.class);
            intent.putExtra("response", responseL.writeToJson().toString());
            context.startActivity(intent);
        } else {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        }
    }

    private static void removeAdFromLoadedAds (SAResponse response) {
        responses.remove(response.placementId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setters & getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListener(SAInterface value) {
        listener = value != null ? value : listener;
    }

    public static void enableParentalGate () {
        isParentalGateEnabled = true;
    }

    public static void disableParentalGate () {
        isParentalGateEnabled = false;
    }

    public static void enableTestMode () {
        isTestingEnabled = true;
    }

    public static void disableTestMode () {
        isTestingEnabled = false;
    }

    public static void setConfigurationProduction () {
        configuration = SAConfiguration.PRODUCTION;
    }

    public static void setConfigurationStaging () {
        configuration = SAConfiguration.STAGING;
    }

    public static void enableBackButton () {
        isBackButtonEnabled = true;
    }

    public static void disableBackButton () {
        isBackButtonEnabled = false;
    }


    // private static methods to handle static vars

    private static SAInterface getListener () {
        return listener;
    }

    private static boolean getIsTestEnabled () {
        return isTestingEnabled;
    }

    private static boolean getIsParentalGateEnabled () {
        return isParentalGateEnabled;
    }

    private static SAConfiguration getConfiguration () {
        return configuration;
    }

    private static boolean getIsBackButtonEnabled () {
        return isBackButtonEnabled;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // The GameWall adapter
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class GameWallAdapter extends BaseAdapter {

        private Context context = null;
        private List<SAAd> ads = new ArrayList<>();

        public GameWallAdapter(Context context, List<SAAd> ads){
            this.context = context;
            this.ads = ads;
        }

        @Override
        public int getCount() {
            return ads.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            // get elements that won't change
            SAAd ad = ads.get(position);
            int size = ads.size();
            String packageName = context.getPackageName();

            // do big layout
            if (size <= 3) {
                int GameWallAppIconId = getResources().getIdentifier("GameWallAppIcon", "id", packageName);
                int GameWallAppNameId = getResources().getIdentifier("GameWallAppName", "id", packageName);
                int cell_sa_gamewallLayout_Big = getResources().getIdentifier("cell_sa_gamewall_big", "layout", packageName);

                // inflate layout
                if (v == null) {
                    v = LayoutInflater.from(context).inflate(cell_sa_gamewallLayout_Big, null);
                }

                ImageView appIcon = (ImageView) v.findViewById(GameWallAppIconId);
                TextView appName = (TextView) v.findViewById(GameWallAppNameId);

                if (appName != null) {
                    appName.setText(ad.creative.name != null ? ad.creative.name : "Undefined");
                }
                if (appIcon != null) {
                    File file = new File(context.getFilesDir(), ad.creative.details.media.playableDiskUrl);
                    if (file.exists()) {
                        String fileUrl = file.toString();
                        Bitmap bitmap = BitmapFactory.decodeFile(fileUrl);

                        // get actual image w & h
                        final float density = context.getResources().getDisplayMetrics().density;
                        int appIconW = (int) (114 * density);
                        int appIconH = (int) (114 * density);
                        float radius = 15 * density;

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, appIconW, appIconH, true);
                        StreamDrawable drawable = new StreamDrawable(scaledBitmap, radius, 0);
                        appIcon.setImageDrawable(drawable);
                    }
                }
            }
            // do small layout
            else {
                int GameWallAppIconId = getResources().getIdentifier("GameWallAppIcon", "id", packageName);
                int GameWallAppNameId = getResources().getIdentifier("GameWallAppName", "id", packageName);
                int cell_sa_gamewallLayout_Small = getResources().getIdentifier("cell_sa_gamewall_small", "layout", packageName);

                // inflate layout
                if (v == null) {
                    v = LayoutInflater.from(context).inflate(cell_sa_gamewallLayout_Small, null);
                }

                ImageView appIcon = (ImageView) v.findViewById(GameWallAppIconId);
                TextView appName = (TextView) v.findViewById(GameWallAppNameId);

                if (appName != null) {
                    appName.setText(ad.creative.name != null ? ad.creative.name : "Undefined");
                }
                if (appIcon != null) {
                    File file = new File(context.getFilesDir(), ad.creative.details.media.playableDiskUrl);
                    if (file.exists()) {
                        String fileUrl = file.toString();
                        Bitmap bitmap = BitmapFactory.decodeFile(fileUrl);

                        // get actual image w & h
                        final float density = context.getResources().getDisplayMetrics().density;
                        int appIconW = (int) (84 * density);
                        int appIconH = (int) (84 * density);
                        float radius = 15 * density;

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, appIconW, appIconH, true);
                        StreamDrawable drawable = new StreamDrawable(scaledBitmap, radius, 0);
                        appIcon.setImageDrawable(drawable);
                    }
                }
            }

            return v;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StreamDrawable class to paint rounded-endges icons on the screen
    ////////////////////////////////////////////////////////////////////////////////////////////////

    class StreamDrawable extends Drawable {
        private final float mCornerRadius;
        private final RectF mRect = new RectF();
        private final BitmapShader mBitmapShader;
        private final Paint mPaint;
        private final int mMargin;

        StreamDrawable(Bitmap bitmap, float cornerRadius, int margin) {
            mCornerRadius = cornerRadius;
            mMargin = margin;
            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setShader(mBitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }
    }
}
