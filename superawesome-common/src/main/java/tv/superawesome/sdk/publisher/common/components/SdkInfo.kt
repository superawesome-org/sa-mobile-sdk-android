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
    private object Keys {
        const val unknown = "unknown"
        const val platform = "android"
        val storedVersion = loadVersion()

        /**
         * Method to load the current version from version.properties.
         *
         * @return the stored string representing the current version
         */

        fun loadVersion(): String {
            val properties = Properties()
            val inputStream = javaClass.classLoader?.getResourceAsStream("version.properties")
            return if(inputStream != null) {
                properties.load(inputStream)
                properties?.getProperty("version.name") ?: ""
            } else {
                ""
            }
        }
    }

    override val version: String
        get() = overrideVersion ?: "${Keys.platform}_${Keys.storedVersion}"
    override val bundle: String = context.packageName ?: Keys.unknown
    override val name: String by lazy { findAppName() }
    override val lang: String = locale.toString()

    private fun findAppName(): String = try {
        val label = context.packageManager.getApplicationLabel(context.applicationInfo).toString()
        encoder.encodeUri(label)
    } catch (exception: PackageManager.NameNotFoundException) {
        Keys.unknown
    }

    companion object {
        var overrideVersion: String? = null
        var overridePlatform: String? = null
    }
}
