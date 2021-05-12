@file:Suppress("RedundantVisibilityModifier", "unused")

package tv.superawesome.sdk.publisher.common.components

public object Version {
    private const val version = "8.0.4"
    private const val sdk = "android"

    public fun getSDKVersion(pluginName: String? = null): String =
        "${sdk}_$version${if (pluginName != null) "_$pluginName" else ""}"
}
