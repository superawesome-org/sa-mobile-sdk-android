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
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.logging.LogRecord;

import tv.superawesome.lib.sanetwork.*;
import tv.superawesome.lib.sautils.*;

/**
 * SANetwork is a simple class with two static methods, sendGet and sendPost, that
 * acts as a useful wrapper against SAGet and SAPost classes
 */
public class SANetwork implements SAGetResultsReceiver.Receiver {

    /** private net listener */
    private SAGetResultsReceiver mReceiver;
    private SANetListener listener;

    /**
     * This is just a wrapper over SAGet, that ads some more fluff and has static methods so you don't have to alloc a new instance
     * @param endpoint - the primary, unaltered endpoint to send data to
     * @param querydict - a map of values that will be transformed into a proper GET query ?=parm1=val1&param2=val2, etc
     * @param listener - another SANetListener object, that just passes what SAGet sends him
     */
    public void sendGET(String endpoint, JsonObject querydict, final SANetListener listener) {

        /** assign listener */
        this.listener = listener;

        /** get a reference to the final endpoint so I can change it */
        String finalEndpoint = endpoint +
                (SAUtils.isJSONEmpty(querydict) ? "?" + SAURLUtils.formGetQueryFromDict(querydict) : "");

        /** Starting Download Service */
        mReceiver = new SAGetResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, SAApplication.getInstance().getApplicationContext(), SAGet.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", finalEndpoint);
        intent.putExtra("receiver", mReceiver);

        SAApplication.getInstance().getApplicationContext().startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case SAGet.STATUS_RUNNING:
                break;
            case SAGet.STATUS_FINISHED: {
                String[] results = resultData.getStringArray("result");
                SALog.Log("GET RESULTS: " + results[0]);

                if (listener != null){
                    listener.success(results[0]);
                }

                break;
            }
            case SAGet.STATUS_ERROR: {
                if (listener != null) {
                    listener.failure();
                }

                break;
            }
        }
    }
}
