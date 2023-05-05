package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.content.pm.PackageManager
import tv.superawesome.sdk.publisher.common.models.Platform
import java.util.Locale
import java.util.Properties

/**
 * Technical information about the AwesomeAds Publisher SDK, such as name and version number
 */
interface SdkInfoType {
    /** Returns the combined version information platform + version number
     * e.g. android_x.y.z */
    val version: String

    /** Returns the version number only
     * e.g. x.y.z */
    val versionNumber: String

    /** Returns the bundle name for the app */
    val bundle: String

    /** Returns the name of the app */
    val name: String

    /** Returns the preferred locale language and region
     * e.g. en_UK */
    val lang: String
}

class SdkInfo(
    private val context: Context,
    private val encoder: EncoderType,
    locale: Locale
) : SdkInfoType {
    private object Keys {
        const val Unknown = "unknown"
        const val VersionFile = "newVersion.properties"
        const val VersionName = "version.name"

        val storedVersion = loadVersion()

        /**
         * Method to load the current version from version.properties.
         *
         * @return the stored string representing the current version
         */
        private fun loadVersion(): String {
            val inputStream = javaClass.classLoader?.getResourceAsStream(VersionFile) ?: return ""
            val properties = Properties()
            properties.load(inputStream)
            return properties.getProperty(VersionName) ?: ""
        }
    }

    override val version: String
        get() = overrideVersion ?: "${Platform.Android.value}_${Keys.storedVersion}"

    override val versionNumber: String
        get() = overrideVersionNumber ?: Keys.storedVersion

    override val bundle: String = context.packageName ?: Keys.Unknown
    override val name: String by lazy { findAppName() }
    override val lang: String = locale.toString()

    private fun findAppName(): String = try {
        val label = context.packageManager.getApplicationLabel(context.applicationInfo).toString()
        encoder.encodeUri(label)
    } catch (exception: PackageManager.NameNotFoundException) {
        Keys.Unknown
    }

    companion object {
        private var overrideVersion: String? = null
        private var overrideVersionNumber: String? = null

        fun overrideVersionPlatform(version: String?, platform: Platform?) {
            if (version != null && platform != null) {
                overrideVersion = "${platform.value}_$version"
                overrideVersionNumber = version
            } else {
                overrideVersion = null
                overrideVersionNumber = null
            }
        }
    }
}
