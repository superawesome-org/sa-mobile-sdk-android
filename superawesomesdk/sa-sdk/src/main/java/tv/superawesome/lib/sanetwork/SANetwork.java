/**
 * @class: SANetwork.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.lib.sanetwork;

/**
 * Imports needed for this implementation
 */
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

import tv.superawesome.lib.sanetwork.*;
import tv.superawesome.lib.sautils.*;

/**
 * SANetwork is a simple class with two static methods, sendGet and sendPost, that
 * acts as a useful wrapper against SAGet and SAPost classes
 */
public class SANetwork {

    /**
     * This is just a wrapper over SAGet, that ads some more fluff and has static methods so you don't have to alloc a new instance
     * @param endpoint - the primary, unaltered endpoint to send data to
     * @param querydict - a map of values that will be transformed into a proper GET query ?=parm1=val1&param2=val2, etc
     * @param listener - another SANetListener object, that just passes what SAGet sends him
     */
    public static void sendGET(String endpoint, JsonObject querydict, final SANetListener listener) {

        /** get a reference to the final endpoint so I can change it */
        String finalEndpoint = endpoint +
                (SAUtils.isJSONEmpty(querydict) ? "?" + SAURLUtils.formGetQueryFromDict(querydict) : "");

        /** finally, execute */
        SAGet getOp = new SAGet();
        getOp.execute(finalEndpoint, listener);
    }

    /**
     * this function acts as a wrapper around SAPost, in the sense that forms POST body params
     * @param endpoint - the final endpoint to send the POST request to
     * @param postparams - a map that will be transformed into a series of POST body params
     * @param listener - another SANetListener object, that just passes what SAPost sends him
     */
    public static void sendPOST(String endpoint, JsonObject postparams, final SANetListener listener) {

        StringEntity postJSONString = null;
        String json = new GsonBuilder().create().toJson(postparams);

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
