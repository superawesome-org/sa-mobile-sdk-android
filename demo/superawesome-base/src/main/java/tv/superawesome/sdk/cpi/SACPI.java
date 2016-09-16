package tv.superawesome.sdk.cpi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.SACPIData;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.SuperAwesome;

/**
 * This class handles all CPI related events in the AwesomeAds SDK
 */
public class SACPI extends BroadcastReceiver {

    private static final String REF_KEYWORD = "referrer";

    @Override
    public void onReceive(Context context, Intent intent) {
        // get referrer
        String referrer = intent.getStringExtra("referrer");

        Log.d("SuperAwesome", "SACPI Service got referrer " + referrer);

        /**
         * referrer is a string that should contain the following values:
         * Campaign Source  = utm_source    = configuration | ex: 0, 1, 2
         * Campaign Name    = utm_campaign  = campaign_id   | ex: 114
         * Campaign Term    = utm_term      = line_item_id  | ex: 138
         * Campaign Content = utm_content   = creative_id   | ex: 114
         * Campaign Medium  = utm_medium    = placement_id  | ex: 113
         *
         * Referrer string will initially look something like:
         * utm_source=1\&utm_medium=113\&utm_term=138\&utm_content=114\&utm_campaign=114
         *
         * Which will need to be translated to:
         * {"utm_source":1, "utm_medium":117, "utm_term":137, "utm_content":114, "utm_campaign":114}
         *
         * and then parsed into an object:
         */

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

        try {
            JSONObject jsonObject = new JSONObject(referrer);
            SACPIData cpiData = new SACPIData(jsonObject);

            // form URL
            Log.d("SuperAwesome", "CPI data is " + cpiData.writeToJson().toString());

            SAUtils.SAConnectionType ct = SAUtils.SAConnectionType.unknown;
            Context c = SAApplication.getSAApplicationContext();
            if (c != null) ct = SAUtils.getNetworkConnectivity(c);

            // create the viewable impression URL
            JSONObject installDict1 = SAJsonParser.newObject(new Object[]{
                    "placement", cpiData.placementId,
                    "line_item", cpiData.lineItemId,
                    "creative", cpiData.creativeId,
                    "type", "install"
            });

            JSONObject installDict2 = SAJsonParser.newObject(new Object[]{
                    "sdkVersion", SuperAwesome.getInstance().getSDKVersion(),
                    "rnd", SAUtils.getCacheBuster(),
                    "ct", ct.ordinal(),
                    "data", SAUtils.encodeDictAsJsonDict(installDict1)
            });


            SASession newsession = new SASession();
            if (cpiData.configuration == 0) {
                newsession.setConfigurationProduction();
            } else {
                newsession.setConfigurationStaging();
            }

            // new event URL
            String cpiEventURL = newsession.getBaseUrl() + "/event?" + SAUtils.formGetQueryFromDict(installDict2);

            // send Event
            SAEvents events = new SAEvents();
            events.sendEventToURL(cpiEventURL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
