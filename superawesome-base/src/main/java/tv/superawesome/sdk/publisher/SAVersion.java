package tv.superawesome.sdk.publisher;

import java.io.InputStream;
import java.util.Properties;

public class SAVersion {

    private static String version = "";
    private static String versionOverride = null;
    private static String sdk = "android";

    static {
        try {
            version = loadVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load the current version from version.properties.
     *
     * @return the stored string representing the current version
     */

    private static String loadVersion() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = SAVersion.class.getClassLoader().getResourceAsStream("version.properties");
        if (inputStream != null) {
            properties.load(inputStream);
            return properties.getProperty("version.name");
        } else {
            throw new Exception("Unable to load version");
        }
    }

    /**
     * Getter for the current SDK
     *
     * @return a string representing the current SDK
     */
    private static String getSdk() {
        return sdk;
    }

    /**
     * Getter for the current version number
     *
     * @return a string representing the current version number
     */
    public static String getSDKVersionNumber() {
        return versionOverride == null ? version : versionOverride;
    }

    /**
     * Getter for a string comprising of SDK & version bundled
     *
     * @return  a string
     * @param pluginName The name of the plugin
     */
    public static String getSDKVersion(String pluginName) {
        String pluginFormatted = pluginName != null ? String.format("_%s", pluginName) : "";
        return String.format("%s_%s%s", getSdk(), getSDKVersionNumber(), pluginFormatted);
    }

    /**
     * Method that sets the versionOverride string. It's used by the AIR & Unity SDKs
     *
     * @param version the new version
     */
    public static void overrideVersion (String version) {
        SAVersion.versionOverride = version;
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
