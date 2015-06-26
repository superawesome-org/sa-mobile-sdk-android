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

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public class RichMediaLoader extends AsyncTask<String, Integer, String> {

    private AdLoaderListener listener;

    public RichMediaLoader(AdLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String[] params) {
        try {
            return getContent(new URL(params[0]));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        this.listener.onRichMediaLoaded(response);
    }


    private String getContent(URL url) throws IOException, JSONException {
        this.listener.onRichMediaBeginLoad(url.toString());
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String inputStr;
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                while ((inputStr = streamReader.readLine()) != null) responseStrBuilder.append(inputStr);

                return responseStrBuilder.toString();

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