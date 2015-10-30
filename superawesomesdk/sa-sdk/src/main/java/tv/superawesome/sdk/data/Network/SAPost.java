/**
 * @class: SAPost.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Network;

/**
 * Needed imports for the implementation
 */
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.IOException;

/**
 * In Java SAGet is a subclass of AsyncTask
 * to make a POST request to the server
 */
public class SAPost extends AsyncTask <String, Integer, String> {

    /** Private variables */
    private SANetListener listener;
    private StringEntity postParamsString;

    /**
     * @brief: This function acts as a wrapper around AsyncTask's execute(url) function,
     *         that adds the possibility to input a list of post params and a SANetListener reference
     * @param url - the URL to make the POST request to
     * @param postParamsString - the list of post params
     * @param listener - the listner reference
     */
    public void execute(String url, StringEntity postParamsString, SANetListener listener) {
        /** get local references to listener and post params */
        this.listener = listener;
        this.postParamsString = postParamsString;

        /** call the super execute */
        super.execute(url);
    }

    @Override
    protected String doInBackground(String[] url) {

        /** create the http client and java http post objects */
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url[0]);

        /** try to add the post params */
        httpPost.setEntity(postParamsString);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type","application/json");

        /** try to obtain a response from the server */
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            handleError(e);
        }

        String responseString = null;
        try {
            responseString = new BasicResponseHandler().handleResponse(response);
        } catch (IOException e) {
            handleError(e);
        }

        /** return the response as a string */
        return responseString;
    }

    @Override
    protected void onPostExecute(String response) {
        if (listener != null) {
            listener.success(response);
        }
    }

    /**
     * @brief: this function will be called every time the SAPost class should return
     * some kind of error
     * @param e - the exception
     * */
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
