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
include(":superawesomecomposeexample")
