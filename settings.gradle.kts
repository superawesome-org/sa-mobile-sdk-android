@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral() 
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://android-sdk.is.com/") }
        maven { url = uri("https://aa-sdk.s3-eu-west-1.amazonaws.com/android_repo") }
    }
}

rootProject.name = "sa-mobile-sdk-android"

includeBuild("gradle/build-logic")

include(":app")
include(":saunity")
include(":saunity-common")
include(":saadmob")
include(":saadmob-common")
include(":superawesome")
include(":superawesome-base")
include(":superawesome-common")
include(":superawesome-common:internal")
include(":superawesome-common:api")
include(":examples:superawesome-compose")
include(":examples:superawesome-ironsource")
