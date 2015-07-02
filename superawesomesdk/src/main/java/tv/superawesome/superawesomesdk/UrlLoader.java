package tv.superawesome.superawesomesdk;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public class UrlLoader extends AsyncTask<String, Integer, String> {

    protected UrlLoaderListener listener;

    public UrlLoader() {

    }

    public void setListener(UrlLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String[] params) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
            listener.onBeginLoad(params[0]);
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String inputStr;
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                while ((inputStr = streamReader.readLine()) != null) responseStrBuilder.append(inputStr);

                return responseStrBuilder.toString();
            } catch (Exception e) {
                listener.onError(e.getMessage());
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String response) {
        this.listener.onLoaded(response);
    }
}
