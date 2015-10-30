/**
 * @class: SASerializer.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Parser;

/**
 * Imports needed for this implementation
 */
import com.google.gson.JsonObject;
import tv.superawesome.sdk.data.Models.SAAd;

/**
 * @brief: This class transforms an SAAd object into a serializable object (JsonObject)
 */
public class SASerializer {

    /**
     * @brief: this function thakes a SA SDK Ad object and takes it's most important three features
     * (campaignId, placementId, lineItemId) and transforms them into a JsonObject
     * @param ad - the Ad we want to serialize
     * @return - returns a JsonObject
     */
    public static JsonObject serializeAdEssentials(SAAd ad) {

        JsonObject dict = new JsonObject();

        if (ad != null) {
            dict.add("placement", ad.placementId);
            dict.add("line_item", ad.lineItemId);
            dict.add("creative", ad.creative.creativeId);
        }

        return dict;
        
    }

}
