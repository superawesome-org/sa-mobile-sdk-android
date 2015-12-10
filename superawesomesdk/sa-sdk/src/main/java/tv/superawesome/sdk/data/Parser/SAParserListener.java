/**
 * @class: SAParserListner.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Parser;

/**
 * Imports needed for this Listner
 */
import tv.superawesome.sdk.data.Models.SAAd;

/**
 * Created by gabriel.coman on 10/12/15.
 */
public interface SAParserListener {
    /**
     * @brief Callback function that's called when an ad has been parsed OK
     * @param ad
     */
    public void parsedAd(SAAd ad);
}
