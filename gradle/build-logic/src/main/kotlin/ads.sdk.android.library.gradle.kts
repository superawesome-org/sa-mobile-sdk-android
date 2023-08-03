import buildlogic.androidConfig
import buildlogic.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("ads.sdk.kotlin-quality")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

group = "sa.android"

android {
    androidConfig()
    configureKotlinAndroid(this)
}
