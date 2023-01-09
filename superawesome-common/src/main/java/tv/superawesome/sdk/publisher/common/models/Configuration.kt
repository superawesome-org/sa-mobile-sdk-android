package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.network.Environment

data class Configuration(
    val environment: Environment = Constants.defaultEnvironment,
    val logging: Boolean = false
)