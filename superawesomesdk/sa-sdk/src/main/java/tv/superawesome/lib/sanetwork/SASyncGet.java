package tv.superawesome.lib.sanetwork;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.data.Loader.SALoader;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SASyncGet {

    public static String execute(String url) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", SAUserAgent.getUserAgent());
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = null;
        try {
            response = client.execute(httpGet);
        } catch (IOException e ) {
            return null;
        }

        String responseString = null;
        try {
            responseString = new BasicResponseHandler().handleResponse(response);
        } catch (IOException e) {
            return null;
        }

        return responseString;
    }
}
