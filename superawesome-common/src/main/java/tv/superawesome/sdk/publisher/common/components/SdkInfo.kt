package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.content.pm.PackageManager
import java.util.Locale

interface SdkInfoType {
    val version: String
    val bundle: String
    val name: String
    val lang: String
}

class SdkInfo(
    private val context: Context,
    private val encoder: EncoderType,
    locale: Locale,
    private val versionNumber: String
) : SdkInfoType {
    object Keys {
        const val unknown = "unknown"
        const val platform = "android"
    }

    override val version: String
        get() = overrideVersion ?: "${Keys.platform}_$versionNumber"
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
