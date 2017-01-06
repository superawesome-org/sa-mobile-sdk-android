package tv.superawesome.sdk.cpi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SACPIData;
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;

/**
 * This class handles all CPI related events in the AwesomeAds SDK
 */
public class SACPI extends BroadcastReceiver {

    private static final String PREFERENCES = "MyPreferences";
    private static final String CPI_INSTALL = "CPI_INSTALL";

    public static void sendCPIEvent (Context context) {

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (!preferences.contains(CPI_INSTALL)) {

            Log.d("SuperAwesome-CPI", "Send CPI install event");

            editor.putBoolean(CPI_INSTALL, true);
            editor.apply();

            SASession session = new SASession(context);
            session.setConfigurationProduction();

            String cpiUrl = session.getBaseUrl() + "/install?bundle=" + session.getPackageName();

            SAEvents events = new SAEvents(context);
            events.sendEventToURL(cpiUrl);
        } else {
            Log.d("SuperAwesome-CPI", "Already sent CPI install event");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // send the actual CPI event
        sendCPIEvent(context);

        // now get the referral data
        String referrer = intent.getStringExtra("referrer");

        if (referrer != null) {

            referrer = referrer.replace("=", " : ");
            referrer = referrer.replace("%3D", " : ");
            referrer = referrer.replace("\\&", ", ");
            referrer = referrer.replace("&", ", ");
            referrer = referrer.replace("%26", ", ");
            referrer = "{ " + referrer + " }";
            referrer = referrer.replace("utm_source", "\"utm_source\"");
            referrer = referrer.replace("utm_campaign", "\"utm_campaign\"");
            referrer = referrer.replace("utm_term", "\"utm_term\"");
            referrer = referrer.replace("utm_content", "\"utm_content\"");
            referrer = referrer.replace("utm_medium", "\"utm_medium\"");

            SACPIData cpiData = new SACPIData(referrer);

            if (cpiData.isValid()) {

                SAUtils.SAConnectionType connectionType = SAUtils.getNetworkConnectivity(context);

                JSONObject installDict1 = SAJsonParser.newObject(new Object[]{
                        "placement", cpiData.placementId,
                        "line_item", cpiData.lineItemId,
                        "creative", cpiData.creativeId,
                        "type", "custom.referred_install"
                });

                JSONObject installDict2 = SAJsonParser.newObject(new Object[]{
                        "sdkVersion", SuperAwesome.getInstance().getSDKVersion(),
                        "rnd", SAUtils.getCacheBuster(),
                        "ct", connectionType.ordinal(),
                        "data", SAUtils.encodeDictAsJsonDict(installDict1)
                });


                SASession newsession = new SASession(context);
                newsession.setConfiguration(SAConfiguration.fromValue(cpiData.configuration));

                String cpiEventURL = newsession.getBaseUrl() + "/event?" + SAUtils.formGetQueryFromDict(installDict2);

                SAEvents events = new SAEvents(context);
                events.sendEventToURL(cpiEventURL);

            }
        }
    }
}
