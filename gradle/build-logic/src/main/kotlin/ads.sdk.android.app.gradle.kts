import buildlogic.Versions
import buildlogic.androidConfig
import buildlogic.configureKotlinAndroid

plugins {
    id("com.android.application")
    id("kotlin-android")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    androidConfig()
    configureKotlinAndroid(this)

    defaultConfig {
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.isFork = true
        options.forkOptions.memoryMaximumSize = "2G"
    }
    tasks.withType(Test::class) {
        maxParallelForks = 4
    }
}
