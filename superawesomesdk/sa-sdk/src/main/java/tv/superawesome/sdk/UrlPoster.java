package tv.superawesome.sdk;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by gabriel.coman on 22/09/15.
 */
public class UrlPoster extends AsyncTask<String, Integer, String> {

    protected UrlLoaderListener listener;
    protected JSONObject postParams;

    public void setListener(UrlLoaderListener listener) {
        this.listener = listener;
    }
    public void setPOSTParams(JSONObject params) { this.postParams = params; }

    @Override
    protected String doInBackground(String[] params) {
        HttpsURLConnection urlConnection = null;
        InputStream in = null;
        OutputStream out = null;
        BufferedReader streamReader = null;
        BufferedWriter streamWriter = null;
        Uri.Builder builder = new Uri.Builder();
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
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            urlConnection = (HttpsURLConnection) new URL(params[0]).openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            String query = "";
            if (postParams != null){
                query = postParams.toString();
            }
            out = urlConnection.getOutputStream();
            streamWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            streamWriter.write(query);
            streamWriter.flush();
            out.close();

        } catch (Exception e) {
            listener.onBeginLoad(e.getMessage());
        }

        //////////////////////////////////////////////////////////////////////
        // Begin response from server / input part
        //////////////////////////////////////////////////////////////////////

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
