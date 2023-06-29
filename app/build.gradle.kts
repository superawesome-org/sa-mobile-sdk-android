plugins {
    id("ads.sdk.android.app")
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
}

android {
    defaultConfig {
        applicationId = "tv.superawesome.demoapp"

        testInstrumentationRunner = "tv.superawesome.demoapp.runner.UITestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        multiDexEnabled = true

        lint.abortOnError = false
        packagingOptions {
            resources.excludes.add("META-INF/*")
        }
    }
}

dependencies {
    implementation(project(":saadmob"))
    implementation(project(":superawesome-common"))
    implementation(project(":superawesome-base"))

    implementation(libs.google.play.services.ads)
    implementation(libs.material)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity)

    // Testing
    testImplementation(libs.junit4)

    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.espresso.intents)
    androidTestImplementation(libs.androidx.test.uiautomator)

    // Mock Server
    androidTestImplementation(libs.wiremock) {
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
    }

    androidTestImplementation(libs.httpcomponents.android)
}
