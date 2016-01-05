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

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import tv.superawesome.lib.sanetwork.SAApplication;

/**
 * Class that contains a lot of static aux functions
 */
public class SAUtils {

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
     * Function that checks if the value of an string is actually integer
     * @param s
     * @return boolean
     */
    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

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
     * Load a resource in a more dynamic way - good for .aar and .jar compatibility
     * @param packageName - the current package name, usually context.getPackageName() or
     *                    currentAppContext.getPackageName()
     * @param className - the class name - layout, drawable, etc
     * @param name - the name of the resource
     * @return - the integer ID, as stored in the final project's R class
     */
    public static int getResourceIdByName(String packageName, String className, String name) {
        Class r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");

            Class[] classes = r.getClasses();
            Class desireClass = null;

            for (int i = 0; i < classes.length; i++) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];

                    break;
                }
            }

            if (desireClass != null) {
                id = desireClass.getField(name).getInt(desireClass);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return id;
    }
}
