package tv.superawesome.lib.sanetwork;

/**
 * Imports for this class
 */
import android.util.Patterns;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAURLUtils {
    /**
     * Cache buster is just a big random number
     */
    public static int getCacheBuster() {
        int min = 1000000;
        int max = 1500000;
        Random rand  = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Function that forms a query string from a dict
     * @param dict
     */
    public static String formGetQueryFromDict(JSONObject dict) {
        String queryString = "";

        // System.out.println("Internal: " + dict);

        ArrayList<String> queryArray = new ArrayList<>();
        Iterator<String> keys = dict.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object valObj = dict.opt(key);
            String val = (valObj != null ? valObj.toString().replace("\"","") : "");

            System.out.println("Key: " + key + " valObj: " + valObj + " val: " + val);

            queryArray.add(key + "=" + val + "&");
        }

        for (String queryObj : queryArray) {
            queryString += queryObj;
        }

        if (queryString.length() > 1) {
            return queryString.substring(0, queryString.length() - 1);
        } else {
            return queryString;
        }
    }

    /**
     * Function that encodes a dict
     * @param dict
     */
    public static String encodeDictAsJsonDict(JSONObject dict) {
        String dictString = "";

        Iterator<String> keys = dict.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object valObj = dict.opt(key);
            String val = (valObj != null ? valObj.toString() : "");
            if (SAUtils.isInteger(val)){
                dictString += "\"" + key + "\":" + Integer.parseInt(val) + ",";
            } else  {
                dictString += "\"" + key + "\":\"" + val.replace("\"","") + "\",";
            }
        }

        if (dictString.length() > 0) {
            dictString = "{" + dictString;
            dictString = dictString.substring(0, dictString.length() - 1);
            dictString += "}";
            dictString = dictString.replace("\"","%22");
            dictString = dictString.replace("}","%7D");
            dictString = dictString.replace("{","%7B");
            dictString = dictString.replace(":","%3A");
            dictString = dictString.replace(",","%2C");
            return dictString;
        } else  {
            return dictString;
        }
    }

    /**
     * checks if an URL is valid
     * @param url - the url in question
     * @return true if valid, false otherwise
     */
    public static boolean isValidURL(String url){
        if (url == null) return false;
        if (url.equals("http://")) return false;
        if (url.equals("https://")) return false;
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public static String encodeURL(String urlStr) throws URISyntaxException, MalformedURLException {
        URL url = new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());

        return uri.toString();
    }
}
