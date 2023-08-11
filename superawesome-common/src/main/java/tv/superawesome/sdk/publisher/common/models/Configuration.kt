package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.network.Environment

/**
 * AwesomeAds configuration.
 *
 * @property environment the environment for AA.
 * @property logging activate logging.
 */
data class Configuration(
    val environment: Environment = Constants.defaultEnvironment,
    val logging: Boolean = false
)
