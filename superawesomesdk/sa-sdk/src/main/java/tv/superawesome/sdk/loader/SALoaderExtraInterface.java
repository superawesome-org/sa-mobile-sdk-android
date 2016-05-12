package tv.superawesome.sdk.loader;

import tv.superawesome.sdk.models.SAAd;

/**
 * Interface for the Extra loader
 */
public interface SALoaderExtraInterface {
    /**
     * When all the data is fully loaded
     * @param finalAd - final Ad that's being sent to the user via callback
     */
    void extraDone(SAAd finalAd);
}