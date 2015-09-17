package tv.superawesome.sdk;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
        HttpsURLConnection urlConnection = null;
        InputStream in = null;
        BufferedReader streamReader = null;
        StringBuilder responseStrBuilder = new StringBuilder();

        try {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            } };
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            urlConnection = (HttpsURLConnection) new URL(params[0]).openConnection();
        } catch (Exception e) {
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
