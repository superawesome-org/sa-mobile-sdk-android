/**
 * @class: SAGet.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.lib.sanetwork;

/**
 * Needed imports for the implementation
 */
import javax.net.ssl.HttpsURLConnection;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.SSLContext;
import tv.superawesome.lib.sautils.*;

/**
 * In Java SAGet is a subclass of AsyncTask
 * to make a GET request to the server
 */
public class SAGet extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "SAGet";

    public SAGet() {
        super(SAGet.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");

        Bundle bundle = new Bundle();

        if (SAURLUtils.isValidURL(url)) {

            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                // get the results
                String[] results = new String[1];
                results[0] = SASyncGet.execute(url);

                /* Sending result back to activity */
                if (null != results[0]) {
                    bundle.putStringArray("result", results);
                    System.out.println("Success: " + url + " ==> " + results[0]);
                    receiver.send(STATUS_FINISHED, bundle);
                }
            } catch (Exception e) {

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }
//        this.stopSelf();
    }
}
