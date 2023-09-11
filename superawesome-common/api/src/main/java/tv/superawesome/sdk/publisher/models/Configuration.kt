package tv.superawesome.sdk.publisher.models

import tv.superawesome.sdk.publisher.network.Environment

/**
 * AwesomeAds configuration.
 */
public interface Configuration {
    /**
     * Environment for AA.
     */
    public val environment: Environment

    /**
     * Activate logging.
     */
    public val logging: Boolean
}
