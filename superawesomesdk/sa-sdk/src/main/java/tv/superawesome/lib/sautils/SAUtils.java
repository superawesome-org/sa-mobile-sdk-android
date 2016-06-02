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
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.View;

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
     * Generate a Unique Key
     * @return a unique key string
     */
    public static String generateUniqueKey () {
        /** constants */
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY0123456789";
        final int length = alphabet.length();
        final int dauLength = 32;

        /** generate the string */
        String s = "";
        for (int i = 0; i < dauLength; i++){
            int index = SAUtils.randomNumberBetween(0, length - 1);
            s += alphabet.charAt(index);
        }
        return s;
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
     * Function that returns the current screen size
     * @param activity - the activity to pass along as context
     * @return a SASize object with width & height members
     */
    public static SASize getRealScreenSize(Activity activity, boolean rotate) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();

        View decorView = activity.getWindow().getDecorView();

        if (!rotate){
            return new SASize(decorView.getWidth(), decorView.getHeight());
        } else {
            return new SASize(decorView.getHeight(), decorView.getWidth());
        }
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
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);

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

    /**
     * Function that gets the app name
     * @return the app name
     */
    public static String getAppLabel() {
        Context pContext = SAApplication.getSAApplicationContext();
        PackageManager lPackageManager = pContext.getPackageManager();
        ApplicationInfo lApplicationInfo = null;
        try {
            lApplicationInfo = lPackageManager.getApplicationInfo(pContext.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }
        String name = (String) (lApplicationInfo != null ? lPackageManager.getApplicationLabel(lApplicationInfo) : "Unknown");
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
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

    /**
     * Function that checks at run-time if a class is loaded or not
     * @param className
     * @return
     */
    public static boolean isClassAvailable(String className){
        boolean driverAvailable = true;

        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            driverAvailable = false;
        }

        return driverAvailable;
    }

    /**
     * Private SASize object
     */
    public static class SASize {
        public int width = 0;
        public int height = 0;

        SASize (int w, int h){
            width = w;
            height = h;
        }
    }
}
