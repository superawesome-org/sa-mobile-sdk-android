/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.cpi;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sasession.SAConfiguration;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAUtils;

public class SAInstallEvent {

    private static final String PREFERENCES = "MyPreferences";
    private static final String CPI_INSTALL = "CPI_INSTALL";

    private Context context;
    private SharedPreferences preferences;
    private SAInstallEventInterface listener = null;

    public SAInstallEvent (Context context) {
        this.context = context;
        this.listener = new SAInstallEventInterface() {@Override public void didCountAnInstall(boolean success) {}};
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void sendEvent (SASession session, String sourceBundle, SAInstallEventInterface listener) {

        this.listener = listener != null ? listener : this.listener;

        String key = CPI_INSTALL + "_" + (session.getConfiguration() == SAConfiguration.PRODUCTION ? "PROD" : "STAG");

        if (!preferences.contains(key)) {

            // make sure this runs **just** once!
            preferences.edit().putBoolean(key, true).apply();

            // create the cpi URL
            String cpiUrl = session.getBaseUrl() + "/install?";
            cpiUrl += "bundle=" + session.getPackageName();
            cpiUrl += sourceBundle != null ? "&sourceBundle=" + sourceBundle : "";

            // and the specific event header
            JSONObject header = SAJsonParser.newObject(new Object[] {
                    "Content-Type", "application/json",
                    "User-Agent", SAUtils.getUserAgent(context)
            });

            // run the network code to send an event ...
            SANetwork network = new SANetwork();
            network.sendGET(context, cpiUrl, new JSONObject(), header, new SANetworkInterface() {
                @Override
                public void response(int status, String payload, boolean success) {

                    // read the payload
                    //  in case of success it's a JSON { "success" : true }
                    //  in case of failure it usually null
                    JSONObject eventResponse = SAJsonParser.newObject(payload);

                    // get the success status
                    boolean eventSuccess = SAJsonParser.getBoolean(eventResponse, "success", false);

                    // call listener
                    SAInstallEvent.this.listener.didCountAnInstall(eventSuccess);

                }
            });

        }
    }

}
