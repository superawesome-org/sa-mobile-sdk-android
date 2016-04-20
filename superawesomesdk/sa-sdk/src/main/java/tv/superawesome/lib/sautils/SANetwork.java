package tv.superawesome.lib.sautils;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import tv.superawesome.sdk.loader.SALoader;

/**
 * Created by gabriel.coman on 06/04/16.
 */
public class SANetwork {

    /**********************************************************************************************/
    /** NETWORK Get Request */
    /**********************************************************************************************/

    /**
     * Execute a get request
     * @param urlString - the URL to send the request to
     * @return a String object containing net data
     * @throws IOException
     */
    public String syncGet(String urlString) throws IOException {

        URL url;
        String response = null;
        try {
            url = new URL(urlString);

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", SAUtils.getUserAgent());

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                response = "";
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * More manageable asyncGet function, based on the generic SAASyncTask class
     * @param urlString - the URL to make the request to
     * @param listener - the listener
     */
    public void asyncGet(String urlString, JSONObject querydict, final SANetListener listener) {

        /** form the actual final endpoint */
        final String endpoint = urlString + (!SAUtils.isJSONEmpty(querydict) ? "?" + SAUtils.formGetQueryFromDict(querydict) : "");

        /** create a new SAAsync Task */
        SAAsyncTask task = new SAAsyncTask(SAApplication.getSAApplicationContext(), new SAAsyncTask.SAAsyncTaskListener() {
            @Override
            public Object taskToExecute() throws Exception {
                return syncGet(endpoint);
            }

            @Override
            public void onFinish(Object result) {
                Log.d("SuperAwesome", "[OK] " + endpoint);
                listener.success(result);
            }

            @Override
            public void onError() {
                listener.failure();
            }
        });
    }

    /**
     * *********************************************************************************************
     * This is a listener interface for SAGet and SAPost async task classes
     * *********************************************************************************************
     */
    public interface SANetListener {

        /**
         * This function should be called in case of Async operation success, and should
         * always return an anonymous data object
         *
         * @param data - is a callback parameter; to be accessed by the class that implements
         * this Listener interface
         */
        void success(Object data);

        /**
         * This function should be called in case of Async operation failure, and
         * should have no parameters
         */
        void failure();

    }
}
