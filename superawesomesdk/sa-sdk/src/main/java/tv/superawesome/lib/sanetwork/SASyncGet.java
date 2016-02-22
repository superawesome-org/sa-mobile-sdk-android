package tv.superawesome.lib.sanetwork;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import java.io.IOException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import tv.superawesome.lib.sautils.SALog;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SASyncGet {

    public static String execute(String url) throws IOException {

        HttpClient client = new DefaultHttpClient();

        HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
//        registry.register(new Scheme("http", socketFactory, 80));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);

        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", SAUserAgent.getUserAgent());
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e ) {
            SALog.Log("Exception: " + e.toString());
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
