/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.saadloader;

import tv.superawesome.lib.samodelspace.saad.SAResponse;

/**
 * Interface used by the SALoader class to send events back to the library users when an ad
 * has finally been fully downloaded and processed
 */
public interface SALoaderInterface {

    /**
     * Method that needs to be implemented in order for the loader to be able to send back
     * a callback
     *
     * @param response an object of type SAResponse
     */
    void saDidLoadAd (SAResponse response);

}