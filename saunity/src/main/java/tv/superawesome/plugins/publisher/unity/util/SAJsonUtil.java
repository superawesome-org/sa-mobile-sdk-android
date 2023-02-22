package tv.superawesome.plugins.publisher.unity.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SAJsonUtil {

    /**
     * Create a JSON object more elegantly
     *
     * @param args an array of key-value pairs given as
     *             "key1", val1, "key2", val2
     * @return a valid JSONObject (or an empty one)
     */
    public static JSONObject newJsonObject(Object... args) {

        // create a new json Object
        JSONObject jsonObject = new JSONObject();

        // exit immediately
        if (args == null || args.length == 0) return jsonObject;

        // go through it, two at a time
        for (int i = 0; i < args.length; i += 2) {

            Object key = null;

            try {
                key = args[i];
            } catch (IndexOutOfBoundsException e) {
                // do nothing
            }

            Object val = null;
            try {
                val = args[i + 1];
            } catch (IndexOutOfBoundsException e) {
                // do nothing
            }

            // if all's well, then try to put it
            if (key instanceof String && val != null) {
                put(jsonObject, (String) key, val);
            }

        }

        // return the json
        return jsonObject;
    }

    /**
     * Function that safely puts a value in a Json object
     *
     * @param jsonObject the target Json object
     * @param key        the key under which to put the target object
     * @param object     the actual target object
     */
    public static void put(JSONObject jsonObject, String key, Object object) {
        // checks
        if (jsonObject == null || key == null) return;

        // if current object is null the just add a standard NULL to the JSON
        if (object == null) {
            try {
                jsonObject.put(key, JSONObject.NULL);
            } catch (JSONException ignored) {
                // do nothing
            }
        }
        // if it's any other object just add it
        else {
            try {
                jsonObject.put(key, object);
            } catch (JSONException ignored) {
                // do nothing
            }
        }
    }

    public static Map<String, Object> JSONtoMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            map.put(key, value);
        }
        return map;
    }
}
