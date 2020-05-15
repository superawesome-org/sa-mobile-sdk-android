package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.BuildConfig
import java.util.*


interface SdkInfoType {
    val version: String
    val bundle: String
    val name: String
    val lang: String
}

class SdkInfo: SdkInfoType {
    private val platform = "android"
    private val versionNumber = BuildConfig.VERSION_NAME

    override val version: String = "${platform}_${versionNumber}"
    override val bundle: String = ""
    override val name: String = ""
    override val lang: String =  Locale.getDefault().toString()
}
