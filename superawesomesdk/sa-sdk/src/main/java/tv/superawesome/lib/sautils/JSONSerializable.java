package tv.superawesome.lib.sautils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gabriel.coman on 12/05/16.
 */
public interface JSONSerializable <T>{

    /**
     * Read from a JSON
     * @param json a valid JSON string
     * @throws JSONException
     */
    public void readFromJson(JSONObject json);

    /**
     * Write to Json
     * @return a valid Json String
     */
    public JSONObject writeToJson();

}
