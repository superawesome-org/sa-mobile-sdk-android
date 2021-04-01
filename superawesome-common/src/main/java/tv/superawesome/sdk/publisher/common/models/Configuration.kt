package tv.superawesome.sdk.publisher.common.models

import tv.superawesome.sdk.publisher.common.network.Environment

data class Configuration(
        val environment: Environment = Environment.Production,
        val logging: Boolean = false,
)