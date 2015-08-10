package tv.superawesome.superawesomesdk;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by connor.leigh-smith on 24/06/15.
 */
public class UrlLoader extends AsyncTask<String, Integer, String> {

    protected UrlLoaderListener listener;

    public void setListener(UrlLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String[] params) {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        BufferedReader streamReader = null;
        StringBuilder responseStrBuilder = new StringBuilder();

        try {
            urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
        } catch (IOException e) {
            listener.onBeginLoad(e.getMessage());
        }

        listener.onBeginLoad(params[0]);

        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            listener.onBeginLoad(e.getMessage());
        }

        if (in != null) {
            String inputStr;
            streamReader = new BufferedReader(new InputStreamReader(in));

            try {
                while ((inputStr = streamReader.readLine()) != null) responseStrBuilder.append(inputStr);
            } catch (IOException e) {
                listener.onBeginLoad(e.getMessage());
            }
        }

        if (urlConnection != null) {
            urlConnection.disconnect();
        }

        return responseStrBuilder.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        this.listener.onLoaded(response);
    }
}
