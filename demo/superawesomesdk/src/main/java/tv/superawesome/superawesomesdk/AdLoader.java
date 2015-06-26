package tv.superawesome.superawesomesdk;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tv.superawesome.superawesomesdk.view.AdLoaderListener;
import tv.superawesome.superawesomesdk.view.PlacementView;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public class AdLoader extends AsyncTask<String, Integer, JSONObject> {

    private AdLoaderListener listener;

    public AdLoader(AdLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(String[] params) {
        try {
            return getJson(new URL(params[0]));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        this.listener.onResponse(response);
    }


    private JSONObject getJson(URL url) throws IOException, JSONException {
        this.listener.onAdBeginLoad(url.toString());
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String inputStr;
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                while ((inputStr = streamReader.readLine()) != null) responseStrBuilder.append(inputStr);

                JSONObject response = new JSONObject("{\"line_item_id\":1, \"campaign_id\":1,\"creative\":{\"id\":1,\"format\":\"rich_media\",\"details\": {\"url\":\"https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html\",\"width\":970,\"height\":90}}}");
//                JSONObject response = new JSONObject(responseStrBuilder.toString());

                return response;
            } catch (Exception e) {
                throw e;
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            throw e;
        }

    }
}