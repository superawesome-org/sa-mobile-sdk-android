import buildlogic.Namespace

plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Base Mobile SDK for Android")
    set("libraryName", "SuperAwesome Base")
}

android {
    namespace = "${Namespace.sdk.publisher}.base"
    resourcePrefix = "superawesome__"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint.abortOnError = false
    testOptions.animationsDisabled = true
    testOptions.unitTests.isReturnDefaultValues = true
}

dependencies {
    implementation (libs.androidx.annotation)

    // Tests
    testImplementation (libs.junit4)
    testImplementation (libs.mockito.core)
    testImplementation (libs.catch.exception)
    testImplementation (libs.assertj.core)
    testImplementation (libs.mockwebserver)
    testImplementation (libs.jsonassert)
    testImplementation (libs.mockk)

    androidTestImplementation (libs.androidx.test.ext.junit)
    androidTestImplementation (libs.androidx.test.rules)
    androidTestImplementation (libs.androidx.test.espresso.core)
    androidTestImplementation (libs.androidx.test.espresso.idling.resource)
    androidTestImplementation (libs.androidx.test.espresso.contrib)
    androidTestImplementation (libs.mockwebserver)
}
