package tv.superawesome.sdk.publisher.components

import android.content.Context
import android.content.pm.PackageManager
import tv.superawesome.sdk.publisher.models.Platform
import java.util.Locale
import java.util.Properties

/**
 * Provides information about the SDK such as version, name, and more.
 */
public class SdkInfo(
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

    @Suppress("SwallowedException")
    private fun findAppName(): String = try {
        val label = context.packageManager.getApplicationLabel(context.applicationInfo).toString()
        encoder.encodeUri(label)
    } catch (exception: PackageManager.NameNotFoundException) {
        Keys.Unknown
    }

    public companion object {
        private var overrideVersion: String? = null
        private var overrideVersionNumber: String? = null

        /**
         * Overrides the [SDKInfo] version.
         */
        @JvmStatic
        public fun overrideVersionPlatform(version: String?, platform: Platform?) {
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
