package tv.superawesome.sdk.publisher.common.models

/**
 * The current platform on which the app is running.
 *
 * @property value name of the platform.
 */
public enum class Platform(public val value: String) {
    /**
     * Android platform.
     */
    Android("android"),

    /**
     * Unity platform.
     */
    Unity("unity")
}
