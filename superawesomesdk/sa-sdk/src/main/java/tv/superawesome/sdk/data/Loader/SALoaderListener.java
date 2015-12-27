/**
 * @class: SALoaderListener.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */
package tv.superawesome.sdk.data.Loader;

/**
 * Imports for the interface definition
 */
import tv.superawesome.sdk.data.Models.SAAd;

/**
 * Created by gabriel.coman on 30/10/15.
 */
public interface SALoaderListener {

    /**
     * After SALoader pre-loads an Ad, this is the function that should be called
     * @param ad - sends back the Ad object that was loaded
     */
    void didLoadAd(SAAd ad);

    /**
     * After SALoader fails to pre-loads an Ad, this is the function that should be called
     * @param placementId - sends back the Ad's placement Id
     */
    void didFailToLoadAdForPlacementId(int placementId);

}
