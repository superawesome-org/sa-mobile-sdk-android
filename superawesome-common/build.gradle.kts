import buildlogic.Namespace
import buildlogic.addTestDependencies

plugins {
    id("ads.sdk.android.library")
    id("kotlinx-serialization")
}

apply(from = "../publish.gradle")

android {
    namespace = "${Namespace.sdk.publisher}.common"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlin {
        explicitApiWarning()

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}

dependencies {
    api(project(":superawesome-common:api"))
    implementation(project(":superawesome-common:internal"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.properties)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.koin.android)
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    // Unit Testing
    addTestDependencies(project)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.mockwebserver)
    testImplementation(testFixtures(project(":superawesome-common:test-fixtures")))
    testImplementation(libs.roboelectric)
}
