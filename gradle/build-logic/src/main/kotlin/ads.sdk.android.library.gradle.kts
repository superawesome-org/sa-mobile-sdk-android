import buildlogic.androidConfig
import buildlogic.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("kotlin-android")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

group = "sa.android"

android {
    androidConfig()
    configureKotlinAndroid(this)
}
