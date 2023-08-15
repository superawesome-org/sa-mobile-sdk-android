import buildlogic.androidConfig
import buildlogic.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("ads.sdk.kotlin-quality")
    id("ads.sdk.jacoco")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

group = "sa.android"

android {
    androidConfig()
    configureKotlinAndroid(this)
}

subprojects {
    tasks.withType(JavaCompile::class) {
        options.isFork = true
        options.forkOptions.memoryMaximumSize = "2G"
    }
    tasks.withType(Test::class) {
        maxParallelForks = 4
    }
}
