import buildlogic.Namespace
import buildlogic.addTestDependencies

plugins {
    id("ads.sdk.android.library")
    id("kotlinx-serialization")
}

android {
    namespace = "${Namespace.sdk.publisher}.common"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":superawesome-common:api"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.properties)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.serialization.converter)
    implementation(files("jni/omsdk-android-1.4.7-release.aar"))

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    // Unit Testing
    addTestDependencies(project)
    testImplementation(libs.mockwebserver)
    testImplementation(testFixtures(project(":superawesome-common:test-fixtures")))
}
