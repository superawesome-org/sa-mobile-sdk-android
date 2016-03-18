/**
 * @class: SAUtils.java
 * @package: tv.superawesome.sdk.aux
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */

/**
 * packaged and imports for this class
 */
package tv.superawesome.lib.sautils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class that contains a lot of static aux functions
 */
public class SAUtils {

    /**
     * system size - mobile or tablet basically
     */
    public enum SASystemSize {
        undefined {
            @Override
            public String toString() {
                return "undefined";
            }
        },
        mobile {
            @Override
            public String toString() {
                return "mobile";
            }
        },
        tablet {
            @Override
            public String toString() {
                return "tablet";
            }
        }
    }

    /**********************************************************************************************/
    /** GENERAL Aux Functions */
    /**********************************************************************************************/

    /**
     * Function that returns a random number between two limits
     * @param min - min edge
     * @param max - max edge
     * @return a random integer
     */
    public static int randomNumberBetween(int min, int max){
        Random rand  = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Function that does the math to transform a pair of Width x Height into a new pair
     * of Width x Height by also maintaing aspect ratio
     * @param newW - new Height
     * @param newH - new Width
     * @param oldW - source Width
     * @param oldH - source Height
     * @return a rect with X, Y, W, H
     */
    public static Rect mapOldSizeIntoNewSize(float newW, float newH, float oldW, float oldH) {
        if (oldW == 1 || oldW == 0) { oldW = newW; }
        if (oldH == 1 || oldH == 0) { oldH = newH; }
        float oldR = oldW / oldH;
        float newR = newW / newH;
        float X = 0, Y = 0, W = 0, H = 0;

        if (oldR > newR) {
            W = newW; H = W / oldR; X = 0; Y = (newH - H) / 2.0f;
        }
        else {
            H = newH; W = H * oldR; Y = 0; X = (newW - W) / 2.0f;
        }

        return new Rect((int)X, (int)Y, (int)W, (int)H);
    }

    /**
     * Remove all elements from a list, except the first
     * @param original - the original list
     * @return - a list with just one element, the first one of the original
     */
    public static List removeAllButFirstElement(List original) {
        if (original.size() >= 1) {
            List finalList = new ArrayList<>();
            finalList.add(original.get(0));
            return finalList;
        } else  {
            return original;
        }
    }

    /**********************************************************************************************/
    /** SYSTEM RESOURCES Aux Functions */
    /**********************************************************************************************/

    /**
     * Get an .txt/.html file from the Assets folder and return a String with its content
     * @param assetPath - the path to the asset
     * @return - the string contents
     */

    public static String openAssetAsString(String assetPath) throws IOException {
        /** create the input streams and all that stuff */
        StringBuilder builder = new StringBuilder();
        InputStream text = SAApplication.getSAApplicationContext().getAssets().open(assetPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(text, "UTF-8"));

        /** go through all the file and append */
        String str;
        while ((str = reader.readLine()) != null) {
            builder.append(str);
        }

        /** close the reader */
        reader.close();

        /** return the new string */
        return builder.toString();
    }


    /**
     * Dynamically returns a resource Id
     * @param name the name of the resource
     * @param type the type of the resource
     * @param context the context
     * @return returns the actual ID or 0
     */
    public static int getResourceIdByName(String name, String type, Activity context){
        if (SAApplication.getSAApplicationContext() != null){
            String packageName = SAApplication.getSAApplicationContext().getPackageName();
            return context.getResources().getIdentifier(name, type, packageName);
        } else {
            return 0;
        }
    }

    /**
     * Returns a string by name
     * @param name the name of the string
     * @param context the current context
     * @return the String
     */
    public static String getStringByName(String name, Activity context){
        int id = getResourceIdByName(name, "string", context);
        return context.getResources().getString(id);
    }

    /**
     * Get the current scale factor
     * @param activity - the activity to pass along as context
     * @return a float meaning the scale
     */
    public static float getScaleFactor(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return  (float) metrics.densityDpi / (float) DisplayMetrics.DENSITY_DEFAULT;
    }

    /**
     * function that gets the system size
     * @return where either tablet or mobile
     */
    public static SASystemSize getSystemSize() {

        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double)width/(double)dens;
        double hi = (double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);

        if (screenInches < 6){
            return SASystemSize.mobile;
        } else {
            return SASystemSize.tablet;
        }
    }

    /**
     * just return the verbose system
     * @return a string
     */
    public static String getVerboseSystemDetails() {
        return "android" + "_" + getSystemSize().toString();
    }

    /**********************************************************************************************/
    /** NETWORK Aux Functions */
    /**********************************************************************************************/

    /** constants */
    private static final String iOS_Mobile_UserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25";
    private static final String iOS_Tablet_UserAgent = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

    /**
     * Get the User agent based on android size (mobile or tablet)
     * @return a user agent string
     */
    public static String getUserAgent() {
        if (getSystemSize() == SASystemSize.tablet) {
            return iOS_Tablet_UserAgent;
        }
        return iOS_Mobile_UserAgent;
    }

    /**
     * Cache buster is just a big random number
     */
    public static int getCacheBuster() {
        return randomNumberBetween(1000000, 1500000);
    }

    /**
     * Function that forms a query string from a dict
     * @param dict
     */
    public static String formGetQueryFromDict(JSONObject dict) {
        String queryString = "";

        ArrayList<String> queryArray = new ArrayList<>();
        Iterator<String> keys = dict.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object valObj = dict.opt(key);
            String val = (valObj != null ? valObj.toString().replace("\"","") : "");

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
        try {
            return URLEncoder.encode(dict.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * return true if json is empty, false otherwise
     * @param dict a json dict
     */
    public static boolean isJSONEmpty(JSONObject dict) {
        if (dict == null) return true;
        if (dict.length() == 0) return true;
        if (dict.toString().equals("{}")) return true;
        return false;
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

    /**********************************************************************************************/
    /** NETWORK Get Request */
    /**********************************************************************************************/

    /**
     * Execute a get request
     * @param urlString - the URL to send the request to
     * @return a String object containing net data
     * @throws IOException
     */
    public static String syncGet(String urlString) throws IOException {

        URL url;
        String response = null;
        try {
            url = new URL(urlString);

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", getUserAgent());

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
    }

    /**
     * More manageable asyncGet function, based on the generic SAASyncTask class
     * @param urlString - the URL to make the request to
     * @param listener - the listener
     */
    public static void asyncGet(String urlString, JSONObject querydict, final SANetListener listener) {

        /** form the actual final endpoint */
        final String endpoint = urlString + (!SAUtils.isJSONEmpty(querydict) ? "?" + SAUtils.formGetQueryFromDict(querydict) : "");

        /** create a new SAAsync Task */
        SAAsyncTask task = new SAAsyncTask(SAApplication.getSAApplicationContext(), new SAAsyncTask.SAAsyncTaskListener() {
            @Override
            public Object taskToExecute() throws Exception {
                return syncGet(endpoint);
            }

            @Override
            public void onFinish(Object result) {
                listener.success(result);
            }

            @Override
            public void onError() {
                listener.failure();
            }
        });
    }

    /**
     * *********************************************************************************************
     * This is a listener interface for SAGet and SAPost async task classes
     * *********************************************************************************************
     */
    public interface SANetListener {

        /**
         * This function should be called in case of Async operation success, and should
         * always return an anonymous data object
         *
         * @param data - is a callback parameter; to be accessed by the class that implements
         * this Listener interface
         */
        void success(Object data);

        /**
         * This function should be called in case of Async operation failure, and
         * should have no parameters
         */
        void failure();

    }
}
