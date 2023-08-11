package tv.superawesome.sdk.publisher.common.network

/**
 * The possible AwesomeAds environments.
 *
 * @property baseUrl the url for the environment endpoints.
 */
enum class Environment(val baseUrl: String) {
    /** Production. */
    Production("https://ads.superawesome.tv/v2/"),
    /** Staging. */
    Staging("https://ads.staging.superawesome.tv/v2/"),
    /** UI testing. */
    UITesting("http://localhost:8080");
}
