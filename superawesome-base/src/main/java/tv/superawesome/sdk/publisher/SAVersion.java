package tv.superawesome.sdk.publisher;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SAVersion {

    private static String versionOverride = "";
    private static String sdk = "android";

    /**
     * Getter for the current version.
     * If the version override is empty (not set) then load the version from the properties file.
     *
     * @param context The context
     *
     * @return string representing the current version
     */
    private static String getVersion(Context context) {
        try {
            return versionOverride.isEmpty() ? loadVersion(context) : versionOverride;
        }
        catch(IOException ex){
            System.out.println("Could not load the version" + ex);
            return "";
        }
    }

    /**
     * Method to load the current version from version.properties.
     *
     * @param context The context
     *
     * @return the stored string representing the current version
     */

    private static String loadVersion(Context context) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = context.getAssets().open("version.properties");
        properties.load(inputStream);
        return properties.getProperty("version.name");
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
     * @param context The context
     */
    public static String getSDKVersion(String pluginName, Context context) {
        String pluginFormatted = pluginName != null ? String.format("_%s", pluginName) : "";
        return String.format("%s_%s%s", getSdk(), getVersion(context), pluginFormatted);
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
