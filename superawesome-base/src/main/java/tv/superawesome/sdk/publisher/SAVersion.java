package tv.superawesome.sdk.publisher;

public class SAVersion {

    // version & sdk private vars
    private static String version = "8.1.5";
    private static String sdk = "android";

    /**
     * Getter for the current version
     *
     * @return string representing the current version
     */
    private static String getVersion() {
        return version;
    }

    /**
     * Getter for the current SDK
     *
     * @return string representing the current SDK
     */
    private static String getSdk() {
        return sdk;
    }

    /**
     * Getter for a string comprising of SDK & version bundled
     *
     * @return  a string
     * @param pluginName The name of the plugin
     */
    public static String getSDKVersion(String pluginName) {
        String pluginFormatted = pluginName != null ? String.format("_%s", pluginName) : "";
        return String.format("%s_%s%s", getSdk(), getVersion(), pluginFormatted);
    }

    /**
     * Method that overrides the current version string. It's used by the AIR & Unity SDKs
     *
     * @param version the new version
     */
    public static void overrideVersion (String version) {
        SAVersion.version = version;
    }

    /**
     * Method that overrides the current sdk string. It's used by the AIR & Unity SDKs
     *
     * @param sdk the new sdk
     */
    public static void overrideSdk (String sdk) {
        SAVersion.sdk = sdk;
    }
}
