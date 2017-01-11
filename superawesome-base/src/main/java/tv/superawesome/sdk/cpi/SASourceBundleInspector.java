package tv.superawesome.sdk.cpi;

import android.content.Context;
import android.content.pm.PackageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sasession.SASession;

public class SASourceBundleInspector {

    private SANetwork network;
    private Context context;
    private SASourceBundleInspectorInterface listener;

    SASourceBundleInspector(Context context) {
        this.network = new SANetwork();
        this.context = context;
        this.listener = new SASourceBundleInspectorInterface() {@Override public void didFindPackage(String sourceBundle) {}};
    }

    void checkPackage(SASession session, final SASourceBundleInspectorInterface listener) {
        // get a reference of the listener
        this.listener = listener != null ? listener : this.listener;

        // create the URL
        String checkUrl = session.getBaseUrl() + "/checkinstall?bundle=" + session.getPackageName();

        network.sendGET(context, checkUrl, new JSONObject(), new JSONObject(), new SANetworkInterface() {
            @Override
            public void response(int status, String payload, boolean success) {

                // parse the new bundle Ids
                JSONArray bundleIds = SAJsonParser.newArray(payload);

                // the souce bundle Id destination
                String sourceBundleId = null;

                for (int i = 0; i < bundleIds.length(); i++) {
                    try {
                        String bundleId = bundleIds.getString(i);
                        boolean exists = isPackageInstalled(bundleId, context.getPackageManager());
                        if (exists) {
                            sourceBundleId = bundleId;
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // send the result back
                SASourceBundleInspector.this.listener.didFindPackage(sourceBundleId);
            }
        });

    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
