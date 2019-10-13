/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sajsonparser;

import org.json.JSONObject;

/**
 * Interface that defines all the methods needed to be able to "describe" a JSON object to a
 * proper Java model and vice-versa
 */
public interface SAJsonSerializable {

    /**
     * This method, when implemented, will have to describe how all the JSON fields map out to
     * model fields
     *
     * @param json a valid JSON object
     */
    void readFromJson (JSONObject json);

    /**
     * This method, when implemented, will have to describe how all the model fields map out
     * to JSON fields
     *
     * @return a valid Json Object
     */
    JSONObject writeToJson ();

    /**
     * This method, when implemented, will have to describe what the conditions for model
     * validity are
     *
     * @return true or false
     */
    boolean isValid ();

}
