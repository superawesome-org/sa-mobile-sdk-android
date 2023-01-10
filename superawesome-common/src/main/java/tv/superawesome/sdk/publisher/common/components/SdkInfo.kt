package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.content.pm.PackageManager
import java.util.*

interface SdkInfoType {
    val version: String
    val versionNumber: String
    val bundle: String
    val name: String
    val lang: String
}

class SdkInfo(
    private val context: Context,
    private val encoder: EncoderType,
    locale: Locale
) : SdkInfoType {
    private object Keys {
        const val Unknown = "unknown"
        const val Platform = "android"
        const val VersionFile = "version.properties"
        const val VersionName = "version.name"

        val storedVersion = loadVersion()

        /**
         * Method to load the current version from version.properties.
         *
         * @return the stored string representing the current version
         */
        fun loadVersion(): String {
            val inputStream = javaClass.classLoader?.getResourceAsStream(VersionFile) ?: return ""
            val properties = Properties()
            properties.load(inputStream)
            return properties.getProperty(VersionName) ?: ""
        }
    }

    override val version: String
        get() = overrideVersion ?: "${Keys.Platform}_${Keys.storedVersion}"
    override val versionNumber: String
        get() = overrideVersion ?: Keys.storedVersion
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
        var overrideVersion: String? = null
    }
}
