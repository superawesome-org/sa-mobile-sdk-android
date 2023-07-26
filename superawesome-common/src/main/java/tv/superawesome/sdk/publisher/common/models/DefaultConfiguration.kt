package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.network.Environment

/**
 * Default AwesomeAds configuration.
 *
 * @property environment the environment for AA.
 * @property logging activate logging.
 */
public data class DefaultConfiguration(
    override val environment: Environment = Constants.defaultEnvironment,
    override val logging: Boolean = false,
) : Configuration
