/**
 * @class: SANetwork.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Network;

/**
 * Imports needed for this implementation
 */
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tv.superawesome.sdk.data.Utils.Utils;

/**
 * SANetwork is a simple class with two static methods, sendGet and sendPost, that
 * acts as a useful wrapper against SAGet and SAPost classes
 */
public class SANetwork {

    /**
     * @brief: This is just a wrapper over SAGet, that ads some more fluff and has static methods so you don't have to alloc a new instance
     * @param endpoint - the primary, unaltered endpoint to send data to
     * @param querydict - a map of values that will be transformed into a proper GET query ?=parm1=val1&param2=val2, etc
     * @param listener - another SANetListener object, that just passes what SAGet sends him
     */
    public static void sendGET(String endpoint, Map<String, Object> querydict, final SANetListener listener) {

        /** get a reference to the final endpoint so I can change it */
        String finalEndpoint = endpoint;

        /** form the GET params */
        ArrayList<String> queryArray = new ArrayList<>();
        for (Map.Entry<String, Object> e : querydict.entrySet()) {
            queryArray.add(e.getKey() + "=" + e.getValue().toString());
        }

        /** form final endpoint */
        if (queryArray.isEmpty() == false){
            finalEndpoint += "?";
            for (String queryObj : queryArray) {
                finalEndpoint += queryObj;
            }
        }

        /** finally, execute */
        SAGet getOp = new SAGet();
        getOp.execute(finalEndpoint, listener);
    }

    /**
     * @brief: this function acts as a wrapper around SAPost, in the sense that forms POST body params
     * @param endpoint - the final endpoint to send the POST request to
     * @param postparams - a map that will be transformed into a series of POST body params
     * @param listener - another SANetListener object, that just passes what SAPost sends him
     */
    public static void sendPOST(String endpoint, Map<String, Object> postparams, final SANetListener listener) {

        JSONObject postJSON = null;
        StringEntity postJSONString = null;

        System.out.println(postparams);
        String json = new GsonBuilder().create().toJson(postparams);
        System.out.println("JSON DATA: " + json);

//        /** Form a JSON pairs from the postparams map */
//        try {
//            postJSON = Utils.getJsonObjectFromMap(postparams);
//        } catch (JSONException e) {
//            listener.failure();
//        }

        /** passes the results to a string builder/entity */
        try {
            postJSONString = new StringEntity(json);
            postJSONString.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        } catch (UnsupportedEncodingException e) {
            /** call to failure */
            listener.failure();
        }

        /** execute */
        SAPost post = new SAPost();
        post.execute(endpoint, postJSONString, listener);
    }
}
