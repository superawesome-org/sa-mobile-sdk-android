package tv.superawesome.sdk.publisher.network

/**
 * The possible AwesomeAds environments.
 *
 * @property baseUrl the url for the environment endpoints.
 */
public enum class Environment(public val baseUrl: String) {
    /** Production. */
    Production("https://ads.superawesome.tv/v2/"),
    /** Staging. */
    Staging("https://ads.staging.superawesome.tv/v2/"),
    /** UI testing. */
    UITesting("http://localhost:8080");
}
