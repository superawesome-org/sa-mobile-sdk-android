package tv.superawesome.lib.sanetwork;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.R;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SASyncGet {

    public static String execute(String urlstr) throws IOException {

        URL url;
        String response = null;
        try {
            url = new URL(urlstr);

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", SAUserAgent.getUserAgent());

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

//        HttpClient client = new DefaultHttpClient();
//
//        HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//        SchemeRegistry registry = new SchemeRegistry();
//        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//        registry.register(new Scheme("https", socketFactory, 443));
//        registry.register(new Scheme("http", socketFactory, 80));
//        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//
//        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
//        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
//
//        HttpGet httpGet = new HttpGet(url);
//        System.out.println(SAUserAgent.getUserAgent());
//        httpGet.setHeader("User-Agent", SAUserAgent.getUserAgent());
//        httpGet.setHeader("Content-Type", "application/json");
//
//        HttpResponse response = null;
//        try {
//            response = httpClient.execute(httpGet);
//        } catch (IOException e ) {
//            SALog.Log("Exception: " + e.toString());
//            return null;
//        }
//
//        String responseString = null;
//        try {
//            responseString = new BasicResponseHandler().handleResponse(response);
//        } catch (IOException e) {
//            return null;
//        }
//
//        return responseString;
    }
}
