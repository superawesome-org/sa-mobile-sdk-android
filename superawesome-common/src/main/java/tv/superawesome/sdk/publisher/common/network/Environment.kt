package tv.superawesome.sdk.publisher.common.network

enum class Environment {
    Production,
    Staging,
    UITesting;

    val baseUrl: String
        get() = when (this) {
            Production -> "https://ads.superawesome.tv/v2/"
            Staging -> "https://ads.staging.superawesome.tv/v2/"
            UITesting -> "http://localhost:8080"
        }
}