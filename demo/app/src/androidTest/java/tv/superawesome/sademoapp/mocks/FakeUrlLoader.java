package tv.superawesome.sademoapp.mocks;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import tv.superawesome.superawesomesdk.UrlLoader;
import tv.superawesome.superawesomesdk.UrlLoaderListener;

/**
 * Created by connor.leigh-smith on 01/07/15.
 */
public class FakeUrlLoader extends UrlLoader {

    String response;

    public FakeUrlLoader(String response) {
        super();
        this.response = response;
    }

    public void setListener(UrlLoaderListener listener) {
        super.setListener(listener);
    }


    @Override
    protected String doInBackground(String[] params) {
        return this.response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
    }
}
