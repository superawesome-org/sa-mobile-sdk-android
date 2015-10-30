/**
 * @class: SAGet.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Network;

/**
 * Needed imports for the implementation
 */
import javax.net.ssl.HttpsURLConnection;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * In Java SAGet is a subclass of AsyncTask
 * to make a GET request to the server
 */
public class SAGet extends AsyncTask<String, Integer, String> {

    /** Private SAGet variables */
    private HttpsURLConnection connection = null;
    private InputStream in = null;
    private BufferedReader reader = null;
    private SSLContext sc = null;

    /** Private net listener */
    private SANetListener listener;

    /**
     *
     * @param url - the URL to get the data from
     * @param listener - a reference to the SANetListner object
     * @return void
     */
    public void execute (String url, SANetListener listener) {
        /** assign listener reference */
        this.listener = listener;

        /** call the super execute */
        super.execute(url);
    }

    /**
     *
     * @param url - a string from where to GET the data
     * @return the contents of the data at the URL given by param
     */
    @Override
    protected String doInBackground(String[] url) {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url[0]);

        HttpResponse response = null;
        try {
            response = client.execute(request);

            Log.d("Response of GET request", response.toString());
        } catch (IOException e ) {
            handleError(e);
        }

        String responseString = null;
        try {
            responseString = new BasicResponseHandler().handleResponse(response);
        } catch (IOException e) {
            handleError(e);
        }

        return responseString;

//        /** 1. Create the response object */
//        StringBuilder response = new StringBuilder();
//
//        /** 2. Create the trust manager and set it to trust all certs */
//        TrustManager[] trustAllCerts = new TrustManager[] {
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        /** do nothing */
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        /** do nothing */
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//                }
//        };
//
//
//        try {
//            /** 3. Init SSLContext */
//            sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            /** 4. Create the HTTP(S) URL connection using the SSL Context created above */
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            connection = (HttpsURLConnection) new URL(url[0]).openConnection();
//
//            /** 5. Create input stream */
//            in = new BufferedInputStream(connection.getInputStream());
//
//        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
//            handleError(e);
//        }
//
//        /** 6. If the input stream is OK, strat reading */
//        if (in != null) {
//            String inputStr;
//            reader = new BufferedReader(new InputStreamReader(in));
//
//            try {
//                while ((inputStr = reader.readLine()) != null) {
//                    response.append(inputStr);
//                }
//            } catch (IOException e) {
//                handleError(e);
//            }
//        }
//        else {
//            handleError(null);
//        }
//
//        if (connection != null) {
//            connection.disconnect();
//        }
//
//        /** 7. return the actual response */
//        return response.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        /** send the success response, with a string as the object */
        if (listener != null) {
            listener.success(response);
        }
    }

    /**
     * @brief: this function will be called everytime the SAGet class should return
     * some kind of error
     * @param e - the exception
     */
    private void handleError(Exception e) {
        /** print stack */
        if (e != null) {
            e.printStackTrace();
        }

        /** call failure, if listner exists (and normally, it should exist) */
        if (listener != null) {
            listener.failure();
        }
    }
}
