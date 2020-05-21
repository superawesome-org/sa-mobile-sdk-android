package tv.superawesome.sdk.publisher.common.network

enum class Environment {
    production,
    staging;

    val baseUrl: String
        get() = when (this) {
            production -> "https://ads.superawesome.tv/v2"
            staging -> "https://ads.staging.superawesome.tv/v2"
        }
}
