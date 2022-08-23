package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.content.pm.PackageManager
import java.util.Locale
import java.util.Properties

interface SdkInfoType {
    val version: String
    val bundle: String
    val name: String
    val lang: String
}

class SdkInfo(
    private val context: Context,
    private val encoder: EncoderType,
    locale: Locale
) : SdkInfoType {
    private object VersionInfo {
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
        get() = overrideVersion ?: "${Platform}_${VersionInfo.storedVersion}"
    override val bundle: String = context.packageName ?: Unknown
    override val name: String by lazy { findAppName() }
    override val lang: String = locale.toString()

    private fun findAppName(): String = try {
        val label = context.packageManager.getApplicationLabel(context.applicationInfo).toString()
        encoder.encodeUri(label)
    } catch (exception: PackageManager.NameNotFoundException) {
        Unknown
    }

    companion object {
        var overrideVersion: String? = null
        var overridePlatform: String? = null
    }
}

const val Unknown = "unknown"
const val Platform = "android"
const val VersionFile = "version.properties"
const val VersionName = "version.name"