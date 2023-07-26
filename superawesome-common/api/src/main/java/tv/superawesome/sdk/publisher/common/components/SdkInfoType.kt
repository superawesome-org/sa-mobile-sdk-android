package tv.superawesome.sdk.publisher.common.components

/**
 * Technical information about the AwesomeAds Publisher SDK, such as name and version number.
 */
public interface SdkInfoType {
    /**
     * Returns the combined version information platform + version number.
     * e.g. android_x.y.z
     */
    public val version: String

    /**
     * Returns the version number only.
     * e.g. x.y.z
     */
    public val versionNumber: String

    /** Returns the bundle name for the app. */
    public val bundle: String

    /** Returns the name of the app. */
    public val name: String

    /**
     * Returns the preferred locale language and region.
     * e.g. en_UK
     */
    public val lang: String
}
